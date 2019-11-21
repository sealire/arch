package org.leesia.concurrent.taskfactory;

import org.leesia.concurrent.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class RunnableFactory {

    private static Logger LOGGER = LoggerFactory.getLogger(RunnableFactory.class);

    @Deprecated
    public static Runnable newRunnable(Function function) {
        return () -> {
            int taskId = getTaskId(null);

            LOGGER.info("taskId: {} started, ThreadName: {{}}", taskId, Thread.currentThread().getName());

            FunctionFactory.apply(function, taskId, taskId);

            LOGGER.info("taskId: {} end, ThreadName: {{}}", taskId, Thread.currentThread().getName());
        };
    }

    public static Runnable newRunnable(Function function, Integer taskId) {
        return () -> {
            Integer tid = getTaskId(taskId);

            LOGGER.info("taskId: {} started, ThreadName: {{}}", tid, Thread.currentThread().getName());

            FunctionFactory.apply(function, tid, tid);

            LOGGER.info("taskId: {} end, ThreadName: {{}}", tid, Thread.currentThread().getName());
        };
    }

    @Deprecated
    public static Runnable newBlankRunnable() {
        return () -> {
            int taskId = getTaskId(null);
            LOGGER.info("taskId: {} started, ThreadName: {{}}", taskId, Thread.currentThread().getName());
            LOGGER.info("taskId: {} end, ThreadName: {{}}", taskId, Thread.currentThread().getName());
        };
    }

    public static Runnable newBlankRunnable(Integer taskId) {
        return () -> {
            Integer tid = getTaskId(taskId);
            LOGGER.info("taskId: {} started, ThreadName: {{}}", tid, Thread.currentThread().getName());
            LOGGER.info("taskId: {} end, ThreadName: {{}}", tid, Thread.currentThread().getName());
        };
    }

    public static Runnable checkInterrupted(Runnable runnable) {
        return () -> {
            int taskId = getTaskId(null);
            try {
                if (Thread.currentThread().isInterrupted()) {

                    throw new InterruptedException();
                }

                runnable.run();

                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException();
                }
            } catch (InterruptedException e) {
                LOGGER.error("taskId: {} interrupted, ThreadName: {{}}", taskId, Thread.currentThread().getName());
            }
        };
    }

    public static <T> CustomRunnable newCustomRunnable(Integer taskId, Function function, T t) {
        return new CustomRunnable<T>(taskId, function, t);
    }

    static class CustomRunnable<T> implements Runnable {

        private Integer taskId;

        private T result;

        private Function function;

        public CustomRunnable(Integer taskId, Function function, T result) {
            this.taskId = getTaskId(taskId);
            this.function = function;
            this.result = result;
        }

        @Override
        public void run() {
            LOGGER.info("taskId: {} started, ThreadName: {{}}", taskId, Thread.currentThread().getName());

            FunctionFactory.apply(function, result, taskId);

            LOGGER.info("taskId: {} end, ThreadName: {{}}", taskId, Thread.currentThread().getName());
        }
    }

    public static Integer getTaskId(Integer taskId) {
        return taskId == null
                ? RandomUtil.randomInt(0, 100000000, true)
                : taskId;
    }
}
