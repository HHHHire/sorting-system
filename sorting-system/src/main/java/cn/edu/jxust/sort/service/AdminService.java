package cn.edu.jxust.sort.service;

import cn.edu.jxust.sort.entity.po.Admin;

import java.util.List;

/**
 * @author: ddh
 * @data: 2020/1/3 11:21
 * @description admin service
 **/
public interface AdminService {

    /**
     * 创建管理员
     *
     * @param admin 管理员实体
     * @return Admin
     */
    Admin createAdmin(Admin admin);

    /**
     * 获取管理员信息
     *
     * @param adminId 管理员 id
     * @return Admin
     */
    Admin getAdmin(String adminId);

    /**
     * 获取所有管理员
     *
     * @return List<Admin>
     */
    List<Admin> getAllAdmin();

    /**
     * 通过管理员 id 删除管理员
     *
     * @param adminId 管理员 id
     * @return Integer
     */
    Integer deleteAdminById(String adminId);
}
