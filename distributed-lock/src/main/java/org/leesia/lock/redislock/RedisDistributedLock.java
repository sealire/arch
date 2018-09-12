package org.leesia.lock.redislock;

import com.alibaba.fastjson.JSONObject;
import org.leesia.lock.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Auther: leesia
 * @Date: 2018/9/6 15:22
 * @Description:
 */
@Service
public class RedisDistributedLock {

    private static final Logger logger = LoggerFactory.getLogger(RedisDistributedLock.class);

    private static String pid = UUID.randomUUID().toString();

    @Autowired
    private RedisService redisService;

    /**
     * 加锁
     *
     * @param lockName       锁的key
     * @param timeout        锁的超时时间
     * @return 锁标识
     */
    public String lock(String lockName, long timeout) {
        logger.info("加锁：{}", lockName);

        LockValue lockValue = createLockValue(timeout);
        JSONObject json = (JSONObject) JSONObject.toJSON(lockValue);
        Long result = redisService.setnx(lockName, json.toString());
        if (result != null && result.intValue() == 1) {
//            int lockExpire = (int) (timeout / 1000);
//            redisService.expire(lockName, lockExpire);
            return lockValue.getLockId();
        } else {
            String value = redisService.get(lockName);
            if (value != null) {
                LockValue lv = JSONObject.parseObject(value, LockValue.class);
                if (System.currentTimeMillis() > lv.getTimeout()) {
                    lockValue = createLockValue(timeout);
                    json = (JSONObject) JSONObject.toJSON(lockValue);
//                    int lockExpire = (int) (timeout / 1000);
                    redisService.set(lockName, json.toString(), null);
                    return lockValue.getLockId();
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    private LockValue createLockValue(long timeout) {
        LockValue lockValue = new LockValue();

        lockValue.setPid(pid);
        lockValue.setLockId(UUID.randomUUID().toString());
        lockValue.setTimeout(timeout + System.currentTimeMillis());

        return lockValue;
    }

    /**
     * 释放锁
     *
     * @param lockName   锁的key
     * @param lockId 释放锁的标识
     * @return
     */
    public boolean unlock(String lockName, String lockId) {
        String lockValue = redisService.get(lockName);
        if (lockValue != null) {
            LockValue lv = JSONObject.parseObject(lockValue, LockValue.class);

            if (lv.getLockId().equals(lockId)) {
                Long del = redisService.del(lockName);
                return del.intValue() == 1;
            }
        }
        return false;
    }
}
