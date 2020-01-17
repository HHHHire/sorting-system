package cn.edu.jxust.sort.service;

import cn.edu.jxust.sort.entity.po.Plan;

import java.util.List;

/**
 * @author: ddh
 * @data: 2020/1/16 20:31
 * @description
 **/
public interface PlanService {
    /**
     * 创建生产规划
     *
     * @param plan 生产规划实体
     * @return Plan
     */
    Plan createPlan(Plan plan);

    /**
     * 获取生产规划
     *
     * @param enterpriseId 企业 id
     * @return List<Plan>
     */
    List<Plan> getPlan(String enterpriseId);

    /**
     * 更新生产规划
     *
     * @param plan 生产规划实体
     * @return Integer
     */
    Integer updatePlan(Plan plan);

    /**
     * 通过分拣口编号获取生产规划
     *
     * @param enterpriseId 企业 id
     * @param sortPortId   分拣口编号
     * @return Plan
     */
    Plan getPlanBySortPortId(String enterpriseId, String sortPortId);
}
