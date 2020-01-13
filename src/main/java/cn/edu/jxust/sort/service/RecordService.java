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
     * @return List<Record>
     */
    List<Record> getOutRecordByEmployeeCard(String enterpriseId, String employeeCard);
}
