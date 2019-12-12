package org.leesia.test.concurrent.lock;

import org.leesia.concurrent.lock.ReentrantLockService;
import org.leesia.concurrent.vo.Task;
import org.leesia.test.concurrent.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: ReentrantLockTest
 * @Description:
 * @author: leesia
 * @date: 2019/12/9 13:52
 */
public class ReentrantLockTest {

    private static Logger LOGGER = LoggerFactory.getLogger(ReentrantLockTest.class);

    private static ReentrantLockService reentrantLockService = new ReentrantLockService();

    private static ReentrantLockService reentrantLockService_fair = new ReentrantLockService(true);

    public static void main(String[] args) {
        test_try_lock();

        LOGGER.info("main exit");
    }

    public static void test_lock() {
        Vote vote = new Vote();
        Task[] tasks = new Task[10];
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = new Task<>(v -> {
                LOGGER.info("{} has lock: {}", Thread.currentThread().getName(), reentrantLockService.isHeldByCurrentThread());
                LOGGER.info("{} threads wait lock", reentrantLockService.getQueueLength());

                reentrantLockService.lock();

                for (int k = 0; k < 5; k++) {
                    reentrantLockService.lock();

                    LOGGER.info("{} lock {} count", Thread.currentThread().getName(), reentrantLockService.getHoldCount());

                    v.add();

                    reentrantLockService.unlock();
                }
                reentrantLockService.unlock();

                return v.get();
            }, vote);
        }

        ThreadUtil.run(tasks);
    }

    public static void test_try_lock() {
        Vote vote = new Vote();
        Task[] tasks = new Task[10];
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = new Task<>(v -> {
                LOGGER.info("{} has lock: {}", Thread.currentThread().getName(), reentrantLockService.isHeldByCurrentThread());
                LOGGER.info("{} threads wait lock", reentrantLockService.getQueueLength());

                if (reentrantLockService.tryLock()) {
                    for (int k = 0; k < 5; k++) {
                        reentrantLockService.lock();

                        LOGGER.info("{} lock {} count", Thread.currentThread().getName(), reentrantLockService.getHoldCount());

                        v.add();

                        reentrantLockService.unlock();
                    }
                    reentrantLockService.unlock();
                }

                return v.get();
            }, vote);
        }

        ThreadUtil.run(tasks);
    }
}
