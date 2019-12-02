package org.leesia.test.concurrent.concurrentcollections;

import org.leesia.concurrent.concurrentcollections.ArrayBlockingQueueService;
import org.leesia.concurrent.taskfactory.ThreadFactory;
import org.leesia.concurrent.vo.Task;
import org.leesia.test.concurrent.util.ThreadUtil;
import org.leesia.util.RandomUtil;
import org.leesia.util.date.LDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: ArrayBlockingQueueTest
 * @Description:
 * @author: leesia
 * @date: 2019/11/29 17:59
 */
public class ArrayBlockingQueueTest {

    private static Logger LOGGER = LoggerFactory.getLogger(ArrayBlockingQueueTest.class);

    private static ArrayBlockingQueueService<String> arrayBlockingQueueService = new ArrayBlockingQueueService<>(3);

    public static void main(String[] args) throws Exception {
        test_offer_timeout();

        LOGGER.info("main exit");
    }

    public static void test_add() {
        Task<ArrayBlockingQueueService<String>, Integer> addTask = new Task<>(service -> {
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
        }, arrayBlockingQueueService);

        Task<ArrayBlockingQueueService<String>, Integer> removeTask = new Task<>(service -> {
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
        }, arrayBlockingQueueService);

        Task<ArrayBlockingQueueService<String>, Integer> elementTask = new Task<>(service -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    LOGGER.info("try to peek ele on {}", LDateUtil.format(new Date(), null));
                    String ele = service.element();
                    LOGGER.info("ele peeked: {} on {}", ele, LDateUtil.format(new Date(), null));

                    ThreadUtil.sleepRandom(1000, 10000);
                } catch (Exception e) {
                    LOGGER.error("queue peek error: {}", e.getMessage(), e);
                }
            }

            return service.size();
        }, arrayBlockingQueueService);

        Thread addThread = ThreadFactory.newThread(addTask);
        Thread removeThread = ThreadFactory.newThread(removeTask);
        Thread elementThread = ThreadFactory.newThread(elementTask);

        addThread.start();
        removeThread.start();
        elementThread.start();
    }

    public static void test_offer() {
        Task<ArrayBlockingQueueService<String>, Integer> offerTask = new Task<>(service -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    String ele = RandomUtil.randomString(10);

                    LOGGER.info("try to offer ele: {} on {}", ele, LDateUtil.format(new Date(), null));
                    service.offer(ele);
                    LOGGER.info("offer ele: {} on {}", ele, LDateUtil.format(new Date(), null));

                    ThreadUtil.sleepRandom(1000, 10000);
                } catch (Exception e) {
                    LOGGER.error("queue offer error: {}", e.getMessage(), e);
                }
            }

            return service.size();
        }, arrayBlockingQueueService);

        Task<ArrayBlockingQueueService<String>, Integer> pollTask = new Task<>(service -> {
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
        }, arrayBlockingQueueService);

        Task<ArrayBlockingQueueService<String>, Integer> peekTask = new Task<>(service -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    LOGGER.info("try to peek ele on {}", LDateUtil.format(new Date(), null));
                    String ele = service.peek();
                    LOGGER.info("ele peeked: {} on {}", ele, LDateUtil.format(new Date(), null));

                    ThreadUtil.sleepRandom(1000, 10000);
                } catch (Exception e) {
                    LOGGER.error("queue peek error: {}", e.getMessage(), e);
                }
            }

            return service.size();
        }, arrayBlockingQueueService);

        Thread offerThread = ThreadFactory.newThread(offerTask);
        Thread pollThread = ThreadFactory.newThread(pollTask);
        Thread peekThread = ThreadFactory.newThread(peekTask);

        offerThread.start();
        pollThread.start();
        peekThread.start();
    }

    public static void test_put() {
        Task<ArrayBlockingQueueService<String>, Integer> putTask = new Task<>(service -> {
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
        }, arrayBlockingQueueService);

        Task<ArrayBlockingQueueService<String>, Integer> takeTask = new Task<>(service -> {
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
        }, arrayBlockingQueueService);

        Thread putThread = ThreadFactory.newThread(putTask);
        Thread takeThread = ThreadFactory.newThread(takeTask);

        putThread.start();
        takeThread.start();
    }

    public static void test_offer_timeout() {
        Task<ArrayBlockingQueueService<String>, Integer> offerTask = new Task<>(service -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    String ele = RandomUtil.randomString(10);

                    LOGGER.info("try to offer ele: {} on {}", ele, LDateUtil.format(new Date(), null));
                    service.offer(ele, 1000, TimeUnit.MILLISECONDS);
                    LOGGER.info("offer ele: {} on {}", ele, LDateUtil.format(new Date(), null));

                    ThreadUtil.sleepRandom(3000, 10000);
                } catch (Exception e) {
                    LOGGER.error("queue offer error: {}", e.getMessage(), e);
                }
            }

            return service.size();
        }, arrayBlockingQueueService);

        Task<ArrayBlockingQueueService<String>, Integer> pollTask = new Task<>(service -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    LOGGER.info("try to poll ele on {}", LDateUtil.format(new Date(), null));
                    String ele = service.poll(1000, TimeUnit.MILLISECONDS);
                    LOGGER.info("ele polled: {} on {}", ele, LDateUtil.format(new Date(), null));

                    ThreadUtil.sleepRandom(3000, 10000);
                } catch (Exception e) {
                    LOGGER.error("queue poll error: {}", e.getMessage(), e);
                }
            }

            return service.size();
        }, arrayBlockingQueueService);

        Thread offerThread = ThreadFactory.newThread(offerTask);
        Thread pollThread = ThreadFactory.newThread(pollTask);

        offerThread.start();
        pollThread.start();
    }
}
