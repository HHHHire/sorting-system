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
    List<Plan> findByEnterpriseId(String enterpriseId);

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update ss_plan set " +
            "category_id=:#{#plan.categoryId}, " +
            "plan_counts=:#{#plan.planCounts}, " +
            "complete_counts=:#{#plan.completeCounts}," +
            "update_time=:#{#plan.updateTime} " +
            "where sort_port_id=:#{#plan.sortPortId}")
    Integer updatePlan(Plan plan);

    Optional<Plan> findByEnterpriseIdAndSortPortId(String enterpriseId, String sortPortId);
}
