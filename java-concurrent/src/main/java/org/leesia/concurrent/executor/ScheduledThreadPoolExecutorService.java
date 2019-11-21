package org.leesia.concurrent.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class ScheduledThreadPoolExecutorService {

    private static Logger LOGGER = LoggerFactory.getLogger(ScheduledThreadPoolExecutorService.class);

    private static ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    public ScheduledThreadPoolExecutorService(int corePoolSize) {
        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(corePoolSize);
    }

    public ScheduledThreadPoolExecutorService(int corePoolSize, ThreadFactory threadFactory) {
        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(corePoolSize, threadFactory);
    }

    public ScheduledThreadPoolExecutorService(int corePoolSize, RejectedExecutionHandler handler) {
        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(corePoolSize, handler);
    }

    public ScheduledThreadPoolExecutorService(int corePoolSize, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(corePoolSize, threadFactory, handler);
    }

    /**
     * 延时执行任务
     *
     * @param command
     * @param delay
     * @param unit
     * @return
     */
    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return scheduledThreadPoolExecutor.schedule(command, delay, unit);
    }

    /**
     * 延时执行任务，并返回Future
     *
     * @param callable
     * @param delay
     * @param unit
     * @param <V>
     * @return
     */
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        return scheduledThreadPoolExecutor.schedule(callable, delay, unit);
    }

    /**
     * 固定的时间周期执行任务
     *
     * @param command
     * @param initialDelay
     * @param period
     * @param unit
     * @return
     */
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return scheduledThreadPoolExecutor.scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    /**
     * 固定的时间间隔周期执行任务
     *
     * @param command
     * @param initialDelay
     * @param delay
     * @param unit
     * @return
     */
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return scheduledThreadPoolExecutor.scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }

    /**
     * 设置当调用了shutdown()时，延迟任务是否继续运行
     *
     * @param value
     */
    public void setExecuteExistingDelayedTasksAfterShutdownPolicy(boolean value) {
        scheduledThreadPoolExecutor.setExecuteExistingDelayedTasksAfterShutdownPolicy(value);
    }

    /**
     * 设置当调用了shutdown()时，周期任务是否继续运行
     *
     * @param value
     */
    public void setContinueExistingPeriodicTasksAfterShutdownPolicy(boolean value) {
        scheduledThreadPoolExecutor.setContinueExistingPeriodicTasksAfterShutdownPolicy(value);
    }

    /**
     * 设置任务取消后，是否从队列中删除
     *
     * @param value
     */
    public void setRemoveOnCancelPolicy(boolean value) {
        scheduledThreadPoolExecutor.setRemoveOnCancelPolicy(value);
    }

    /**
     * 关闭线程池
     */
    public void shutdown() {
        scheduledThreadPoolExecutor.shutdown();
    }
}
