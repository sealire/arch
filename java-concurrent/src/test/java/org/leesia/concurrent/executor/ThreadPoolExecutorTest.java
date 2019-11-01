package org.leesia.concurrent.executor;

import org.leesia.concurrent.thread.CallableFactory;
import org.leesia.concurrent.thread.RunnableFactory;
import org.leesia.concurrent.util.FunctionUtil;
import org.leesia.concurrent.util.RandomUtil;
import org.leesia.concurrent.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
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
        test_shutdown_now10_20_1();

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
     * 20个任务，在[3, 10]的线程池中运行，随机睡眠[0, 5]秒，2秒后关闭线程池，提交过的任务继续运行，关闭之后提交的任务就会抛出异常
     *
     * @throws InterruptedException
     */
    public static void test_shutdown10_20() throws InterruptedException {
        try {
            execute(threadPoolExecutorService3_10, 20, FunctionUtil.newSleepRandomFunction(0, 5000));
        } catch (Exception e) {
            LOGGER.error("catch e: {}", e);
        }


        LOGGER.info("pool awaitTermination: {}", threadPoolExecutorService3_10.awaitTermination(1000, TimeUnit.MILLISECONDS));
        Thread.sleep(2000);

        threadPoolExecutorService3_10.shutdown();

        LOGGER.info("pool shutdown: {}", threadPoolExecutorService3_10.isShutdown());
        LOGGER.info("pool isTerminating: {}, isTerminated: {}", threadPoolExecutorService3_10.isTerminating(), threadPoolExecutorService3_10.isTerminated());


        try {
            execute(threadPoolExecutorService3_10, 3, FunctionUtil.newSleepRandomFunction(0, 5000));
        } catch (Exception e) {
            LOGGER.error("catch e: {}", e);
        }

        LOGGER.info("pool isTerminating: {}, isTerminated: {}", threadPoolExecutorService3_10.isShutdown(), threadPoolExecutorService3_10.isTerminating(), threadPoolExecutorService3_10.isTerminated());
    }

    /**
     * 20个任务，在[10, 20]的线程池中运行，
     * 关闭线程池，有些线程被中断，但线程内没有判断if(Thread.currentThread().isInterrupted() == true)，所以继续运行
     */
    public static void test_shutdown_now10_20() {
        try {
            execute(threadPoolExecutorService10_20, 20, FunctionUtil.newBlankFunction());
        } catch (Exception e) {
            LOGGER.error("catch e: {}", e);
        }

//        Thread.sleep(2000);

        LOGGER.info("pool shutdownNow");
        List<Runnable> runnables = threadPoolExecutorService10_20.shutdownNow();
        LOGGER.info("shutdownNow return: {}", runnables.size());
    }

    /**
     * 20个任务，在[10, 20]的线程池中运行，关闭线程池，有些线程被中断
     */
    public static void test_shutdown_now10_20_1() {
        try {
            executeCheckInterrupted(threadPoolExecutorService10_20, 20, FunctionUtil.newBlankFunction());
        } catch (Exception e) {
            LOGGER.error("catch e: {}", e);
        }

//        Thread.sleep(2000);

        LOGGER.info("pool shutdownNow");
        List<Runnable> runnables = threadPoolExecutorService10_20.shutdownNow();
        LOGGER.info("shutdownNow return: {}", runnables.size());
    }

    /**
     * 10个任务，在[3, 10]的线程池中运行，设置核心线程可超时回收
     *
     * @throws InterruptedException
     */
    public static void test_allow_core_timeout() throws InterruptedException {
        execute(threadPoolExecutorService3_10, 10, FunctionUtil.newBlankFunction());

        LOGGER.info("core size: {}, pool size: {}", threadPoolExecutorService3_10.getCorePoolSize(), threadPoolExecutorService3_10.getPoolSize());

        threadPoolExecutorService3_10.allowCoreThreadTimeOut(true);

        Thread.sleep(15000);

        LOGGER.info("core size: {}, pool size: {}", threadPoolExecutorService3_10.getCorePoolSize(), threadPoolExecutorService3_10.getPoolSize());

        threadPoolExecutorService3_10.shutdown();
    }

    /**
     * 预启动核心线程
     */
    public static void test_prestart_core() {
        threadPoolExecutorService3_10.prestartCoreThread();

        LOGGER.info("core size: {}, pool size: {}", threadPoolExecutorService3_10.getCorePoolSize(), threadPoolExecutorService3_10.getPoolSize());

        threadPoolExecutorService3_10.prestartAllCoreThreads();

        LOGGER.info("core size: {}, pool size: {}", threadPoolExecutorService3_10.getCorePoolSize(), threadPoolExecutorService3_10.getPoolSize());

        threadPoolExecutorService3_10.shutdown();
    }

    /**
     * 20个任务运行在[3, 10]的线程池中运行，睡眠[0, 2]秒钟，获取一次已完成的任务数
     * 再运行20个任务，睡眠[0, 2]秒钟，再获取一次已完成的任务数
     *
     * @throws InterruptedException
     */
    public static void test_completed_task() throws InterruptedException {
        try {
            execute(threadPoolExecutorService3_10, 20, FunctionUtil.newSleepRandomFunction(0, 1000));
        } catch (Exception e) {
            LOGGER.error("catch e: {}", e);
        }

        Thread.sleep(RandomUtil.randomLong(0, 2000, true));

        LOGGER.info("completed task: {}", threadPoolExecutorService3_10.getCompletedTaskCount());


        try {
            execute(threadPoolExecutorService3_10, 20, FunctionUtil.newSleepRandomFunction(0, 1000));
        } catch (Exception e) {
            LOGGER.error("catch e: {}", e);
        }

        Thread.sleep(RandomUtil.randomLong(0, 2000, true));

        LOGGER.info("completed task: {}", threadPoolExecutorService3_10.getCompletedTaskCount());

        threadPoolExecutorService3_10.shutdown();
    }

    /**
     * 20个任务运行在[3, 10]的线程池，获取一次运行任务数和提交的任务数，
     * 睡眠3秒后，再提交20个任务，再获取一次运行任务数和提交的任务数
     *
     * @throws InterruptedException
     */
    public static void test_task_count() throws InterruptedException {
        try {
            execute(threadPoolExecutorService3_10, 20, FunctionUtil.newSleepRandomFunction(0, 1000));
        } catch (Exception e) {
            LOGGER.error("catch e: {}", e);
        }
        LOGGER.info("active count: {}, task count: {}", threadPoolExecutorService3_10.getActiveCount(), threadPoolExecutorService3_10.getTaskCount());

        Thread.sleep(RandomUtil.randomLong(0, 3000, true));

        try {
            execute(threadPoolExecutorService3_10, 20, FunctionUtil.newSleepRandomFunction(0, 1000));
        } catch (Exception e) {
            LOGGER.error("catch e: {}", e);
        }

        Thread.sleep(RandomUtil.randomLong(0, 1000, true));

        LOGGER.info("active count: {}, task count: {}", threadPoolExecutorService3_10.getActiveCount(), threadPoolExecutorService3_10.getTaskCount());

        threadPoolExecutorService3_10.shutdown();
    }

    /**
     * 测试线程开始执行、结束执行时执行一些操作，线程池结束时执行一些操作
     */
    public static void test_before_after_execute() {
        threadPoolExecutorService3_10.beforeAfterExecute(RunnableFactory.newBlankRunnable());
        threadPoolExecutorService3_10.beforeAfterExecute(RunnableFactory.newBlankRunnable());
        threadPoolExecutorService3_10.beforeAfterExecute(RunnableFactory.newBlankRunnable());
        threadPoolExecutorService3_10.beforeAfterExecute(RunnableFactory.newBlankRunnable());
        threadPoolExecutorService3_10.beforeAfterExecute(RunnableFactory.newBlankRunnable());
        threadPoolExecutorService3_10.beforeAfterExecute(RunnableFactory.newBlankRunnable());

        threadPoolExecutorService3_10.shutdown();
    }

    /**
     * 10个任务，通过Runnable提交后，返回Future没有返回值
     */
    public static void test_submit_runnable() {
        Map<Integer, Future> futures = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            int taskId = RunnableFactory.getTaskId();
            futures.put(taskId, threadPoolExecutorService3_10.submit(RunnableFactory.newBlankRunnable(taskId)));
        }

        for (Map.Entry<Integer, Future> entry : futures.entrySet()) {
            try {
                LOGGER.info("task: {}, return: {}", entry.getKey(), entry.getValue().get());
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.info("task: {}, error: {}", entry.getKey(), e);
            }
        }

        threadPoolExecutorService3_10.shutdown();
    }

    public static void test_submit_runnable_with_result() {
        Map<Integer, Future> futures = new HashMap<>();
    }

    /**
     * 10个任务，通过Callable提交后，通过Future获取返回值
     */
    public static void test_submit_callable() {
        Map<Integer, Future> futures = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            int taskId = RunnableFactory.getTaskId();
            futures.put(taskId, threadPoolExecutorService3_10.submit(CallableFactory.newBlankCallable(taskId)));
        }

        for (Map.Entry<Integer, Future> entry : futures.entrySet()) {
            try {
                LOGGER.info("task: {}, return: {}", entry.getKey(), entry.getValue().get());
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.info("task: {}, error: {}", entry.getKey(), e);
            }
        }

        threadPoolExecutorService3_10.shutdown();
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
