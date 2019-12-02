package org.leesia.test.concurrent.concurrentcollections;

import org.leesia.concurrent.concurrentcollections.CopyOnWriteArrayListService;
import org.leesia.concurrent.taskfactory.RunnableFactory;
import org.leesia.concurrent.vo.Task;
import org.leesia.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: CopyOnWriteArrayListTest
 * @Description:
 * @author: leesia
 * @date: 2019/11/27 11:41
 */
public class CopyOnWriteArrayListTest {

    private static Logger LOGGER = LoggerFactory.getLogger(CopyOnWriteArrayListTest.class);

    private static CopyOnWriteArrayListService<String> copyOnWriteArrayListService = new CopyOnWriteArrayListService<>();

    public static void main(String[] args) throws Exception {
        test_sync();

        LOGGER.info("main exit");
    }

    public static void test_sync() throws InterruptedException {
        Task<CopyOnWriteArrayListService<String>, Integer> task = new Task<>(service -> {
            for (int i = 0; i < 10000; i++) {
                service.add(RandomUtil.randomString(10));
            }
            return service.size();
        }, copyOnWriteArrayListService);

        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(RunnableFactory.newRunnable(task));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        LOGGER.info("result: {}", task.getResult());
    }
}
