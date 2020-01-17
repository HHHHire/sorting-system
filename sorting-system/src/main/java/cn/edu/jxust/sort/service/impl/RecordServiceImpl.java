package cn.edu.jxust.sort.service.impl;

import cn.edu.jxust.sort.entity.po.Enterprise;
import cn.edu.jxust.sort.entity.po.Inventory;
import cn.edu.jxust.sort.entity.po.Plan;
import cn.edu.jxust.sort.entity.po.Record;
import cn.edu.jxust.sort.repository.EnterpriseRepository;
import cn.edu.jxust.sort.repository.InventoryRepository;
import cn.edu.jxust.sort.repository.PlanRepository;
import cn.edu.jxust.sort.repository.RecordRepository;
import cn.edu.jxust.sort.service.RecordService;
import cn.edu.jxust.sort.util.RedisPoolUtil;
import cn.edu.jxust.sort.util.SerializeUtil;
import cn.edu.jxust.sort.util.common.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @author ddh
 * @date 2020/1/8 16:02
 * @description
 **/
@Service
public class RecordServiceImpl implements RecordService {
    private static final String FIRST_DAY_IN_MONTH = "01";
    private static final Integer WEEK = 7;
    private static final Integer YEAR = 12;

    private final RecordRepository recordRepository;
    private final InventoryRepository inventoryRepository;
    private final EnterpriseRepository enterpriseRepository;
    private final PlanRepository planRepository;
    private final RedisPoolUtil redisPoolUtil;

    @Autowired
    public RecordServiceImpl(RecordRepository recordRepository, EnterpriseRepository enterpriseRepository, RedisPoolUtil redisPoolUtil, InventoryRepository inventoryRepository, PlanRepository planRepository) {
        this.recordRepository = recordRepository;
        this.enterpriseRepository = enterpriseRepository;
        this.inventoryRepository = inventoryRepository;
        this.redisPoolUtil = redisPoolUtil;
        this.planRepository = planRepository;
    }

    @Override
    public Page<Record> getRecord(String enterpriseId, String categoryId, String startTime, String endTime, Pageable pageable) {
        Long start = analyticalStartTime(startTime);
        Long end = analyticalEndTime(endTime);
        if (categoryId == null) {
            return recordRepository.findByCreateTimeBetweenAndEnterpriseIdOrderByCreateTimeDesc(start, end, enterpriseId, pageable);
        } else {
            return recordRepository.findByCreateTimeBetweenAndEnterpriseIdAndCategoryIdOrderByCreateTimeDesc(start, end, enterpriseId, categoryId, pageable);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer createRecord(Record record) {
        Record save = recordRepository.save(record);
        // 更新规划中的已完成数量
        Plan plan = planRepository.findByEnterpriseIdAndSortPortId(record.getEnterpriseId(), record.getSortPortId()).orElse(null);
        if (plan != null) {
            plan.setCompleteCounts(plan.getCompleteCounts() + record.getCounts());
            plan.setUpdateTime(System.currentTimeMillis());
            planRepository.updatePlan(plan);
        }
        // 更新库存
        if (save != null) {
            List<Inventory> inventories = inventoryRepository.findByEnterpriseIdAndCategoryId(record.getEnterpriseId(), record.getCategoryId());
            if (inventories.size() > 1) {
                // 旧的类别还有货 更新旧的类别的库存
                Inventory inventory = inventoryRepository.findBylengthAndWeight(record.getEnterpriseId(), record.getCategoryLength(), record.getLengthTolerancePo(), record.getLengthToleranceNe(), record.getWeight(), record.getWeightTolerance()).orElse(null);
                if (inventory != null) {
                    Integer counts = inventoryRepository.findCountsById(record.getEnterpriseId(), inventory.getInventoryId());
                    // 如果更新库存后小于0，将库存置为0
                    if (counts != null && counts + record.getCounts() > 0) {
                        return inventoryRepository.updateInventoryById(record.getEnterpriseId(), inventory.getInventoryId(), counts + record.getCounts(), System.currentTimeMillis());
                    } else if (counts != null && counts + record.getCounts() <= 0) {
                        return inventoryRepository.updateInventoryById(record.getEnterpriseId(), inventory.getInventoryId(), 0, System.currentTimeMillis());
                    }
                }
            } else {
                Integer count = inventoryRepository.findCounts(record.getEnterpriseId(), record.getCategoryId());
                if (count != null) {
                    if (count + record.getCounts() > 0) {
                        return inventoryRepository.updateInventory(record.getEnterpriseId(), record.getCategoryId(), count + record.getCounts(), System.currentTimeMillis());
                    } else {
                        return inventoryRepository.updateInventory(record.getEnterpriseId(), record.getCategoryId(), 0, System.currentTimeMillis());
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public List<Record> getOutRecord(String enterpriseId) {
        return recordRepository.findOutRecord(enterpriseId);
    }

    @Override
    public List<Record> getOutRecordByEmployeeCard(String enterpriseId, String employeeCard, String startTime, String endTime) {
        Long start = analyticalStartTime(startTime);
        Long end = analyticalEndTime(endTime);
        return recordRepository.findOutRecordByEmployeeCard(enterpriseId, employeeCard, start, end);
    }

    @Override
    public List<Record> getInRecordByEmployeeCard(String enterpriseId, String employeeCard, String startTime, String endTime) {
        Long start = analyticalStartTime(startTime);
        Long end = analyticalEndTime(endTime);
        return recordRepository.findInRecordByEmployeeCard(enterpriseId, employeeCard, start, end);
    }



    @Override
    public Integer getOutputToday(String enterpriseId) {
        long nowTime = System.currentTimeMillis();
        long todayStartTime = nowTime - (nowTime + TimeZone.getDefault().getRawOffset()) % (1000 * 3600 * 24);
        return calcOutputToday(enterpriseId, todayStartTime, nowTime);
    }

    @Override
    public List<Integer> getOutputWeek(String enterpriseId) {
        long nowTime = System.currentTimeMillis();
        long startTime = nowTime - (nowTime + TimeZone.getDefault().getRawOffset()) % (1000 * 3600 * 24);
        List<Integer> outputWeek = new ArrayList<>();
        for (int i = 0; i < WEEK; i++) {
            String week = getWeek(nowTime);
            Integer value = (Integer) redisPoolUtil.getValueByte((enterpriseId + week).getBytes());
            if (value == null) {
                outputWeek.add(0);
            } else {
                outputWeek.add(value);
            }
            nowTime -= (1000 * 3600 * 24);
        }
        outputWeek.set(0, calcOutputToday(enterpriseId, startTime, System.currentTimeMillis()));
        return outputWeek;
    }

    @Override
    public List<Integer> getOutputYear(String enterpriseId) {
        long nowTime = System.currentTimeMillis();
        long startTime = nowTime - (nowTime + TimeZone.getDefault().getRawOffset()) % (1000 * 3600 * 24);
        List<Integer> outputMonth = new ArrayList<>();
        for (int i = 0; i < YEAR; i++) {
            String month = getMonth(nowTime);
            Integer value = (Integer) redisPoolUtil.getValueByte((enterpriseId + month).getBytes());
            if (value == null) {
                outputMonth.add(0);
            } else {
                outputMonth.add(value);
            }
            nowTime -= (1000L * 3600L * 24L * 30L);
        }
        if (FIRST_DAY_IN_MONTH.equals(getDate(nowTime))) {
            outputMonth.set(0, calcOutputToday(enterpriseId, startTime, nowTime));
        } else {
            outputMonth.set(0, outputMonth.get(0) + calcOutputToday(enterpriseId, startTime, nowTime));
        }
        return outputMonth;
    }

    @Override
    public Integer getWorkloadToday(String enterpriseId, String employeeCard) {
        long nowTime = System.currentTimeMillis();
        long startTime = nowTime - (nowTime + TimeZone.getDefault().getRawOffset()) % (1000 * 3600 * 24);
        return calcWorkloadToday(enterpriseId, employeeCard, startTime, nowTime);
    }

    @Override
    public List<Integer> getWorkloadWeek(String enterpriseId, String employeeCard) {
        long endTime = System.currentTimeMillis();
        long startTime = endTime - (endTime + TimeZone.getDefault().getRawOffset()) % (1000 * 3600 * 24);
        List<Integer> workload = new ArrayList<>();
        for (int i = 0; i < WEEK; i++) {
            workload.add(calcWorkloadToday(enterpriseId, employeeCard, startTime, endTime));
            endTime = startTime - 1;
            startTime -= (1000 * 3600 * 24);
        }
        return workload;
    }

    @Override
    public List<String> getWorkEffWeek(String enterpriseId, String employeeCard) {
        long endTime = System.currentTimeMillis();
        long startTime = endTime - (endTime + TimeZone.getDefault().getRawOffset()) % (1000 * 3600 * 24);
        List<String> workEff = new ArrayList<>();
        for (int i = 0; i < WEEK; i++) {
            workEff.add(String.format("%.4f", calcWorkEff(enterpriseId, employeeCard, startTime, endTime)));
            endTime = startTime - 1;
            startTime -= (1000 * 3600 * 24);
        }
        return workEff;
    }


    /***********************************************************************************************/

    private Long analyticalStartTime(String startTime) {
        if (startTime == null) {
            return Const.PROJECT_START_TIME;
        } else if (startTime.length() != Const.TIMESTAMP_LENGTH) {
            return null;
        } else {
            return Long.parseLong(startTime);
        }
    }

    private Long analyticalEndTime(String endTime) {
        if (endTime == null) {
            return System.currentTimeMillis();
        } else if (endTime.length() != Const.TIMESTAMP_LENGTH) {
            return null;
        } else {
            return Long.parseLong(endTime);
        }
    }

    /**
     * 计算产量(应该每进来一条记录，就更新到redis，后续完善)
     *
     * @param enterpriseId 企业 id
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @return Integer
     */
    private Integer calcOutputToday(String enterpriseId, Long startTime, Long endTime) {
        Integer output = 0;
        List<Record> inRecord = recordRepository.findInRecord(enterpriseId, startTime, endTime);
        for (Record record : inRecord) {
            output += record.getCounts();
        }
        return output;
    }

    /**
     * 计算工人工作量
     *
     * @param enterpriseId 企业 id
     * @param employeeCard 员工卡号
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @return Integer
     */
    private Integer calcWorkloadToday(String enterpriseId, String employeeCard, Long startTime, Long endTime) {
        Integer workload = 0;
        List<Record> records = recordRepository.findInRecordByEmployeeCard(enterpriseId, employeeCard, startTime, endTime);
        for (Record record : records) {
            workload += record.getCounts();
        }
        return workload;
    }

    /**
     * 计算工人效率
     *
     * @param enterpriseId 企业 id
     * @param employeeCard 员工卡号
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @return Double
     */
    private Double calcWorkEff(String enterpriseId, String employeeCard, Long startTime, Long endTime) {
        Integer workload = 0;
        List<Record> records = recordRepository.findInRecordByEmployeeCard(enterpriseId, employeeCard, startTime, endTime);
        for (Record record : records) {
            workload += record.getCounts();
        }

        if (records.size() > 0) {
            Long end = records.get(0).getCreateTime();
            Long start = records.get(records.size() - 1).getCreateTime();
            return (Double.valueOf(workload) / ((end - start) / (1000 * 60)));
        } else {
            return 0.0;
        }

    }

    /**
     * 定时保存每日的产量到 redis
     */
    @Scheduled(cron = "0 01 0 * * *")
    @Async
    @Override
    public void saveOutputToday() {
        long nowTime = System.currentTimeMillis();
        // 昨天结束时间
        long endTime = nowTime - (nowTime + TimeZone.getDefault().getRawOffset()) % (1000 * 3600 * 24) - 1;
        long startTime = endTime - 86399999;
        List<Enterprise> enterprises = enterpriseRepository.findAll();
        for (Enterprise enterprise : enterprises) {
            // 判断 redis 是否已经初始化过
            Object valueByte = redisPoolUtil.getValueByte((enterprise.getEnterpriseId() + nowTime).getBytes());
            if (valueByte == null) {
                initRedis(enterprise.getEnterpriseId(), nowTime);
            }

            Integer total = 0;
            Integer output = calcOutputToday(enterprise.getEnterpriseId(), startTime, endTime);
            // 保存当日数据
            redisPoolUtil.setValueByte((enterprise.getEnterpriseId() + getWeek(endTime)).getBytes(), SerializeUtil.serialize(output));
            if ("01".equals(getDate(endTime))) {
                redisPoolUtil.setValueByte((enterprise.getEnterpriseId() + getMonth(endTime)).getBytes(), SerializeUtil.serialize(total));
            }
            total = (Integer) redisPoolUtil.getValueByte((enterprise.getEnterpriseId() + getMonth(endTime)).getBytes());
            total += output;
            // 更新当月数据
            redisPoolUtil.setValueByte((enterprise.getEnterpriseId() + getMonth(endTime)).getBytes(), SerializeUtil.serialize(total));
        }
    }

    /**
     * 获取星期
     *
     * @param time 时间
     * @return String
     */
    private String getWeek(Long time) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        return format.format(date);
    }

    private String getMonth(Long time) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("MM");
        return format.format(date);
    }

    private String getDate(Long time) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("dd");
        return format.format(date);
    }

    /**
     * 初始化 redis
     *
     * @param enterpriseId 企业 id
     * @param time         时间
     */
    private void initRedis(String enterpriseId, Long time) {
        Long timeBack = time;
        for (int i = 0; i < WEEK; i++) {
            redisPoolUtil.setValueByte((enterpriseId + getWeek(time)).getBytes(), SerializeUtil.serialize(0));
            time -= 86400000;
        }
        for (int i = 0; i < YEAR; i++) {
            redisPoolUtil.setValueByte((enterpriseId + getMonth(timeBack)).getBytes(), SerializeUtil.serialize(0));
            timeBack -= 2592000000L;
        }
    }
}
