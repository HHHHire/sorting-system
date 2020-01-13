package cn.edu.jxust.sort.service.impl;

import cn.edu.jxust.sort.entity.po.Enterprise;
import cn.edu.jxust.sort.repository.EnterpriseRepository;
import cn.edu.jxust.sort.service.EnterpriseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author: ddh
 * @data: 2020/1/4 20:11
 * @description
 **/
@Service
public class EnterpriseServiceImpl implements EnterpriseService {
    private final EnterpriseRepository enterpriseRepository;

    public EnterpriseServiceImpl(EnterpriseRepository enterpriseRepository) {
        this.enterpriseRepository = enterpriseRepository;
    }

    @Override
    public Enterprise getEnterpriseByName(String enterpriseName) {
        return enterpriseRepository.findByEnterpriseName(enterpriseName).orElse(null);
    }

    @Override
    public Enterprise createEnterprise(Enterprise enterprise) {
        return enterpriseRepository.save(enterprise);
    }

    @Override
    public Page<Enterprise> getAllEnterprise(Pageable pageable) {
        return enterpriseRepository.findAll(pageable);
    }

    @Override
    public Enterprise getEnterpriseById(String enterpriseId) {
        return enterpriseRepository.findById(enterpriseId).orElse(null);
    }

    @Override
    public Integer deleteEnterpriseById(String enterpriseId) {
        return enterpriseRepository.deleteByEnterpriseId(enterpriseId);
    }
}
