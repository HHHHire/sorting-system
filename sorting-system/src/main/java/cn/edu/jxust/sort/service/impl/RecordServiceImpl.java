package cn.edu.jxust.sort.service.impl;

import cn.edu.jxust.sort.entity.po.Record;
import cn.edu.jxust.sort.repository.RecordRepository;
import cn.edu.jxust.sort.service.RecordService;
import cn.edu.jxust.sort.util.common.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ddh
 * @date 2020/1/8 16:02
 * @description
 **/
@Service
public class RecordServiceImpl implements RecordService {
    private final RecordRepository recordRepository;

    @Autowired
    public RecordServiceImpl(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Override
    public Page<Record> getRecord(String enterpriseId, String categoryId, String startTime, String endTime, Pageable pageable) {
        Long start = analyticalStartTime(startTime);
        Long end = analyticalEndTime(endTime);
        if (categoryId == null) {
            return recordRepository.findByCreateTimeBetweenAndEnterpriseIdOrderByCreateTimeDesc(start, end, enterpriseId);
        } else {
            return recordRepository.findByCreateTimeBetweenAndEnterpriseIdAndCategoryIdOrderByCreateTimeDesc(start, end, enterpriseId, categoryId);
        }
    }

    @Override
    public Record createRecord(Record record) {
        return recordRepository.save(record);
    }

    @Override
    public List<Record> getOutRecord(String enterpriseId) {
        return recordRepository.findOutRecord(enterpriseId);
    }

    @Override
    public List<Record> getOutRecordByEmployeeCard(String enterpriseId, String employeeCard) {
        return recordRepository.findOutRecordByEmployeeCard(enterpriseId, employeeCard);
    }




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
}
