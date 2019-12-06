package org.leesia.test.concurrent.concurrentcollections;

import org.leesia.concurrent.concurrentcollections.LinkedTransferQueueService;
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
 * @ClassName: LinkedTransferQueueTest
 * @Description:
 * @author: leesia
 * @date: 2019/12/3 8:59
 */
public class LinkedTransferQueueTest {

    private static Logger LOGGER = LoggerFactory.getLogger(LinkedTransferQueueTest.class);

    private static LinkedTransferQueueService<String> linkedTransferQueueService = new LinkedTransferQueueService<>();

    public static void main(String[] args) throws Exception {
        test_try_transfer_timeout();

        LOGGER.info("main exit");
    }

    public static void test_transfer() {
        Task<LinkedTransferQueueService<String>, Integer> transferTask = new Task<>(service -> {
            for (int i = 0; i < 20; i++) {
                try {
                    String ele = RandomUtil.randomString(10);

                    LOGGER.info("try to transfer ele: {} on {}", ele, LDateUtil.format(new Date(), null));
                    service.transfer(ele);
                    LOGGER.info("transfer ele: {} on {}", ele, LDateUtil.format(new Date(), null));

                    ThreadUtil.sleepRandom(1000, 10000);
                } catch (Exception e) {
                    LOGGER.error("queue transfer error: {}", e.getMessage(), e);
                }
            }

            return 0;
        }, linkedTransferQueueService);

        Task<LinkedTransferQueueService<String>, Integer> takeTask = new Task<>(service -> {
            for (int i = 0; i < 20; i++) {
                try {
                    LOGGER.info("try to take ele on {}", LDateUtil.format(new Date(), null));
                    String ele = service.take();
                    LOGGER.info("ele take: {} on {}", ele, LDateUtil.format(new Date(), null));

                    ThreadUtil.sleepRandom(1000, 10000);
                } catch (Exception e) {
                    LOGGER.error("queue take error: {}", e.getMessage(), e);
                }
            }

            return 0;
        }, linkedTransferQueueService);

        Thread transferThread = ThreadFactory.newThread(transferTask);
        Thread takeThread = ThreadFactory.newThread(takeTask);

        transferThread.start();
        takeThread.start();
    }

    public static void test_try_transfer() {
        Task<LinkedTransferQueueService<String>, Integer> transferTask = new Task<>(service -> {
            for (int i = 0; i < 20; i++) {
                try {
                    String ele = RandomUtil.randomString(10);

                    LOGGER.info("try to transfer ele: {} on {}", ele, LDateUtil.format(new Date(), null));
                    service.tryTransfer(ele);
                    LOGGER.info("transfer ele: {} on {}, size: {}", ele, LDateUtil.format(new Date(), null), service.size());

                    ThreadUtil.sleepRandom(1000, 10000);
                } catch (Exception e) {
                    LOGGER.error("queue transfer error: {}", e.getMessage(), e);
                }
            }

            return 0;
        }, linkedTransferQueueService);

        Task<LinkedTransferQueueService<String>, Integer> takeTask = new Task<>(service -> {
            for (int i = 0; i < 20; i++) {
                try {
                    LOGGER.info("try to take ele on {}", LDateUtil.format(new Date(), null));
                    String ele = service.take();
                    LOGGER.info("ele take: {} on {}", ele, LDateUtil.format(new Date(), null));

                    ThreadUtil.sleepRandom(1000, 10000);
                } catch (Exception e) {
                    LOGGER.error("queue take error: {}", e.getMessage(), e);
                }
            }

            return 0;
        }, linkedTransferQueueService);

        Thread transferThread = ThreadFactory.newThread(transferTask);
        Thread takeThread = ThreadFactory.newThread(takeTask);

        transferThread.start();
        takeThread.start();
    }

    public static void test_try_transfer_timeout() {
        Task<LinkedTransferQueueService<String>, Integer> transferTask = new Task<>(service -> {
            for (int i = 0; i < 20; i++) {
                try {
                    String ele = RandomUtil.randomString(10);

                    LOGGER.info("try to transfer ele: {} on {}", ele, LDateUtil.format(new Date(), null));
                    boolean added = service.tryTransfer(ele, RandomUtil.randomLong(100, 1000, true), TimeUnit.MILLISECONDS);
                    LOGGER.info("transfer ele: {} on {}, size: {}", (added ? ele : null), LDateUtil.format(new Date(), null), service.size());

                    ThreadUtil.sleepRandom(1000, 10000);
                } catch (Exception e) {
                    LOGGER.error("queue transfer error: {}", e.getMessage(), e);
                }
            }

            return 0;
        }, linkedTransferQueueService);

        Task<LinkedTransferQueueService<String>, Integer> takeTask = new Task<>(service -> {
            for (int i = 0; i < 20; i++) {
                try {
                    LOGGER.info("try to take ele on {}", LDateUtil.format(new Date(), null));
                    String ele = service.take();
                    LOGGER.info("ele take: {} on {}", ele, LDateUtil.format(new Date(), null));

                    ThreadUtil.sleepRandom(1000, 10000);
                } catch (Exception e) {
                    LOGGER.error("queue take error: {}", e.getMessage(), e);
                }
            }

            return 0;
        }, linkedTransferQueueService);

        Thread transferThread = ThreadFactory.newThread(transferTask);
        Thread takeThread = ThreadFactory.newThread(takeTask);

        transferThread.start();
        takeThread.start();
    }
}
