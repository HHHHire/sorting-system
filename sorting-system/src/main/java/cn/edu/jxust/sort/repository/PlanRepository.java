package cn.edu.jxust.sort.repository;

import cn.edu.jxust.sort.entity.po.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author: ddh
 * @data: 2020/1/14 17:06
 * @description plan 持久化
 **/
public interface PlanRepository extends JpaRepository<Plan, String> {
    /**
     * 查询生产规划
     *
     * @param enterpriseId 企业 id
     * @return List<Plan>
     */
    List<Plan> findByEnterpriseId(String enterpriseId);

    /**
     * 更新生产规划
     *
     * @param plan 生产规划实体
     * @return Integer
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update ss_plan set " +
            "category_id=:#{#plan.categoryId}, " +
            "plan_counts=:#{#plan.planCounts}, " +
            "complete_counts=:#{#plan.completeCounts}," +
            "update_time=:#{#plan.updateTime} " +
            "where sort_port_id=:#{#plan.sortPortId}")
    Integer updatePlan(Plan plan);

    /**
     * 通过分拣口编号查询生产规划
     *
     * @param enterpriseId 企业 id
     * @param sortPortId   分拣口编号
     * @return Optional<Plan>
     */
    Optional<Plan> findByEnterpriseIdAndSortPortId(String enterpriseId, String sortPortId);
}
