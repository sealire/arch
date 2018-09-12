package org.leesia.lock.kill;

import org.leesia.lock.redis.RedisService;
import org.leesia.lock.redislock.RedisDistributedLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: leesia
 * @Date: 2018/9/6 15:38
 * @Description:
 */
@Service
public class SecKill {

    private static final Logger logger = LoggerFactory.getLogger(SecKill.class);

    @Autowired
    private RedisDistributedLock redisDistributedLock;

    @Autowired
    private RedisService redisService;

    public void seckill(String cid, String pid, long timeout) {
        String skId = "sk_" + pid;

        logger.info("秒杀:{},{}", cid, pid);
        String lockId = null;
        long start = System.currentTimeMillis();
        long end;
        do {
            try {
                lockId = redisDistributedLock.lock(skId, 2000);
            } catch (Exception e) {
                logger.error("加锁失败:{},{},{}", cid, pid, e);
            }
            end = System.currentTimeMillis();

        } while ((lockId == null || lockId.equals("")) && ((end - start) < timeout));

        if (lockId == null || lockId.equals("")) {
            logger.error("秒杀失败:{},{}", cid, pid);
            return;
        }

        try {
            int count = Integer.parseInt(redisService.get(pid));
            if (count > 0) {
                redisService.set(pid, "" + (--count), 3600);
            }
            logger.info("秒杀成功:{},{}", cid, pid);
        } catch (Exception e) {
            logger.error("秒杀失败:{},{},{}", cid, pid, e);
        }

        redisDistributedLock.unlock(skId, lockId);
    }
}
