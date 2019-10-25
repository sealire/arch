package org.leesia.concurrent.utility;

import org.leesia.concurrent.util.RandomUtil;
import org.leesia.concurrent.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class CountDownLatchTest {

    private static Logger LOGGER = LoggerFactory.getLogger(CountDownLatchTest.class);

    private static CountDownLatchService countDownLatchService5 = new CountDownLatchService(5);

    private static CountDownLatchService countDownLatchService1 = new CountDownLatchService(1);

    public static void main(String[] args) throws Exception {
        test_wait_timeout5_1();

        LOGGER.info("main exit");
    }

    /**
     * 主线程等待5个子线程
     *
     * @throws InterruptedException
     */
    public static void test_wait5() throws InterruptedException {
        ThreadUtil.run(5, x -> {

            try {
                Thread.sleep(RandomUtil.randomLong(0, 2000, true));
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }

            LOGGER.info("Thread: {} arrived, count: {}", Thread.currentThread().getName(), countDownLatchService5.getCount());
            countDownLatchService5.countDown();
            return x;
        });

        LOGGER.info("main await");
        countDownLatchService5.await();
        LOGGER.info("main continue");
    }

    /**
     * 1个线程等待另外5个线程
     */
    public static void test_wait5_1() {
        ThreadUtil.run(5, x -> {

            LOGGER.info("Thread: {} arrived", Thread.currentThread().getName());
            countDownLatchService5.countDown();
            return x;
        });

        ThreadUtil.run(1, x -> {
            try {
                Thread.sleep(2000);

                LOGGER.info("Thread: {} await", Thread.currentThread().getName());
                countDownLatchService5.await();
                LOGGER.info("Thread: {} continue", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }
            return x;
        });
    }

    /**
     * 1个线程等待另外5个线程
     */
    public static void test_wait5_1_1() {
        ThreadUtil.run(1, x -> {

            try {
                LOGGER.info("Thread: {} await", Thread.currentThread().getName());
                countDownLatchService5.await();
                LOGGER.info("Thread: {} continue", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }
            return x;
        });

        ThreadUtil.run(5, x -> {

            LOGGER.info("Thread: {} arrived", Thread.currentThread().getName());
            countDownLatchService5.countDown();
            return x;
        });
    }

    /**
     * 1个线程等待另外5个线程，但只有4个线程到达
     */
    public static void test_wait4_1() {
        ThreadUtil.run(1, x -> {

            try {
                LOGGER.info("Thread: {} await", Thread.currentThread().getName());
                countDownLatchService5.await();
                LOGGER.info("Thread: {} continue", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }
            return x;
        });

        ThreadUtil.run(4, x -> {

            LOGGER.info("Thread: {} arrived", Thread.currentThread().getName());
            countDownLatchService5.countDown();
            return x;
        });
    }

    /**
     * 5个线程等待另外1个线程
     */
    public static void test_wait1_5() {
        ThreadUtil.run(1, x -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }

            LOGGER.info("Thread: {} arrived", Thread.currentThread().getName());
            countDownLatchService1.countDown();
            return x;
        });

        ThreadUtil.run(5, x -> {
            try {
                Thread.sleep(RandomUtil.randomLong(0, 2000, true));

                LOGGER.info("Thread: {} await", Thread.currentThread().getName());
                countDownLatchService1.await();
                LOGGER.info("Thread: {} continue", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }
            return x;
        });
    }

    /**
     * 1个线程等待另外5个线程，最大等待时间为1秒，超时时继续运行
     *
     * @throws InterruptedException
     */
    public static void test_wait_timeout5_1() throws InterruptedException {
        ThreadUtil.run(5, x -> {

            try {
                Thread.sleep(RandomUtil.randomLong(0, 2000, true));
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }

            LOGGER.info("Thread: {} arrived, count: {}", Thread.currentThread().getName(), countDownLatchService5.getCount());
            countDownLatchService5.countDown();
            return x;
        });

        ThreadUtil.run(1, x -> {
            try {
                LOGGER.info("Thread: {} await", Thread.currentThread().getName());
                countDownLatchService5.await(1, TimeUnit.SECONDS);
                LOGGER.info("Thread: {} continue, count: {}", Thread.currentThread().getName(), countDownLatchService5.getCount());
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }
            return x;
        });
    }
}
