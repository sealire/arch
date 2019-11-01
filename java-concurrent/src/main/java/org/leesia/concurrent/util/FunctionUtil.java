package org.leesia.concurrent.util;

import org.leesia.concurrent.thread.RunnableFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class FunctionUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(RunnableFactory.class);

    public static Function newSleepFunction(long sleep) {
        return taskId -> {
            LOGGER.info("Function: {}, taskId: {}", Thread.currentThread().getName(), taskId);

            try {
                LOGGER.info("Thread: {} sleep {}ms", Thread.currentThread().getName(), sleep);
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                LOGGER.info("Thread: {} interrupted on sleep Function", Thread.currentThread().getName());
            }

            return taskId;
        };
    }

    public static Function newSleepRandomFunction(int min, int max) {
        return taskId -> {
            LOGGER.info("Function: {}, taskId: {}", Thread.currentThread().getName(), taskId);

            try {
                int sleep = RandomUtil.randomInt(min, max, true);
                LOGGER.info("Thread: {} sleep {}ms", Thread.currentThread().getName(), sleep);
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                LOGGER.info("Thread: {} interrupted on sleep Function", Thread.currentThread().getName());
            }

            return taskId;
        };
    }

    public static Function newBlankFunction() {
        return taskId -> {
            LOGGER.info("Function: {}, taskId: {}", Thread.currentThread().getName(), taskId);
            return taskId;
        };
    }

    public static void apply(Function function) {
        if (function == null) {
            FunctionUtil.newBlankFunction().apply(null);
        } else {
            function.apply(null);
        }
    }
}
