package cn.edu.jxust.sort.service;

import cn.edu.jxust.sort.entity.po.Plan;
import cn.edu.jxust.sort.repository.InventoryRepository;

import java.util.List;

/**
 * @author: ddh
 * @data: 2020/1/16 20:31
 * @description
 **/
public interface PlanService {
    Plan createPlan(Plan plan);

    List<Plan> getPlan(String enterpriseId);

    Integer updatePlan(Plan plan);

    Plan getPlanBySortPortId(String enterpriseId, String sortPortId);
}
