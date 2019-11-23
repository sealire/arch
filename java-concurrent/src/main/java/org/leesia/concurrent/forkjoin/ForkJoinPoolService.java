package org.leesia.concurrent.forkjoin;

import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: ForkJoinPoolTest
 * @Description:
 * @author: leesia
 * @date: 2019/11/21 12:56
 */
public class ForkJoinPoolService {

    private ForkJoinPool pool;

    public ForkJoinPoolService() {
        pool = new ForkJoinPool();
    }

    public ForkJoinPoolService(int parallelism) {
        pool = new ForkJoinPool(parallelism);
    }

    public ForkJoinPoolService(int parallelism,
                               ForkJoinPool.ForkJoinWorkerThreadFactory factory,
                               Thread.UncaughtExceptionHandler handler,
                               boolean asyncMode) {
        pool = new ForkJoinPool(parallelism, factory, handler, asyncMode);
    }

    /**
     * 执行任务
     *
     * @param task
     */
    public void execute(Runnable task) {
        pool.execute(task);
    }

    /**
     * 执行任务
     *
     * @param task
     */
    public void execute(ForkJoinTask<?> task) {
        pool.execute(task);
    }

    /**
     * 提交任务
     *
     * @param task
     * @param <T>
     * @return
     */
    public <T> ForkJoinTask<T> submit(ForkJoinTask<T> task) {
        return pool.submit(task);
    }

    /**
     * 提交任务
     *
     * @param task
     * @return
     */
    public ForkJoinTask<?> submit(Runnable task) {
        return pool.submit(task);
    }

    /**
     * 提交任务
     *
     * @param task
     * @param <T>
     * @return
     */
    public <T> ForkJoinTask<T> submit(Callable<T> task) {
        return pool.submit(task);
    }

    /**
     * 提交任务
     *
     * @param task
     * @param result
     * @param <T>
     * @return
     */
    public <T> ForkJoinTask<T> submit(Runnable task, T result) {
        return pool.submit(task, result);
    }

    /**
     * 关闭线程池
     */
    public void shutdown() {
        pool.shutdown();
    }

    /**
     * 关闭线程池
     */
    public void shutdownNow() {
        pool.shutdownNow();
    }

    /**
     * 是否关闭
     *
     * @return
     */
    public boolean isTerminated() {
        return pool.isTerminated();
    }

    /**
     * 是否关闭
     *
     * @return
     */
    public boolean isTerminating() {
        return pool.isTerminating();
    }

    /**
     * 是否关闭
     *
     * @return
     */
    public boolean isShutdown() {
        return pool.isShutdown();
    }

    /**
     * 阻塞等待关闭
     *
     * @param timeout
     * @param unit
     * @throws InterruptedException
     */
    public void awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        pool.awaitTermination(timeout, unit);
    }
}
