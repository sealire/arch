package org.leesia.concurrent.thread;

import org.leesia.concurrent.util.RandomUtil;
import org.leesia.concurrent.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class RunnableFactory {

    private static Logger LOGGER = LoggerFactory.getLogger(RunnableFactory.class);

    @Deprecated
    public static Runnable newRunnable(Function function) {
        return () -> {
            int taskId = getTaskId();
            LOGGER.info("Thread: {} started, taskId: {{}}", Thread.currentThread().getName(), taskId);

            function.apply(taskId);

            LOGGER.info("Thread: {} end", Thread.currentThread().getName());
        };
    }

    public static Runnable newRunnable(Function function, Integer taskId) {
        return () -> {
            Integer tid = taskId;
            if (tid == null) {
                tid = getTaskId();
            }
            LOGGER.info("Thread: {} started, taskId: {{}}", Thread.currentThread().getName(), tid);

            function.apply(tid);

            LOGGER.info("Thread: {} end", Thread.currentThread().getName());
        };
    }

    @Deprecated
    public static Runnable newBlankRunnable() {
        return () -> {
            LOGGER.info("Thread: {} started, taskId: {{}}", Thread.currentThread().getName(), getTaskId());
            LOGGER.info("Thread: {} end", Thread.currentThread().getName());
        };
    }

    public static Runnable newBlankRunnable(Integer taskId) {
        return () -> {
            Integer tid = taskId;
            if (tid == null) {
                tid = getTaskId();
            }
            LOGGER.info("Thread: {} started, taskId: {{}}", Thread.currentThread().getName(), tid);
            LOGGER.info("Thread: {} end", Thread.currentThread().getName());
        };
    }

    public static Runnable checkInterrupted(Runnable runnable) {
        return () -> {
            try {
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException("Thread " + Thread.currentThread().getName() + " interrupted");
                }

                runnable.run();

                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException("Thread " + Thread.currentThread().getName() + " interrupted");
                }
            } catch (InterruptedException e) {
                LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
            }
        };
    }

    public static CustomRunnable newCustomRunnable(Function function, Integer taskId) {
        return new CustomRunnable<Result>();
    }

    static class CustomRunnable<T> implements Runnable {

        private T t;

        public CustomRunnable() {

        }

        @Override
        public void run() {

        }
    }

    public static int getTaskId() {
        return RandomUtil.randomInt(0, 100000000, true);
    }
}
