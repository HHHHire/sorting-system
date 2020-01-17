package cn.edu.jxust.sort.service.impl;

import cn.edu.jxust.sort.entity.po.Plan;
import cn.edu.jxust.sort.repository.PlanRepository;
import cn.edu.jxust.sort.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: ddh
 * @data: 2020/1/16 20:32
 * @description
 **/
@Service
public class PlanServiceImpl implements PlanService {
    private final PlanRepository planRepository;

    @Autowired
    public PlanServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public Plan createPlan(Plan plan) {
        return planRepository.save(plan);
    }

    @Override
    public List<Plan> getPlan(String enterpriseId) {
        return planRepository.findByEnterpriseId(enterpriseId);
    }

    @Override
    public Integer updatePlan(Plan plan) {
        return planRepository.updatePlan(plan);
    }

    @Override
    public Plan getPlanBySortPortId(String enterpriseId, String sortPortId) {
        return planRepository.findByEnterpriseIdAndSortPortId(enterpriseId, sortPortId).orElse(null);
    }
}
