package org.leesia.test.concurrent.basic;

import org.leesia.concurrent.basic.ThreadLocalService;
import org.leesia.concurrent.taskfactory.ThreadFactory;
import org.leesia.concurrent.vo.Task;
import org.leesia.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ThreadLocalTest
 * @Description:
 * @author: leesia
 * @date: 2019/12/3 16:40
 */
public class ThreadLocalTest {

    private static Logger LOGGER = LoggerFactory.getLogger(ThreadLocalTest.class);

    private static ThreadLocalService<List<String>> threadLocalService = new ThreadLocalService<>();

    public static void main(String[] args) throws Exception {
        test_thread_local();

        LOGGER.info("main exit");
    }

    public static void test_thread_local() {
        Task<ThreadLocalService<List<String>>, Integer> task = new Task<>(service -> {
            List<String> strings = new ArrayList<>();
            for (int i = 0; i < RandomUtil.randomInt(10, 30, true); i++) {
                strings.add(RandomUtil.randomString(10));
            }
            service.set(strings);

            LOGGER.info("size: {}", service.get().size());

            return 0;
        }, threadLocalService);

        Thread thread1 = ThreadFactory.newThread(task);
        Thread thread2 = ThreadFactory.newThread(task);
        Thread thread3 = ThreadFactory.newThread(task);

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
