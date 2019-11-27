package org.leesia.test.concurrent.concurrentcollections;

import org.leesia.concurrent.concurrentcollections.ConcurrentLinkedQueueService;
import org.leesia.concurrent.concurrentcollections.ConcurrentSkipListSetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: ConcurrentLinkedQueueTest
 * @Description:
 * @author: leesia
 * @date: 2019/11/27 9:09
 */
public class ConcurrentLinkedQueueTest {

    private static Logger LOGGER = LoggerFactory.getLogger(ConcurrentLinkedQueueTest.class);

    private static ConcurrentLinkedQueueService<String> concurrentLinkedQueueService = new ConcurrentLinkedQueueService<>();

    public static void main(String[] args) throws Exception {
        test_element();

        LOGGER.info("main exit");
    }

    public static void test_poll() {
        String s = concurrentLinkedQueueService.poll();

        LOGGER.info("value: {}", s);
    }

    public static void test_peek() {
        String s = concurrentLinkedQueueService.peek();

        LOGGER.info("value: {}", s);
    }

    public static void test_element() {
        String s = concurrentLinkedQueueService.element();

        LOGGER.info("value: {}", s);
    }
}
