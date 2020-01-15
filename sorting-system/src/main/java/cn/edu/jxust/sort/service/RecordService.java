package cn.edu.jxust.sort.service;

import cn.edu.jxust.sort.entity.po.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author: ddh
 * @data: 2020/1/8 15:59
 * @description record service
 **/
public interface RecordService {

    /**
     * 分页查询记录
     *
     * @param enterpriseId 企业 id
     * @param categoryId   分类编号
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @param pageable     分页信息
     * @return Page<Record>
     */
    Page<Record> getRecord(String enterpriseId, String categoryId, String startTime, String endTime, Pageable pageable);

    /**
     * 添加记录
     *
     * @param record 记录实体
     * @return Record
     */
    Record createRecord(Record record);

    /**
     * 获取出库记录
     *
     * @param enterpriseId 企业 id
     * @return List<Record>
     */
    List<Record> getOutRecord(String enterpriseId);

    /**
     * 通过员工卡号获取出库记录
     *
     * @param enterpriseId 企业 id
     * @param employeeCard 员工卡号
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @return List<Record>
     */
    List<Record> getOutRecordByEmployeeCard(String enterpriseId, String employeeCard, String startTime, String endTime);

    /**
     * 通过员工卡号获取入库记录
     *
     * @param enterpriseId 企业 id
     * @param employeeCard 员工卡号
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @return List<Record>
     */
    List<Record> getInRecordByEmployeeCard(String enterpriseId, String employeeCard, String startTime, String endTime);

    /**
     * 获取当天产量
     *
     * @param enterpriseId 企业 id
     * @return Integer
     */
    Integer outputToday(String enterpriseId);

    /**
     * 获取过去七天产量
     *
     * @param enterpriseId 企业 id
     * @return List<Integer>
     */
    List<Integer> outputWeek(String enterpriseId);

    /**
     * 获取近一年的产量
     *
     * @param enterpriseId 企业 id
     * @return List<Integer>
     */
    List<Integer> outputYear(String enterpriseId);

    /**
     * 今日员工的工作量
     *
     * @param enterpriseId 企业 id
     * @param employeeCard 员工卡号
     * @return Integer
     */
    Integer workloadToday(String enterpriseId, String employeeCard);

    /**
     * 员工一周工作量
     *
     * @param enterpriseId 企业 id
     * @param employeeCard 员工卡号
     * @return List<Integer>
     */
    List<Integer> workloadSevenDays(String enterpriseId, String employeeCard);

    /**
     * 员工一周工作效率
     *
     * @param enterpriseIs 企业 id
     * @param employeeCard 员工卡号
     * @return List<Double>
     */
    List<Double> workEfficiency(String enterpriseIs, String employeeCard);
}
