package cn.edu.jxust.sort.repository;

import cn.edu.jxust.sort.entity.po.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: ddh
 * @data: 2020/1/14 17:06
 * @description plan 持久化
 **/
public interface PlanRepository extends JpaRepository<Plan, String> {

}
