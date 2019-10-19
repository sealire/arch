package org.leesia.concurrent.util;

import org.leesia.concurrent.thread.RunnableFactory;

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
                threads[i] = new Thread(RunnableFactory.newBlankRunnable(), "thread-name-" + (i + 1));
            } else {
                threads[i] = new Thread(RunnableFactory.newRunnable(function), "thread-name-" + (i + 1));
            }
        }

        for (Thread thread : threads) {
            thread.start();
        }

        return threads;
    }
}
