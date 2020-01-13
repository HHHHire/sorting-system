package cn.edu.jxust.sort.service;

import cn.edu.jxust.sort.entity.po.Enterprise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author: ddh
 * @data: 2020/1/4 20:08
 * @description enterprise service
 **/
public interface EnterpriseService {

    /**
     * 通过企业名获取企业信息
     *
     * @param enterpriseName 企业名
     * @return Enterprise
     */
    Enterprise getEnterpriseByName(String enterpriseName);

    /**
     * 创建企业
     *
     * @param enterprise 企业实体
     * @return Enterprise
     */
    Enterprise createEnterprise(Enterprise enterprise);

    /**
     * 分页获取所有企业
     *
     * @param page 分页信息
     * @return Page<Enterprise>
     */
    Page<Enterprise> getAllEnterprise(Pageable page);

    /**
     * 通过企业 id 获取企业信息
     *
     * @param enterpriseId 企业 id
     * @return Enterprise
     */
    Enterprise getEnterpriseById(String enterpriseId);

    /**
     * 通过企业 id 获取企业信息
     *
     * @param enterpriseId 企业 id
     * @return Integer
     */
    Integer deleteEnterpriseById(String enterpriseId);
}
