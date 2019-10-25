package org.leesia.concurrent.util;

import org.leesia.concurrent.thread.RunnableFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class FunctionUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(RunnableFactory.class);

    public static Function newSleepFunction(long sleep) {
        return x -> {
            LOGGER.info("Function: {} started", Thread.currentThread().getName());

            try {
                LOGGER.info("Thread: {} sleep {}ms", Thread.currentThread().getName(), sleep);
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                LOGGER.info("Thread: {} interrupted on sleep Function", Thread.currentThread().getName());
            }

            LOGGER.info("Function: {} end", Thread.currentThread().getName());
            return x;
        };
    }

    public static Function newSleepRandomFunction(int min, int max) {
        return x -> {
            LOGGER.info("Function: {} started", Thread.currentThread().getName());

            try {
                int sleep = RandomUtil.randomInt(min, max, true);
                LOGGER.info("Thread: {} sleep {}ms", Thread.currentThread().getName(), sleep);
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                LOGGER.info("Thread: {} interrupted on sleep Function", Thread.currentThread().getName());
            }

            LOGGER.info("Function: {} end", Thread.currentThread().getName());
            return x;
        };
    }

    public static Function newBlankFunction() {
        return x -> {
            LOGGER.info("Function: {} started", Thread.currentThread().getName());
            LOGGER.info("Function: {} function blank", Thread.currentThread().getName());
            LOGGER.info("Function: {} end", Thread.currentThread().getName());
            return x;
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
