package org.leesia.test.concurrent.concurrentcollections;

import org.leesia.concurrent.concurrentcollections.ConcurrentHashMapService;
import org.leesia.concurrent.taskfactory.RunnableFactory;
import org.leesia.concurrent.vo.Task;
import org.leesia.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: ConcurrentHashMapTest
 * @Description:
 * @author: leesia
 * @date: 2019/11/26 11:25
 */
public class ConcurrentHashMapTest {

    private static Logger LOGGER = LoggerFactory.getLogger(ConcurrentHashMapTest.class);

    private static ConcurrentHashMapService<String, String> concurrentHashMapService = new ConcurrentHashMapService<>();

    public static void main(String[] args) throws Exception {
        test_sync();

        LOGGER.info("main exit");
    }

    public static void test_sync() throws InterruptedException {
        Task<ConcurrentHashMapService<String, String>, Integer> task = new Task<>(service -> {
            for (int i = 0; i < 100000; i++) {
                service.put(RandomUtil.randomString(10), RandomUtil.randomString(10));
            }
            return service.size();
        }, concurrentHashMapService);

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
