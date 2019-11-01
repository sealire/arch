package org.leesia.concurrent.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolExecutorService {

    private static Logger LOGGER = LoggerFactory.getLogger(ThreadPoolExecutorService.class);

    private ThreadPoolExecutor executor;

    private MyExecutor myExecutor;

    public ThreadPoolExecutorService(int corePoolSize,
                                     int maximumPoolSize,
                                     long keepAliveTime,
                                     TimeUnit unit,
                                     BlockingQueue<Runnable> workQueue) {
        executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        myExecutor = new MyExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public ThreadPoolExecutorService(int corePoolSize,
                                     int maximumPoolSize,
                                     long keepAliveTime,
                                     TimeUnit unit,
                                     BlockingQueue<Runnable> workQueue,
                                     ThreadFactory threadFactory) {
        executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        myExecutor = new MyExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public ThreadPoolExecutorService(int corePoolSize,
                                     int maximumPoolSize,
                                     long keepAliveTime,
                                     TimeUnit unit,
                                     BlockingQueue<Runnable> workQueue,
                                     RejectedExecutionHandler handler) {
        executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        myExecutor = new MyExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public ThreadPoolExecutorService(int corePoolSize,
                                     int maximumPoolSize,
                                     long keepAliveTime,
                                     TimeUnit unit,
                                     BlockingQueue<Runnable> workQueue,
                                     ThreadFactory threadFactory,
                                     RejectedExecutionHandler handler) {
        executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        myExecutor = new MyExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    /**
     * 执行任务
     *
     * @param command
     */
    public void execute(Runnable command) {
        executor.execute(command);
    }

    /**
     * 提交任务
     *
     * @param task
     * @return
     */
    public Future<?> submit(Runnable task) {
        return executor.submit(task);
    }

    /**
     * 提交任务
     *
     * @param task
     * @return
     */
    public <T> Future<T> submit(Runnable task, T result) {
        return executor.submit(task, result);
    }

    /**
     * 提交任务
     *
     * @param task
     * @return
     */
    public <T> Future<T> submit(Callable<T> task) {
        return executor.submit(task);
    }

    /**
     * 关闭线程池
     */
    public void shutdown() {
        executor.shutdown();
        myExecutor.shutdown();
    }

    /**
     * 立即关闭线程池
     *
     * @return
     */
    public List<Runnable> shutdownNow() {
        return executor.shutdownNow();
    }

    /**
     * 判断线程池是否被关闭
     *
     * @return
     */
    public boolean isShutdown() {
        return executor.isShutdown();
    }

    /**
     * 判断线程池是否正在等待关闭
     *
     * @return
     */
    public boolean isTerminating() {
        return executor.isTerminating();
    }

    /**
     * 判断线程池是否已关闭
     *
     * @return
     */
    public boolean isTerminated() {
        return executor.isTerminated();
    }

    /**
     * 查看在指定的时间之间，线程池是否已经终止工作，阻塞等待
     *
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     */
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return executor.awaitTermination(timeout, unit);
    }

    /**
     * 是否允许核心线程超时
     *
     * @param value
     */
    public void allowCoreThreadTimeOut(boolean value) {
        executor.allowCoreThreadTimeOut(value);
    }

    /**
     * 获取线程池大小
     *
     * @return
     */
    public int getPoolSize() {
        return executor.getPoolSize();
    }

    /**
     * 获取线程池核心池大小，corePoolSize参数
     *
     * @return
     */
    public int getCorePoolSize() {
        return executor.getCorePoolSize();
    }

    /**
     * 启动一个核心线程
     *
     * @return
     */
    public boolean prestartCoreThread() {
        return executor.prestartCoreThread();
    }

    /**
     * 启动全部核心线程
     *
     * @return
     */
    public int prestartAllCoreThreads() {
        return executor.prestartAllCoreThreads();
    }

    /**
     * 获取已经完成的任务
     *
     * @return
     */
    public long getCompletedTaskCount() {
        return executor.getCompletedTaskCount();
    }

    /**
     * 获取线程池最大大小，maximumPoolSize参数
     *
     * @return
     */
    public int getMaximumPoolSize() {
        return executor.getMaximumPoolSize();
    }

    /**
     * 取得有多少个线程正执行任务
     *
     * @return
     */
    public int getActiveCount() {
        return executor.getActiveCount();
    }

    /**
     * 取得有多少个任务发送给了线程池
     *
     * @return
     */
    public long getTaskCount() {
        return executor.getTaskCount();
    }

    /**
     * 测试线程池执行任务前、后执行一些操作
     *
     * @param command
     */
    public void beforeAfterExecute(Runnable command) {
        myExecutor.execute(command);
    }

    /**
     * 删除execute提交的且尚未被执行的任务
     *
     * @param task
     * @return
     */
    public boolean remove(Runnable task) {
        return executor.remove(task);
    }

    class MyExecutor extends ThreadPoolExecutor {

        public MyExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        public MyExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        }

        public MyExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        }

        public MyExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }

        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            super.beforeExecute(t, r);

            LOGGER.info("准备执行: {}", t.getName());
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);

            LOGGER.info("执行完成: {}, e: {}", Thread.currentThread().getName(), t);
        }

        @Override
        protected void terminated() {
            super.terminated();

            LOGGER.info("pool terminated");
        }
    }
}
