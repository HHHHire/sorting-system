package cn.edu.jxust.sort.util;

import cn.edu.jxust.sort.util.common.RedisPool;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * @author: ddh
 * @data: 2020/1/14 18:19
 * @description
 **/
@Component
public class RedisPoolUtil {
    public String setValueByte(byte[] key, byte[] value) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            // 选择数据库
            jedis.select(1);
            result = jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert jedis != null;
            RedisPool.returnJedis(jedis);
        }
        return result;
    }

    public Object getValueByte(byte[] key) {
        Jedis jedis = null;
        Object result = null;
        try {
            jedis = RedisPool.getJedis();
            // 选择数据库
            jedis.select(1);
            result = SerializeUtil.unserialize(jedis.get(key));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert jedis != null;
            RedisPool.returnJedis(jedis);
        }
        return result;
    }

    public Long delKey(String key) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert jedis != null;
            RedisPool.returnJedis(jedis);
        }
        return result;
    }

    public Long hSet(String mapName, Map<String, String> map) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            jedis.auth("123456");
            result = jedis.hset(mapName, map);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert jedis != null;
            RedisPool.returnJedis(jedis);
        }
        return result;
    }

    public Map<String, String> hGetAll(String mapName) {
        Jedis jedis = null;
        Map<String, String> result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.hgetAll(mapName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert jedis != null;
            RedisPool.returnJedis(jedis);
        }
        return result;
    }

    public String hGetByName(String mapName, String keyName) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.hget(mapName, keyName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert jedis != null;
            RedisPool.returnJedis(jedis);
        }
        return result;
    }

    public void initRedis() {

    }
}
