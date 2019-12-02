package org.leesia.test.concurrent.concurrentcollections;

import org.leesia.concurrent.concurrentcollections.ArrayBlockingQueueService;
import org.leesia.concurrent.concurrentcollections.SynchronousQueueService;
import org.leesia.concurrent.taskfactory.ThreadFactory;
import org.leesia.concurrent.vo.Task;
import org.leesia.test.concurrent.util.ThreadUtil;
import org.leesia.util.RandomUtil;
import org.leesia.util.date.LDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @ClassName: SynchronousQueueTest
 * @Description:
 * @author: leesia
 * @date: 2019/12/2 16:47
 */
public class SynchronousQueueTest {

    private static Logger LOGGER = LoggerFactory.getLogger(SynchronousQueueTest.class);

    private static SynchronousQueueService<String> synchronousQueueService = new SynchronousQueueService<>();

    public static void main(String[] args) throws Exception {
        test_put();

        LOGGER.info("main exit");
    }

    public static void test_add() {
        Task<SynchronousQueueService<String>, Integer> addTask = new Task<>(service -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    String ele = RandomUtil.randomString(10);

                    LOGGER.info("try to add ele: {} on {}", ele, LDateUtil.format(new Date(), null));
                    service.add(ele);
                    LOGGER.info("added ele: {} on {}", ele, LDateUtil.format(new Date(), null));

                    ThreadUtil.sleepRandom(1000, 10000);
                } catch (Exception e) {
                    LOGGER.error("queue add error: {}", e.getMessage(), e);
                }
            }

            return service.size();
        }, synchronousQueueService);

        Task<SynchronousQueueService<String>, Integer> removeTask = new Task<>(service -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    LOGGER.info("try to remove ele on {}", LDateUtil.format(new Date(), null));
                    String ele = service.remove();
                    LOGGER.info("ele removed: {} on {}", ele, LDateUtil.format(new Date(), null));

                    ThreadUtil.sleepRandom(1000, 10000);
                } catch (Exception e) {
                    LOGGER.error("queue remove error: {}", e.getMessage(), e);
                }
            }

            return service.size();
        }, synchronousQueueService);

        Thread addThread = ThreadFactory.newThread(addTask);
        Thread removeThread = ThreadFactory.newThread(removeTask);

        addThread.start();
        removeThread.start();
    }

    public static void test_offer() {
        Task<SynchronousQueueService<String>, Integer> offerTask = new Task<>(service -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    String ele = RandomUtil.randomString(10);

                    LOGGER.info("try to offer ele: {} on {}", ele, LDateUtil.format(new Date(), null));
                    boolean offered = service.offer(ele);
                    LOGGER.info("offer ele: {} on {}", (offered == true ? ele : null), LDateUtil.format(new Date(), null));

                    ThreadUtil.sleepRandom(1000, 10000);
                } catch (Exception e) {
                    LOGGER.error("queue offer error: {}", e.getMessage(), e);
                }
            }

            return service.size();
        }, synchronousQueueService);

        Task<SynchronousQueueService<String>, Integer> pollTask = new Task<>(service -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    LOGGER.info("try to poll ele on {}", LDateUtil.format(new Date(), null));
                    String ele = service.poll();
                    LOGGER.info("ele polled: {} on {}", ele, LDateUtil.format(new Date(), null));

                    ThreadUtil.sleepRandom(1000, 10000);
                } catch (Exception e) {
                    LOGGER.error("queue poll error: {}", e.getMessage(), e);
                }
            }

            return service.size();
        }, synchronousQueueService);

        Thread offerThread = ThreadFactory.newThread(offerTask);
        Thread pollThread = ThreadFactory.newThread(pollTask);

        offerThread.start();
        pollThread.start();
    }

    public static void test_put() {
        Task<SynchronousQueueService<String>, Integer> putTask = new Task<>(service -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    String ele = RandomUtil.randomString(10);

                    LOGGER.info("try to put ele: {} on {}", ele, LDateUtil.format(new Date(), null));
                    service.put(ele);
                    LOGGER.info("put ele: {} on {}", ele, LDateUtil.format(new Date(), null));

                    ThreadUtil.sleepRandom(1000, 10000);
                } catch (Exception e) {
                    LOGGER.error("queue offer error: {}", e.getMessage(), e);
                }
            }

            return service.size();
        }, synchronousQueueService);

        Task<SynchronousQueueService<String>, Integer> takeTask = new Task<>(service -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    LOGGER.info("try to take ele on {}", LDateUtil.format(new Date(), null));
                    String ele = service.take();
                    LOGGER.info("ele take: {} on {}", ele, LDateUtil.format(new Date(), null));

                    ThreadUtil.sleepRandom(1000, 10000);
                } catch (Exception e) {
                    LOGGER.error("queue take error: {}", e.getMessage(), e);
                }
            }

            return service.size();
        }, synchronousQueueService);

        Thread putThread = ThreadFactory.newThread(putTask);
        Thread takeThread = ThreadFactory.newThread(takeTask);

        putThread.start();
        takeThread.start();
    }
}
