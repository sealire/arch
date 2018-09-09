package org.leesia.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Auther: leesia
 * @Date: 2018/9/8 15:34
 * @Description:
 */
@Component
@Configuration
@PropertySource("classpath:redis.properties")
public class RedisConfig {

    @Value("${redis.ip}")
    private String ip;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.passWord}")
    private String passWord;

    @Value("${redis.timeout}")
    private int timeout;

    @Value("${jedis.pool.maxActive}")
    private int maxActive;

    @Value("${jedis.pool.maxIdle}")
    private int maxIdle;

    @Value("${jedis.pool.maxWait}")
    private int maxWait;

    @Value("${jedis.pool.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${jedis.pool.testOnReturn}")
    private boolean testOnReturn;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }
}
