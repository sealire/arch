package org.leesia.test.concurrent.executor;

import org.leesia.concurrent.executor.ScheduledThreadPoolExecutorService;
import org.leesia.concurrent.taskfactory.CallableFactory;
import org.leesia.concurrent.taskfactory.RunnableFactory;
import org.leesia.test.concurrent.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorTest {

    private static Logger LOGGER = LoggerFactory.getLogger(ScheduledThreadPoolExecutorTest.class);

    private static ScheduledThreadPoolExecutorService scheduledThreadPoolExecutorService_10 =
            new ScheduledThreadPoolExecutorService(10);

    public static void main(String[] args) throws Exception {
        test_schedule_with_fixed_delay();

        LOGGER.info("main exit");
    }

    /**
     * 1个任务，延时5秒执行
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void test_schedule_runnable() throws ExecutionException, InterruptedException {
        ScheduledFuture future = scheduledThreadPoolExecutorService_10.schedule(RunnableFactory.newBlankRunnable(null), 5, TimeUnit.SECONDS);

//        LOGGER.info("task return: {}", future.get());

//        scheduledThreadPoolExecutorService_10.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
        scheduledThreadPoolExecutorService_10.shutdown();
    }

    /**
     * 1个任务，延时5秒执行
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void test_schedule_callable() throws ExecutionException, InterruptedException {
        ScheduledFuture future = scheduledThreadPoolExecutorService_10.schedule(CallableFactory.newBlankCallable(null), 5, TimeUnit.SECONDS);

        LOGGER.info("task return: {}", future.get());

        scheduledThreadPoolExecutorService_10.shutdown();
    }

    /**
     * 1个任务，每3秒执行一次
     */
    public static void test_schedule_at_fixed_rate() {
        scheduledThreadPoolExecutorService_10.scheduleAtFixedRate(RunnableFactory.newBlankRunnable(null), 1, 3, TimeUnit.SECONDS);

        ThreadUtil.sleepRandom(4000, 10000);
        scheduledThreadPoolExecutorService_10.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
        scheduledThreadPoolExecutorService_10.shutdown();
    }

    /**
     * 1个任务，每间隔3秒执行一次
     */
    public static void test_schedule_with_fixed_delay() {
        scheduledThreadPoolExecutorService_10.scheduleWithFixedDelay(RunnableFactory.newBlankRunnable(null), 1, 3, TimeUnit.SECONDS);

        ThreadUtil.sleepRandom(4000, 10000);
        scheduledThreadPoolExecutorService_10.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
        scheduledThreadPoolExecutorService_10.shutdown();
    }
}
