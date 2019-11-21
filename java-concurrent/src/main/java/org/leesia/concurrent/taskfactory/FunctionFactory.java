package org.leesia.concurrent.taskfactory;

import org.leesia.concurrent.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 * @ClassName: FunctionFactory
 * @Description: Function工厂类
 * @author: leesia
 * @date: 2019/11/19 17:28
 */
public class FunctionFactory {

    private static Logger LOGGER = LoggerFactory.getLogger(RunnableFactory.class);

    public static Function newSleepFunction(long sleep) {
        return taskId -> {

            LOGGER.info("taskId: {} sleep {}ms in newSleepFunction, ThreadName: {{}}", taskId, sleep, Thread.currentThread().getName());
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                LOGGER.info("taskId: {} interrupted in sleep newSleepFunction, ThreadName: {{}}", taskId, Thread.currentThread().getName());
            }

            return taskId;
        };
    }

    public static Function newSleepRandomFunction(long min, long max) {
        return taskId -> {
            long sleep = RandomUtil.randomLong(min, max, true);
            LOGGER.info("taskId: {} sleep {}ms in newSleepRandomFunction, ThreadName: {{}}", taskId, sleep, Thread.currentThread().getName());

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                LOGGER.info("taskId: {} interrupted in sleep newSleepRandomFunction, ThreadName: {{}}", taskId, Thread.currentThread().getName());
            }

            return taskId;
        };
    }

    public static Function newBlankFunction() {
        return taskId -> {
            LOGGER.info("taskId: {} run blank funtion, ThreadName: {{}}", taskId, Thread.currentThread().getName());
            return taskId;
        };
    }

    @Deprecated
    public static void apply(Function function) {
        if (function == null) {
            FunctionFactory.newBlankFunction().apply(RunnableFactory.getTaskId(null));
        } else {
            function.apply(null);
        }
    }

    @Deprecated
    public static <T> void apply(Function function, T result, Integer taskId) {
        if (function == null) {
            FunctionFactory.newBlankFunction().apply(RunnableFactory.getTaskId(taskId));
        } else {
            function.apply(result);
        }
    }
}
