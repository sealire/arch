package org.leesia.concurrent.taskfactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.function.Function;

public class CallableFactory {

    private static Logger LOGGER = LoggerFactory.getLogger(CallableFactory.class);

    public static Callable newCallable(Function function, Integer taskId) {
        return () -> {
            Integer tid = RunnableFactory.getTaskId(taskId);

            LOGGER.info("taskId: {} started, ThreadName: {{}}", tid, Thread.currentThread().getName());

            FunctionFactory.apply(function, tid, tid);

            LOGGER.info("taskId: {} end, ThreadName: {{}}", tid, Thread.currentThread().getName());

            return tid;
        };
    }

    public static Callable newBlankCallable(Integer taskId) {
        return () -> {
            Integer tid = RunnableFactory.getTaskId(taskId);

            LOGGER.info("taskId: {} started, ThreadName: {{}}", tid, Thread.currentThread().getName());
            LOGGER.info("taskId: {} end, ThreadName: {{}}", tid, Thread.currentThread().getName());

            return tid;
        };
    }
}
