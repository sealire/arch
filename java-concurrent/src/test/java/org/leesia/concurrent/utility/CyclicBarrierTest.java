package org.leesia.concurrent.utility;

import org.leesia.concurrent.thread.RunnableFactory;
import org.leesia.concurrent.util.RandomUtil;
import org.leesia.concurrent.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CyclicBarrierTest {

    private static Logger LOGGER = LoggerFactory.getLogger(CyclicBarrierTest.class);

    private static CyclicBarrierService cyclicBarrierService3 = new CyclicBarrierService(3, RunnableFactory.newBlankRunnable());

    private static CyclicBarrierService cyclicBarrierService6 = new CyclicBarrierService(6, RunnableFactory.newBlankRunnable());

    public static void main(String[] args) throws Exception {
        test_reset3_2();

        LOGGER.info("main exit");
    }

    /**
     * 3个线程互相等待，屏障等待3个线程
     */
    public static void test_await3_3() {
        ThreadUtil.run(3, x -> {
            try {
                LOGGER.info("Thread: {} await, parties: {}", Thread.currentThread().getName(), cyclicBarrierService3.getParties());
                cyclicBarrierService3.await();
                LOGGER.info("Thread: {} continue", Thread.currentThread().getName());
            } catch (BrokenBarrierException e) {
                LOGGER.error("Thread: {} barrier broken", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }
            return x;
        });
    }

    /**
     * 6个线程互相等待，屏障等待3个线程
     */
    public static void test_await3_6() {
        ThreadUtil.run(6, x -> {
            try {
                LOGGER.info("Thread: {} await", Thread.currentThread().getName());
                cyclicBarrierService3.await();
                LOGGER.info("Thread: {} continue", Thread.currentThread().getName());
            } catch (BrokenBarrierException e) {
                LOGGER.error("Thread: {} barrier broken", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }
            return x;
        });
    }

    /**
     * 2个线程互相等待，屏障等待3个线程
     */
    public static void test_await3_2() {
        ThreadUtil.run(2, x -> {
            try {
                LOGGER.info("Thread: {} await", Thread.currentThread().getName());
                cyclicBarrierService3.await();
                LOGGER.info("Thread: {} continue", Thread.currentThread().getName());
            } catch (BrokenBarrierException e) {
                LOGGER.error("Thread: {} barrier broken", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }
            return x;
        });
    }

    /**
     * 6个线程互相等待，最大等待1秒，屏障等待6个线程
     */
    public static void test_timeout_await6_6() {
        ThreadUtil.run(6, x -> {
            try {
                Thread.sleep(RandomUtil.randomLong(0, 4000, true));

                LOGGER.info("Thread: {} await, arrived: {}", Thread.currentThread().getName(), cyclicBarrierService6.getNumberWaiting());
                cyclicBarrierService6.await(1, TimeUnit.SECONDS);
                LOGGER.info("Thread: {} continue", Thread.currentThread().getName());
            } catch (BrokenBarrierException e) {
                LOGGER.error("Thread: {} barrier broken: {}", Thread.currentThread().getName(), cyclicBarrierService6.isBroken());
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            } catch (TimeoutException e) {
                LOGGER.error("Thread: {} wait timeout, isBroken: {}", Thread.currentThread().getName(), cyclicBarrierService6.isBroken());
            }
            return x;
        });
    }

    /**
     * 6个线程互相等待，屏障等待6个线程，损坏后重置
     */
    public static void test_reset6_6() throws InterruptedException {
        Thread[] threads = ThreadUtil.run(6, x -> {
            try {
                Thread.sleep(RandomUtil.randomLong(0, 4000, true));

                LOGGER.info("Thread: {} await, arrived: {}", Thread.currentThread().getName(), cyclicBarrierService6.getNumberWaiting());
                cyclicBarrierService6.await(1, TimeUnit.SECONDS);
                LOGGER.info("Thread: {} continue", Thread.currentThread().getName());
            } catch (BrokenBarrierException e) {
                LOGGER.error("Thread: {} barrier broken: {}", Thread.currentThread().getName(), cyclicBarrierService6.isBroken());
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            } catch (TimeoutException e) {
                LOGGER.error("Thread: {} wait timeout, isBroken: {}", Thread.currentThread().getName(), cyclicBarrierService6.isBroken());
            }
            return x;
        });

        for (Thread thread : threads) {
            thread.join();
        }

        LOGGER.info("reset CyclicBarrier");
        cyclicBarrierService6.reset();

        ThreadUtil.run(6, x -> {
            try {
                LOGGER.info("Thread: {} await, arrived: {}", Thread.currentThread().getName(), cyclicBarrierService6.getNumberWaiting());
                cyclicBarrierService6.await();
                LOGGER.info("Thread: {} continue", Thread.currentThread().getName());
            } catch (BrokenBarrierException e) {
                LOGGER.error("Thread: {} barrier broken: {}", Thread.currentThread().getName(), cyclicBarrierService6.isBroken());
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }
            return x;
        });
    }

    /**
     * 2个线程互相等待，屏障等待3个线程，重置
     */
    public static void test_reset3_2() throws InterruptedException {
        ThreadUtil.run(2, x -> {
            try {
                LOGGER.info("Thread: {} await", Thread.currentThread().getName());
                cyclicBarrierService3.await();
                LOGGER.info("Thread: {} continue", Thread.currentThread().getName());
            } catch (BrokenBarrierException e) {
                LOGGER.error("Thread: {} barrier broken", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }
            return x;
        });

        Thread.sleep(3000);
        LOGGER.info("reset CyclicBarrier");
        cyclicBarrierService3.reset();

        ThreadUtil.run(3, x -> {
            try {
                LOGGER.info("Thread: {} await", Thread.currentThread().getName());
                cyclicBarrierService3.await();
                LOGGER.info("Thread: {} continue", Thread.currentThread().getName());
            } catch (BrokenBarrierException e) {
                LOGGER.error("Thread: {} barrier broken", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }
            return x;
        });
    }
}
