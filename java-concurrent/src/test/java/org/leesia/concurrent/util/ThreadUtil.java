package org.leesia.concurrent.util;

import org.leesia.concurrent.thread.RunnableFactory;

import java.util.concurrent.ExecutorService;
import java.util.function.Function;

/**
 * @Auther: leesia
 * @Date: 2019/10/19 22:27
 * @Description:
 */
public class ThreadUtil {

    /**
     * 启动多线程运行
     *
     * @param threadCount
     * @param function
     * @return
     */
    public static Thread[] run(int threadCount, Function function) {
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            if (function == null) {
                threads[i] = new Thread(RunnableFactory.newBlankRunnable(), getThreadName());
            } else {
                threads[i] = new Thread(RunnableFactory.newRunnable(function), getThreadName());
            }
        }

        for (Thread thread : threads) {
            thread.start();
        }

        return threads;
    }

    /**
     * 多线程在线程池中运行
     *
     * @param executor
     * @param threadCount
     * @param function
     * @return
     */
    public static void executeOnPool(ExecutorService executor, int threadCount, Function function) {
        for (int i = 0; i < threadCount; i++) {
            if (function == null) {
                executor.execute(RunnableFactory.newBlankRunnable());
            } else {
                executor.execute(RunnableFactory.newRunnable(function));
            }
        }
    }

    /**
     * 随机生成线程名称
     *
     * @return
     */
    public static String getThreadName() {
        return "thread-test-" + RandomUtil.randomInt(1000000, 10000000, false);
    }
}
