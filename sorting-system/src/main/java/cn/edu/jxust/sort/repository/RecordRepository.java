package cn.edu.jxust.sort.repository;


import cn.edu.jxust.sort.entity.po.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author: ddh
 * @data: 2020/1/8 15:29
 * @description record 持久化
 **/
public interface RecordRepository extends JpaRepository<Record, String> {
    /**
     * 通过企业查找所有
     *
     * @param enterpriseId 企业Id
     * @param pageable     分页信息
     * @return Page<Record>
     */
    Page<Record> findAllByEnterpriseId(String enterpriseId, Pageable pageable);

    /**
     * 通过时间查询记录
     *
     * @param start        开始时间
     * @param end          结束时间
     * @param enterpriseId 企业 id
     * @param pageable     分页信息
     * @return Page<Record>
     */
    Page<Record> findByCreateTimeBetweenAndEnterpriseIdOrderByCreateTimeDesc(Long start, Long end, String enterpriseId, Pageable pageable);

    /**
     * 通过时间和分类编号查询记录
     *
     * @param start        开始时间
     * @param end          结束时间
     * @param enterpriseId 企业 id
     * @param categoryId   分类编号
     * @param pageable     分页信息
     * @return Page<Record>
     */
    Page<Record> findByCreateTimeBetweenAndEnterpriseIdAndCategoryIdOrderByCreateTimeDesc(Long start, Long end, String enterpriseId, String categoryId, Pageable pageable);

    /**
     * 查询出库记录
     *
     * @param enterpriseId 企业 id
     * @return List<Record>
     */
    @Query(value = "select * from ss_record sr where sr.enterprise_id = ?1 and sr.counts < 0 order by sr.create_time desc", nativeQuery = true)
    List<Record> findOutRecord(String enterpriseId);

    /**
     * 查询入库记录
     *
     * @param enterpriseId 企业 id
     * @param start        开始时间
     * @param end          结束时间
     * @return List<Record>
     */
    @Query(value = "select * from ss_record sr where sr.enterprise_id = ?1 and sr.counts > 0 and sr.create_time between ?2 and ?3 order by sr.create_time desc", nativeQuery = true)
    List<Record> findInRecord(String enterpriseId, Long start, Long end);

    /**
     * 通过员工卡号查询记录
     *
     * @param enterpriseId 企业 id
     * @param employeeCard 员工卡号
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @return List<Record>
     */
    @Query(value = "select * from ss_record sr where sr.enterprise_id = ?1 and sr.employee_card = ?2 and sr.counts < 0 and sr.create_time between ?3 and ?4 order by sr.create_time desc", nativeQuery = true)
    List<Record> findOutRecordByEmployeeCard(String enterpriseId, String employeeCard, Long startTime, Long endTime);

    /**
     * 通过员工卡号查询入库记录
     *
     * @param enterpriseId 企业 id
     * @param employeeCard 员工卡号
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @return List<Record>
     */
    @Query(value = "select * from ss_record sr where sr.enterprise_id = ?1 and sr.employee_card = ?2 and sr.counts > 0 and sr.create_time between ?3 and ?4 order by sr.create_time desc", nativeQuery = true)
    List<Record> findInRecordByEmployeeCard(String enterpriseId, String employeeCard, Long startTime, Long endTime);
}
