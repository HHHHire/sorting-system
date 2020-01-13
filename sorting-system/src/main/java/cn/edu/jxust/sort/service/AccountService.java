package cn.edu.jxust.sort.service;

import cn.edu.jxust.sort.entity.po.Account;

/**
 * @author: ddh
 * @data: 2020/1/3 11:26
 * @description account service
 **/
public interface AccountService {
    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param userPwd  用户密码
     * @return Account
     */
    Account getLogin(String userName, String userPwd);

    /**
     * 创建账号
     *
     * @param account 用户实体
     * @return Account
     */
    Account createAccount(Account account);

    /**
     * 删除账号
     *
     * @param userId 用户 id
     * @return Integer
     */
    Integer deleteAccountByUserId(String userId);
}
