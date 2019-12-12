package org.leesia.concurrent.taskfactory;

import org.leesia.concurrent.vo.Task;
import org.leesia.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * @ClassName: ThreadFactory
 * @Description:
 * @author: leesia
 * @date: 2019/12/2 9:20
 */
public class ThreadFactory {

    private static Logger LOGGER = LoggerFactory.getLogger(ThreadFactory.class);

    public static Thread newThread(Task task) {
        return new Thread(RunnableFactory.newRunnable(task), task.getThreadName());
    }

    public static Thread newThread(Consumer consumer) {
        return new Thread(RunnableFactory.newRunnable(consumer), getThreadName());
    }

    public static String getThreadName() {
        return "thread-" + RandomUtil.randomNumberString(10);
    }
}
