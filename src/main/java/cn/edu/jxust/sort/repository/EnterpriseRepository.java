package cn.edu.jxust.sort.repository;

import cn.edu.jxust.sort.entity.po.Enterprise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author: ddh
 * @data: 2020/1/3 9:36
 * @description enterprise 持久化
 **/
public interface EnterpriseRepository extends JpaRepository<Enterprise, String> {

    /**
     * 通过企业名称查询企业
     *
     * @param enterpriseName 企业名称
     * @return Optional<Enterprise>
     */
    Optional<Enterprise> findByEnterpriseName(String enterpriseName);

    /**
     * 分页查询所有企业
     *
     * @param pageable 分页信息
     * @return Page<Enterprise>
     */
    @Override
    Page<Enterprise> findAll(Pageable pageable);

    /**
     * 通过企业 id 删除企业
     *
     * @param enterpriseId 企业 id
     * @return Integer
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    Integer deleteByEnterpriseId(String enterpriseId);
}
