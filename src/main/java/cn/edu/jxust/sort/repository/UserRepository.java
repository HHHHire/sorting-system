package cn.edu.jxust.sort.repository;

import cn.edu.jxust.sort.entity.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author: ddh
 * @data: 2020/1/3 9:36
 * @description user 持久化
 **/
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * 通过企业 id 删除用户
     *
     * @param enterpriseId 企业 id
     * @return Integer
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "delete sa,st,su from ss_account sa,ss_user su,ss_token st where sa.user_id = su.user_id and st.user_id = su.user_id and su.enterprise_id = ?1", nativeQuery = true)
    Integer deleteByEnterpriseId(String enterpriseId);

    /**
     * 通过用户名查询用户信息
     *
     * @param userName 用户名
     * @return Optional<User>
     */
    Optional<User> findByUserName(String userName);

    /**
     * 分页查询所有用户
     *
     * @param enterpriseId 企业 id
     * @param pageable     分页信息
     * @return Page<User>
     */
    Page<User> findAllByEnterpriseId(String enterpriseId, Pageable pageable);

    /**
     * 通过用户 id 删除用户
     *
     * @param userId 用户 id
     * @return Integer
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    Integer deleteByUserId(String userId);

    /**
     * 查询所有用户
     *
     * @param enterpriseId 企业 id
     * @return List<User>
     */
    List<User> findAllByEnterpriseId(String enterpriseId);
}
