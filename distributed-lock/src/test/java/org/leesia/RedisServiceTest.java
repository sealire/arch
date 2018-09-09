package org.leesia;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.leesia.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: leesia
 * @Date: 2018/9/8 16:21
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(RedisServiceTest.class);

    @Autowired
    private RedisService redisService;

    @Test
    public void testSet() {
//        for (int i = 0; i < 200; i++) {
            ClientThread t = new ClientThread(0);
            t.start();
//        }
    }

    class ClientThread extends Thread {
        int i = 0;

        public ClientThread(int i) {
            this.i = i;
        }

        public void run() {
            try {
                logger.info("线程：{}", i);

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
                String time = sdf.format(date);
                String result = redisService.set("key" + i, time, 100);

                logger.info("线程：{}, over:{}", i, result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
