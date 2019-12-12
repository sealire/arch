package org.leesia.test.concurrent.lock;

import org.leesia.concurrent.lock.LockSupportService;
import org.leesia.concurrent.taskfactory.ThreadFactory;
import org.leesia.test.concurrent.util.ThreadUtil;
import org.leesia.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * @ClassName: LockSupportTest
 * @Description:
 * @author: leesia
 * @date: 2019/12/12 17:37
 */
public class LockSupportTest {

    private static Logger LOGGER = LoggerFactory.getLogger(LockSupportTest.class);

    public static void main(String[] args) {
        test_park();

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
}
