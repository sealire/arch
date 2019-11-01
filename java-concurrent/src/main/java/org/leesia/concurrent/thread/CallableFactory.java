package org.leesia.concurrent.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.function.Function;

public class CallableFactory {

    private static Logger LOGGER = LoggerFactory.getLogger(CallableFactory.class);

    public static Callable newCallable(Function function, Integer taskId) {
        return () -> {
            Integer tid = taskId;
            if (tid == null) {
                tid = RunnableFactory.getTaskId();
            }
            LOGGER.info("Thread: {} started, taskId: {{}}", Thread.currentThread().getName(), tid);

            function.apply(tid);

            LOGGER.info("Thread: {} end", Thread.currentThread().getName());

            return tid;
        };
    }

    public static Callable newBlankCallable(Integer taskId) {
        return () -> {
            Integer tid = taskId;
            if (tid == null) {
                tid = RunnableFactory.getTaskId();
            }
            LOGGER.info("Thread: {} started, taskId: {{}}", Thread.currentThread().getName(), tid);
            LOGGER.info("Thread: {} end", Thread.currentThread().getName());

            return tid;
        };
    }
}
