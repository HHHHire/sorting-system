package cn.edu.jxust.sort.service;

import cn.edu.jxust.sort.entity.po.Token;

/**
 * @author: ddh
 * @data: 2020/1/5 21:13
 * @description token service
 **/
public interface TokenService {

    /**
     * 通过用户 id 获取 token
     *
     * @param userId 用户 id
     * @return Token
     */
    Token getTokenById(String userId);

    /**
     * 创建用户 token
     *
     * @param token token 实体
     * @return Token
     */
    Token createToken(Token token);

    /**
     * 通过用户 id 删除 token
     *
     * @param userId 用户 id
     * @return Integer
     */
    Integer deleteTokenByUserId(String userId);

    /**
     * 判断 token 是否存在
     *
     * @param token 用户 token
     * @return boolean
     */
    boolean isExist(String token);
}
