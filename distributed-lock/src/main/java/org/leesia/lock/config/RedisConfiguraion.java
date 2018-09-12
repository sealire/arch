package org.leesia.lock.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Auther: leesia
 * @Date: 2018/9/8 19:50
 * @Description:
 */
@Configuration
public class RedisConfiguraion {

    @Bean(name = "jedisPool")
    public JedisPool jedisPool(@Qualifier("jedisPoolConfig") JedisPoolConfig config,
                               @Value("${redis.ip}") String host,
                               @Value("${redis.port}") int port) {
        return new JedisPool(config, host, port);
    }

    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig(
            @Value("${jedis.pool.maxActive}") int maxActive,
            @Value("${jedis.pool.maxIdle}") int maxIdle,
            @Value("${jedis.pool.maxWait}") int maxWait,
            @Value("${jedis.pool.testOnBorrow}") boolean testOnBorrow) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxActive);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWait);
        config.setTestOnBorrow(testOnBorrow);
        return config;
    }
}
