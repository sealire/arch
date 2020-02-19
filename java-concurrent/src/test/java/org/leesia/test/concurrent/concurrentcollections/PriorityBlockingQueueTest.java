package org.leesia.test.concurrent.concurrentcollections;

import org.leesia.concurrent.concurrentcollections.PriorityBlockingQueueService;
import org.leesia.concurrent.taskfactory.ThreadFactory;
import org.leesia.concurrent.vo.Person;
import org.leesia.concurrent.vo.Task;
import org.leesia.test.concurrent.util.ThreadUtil;
import org.leesia.util.RandomUtil;
import org.leesia.util.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @ClassName: PriorityBlockingQueueTest
 * @Description:
 * @author: leesia
 * @date: 2019/12/2 13:19
 */
public class PriorityBlockingQueueTest {

    private static Logger LOGGER = LoggerFactory.getLogger(PriorityBlockingQueueTest.class);

    private static PriorityBlockingQueueService<Person> priorityBlockingQueueService = new PriorityBlockingQueueService<>(5);

    public static void main(String[] args) throws Exception {
        test_priority();

        LOGGER.info("main exit");
    }

    public static void test_priority() {
        Task<PriorityBlockingQueueService<Person>, Integer> putTask = new Task<>(service -> {
            for (int i = 0; i < 20; i++) {
                try {
                    Person person = new Person(RandomUtil.randomString(10), RandomUtil.randomInt(10, 60, true), RandomUtil.randomString(10));

                    LOGGER.info("try to put ele: {} on {}", person, DateUtil.format(new Date(), null));
                    service.put(person);
                    LOGGER.info("put ele: {} on {}", person, DateUtil.format(new Date(), null));

                    ThreadUtil.sleepRandom(1000, 3000);
                } catch (Exception e) {
                    LOGGER.error("queue offer error: {}", e.getMessage(), e);
                }
            }

            return service.size();
        }, priorityBlockingQueueService);

        Task<PriorityBlockingQueueService<Person>, Integer> takeTask = new Task<>(service -> {
            for (int i = 0; i < 20; i++) {
                try {
                    LOGGER.info("try to take ele on {}", DateUtil.format(new Date(), null));
                    Person person = service.take();
                    LOGGER.info("ele take: {} on {}", person, DateUtil.format(new Date(), null));

                    ThreadUtil.sleepRandom(2000, 10000);
                } catch (Exception e) {
                    LOGGER.error("queue take error: {}", e.getMessage(), e);
                }
            }

            return service.size();
        }, priorityBlockingQueueService);

        Thread putThread = ThreadFactory.newThread(putTask);
        Thread takeThread = ThreadFactory.newThread(takeTask);

        putThread.start();
        takeThread.start();
    }
}
