package cn.edu.jxust.sort.repository;

import cn.edu.jxust.sort.entity.po.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: ddh
 * @data: 2020/1/3 9:36
 * @description account 持久化
 **/
public interface AccountRepository extends JpaRepository<Account, String> {

    /**
     * 通过用户名和密码获取 Account 对象
     *
     * @param userName 用户名
     * @param userPwd  密码
     * @return Account
     */
    Account findByUserNameAndUserPwd(String userName, String userPwd);

    /**
     * 删除账户
     *
     * @param userId 用户 id
     * @return Integer
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    Integer deleteByUserId(String userId);
}
