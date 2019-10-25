package org.leesia.concurrent.executor;

import org.leesia.concurrent.util.FunctionUtil;
import org.leesia.concurrent.util.RandomUtil;
import org.leesia.concurrent.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class ExecutorsTest {

    private static Logger LOGGER = LoggerFactory.getLogger(ExecutorsTest.class);

    public static void main(String[] args) throws Exception {

        LOGGER.info("main exit");
    }

    /**
     * 10个线程运行在无界线程池中，空闲60秒后，线程被回收
     *
     * @throws InterruptedException
     */
    public static void test_cached_pool10() throws InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) ExecutorsService.newCachedThreadPool();

        ThreadUtil.executeOnPool(executor, 10, FunctionUtil.newSleepFunction(RandomUtil.randomLong(0, 500, false)));

        LOGGER.info("Thread: {}", executor.getPoolSize());

        Thread.sleep(200);

        LOGGER.info("Thread: {}", executor.getPoolSize());

        Thread.sleep(70 * 1000);

        LOGGER.info("Thread: {}", executor.getPoolSize());

        executor.shutdown();
    }

    /**
     * 10个线程运行在无界线程池中，再次提交任务复用线程
     *
     * @throws InterruptedException
     */
    public static void test_cached_pool10_1() throws InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) ExecutorsService.newCachedThreadPool();

        ThreadUtil.executeOnPool(executor, 10, FunctionUtil.newSleepFunction(RandomUtil.randomLong(0, 500, false)));

        LOGGER.info("Thread: {}", executor.getPoolSize());

        Thread.sleep(10000);

        LOGGER.info("Thread: {}", executor.getPoolSize());

        ThreadUtil.executeOnPool(executor, 10, x -> {

            LOGGER.info("Thread: {}, random: {}", Thread.currentThread().getName(), RandomUtil.randomLong(0, 1000, true));

            return x;
        });

        executor.shutdown();
    }

    /**
     * 10个线程运行在无界线程池中，指定线程工厂，再次提交任务复用线程
     *
     * @throws InterruptedException
     */
    public static void test_cached_pool10_2() throws InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) ExecutorsService.newCachedThreadPool(r -> new Thread(r, ThreadUtil.getThreadName()));

        ThreadUtil.executeOnPool(executor, 10, FunctionUtil.newSleepFunction(RandomUtil.randomLong(0, 500, false)));

        Thread.sleep(10000);

        ThreadUtil.executeOnPool(executor, 10, x -> {

            LOGGER.info("Thread: {}, random: {}", Thread.currentThread().getName(), RandomUtil.randomLong(0, 1000, true));

            return x;
        });

        executor.shutdown();
    }

    /**
     * 10个线程运行在固定线程池中，空闲60秒后，线程不会被回收
     *
     * @throws InterruptedException
     */
    public static void test_fixed_pool10() throws InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) ExecutorsService.newFixedThreadPool(10);

        ThreadUtil.executeOnPool(executor, 10, FunctionUtil.newSleepFunction(RandomUtil.randomLong(0, 500, false)));

        LOGGER.info("Thread: {}", executor.getPoolSize());

        Thread.sleep(200);

        LOGGER.info("Thread: {}", executor.getPoolSize());

        Thread.sleep(70 * 1000);

        LOGGER.info("Thread: {}", executor.getPoolSize());

        executor.shutdown();
    }


    /**
     * 10个线程运行在有界线程池中，指定线程工厂，再次提交任务复用线程
     *
     * @throws InterruptedException
     */
    public static void test_fixed_pool10_1() throws InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) ExecutorsService.newFixedThreadPool(10, r -> new Thread(r, ThreadUtil.getThreadName()));

        ThreadUtil.executeOnPool(executor, 10, FunctionUtil.newSleepFunction(RandomUtil.randomLong(0, 500, false)));

        Thread.sleep(10000);

        ThreadUtil.executeOnPool(executor, 10, x -> {

            LOGGER.info("Thread: {}, random: {}", Thread.currentThread().getName(), RandomUtil.randomLong(0, 1000, true));

            return x;
        });

        executor.shutdown();
    }

    /**
     * 10个线程运行在单一线程的线程池
     */
    public static void test_single_pool() {
        ExecutorService executor = ExecutorsService.newSingleThreadExecutor();

        ThreadUtil.executeOnPool(executor, 10, FunctionUtil.newSleepFunction(RandomUtil.randomLong(0, 500, false)));

        executor.shutdown();
    }

    /**
     * 10个线程运行在单一线程的线程池，指定线程工厂
     */
    public static void test_single_pool_1() {
        ExecutorService executor = ExecutorsService.newSingleThreadExecutor(r -> new Thread(r, ThreadUtil.getThreadName()));

        ThreadUtil.executeOnPool(executor, 10, FunctionUtil.newSleepFunction(RandomUtil.randomLong(0, 500, false)));

        executor.shutdown();
    }
}
