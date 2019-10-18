package org.leesia.concurrent.util;

import org.leesia.concurrent.thread.RunnableFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class FunctionUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(RunnableFactory.class);

    public static Function newSleepFunction(long sleep) {
        return x -> {
            LOGGER.info("Thread: {} started", Thread.currentThread().getName());

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            LOGGER.info("Thread: {} end", Thread.currentThread().getName());
            return x;
        };
    }
}
