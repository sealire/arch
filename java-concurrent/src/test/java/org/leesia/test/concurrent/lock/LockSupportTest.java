package org.leesia.test.concurrent.lock;

import org.leesia.concurrent.lock.LockSupportService;
import org.leesia.concurrent.taskfactory.ThreadFactory;
import org.leesia.test.concurrent.util.ThreadUtil;
import org.leesia.util.RandomUtil;
import org.leesia.util.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: LockSupportTest
 * @Description:
 * @author: leesia
 * @date: 2019/12/12 17:37
 */
public class LockSupportTest {

    private static Logger LOGGER = LoggerFactory.getLogger(LockSupportTest.class);

    public static void main(String[] args) {
        test_park_until();

        LOGGER.info("main exit");
    }

    public static void test_park() {
        Thread a = ThreadFactory.newThread(o -> {
            int count = 0;
            while (true) {
                count++;
                if (count % 10 == 0) {
                    count = 0;

                    LOGGER.info("park: {}", Thread.currentThread().getName());
                    LockSupportService.park();
                }

                LOGGER.info("{} get: {}", count, RandomUtil.randomNumberString(10));

                ThreadUtil.sleep(1000);
            }
        });

        Thread b = ThreadFactory.newThread(o -> {
            int count = 0;
            while (true) {
                count++;
                if (count % 20 == 0) {
                    count = 0;

                    LOGGER.info("unpark: {}", a.getName());
                    LockSupportService.unpark(a);
                }

                LOGGER.info("{} get: {}", count, RandomUtil.randomNumberString(20));

                ThreadUtil.sleep(1000);
            }
        });

        a.start();
        b.start();
    }

    public static void test_park_nano() {
        Thread a = ThreadFactory.newThread(o -> {
            while (true) {
                LOGGER.info("park at: {}", new Date());
                LockSupportService.parkNanos(1000000000);
                LOGGER.info("unpark at: {}", new Date());

                ThreadUtil.sleep(1000);
            }
        });
        a.start();
    }

    public static void test_park_until() {
        Thread a = ThreadFactory.newThread(o -> {
            while (true) {
                LOGGER.info("park at: {}", new Date());
                LockSupportService.parkUntil(DateUtil.future(2, TimeUnit.SECONDS).getTime());
                LOGGER.info("unpark at: {}", new Date());

                ThreadUtil.sleep(1000);
            }
        });
        a.start();
    }
}
