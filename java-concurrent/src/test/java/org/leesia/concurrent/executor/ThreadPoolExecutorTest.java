package org.leesia.concurrent.executor;

import org.leesia.concurrent.thread.RunnableFactory;
import org.leesia.concurrent.util.FunctionUtil;
import org.leesia.concurrent.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class ThreadPoolExecutorTest {

    private static Logger LOGGER = LoggerFactory.getLogger(ThreadPoolExecutorTest.class);

    private static ThreadPoolExecutorService threadPoolExecutorService3_10 = new ThreadPoolExecutorService(
            3, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));

    private static ThreadPoolExecutorService threadPoolExecutorService10_20 = new ThreadPoolExecutorService(
            10, 20, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));

    private static ThreadPoolExecutorService threadPoolExecutorService3_10_f = new ThreadPoolExecutorService(
            3, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10), r -> new Thread(r, ThreadUtil.getThreadName()));

    private static ThreadPoolExecutorService threadPoolExecutorService3_10_h = new ThreadPoolExecutorService(
            3, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10), new ThreadPoolExecutor.AbortPolicy());

    private static ThreadPoolExecutorService threadPoolExecutorService3_10_f_h = new ThreadPoolExecutorService(
            3, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10), r -> new Thread(r, ThreadUtil.getThreadName()), new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) throws Exception {
        test_shutdown_now10_10_1();

        LOGGER.info("main exit");
    }

    /**
     * 10个任务，在[3, 10]的线程池中运行，只起了3个核心线程，剩余7个任务在任务队列
     */
    public static void test_execute10_10() {
        execute(threadPoolExecutorService3_10, 10, FunctionUtil.newSleepRandomFunction(0, 500));

        threadPoolExecutorService3_10.shutdown();
    }

    /**
     * 18个任务，在[3, 10]的线程池中运行，先起3个核心线程，10个任务加入任务队列，剩下5个任务再起5个线程，一共起了8个线程
     */
    public static void test_execute10_18() {
        execute(threadPoolExecutorService3_10, 18, FunctionUtil.newSleepRandomFunction(0, 500));

        threadPoolExecutorService3_10.shutdown();
    }

    /**
     * 10个任务，在[3, 10]的线程池中运行，指定线程工厂
     */
    public static void test_execute10_10_f() {
        execute(threadPoolExecutorService3_10_f, 10, FunctionUtil.newSleepRandomFunction(0, 500));

        threadPoolExecutorService3_10_f.shutdown();
    }

    /**
     * 21个任务，在[3, 10]的线程池中运行，任务队列最大为10，有一个任务要丢弃，抛出异常
     */
    public static void test_execute10_21_h() {
        try {
            execute(threadPoolExecutorService3_10_h, 21, FunctionUtil.newSleepRandomFunction(0, 500));
        } catch (Exception e) {
            LOGGER.error("catch e: {}", e);
        } finally {
            threadPoolExecutorService3_10_h.shutdown();
        }
    }

    /**
     * 21个任务，在[3, 10]的线程池中运行，指定线程工厂，任务队列最大为10，有一个任务要丢弃，抛出异常
     */
    public static void test_execute10_21_f_h() {
        try {
            execute(threadPoolExecutorService3_10_f_h, 21, FunctionUtil.newSleepRandomFunction(0, 500));
        } catch (Exception e) {
            LOGGER.error("catch e: {}", e);
        } finally {
            threadPoolExecutorService3_10_f_h.shutdown();
        }
    }

    /**
     * 10个任务，在[3, 10]的线程池中运行，随机睡眠[0, 5]秒，2秒后关闭线程池，提交过的任务继续运行，关闭之后提交的任务就会抛出异常
     *
     * @throws InterruptedException
     */
    public static void test_shutdown10_10() throws InterruptedException {
        try {
            execute(threadPoolExecutorService3_10, 10, FunctionUtil.newSleepRandomFunction(0, 5000));
        } catch (Exception e) {
            LOGGER.error("catch e: {}", e);
        }

        Thread.sleep(2000);

        LOGGER.info("pool shutdown");
        threadPoolExecutorService3_10.shutdown();

        try {
            execute(threadPoolExecutorService3_10, 3, FunctionUtil.newSleepRandomFunction(0, 5000));
        } catch (Exception e) {
            LOGGER.error("catch e: {}", e);
        }
    }

    /**
     * 10个任务，在[10, 20]的线程池中运行，随机睡眠[0, 5]秒，2秒后立即关闭线程池，
     * 有些线程被中断，但线程内没有判断if(Thread.currentThread().isInterrupted() == true)，所以继续运行
     *
     * @throws InterruptedException
     */
    public static void test_shutdown_now10_10() throws InterruptedException {
        try {
            execute(threadPoolExecutorService10_20, 10, FunctionUtil.newSleepRandomFunction(0, 5000));
        } catch (Exception e) {
            LOGGER.error("catch e: {}", e);
        }

        Thread.sleep(2000);

        LOGGER.info("pool shutdownNow");
        List<Runnable> runnables = threadPoolExecutorService10_20.shutdownNow();
        LOGGER.info("shutdownNow return: {}", runnables.size());
    }

    public static void test_shutdown_now10_10_1() throws InterruptedException {
        try {
            executeCheckInterrupted(threadPoolExecutorService10_20, 10, FunctionUtil.newSleepRandomFunction(0, 4000));
        } catch (Exception e) {
            LOGGER.error("catch e: {}", e);
        }

        Thread.sleep(2000);

        LOGGER.info("pool shutdownNow");
        List<Runnable> runnables = threadPoolExecutorService10_20.shutdownNow();
        LOGGER.info("shutdownNow return: {}", runnables.size());
    }

    private static void execute(ThreadPoolExecutorService executorService, int threadCount, Function function) {
        for (int i = 0; i < threadCount; i++) {
            if (function == null) {
                executorService.execute(RunnableFactory.newBlankRunnable());
            } else {
                executorService.execute(RunnableFactory.newRunnable(function));
            }
        }
    }

    private static void executeCheckInterrupted(ThreadPoolExecutorService executorService, int threadCount, Function function) {
        for (int i = 0; i < threadCount; i++) {
            if (function == null) {
                executorService.execute(RunnableFactory.checkInterrupted(RunnableFactory.newBlankRunnable()));
            } else {
                executorService.execute(RunnableFactory.checkInterrupted(RunnableFactory.newRunnable(function)));
            }
        }
    }
}
