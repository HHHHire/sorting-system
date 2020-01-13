package cn.edu.jxust.sort.repository;

import cn.edu.jxust.sort.entity.po.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author: ddh
 * @data: 2020/1/3 9:36
 * @description Admin 持久化
 **/
public interface AdminRepository extends JpaRepository<Admin, String> {

    /**
     * 通过管理员名称查询管理员
     *
     * @param adminName 管理员名称
     * @return Optional<Admin>
     */
    Optional<Admin> findByAdminName(String adminName);

    /**
     * 通过管理员 id 删除管理员
     *
     * @param adminId 管理员 id
     * @return Integer
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    Integer deleteByAdminId(String adminId);
}
