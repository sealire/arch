package org.leesia.concurrent.utility;

import org.leesia.concurrent.util.FunctionUtil;
import org.leesia.concurrent.util.RandomUtil;
import org.leesia.concurrent.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {

    private static Logger LOGGER = LoggerFactory.getLogger(SemaphoreTest.class);

    private static SemaphoreService semaphoreService1 = new SemaphoreService(1, false);

    private static SemaphoreService semaphoreService1_fair = new SemaphoreService(1, true);

    private static SemaphoreService semaphoreService2 = new SemaphoreService(2, false);

    private static SemaphoreService semaphoreService10 = new SemaphoreService(10, false);

    public static void main(String[] args) throws Exception {
        test_available_permits();

        LOGGER.info("main exit");
    }

    /**
     * 5个线程竞争1个信号量
     */
    public static void test1_1() {
        ThreadUtil.run(5, x ->  {
            try {
                LOGGER.info("Thread: {} acquire 1", Thread.currentThread().getName());
                semaphoreService1.acquire(1);

                FunctionUtil.apply(FunctionUtil.newSleepFunction(2000));

                LOGGER.info("Thread: {} release 1", Thread.currentThread().getName());
                semaphoreService1.release(1);
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }
            return x;
        });
    }

    /**
     * 5个线程竞争1个公平信号量
     */
    public static void test_fair1_1() {
        ThreadUtil.run(5, x ->  {
            try {
                LOGGER.info("Thread: {} acquire 1", Thread.currentThread().getName());
                semaphoreService1_fair.acquire(1);

                FunctionUtil.apply(FunctionUtil.newSleepFunction(2000));

                LOGGER.info("Thread: {} release 1", Thread.currentThread().getName());
                semaphoreService1_fair.release(1);
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }
            return x;
        });
    }

    /**
     * 5个线程竞争2个信号量，每个线程竞争1个信号量
     */
    public static void test2_1() {
        ThreadUtil.run(5, x ->  {
            try {
                LOGGER.info("Thread: {} acquire 1", Thread.currentThread().getName());
                semaphoreService2.acquire(1);

                FunctionUtil.apply(FunctionUtil.newSleepFunction(2000));

                LOGGER.info("Thread: {} release 1", Thread.currentThread().getName());
                semaphoreService2.release(1);
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }
            return x;
        });
    }

    /**
     * 5个线程竞争1个信号量，随机中断一些线程
     */
    public static void test1_1_interrupt_random(boolean interrupted) {
        if (interrupted) {
            int threadCount = 5;
            Thread[] threads = ThreadUtil.run(threadCount, x ->  {
                LOGGER.info("Thread: {} acquire 1 rninterruptibly", Thread.currentThread().getName());
                semaphoreService1.acquireUninterruptibly(1);

                FunctionUtil.apply(FunctionUtil.newSleepFunction(2000));

                LOGGER.info("Thread: {} release 1", Thread.currentThread().getName());
                semaphoreService1.release(1);
                return x;
            });

            int count = RandomUtil.randomInt(1, threadCount, true);
            interruptRandom(threads, count);
        } else {
            int threadCount = 5;
            Thread[] threads = ThreadUtil.run(threadCount, x ->  {
                try {
                    LOGGER.info("Thread: {} acquire 1", Thread.currentThread().getName());
                    semaphoreService1.acquire(1);

                    FunctionUtil.apply(FunctionUtil.newSleepFunction(2000));

                    LOGGER.info("Thread: {} release 1", Thread.currentThread().getName());
                    semaphoreService1.release(1);
                } catch (InterruptedException e) {
                    LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
                }
                return x;
            });

            int count = RandomUtil.randomInt(1, threadCount, true);
            interruptRandom(threads, count);
        }
    }

    /**
     * 10个线程竞争10个信号量，每个线程竞争3个信号量
     */
    public static void test10_3() {
        ThreadUtil.run(10, x ->  {
            try {
                LOGGER.info("Thread: {} acquire 3", Thread.currentThread().getName());
                semaphoreService10.acquire(3);

                FunctionUtil.apply(FunctionUtil.newSleepFunction(2000));

                LOGGER.info("Thread: {} release 3", Thread.currentThread().getName());
                semaphoreService10.release(3);
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }
            return x;
        });
    }

    /**
     * 5个线程竞争2个信号量，每个线程尝试竞争1个信号量
     */
    public static void test_try2_1() {
        ThreadUtil.run(5, x ->  {
            LOGGER.info("Thread: {} tryAcquire 1", Thread.currentThread().getName());
            if (semaphoreService2.tryAcquire(1)) {
                FunctionUtil.apply(FunctionUtil.newSleepFunction(2000));

                LOGGER.info("Thread: {} release 1", Thread.currentThread().getName());
                semaphoreService2.release(1);
            } else {
                LOGGER.info("Thread: {} unable to tryAcquire 1", Thread.currentThread().getName());
            }
            return x;
        });
    }

    /**
     * 10个线程竞争10个信号量，每个线程尝试竞争3个信号量
     */
    public static void test_try10_3() {
        ThreadUtil.run(5, x ->  {
            LOGGER.info("Thread: {} tryAcquire 3", Thread.currentThread().getName());
            if (semaphoreService10.tryAcquire(3)) {
                FunctionUtil.apply(FunctionUtil.newSleepFunction(2000));

                LOGGER.info("Thread: {} release 3", Thread.currentThread().getName());
                semaphoreService10.release(3);
            } else {
                LOGGER.info("Thread: {} unable to tryAcquire 3", Thread.currentThread().getName());
            }
            return x;
        });
    }

    /**
     * 5个线程竞争2个信号量，每个线程尝试竞争1个信号量，最大等待3秒
     */
    public static void test_timeout_try2_1() {
        ThreadUtil.run(5, x ->  {
            try {
                LOGGER.info("Thread: {} tryAcquire 1", Thread.currentThread().getName());
                if (semaphoreService2.tryAcquire(1, 3, TimeUnit.SECONDS)) {
                    FunctionUtil.apply(FunctionUtil.newSleepFunction(2000));

                    LOGGER.info("Thread: {} release 1", Thread.currentThread().getName());
                    semaphoreService2.release(1);
                } else {
                    LOGGER.info("Thread: {} unable to tryAcquire 1", Thread.currentThread().getName());
                }
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }
            return x;
        });
    }

    /**
     * 动态增加信号量
     */
    public static void test_release() {
        ThreadUtil.run(1, x -> {
            LOGGER.info("Thread: {} available permits: {}", Thread.currentThread().getName(), semaphoreService1.availablePermits());

            LOGGER.info("Thread: {} release 1", Thread.currentThread().getName());
            semaphoreService1.release(1);

            LOGGER.info("Thread: {} available permits: {}", Thread.currentThread().getName(), semaphoreService1.availablePermits());
            return x;
        });
    }

    public static void test_available_permits() throws Exception {
        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(() -> {
                try {
                    semaphoreService2.acquire(1);
                    LOGGER.info("Thread: {} start", Thread.currentThread().getName());

                    Thread.sleep(2000);

                    LOGGER.info("Thread: {} end", Thread.currentThread().getName());
                    semaphoreService2.release(1);
                } catch (InterruptedException e) {
                    LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
                }
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        Thread.sleep(1000);

        LOGGER.info("availablePermits: {}", semaphoreService2.availablePermits());
        LOGGER.info("getQueueLength: {}", semaphoreService2.getQueueLength());
        LOGGER.info("hasQueuedThreads: {}", semaphoreService2.hasQueuedThreads());
        LOGGER.info("drainPermits: {}", semaphoreService2.drainPermits());

        for (Thread thread : threads) {
            thread.join();
        }
        LOGGER.info("availablePermits: {}", semaphoreService2.availablePermits());
    }

    /**
     * 随机中断一些线程
     *
     * @param threads
     * @param count
     */
    private static void interruptRandom(Thread[] threads, int count) {
        Set<Integer> indexes = RandomUtil.randomSet(0, threads.length - 1, count);
        for (Integer index : indexes) {
            threads[index].interrupt();
            LOGGER.info("interrupt thread: {} ", threads[index].getName());
        }
    }
}

