package org.leesia.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Auther: leesia
 * @Date: 2018/9/8 16:05
 * @Description:
 */
@Service("redisService")
public class RedisService {

    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    private JedisPool jedisPool;

    /**
     * string(字符串)
     * set key value [EX seconds] [PX milliseconds] [NX|XX]
     *
     * @param key
     * @param value
     * @param cacheSeconds
     * @return
     */
    public String set(String key, String value, Integer cacheSeconds) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            if (cacheSeconds != null && cacheSeconds > 0) {
                result = jedis.setex(key, cacheSeconds, value);
            } else {
                result = jedis.set(key, value);
            }
            logger.info("set {} = {}", key, value);
        } catch (Exception e) {
            logger.error("set {} = {}, error:{}", key, value, e);
        } finally {
            jedis.close();
        }
        return result;
    }
//
//    /**
//     * string(字符串)
//     * get key
//     *
//     * @param key
//     * @return
//     */
//    public String get(String key) {
//        Jedis jedis = null;
//        String value = null;
//        try {
//            jedis = jedisPool.getResource();
//            if (jedis.exists(key)) {
//                value = jedis.get(key);
//                logger.info("get {} = {}", key, value);
//            }
//        } catch (Exception e) {
//            logger.error("get {} = {}, error:{}", key, value, e);
//        } finally {
//            jedis.close();
//        }
//        return value;
//    }

    public String get(String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.get(key);
        } catch (Exception e) {
            logger.error("get {} = {}, error:{}", key, result, e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
            return result;
        }
    }

    public Long setnx(String key, String value) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.setnx(key, value);
        } catch (Exception e) {
            logger.error("setnx {} = {}, error:{}", key, result, e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
            return result;
        }
    }

    public String getSet(String key, String value) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.getSet(key, value);
        } catch (Exception e) {
            logger.error("getSet {} = {}, error:{}", key, result, e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
            return result;
        }
    }

    public Long expire(String key, int seconds) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.expire(key, seconds);
        } catch (Exception e) {
            logger.error("expire {} = {}, error:{}", key, result, e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
            return result;
        }
    }

    public Long del(String key) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.del(key);
        } catch (Exception e) {
            logger.error("del {} = {}, error:{}", key, result, e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
            return result;
        }
    }

}
