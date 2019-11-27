package org.leesia.test.concurrent.concurrentcollections;

import org.leesia.concurrent.concurrentcollections.ConcurrentHashMapService;
import org.leesia.concurrent.concurrentcollections.ConcurrentSkipListMapService;
import org.leesia.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @ClassName: ConcurrentSkipListMapTest
 * @Description:
 * @author: leesia
 * @date: 2019/11/26 19:37
 */
public class ConcurrentSkipListMapTest {

    private static Logger LOGGER = LoggerFactory.getLogger(ConcurrentSkipListMapTest.class);

    private static ConcurrentSkipListMapService<String, String> concurrentSkipListMapService = new ConcurrentSkipListMapService<>();

    public static void main(String[] args) throws Exception {
        test_sort();

        LOGGER.info("main exit");
    }

    public static void test_sort() {
        for (int i = 0; i < 100; i++) {
            concurrentSkipListMapService.put("" + i, RandomUtil.randomString(10));
        }

        for (int i = 0; i < 50; i++) {
            Map.Entry<String, String> entry = concurrentSkipListMapService.pollFirstEntry();
            LOGGER.info("key: {}, value: {}", entry.getKey(), entry.getValue());
        }
        for (int i = 0; i < 50; i++) {
            Map.Entry<String, String> entry = concurrentSkipListMapService.pollLastEntry();
            LOGGER.info("key: {}, value: {}", entry.getKey(), entry.getValue());
        }
    }
}
