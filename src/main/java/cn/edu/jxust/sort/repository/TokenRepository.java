package cn.edu.jxust.sort.repository;

import cn.edu.jxust.sort.entity.po.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author: ddh
 * @data: 2020/1/3 9:36
 * @description token 持久化
 **/
public interface TokenRepository extends JpaRepository<Token, String> {

    /**
     * 通过用户 id 删除 token
     *
     * @param userId 用户 id
     * @return Integer
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    Integer deleteByUserId(String userId);

    /**
     * 查询 token
     *
     * @param token 用户 token
     * @return Optional<Token>
     */
    Optional<Token> findByUserToken(String token);
}
