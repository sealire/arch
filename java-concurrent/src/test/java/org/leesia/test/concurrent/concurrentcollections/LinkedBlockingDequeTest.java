package org.leesia.test.concurrent.concurrentcollections;

import org.leesia.concurrent.concurrentcollections.LinkedBlockingDequeService;
import org.leesia.concurrent.taskfactory.ThreadFactory;
import org.leesia.concurrent.vo.Task;
import org.leesia.test.concurrent.util.ThreadUtil;
import org.leesia.util.RandomUtil;
import org.leesia.util.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @ClassName: LinkedBlockingDequeTest
 * @Description:
 * @author: leesia
 * @date: 2019/12/2 13:59
 */
public class LinkedBlockingDequeTest {

    private static Logger LOGGER = LoggerFactory.getLogger(LinkedBlockingDequeTest.class);

    private static LinkedBlockingDequeService<String> linkedBlockingDequeService = new LinkedBlockingDequeService<>(10);

    public static void main(String[] args) throws Exception {
        test_take();

        LOGGER.info("main exit");
    }

    public static void test_take() {
        Task<LinkedBlockingDequeService<String>, Integer> putTask = new Task<>(service -> {
            for (int i = 0; i < 100; i++) {
                try {
                    String ele = RandomUtil.randomString(10);

                    LOGGER.info("try to put ele: {} on {}", ele, DateUtil.format(new Date(), null));
                    service.put(ele);
                    LOGGER.info("put ele: {} on {}", ele, DateUtil.format(new Date(), null));

                    ThreadUtil.sleepRandom(1000, 10000);
                } catch (Exception e) {
                    LOGGER.error("queue offer error: {}", e.getMessage(), e);
                }
            }

            return service.size();
        }, linkedBlockingDequeService);

        Task<LinkedBlockingDequeService<String>, Integer> takeTask = new Task<>(service -> {
            for (int i = 0; i < 100; i++) {
                try {
                    LOGGER.info("try to take ele on {}", DateUtil.format(new Date(), null));
                    String ele = service.take();
                    LOGGER.info("ele take: {} on {}", ele, DateUtil.format(new Date(), null));

                    ThreadUtil.sleepRandom(1000, 10000);
                } catch (Exception e) {
                    LOGGER.error("queue take error: {}", e.getMessage(), e);
                }
            }

            return service.size();
        }, linkedBlockingDequeService);

        Thread putThread = ThreadFactory.newThread(putTask);
        Thread takeThread = ThreadFactory.newThread(takeTask);

        putThread.start();
        takeThread.start();
    }
}
