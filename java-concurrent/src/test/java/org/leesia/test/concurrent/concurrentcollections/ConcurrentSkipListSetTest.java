package org.leesia.test.concurrent.concurrentcollections;

import org.leesia.concurrent.concurrentcollections.ConcurrentSkipListSetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: ConcurrentSkipListSetTest
 * @Description:
 * @author: leesia
 * @date: 2019/11/26 20:05
 */
public class ConcurrentSkipListSetTest {

    private static Logger LOGGER = LoggerFactory.getLogger(ConcurrentSkipListSetTest.class);

    private static ConcurrentSkipListSetService<String> concurrentSkipListSetService = new ConcurrentSkipListSetService<>();

    public static void main(String[] args) throws Exception {
        test_sort();

        LOGGER.info("main exit");
    }

    public static void test_sort() {
        for (int i = 0; i < 100; i++) {
            concurrentSkipListSetService.add("" + i);
        }

        for (int i = 0; i < 50; i++) {
            String value = concurrentSkipListSetService.pollFirst();
            LOGGER.info("value: {}", value);
        }

        for (int i = 0; i < 50; i++) {
            String value = concurrentSkipListSetService.pollLast();
            LOGGER.info("value: {}", value);
        }
    }
}
