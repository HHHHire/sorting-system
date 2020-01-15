package cn.edu.jxust.sort.util.common;

import cn.edu.jxust.sort.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author: ddh
 * @data: 2020/1/14 18:05
 * @description
 **/
public class RedisPool {
    private static JedisPool pool;
    private static Integer maxTotal = Integer.valueOf(PropertiesUtil.getProperties("redis.max.total", "20"));
    private static Integer maxIdle = Integer.valueOf(PropertiesUtil.getProperties("redis.max.idle", "10"));
    private static Integer minIdle = Integer.valueOf(PropertiesUtil.getProperties("redis.min.idle", "2"));
    private static Boolean testOnBorrow = Boolean.valueOf(PropertiesUtil.getProperties("redis.test.borrow", "true"));
    private static Boolean testOnReturn = Boolean.valueOf(PropertiesUtil.getProperties("redis.test.return", "false"));
    private static Integer redisPort = Integer.valueOf(PropertiesUtil.getProperties("redis.port", "8379"));
    private static String redisIp = PropertiesUtil.getProperties("redis.ip");

    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnReturn(testOnReturn);
        config.setTestOnBorrow(testOnBorrow);
        config.setBlockWhenExhausted(true);

        pool = new JedisPool(config, redisIp, redisPort, 1000 * 2);
    }

    static {
        initPool();
    }

    public static Jedis getJedis() {
        return pool.getResource();
    }

    public static void returnJedis(Jedis jedis) {
        jedis.close();
    }
}
