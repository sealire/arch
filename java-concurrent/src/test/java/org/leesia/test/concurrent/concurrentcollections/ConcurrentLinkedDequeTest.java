package org.leesia.test.concurrent.concurrentcollections;

import org.leesia.concurrent.concurrentcollections.ConcurrentLinkedDequeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: ConcurrentLinkedDequeTest
 * @Description:
 * @author: leesia
 * @date: 2019/11/27 9:41
 */
public class ConcurrentLinkedDequeTest {

    private static Logger LOGGER = LoggerFactory.getLogger(ConcurrentLinkedDequeTest.class);

    private static ConcurrentLinkedDequeService<String> concurrentLinkedDequeService = new ConcurrentLinkedDequeService<>();

    public static void main(String[] args) throws Exception {
        test_peek();

        LOGGER.info("main exit");
    }

    public static void test_poll() {
        concurrentLinkedDequeService.add("" + 1);
        concurrentLinkedDequeService.addFirst("" + 2);
        concurrentLinkedDequeService.addLast("" + 3);

        LOGGER.info("first: {}", concurrentLinkedDequeService.poll());
        LOGGER.info("first: {}", concurrentLinkedDequeService.pollFirst());
        LOGGER.info("last: {}", concurrentLinkedDequeService.pollLast());
        LOGGER.info("first: {}", concurrentLinkedDequeService.pollFirst());
        LOGGER.info("last: {}", concurrentLinkedDequeService.pollLast());
    }

    public static void test_peek() {
        concurrentLinkedDequeService.add("" + 1);
        concurrentLinkedDequeService.addFirst("" + 2);
        concurrentLinkedDequeService.addLast("" + 3);

        LOGGER.info("first: {}", concurrentLinkedDequeService.peek());
        LOGGER.info("first: {}", concurrentLinkedDequeService.peekFirst());
        LOGGER.info("last: {}", concurrentLinkedDequeService.peekLast());
        LOGGER.info("first: {}", concurrentLinkedDequeService.peekFirst());
        LOGGER.info("last: {}", concurrentLinkedDequeService.peekLast());
    }
}
