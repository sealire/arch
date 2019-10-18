package org.leesia.concurrent.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class RunnableFactory {

    private static Logger LOGGER = LoggerFactory.getLogger(RunnableFactory.class);

    public static Runnable newRunnable(Function function) {
        return () -> function.apply(null);
    }

    public static Runnable newBlankRunnable() {
        return () -> LOGGER.info("Thread: {} run blank", Thread.currentThread().getName());
    }
}
