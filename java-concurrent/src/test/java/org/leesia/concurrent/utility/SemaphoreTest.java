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

    private static SemaphoreService semaphoreService = new SemaphoreService();

    public static void main(String[] args) {
        test1_1();

        LOGGER.info("main exit");
    }

    /**
     * 5个线程竞争1个信号量
     */
    public static void test1_1() {
        ThreadUtil.run(5, x ->  {
            try {
                semaphoreService.syn1_1(FunctionUtil.newSleepFunction(2000));
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
                semaphoreService.syn_fair1_1(FunctionUtil.newSleepFunction(2000));
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
                semaphoreService.syn2_1(FunctionUtil.newSleepFunction(2000));
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
                semaphoreService.syn1_1_uninterruptibly(FunctionUtil.newSleepFunction(2000));
                return x;
            });

            int count = RandomUtil.randomInt(1, threadCount, true);
            interruptRandom(threads, count);
        } else {
            int threadCount = 5;
            Thread[] threads = ThreadUtil.run(threadCount, x ->  {
                try {
                    semaphoreService.syn1_1(FunctionUtil.newSleepFunction(2000));
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
                semaphoreService.syn10_3(FunctionUtil.newSleepFunction(2000));
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
            semaphoreService.try_syn2_1(FunctionUtil.newSleepFunction(2000));
            return x;
        });
    }

    /**
     * 10个线程竞争10个信号量，每个线程尝试竞争3个信号量
     */
    public static void test_try10_3() {
        ThreadUtil.run(5, x ->  {
            semaphoreService.try_syn10_3(FunctionUtil.newSleepFunction(2000));
            return x;
        });
    }

    /**
     * 5个线程竞争2个信号量，每个线程尝试竞争1个信号量，最大等待3秒
     */
    public static void test_timeout_try2_1() {
        ThreadUtil.run(5, x ->  {
            try {
                semaphoreService.try_timeout_syn2_1(FunctionUtil.newSleepFunction(2000), 3, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }
            return x;
        });
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

