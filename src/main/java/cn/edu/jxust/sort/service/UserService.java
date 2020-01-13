package cn.edu.jxust.sort.service;

import cn.edu.jxust.sort.entity.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author: ddh
 * @data: 2020/1/4 20:09
 * @description user service
 **/
public interface UserService {

    /**
     * 通过企业 id 删除用户
     *
     * @param enterpriseId 企业 id
     * @return Integer
     */
    Integer deleteUserByEnterpriseId(String enterpriseId);

    /**
     * 通过用户名获取用户信息
     *
     * @param userName 用户名
     * @return User
     */
    User getUserByUserName(String userName);

    /**
     * 创建用户
     *
     * @param user 用户实体
     * @return User
     */
    User createUser(User user);

    /**
     * 分页获取所有用户
     *
     * @param enterpriseId 企业 id
     * @param pageable     分页信息
     * @return Page<User>
     */
    Page<User> getAllUser(String enterpriseId, Pageable pageable);

    /**
     * 通过用户 id 获取用户信息
     *
     * @param userId 用户 id
     * @return User
     */
    User getUserById(String userId);

    /**
     * 通过用户 id 删除用户
     *
     * @param userId 用户 id
     * @return Integer
     */
    Integer deleteUserByUserId(String userId);

    /**
     * 通过企业 id 获取用户
     *
     * @param enterpriseId 企业 id
     * @return List<User>
     */
    List<User> getUserByEnterpriseId(String enterpriseId);
}
