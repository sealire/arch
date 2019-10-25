package org.leesia.concurrent.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ExecutorsService {

    /**
     * 创建无界线程池
     *
     * @return
     */
    public static ExecutorService newCachedThreadPool() {
        return Executors.newCachedThreadPool();
    }

    /**
     * 指定线程工厂创建无界线程池
     *
     * @param threadFactory
     * @return
     */
    public static ExecutorService newCachedThreadPool(ThreadFactory threadFactory) {
        return Executors.newCachedThreadPool(threadFactory);
    }

    /**
     * 创建有界线程池
     *
     * @param nThreads
     * @return
     */
    public static ExecutorService newFixedThreadPool(int nThreads) {
        return Executors.newFixedThreadPool(nThreads);
    }

    /**
     * 指定线程工厂创建有界线程池
     *
     * @param nThreads
     * @param threadFactory
     * @return
     */
    public static ExecutorService newFixedThreadPool(int nThreads, ThreadFactory threadFactory) {
        return Executors.newFixedThreadPool(nThreads, threadFactory);
    }

    /**
     * 创建单一线程的线程池
     *
     * @return
     */
    public static ExecutorService newSingleThreadExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    /**
     * 指定线程工厂创建单一线程的线程池
     *
     * @param threadFactory
     * @return
     */
    public static ExecutorService newSingleThreadExecutor(ThreadFactory threadFactory) {
        return Executors.newSingleThreadExecutor(threadFactory);
    }

    /**
     * 创建周期执行线程池
     *
     * @param nThreads
     * @return
     */
    public static ExecutorService newScheduledThreadPool(int nThreads) {
        return Executors.newScheduledThreadPool(nThreads);
    }
}
