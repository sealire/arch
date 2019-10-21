package org.leesia.concurrent.utility;

import org.leesia.concurrent.util.FunctionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class SemaphoreService {

    private static Logger LOGGER = LoggerFactory.getLogger(SemaphoreService.class);

    private Semaphore semaphore_1 = new Semaphore(1);

    private Semaphore semaphore_1_fair = new Semaphore(1, true);

    private Semaphore semaphore_2 = new Semaphore(2);

    private Semaphore semaphore_10 = new Semaphore(10);

    /**
     * 1个信号量，每次竞争1个
     *
     * @param function
     * @throws InterruptedException
     */
    public void syn1_1(Function function) throws InterruptedException {
        LOGGER.info("Thread: {} acquire 1", Thread.currentThread().getName());
        acquire(semaphore_1, 1);

        FunctionUtil.apply(function);

        LOGGER.info("Thread: {} release 1", Thread.currentThread().getName());
        release(semaphore_1, 1);
    }

    /**
     * 1个公平信号量，每次竞争1个
     *
     * @param function
     * @throws InterruptedException
     */
    public void syn_fair1_1(Function function) throws InterruptedException {
        LOGGER.info("Thread: {} acquire 1", Thread.currentThread().getName());
        acquire(semaphore_1_fair, 1);

        FunctionUtil.apply(function);

        LOGGER.info("Thread: {} release 1", Thread.currentThread().getName());
        release(semaphore_1_fair, 1);
    }

    /**
     * 1个信号量，每次竞争1个，不可中断
     *
     * @param function
     */
    public void syn1_1_uninterruptibly(Function function) {
        LOGGER.info("Thread: {} acquire 1 rninterruptibly", Thread.currentThread().getName());
        acquireUninterruptibly(semaphore_1, 1);

        FunctionUtil.apply(function);

        LOGGER.info("Thread: {} release 1", Thread.currentThread().getName());
        release(semaphore_1, 1);
    }

    /**
     * 2个信号量，每次竞争1个
     *
     * @param function
     * @throws InterruptedException
     */
    public void syn2_1(Function function) throws InterruptedException {
        LOGGER.info("Thread: {} acquire 1", Thread.currentThread().getName());
        acquire(semaphore_2, 1);

        FunctionUtil.apply(function);

        LOGGER.info("Thread: {} release 1", Thread.currentThread().getName());
        release(semaphore_2, 1);
    }

    /**
     * 10个信号量，每次竞争3个
     * @param function
     * @throws InterruptedException
     */
    public void syn10_3(Function function) throws InterruptedException {
        LOGGER.info("Thread: {} acquire 3", Thread.currentThread().getName());
        acquire(semaphore_10, 3);

        FunctionUtil.apply(function);

        LOGGER.info("Thread: {} release 3", Thread.currentThread().getName());
        release(semaphore_10, 3);
    }

    /**
     * 2个信号量，每次尝试竞争1个
     *
     * @param function
     */
    public void try_syn2_1(Function function) {
        LOGGER.info("Thread: {} tryAcquire 1", Thread.currentThread().getName());
        if (tryAcquire(semaphore_2, 1)) {
            FunctionUtil.apply(function);

            LOGGER.info("Thread: {} release 1", Thread.currentThread().getName());
            release(semaphore_2, 1);
        } else {
            LOGGER.info("Thread: {} unable to tryAcquire 1", Thread.currentThread().getName());
        }
    }

    /**
     * 10个信号量，每次尝试竞争3个
     *
     * @param function
     */
    public void try_syn10_3(Function function) {
        LOGGER.info("Thread: {} tryAcquire 3", Thread.currentThread().getName());
        if (tryAcquire(semaphore_10, 3)) {
            FunctionUtil.apply(function);

            LOGGER.info("Thread: {} release 3", Thread.currentThread().getName());
            release(semaphore_10, 3);
        } else {
            LOGGER.info("Thread: {} unable to tryAcquire 3", Thread.currentThread().getName());
        }
    }

    /**
     * 2个信号量，每次尝试竞争1个，最长等待timeout时间
     *
     * @param function
     * @param timeout
     * @param unit
     * @throws InterruptedException
     */
    public void try_timeout_syn2_1(Function function, long timeout, TimeUnit unit) throws InterruptedException {
        LOGGER.info("Thread: {} tryAcquire 1", Thread.currentThread().getName());
        if (tryAcquire(semaphore_2, 1, timeout, unit)) {
            FunctionUtil.apply(function);

            LOGGER.info("Thread: {} release 1", Thread.currentThread().getName());
            release(semaphore_2, 1);
        } else {
            LOGGER.info("Thread: {} unable to tryAcquire 1", Thread.currentThread().getName());
        }
    }

    /**
     * 动态增加信号量
     */
    public void test_release() {
        LOGGER.info("Thread: {} available permits: {}", Thread.currentThread().getName(), semaphore_1.availablePermits());

        LOGGER.info("Thread: {} release 1", Thread.currentThread().getName());
        release(semaphore_1, 1);

        LOGGER.info("Thread: {} available permits: {}", Thread.currentThread().getName(), semaphore_1.availablePermits());
    }

    /**
     * 获取信号量
     *
     * @param semaphore
     * @param permits
     * @throws InterruptedException
     */
    public void acquire(Semaphore semaphore, int permits) throws InterruptedException {
        if (permits < 2) {
            semaphore.acquire();
        } else {
            semaphore.acquire(permits);
        }
    }

    /**
     * 获取信号量，不可中断
     *
     * @param semaphore
     * @param permits
     */
    public void acquireUninterruptibly(Semaphore semaphore, int permits) {
        if (permits < 2) {
            semaphore.acquireUninterruptibly();
        } else {
            semaphore.acquireUninterruptibly(permits);
        }
    }

    /**
     * 尝试获取信号量
     *
     * @param semaphore
     * @param permits
     * @return
     */
    public boolean tryAcquire(Semaphore semaphore, int permits) {
        if (permits < 2) {
            return semaphore.tryAcquire();
        } else {
            return semaphore.tryAcquire(permits);
        }
    }

    /**
     * 在最大等待时间内尝试获取信号量
     *
     * @param semaphore
     * @param permits
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     */
    public boolean tryAcquire(Semaphore semaphore, int permits, long timeout, TimeUnit unit) throws InterruptedException {
        if (permits < 2) {
            return semaphore.tryAcquire(timeout, unit);
        } else {
            return semaphore.tryAcquire(permits, timeout, unit);
        }
    }

    /**
     * 释放信号量
     *
     * @param semaphore
     * @param permits
     */
    public void release(Semaphore semaphore, int permits) {
        if (permits < 2) {
            semaphore.release();
        } else {
            semaphore.release(permits);
        }
    }

    public int availablePermits(Semaphore semaphore) {
        return semaphore.availablePermits();
    }

    public int drainPermits(Semaphore semaphore) {
        return semaphore.drainPermits();
    }

    public int getQueueLength(Semaphore semaphore) {
        return semaphore.getQueueLength();
    }

    public boolean hasQueuedThreads(Semaphore semaphore) {
        return semaphore.hasQueuedThreads();
    }

    public static void main(String[] args) throws Exception {
        Semaphore semaphore = new Semaphore(2);

        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(() -> {
                try {
                    semaphore.acquire();
                    LOGGER.info("Thread: {} start", Thread.currentThread().getName());

                    Thread.sleep(2000);

                    LOGGER.info("Thread: {} end", Thread.currentThread().getName());
                    semaphore.release();
                } catch (InterruptedException e) {
                    LOGGER.error("Thread: {} interrupted", Thread.currentThread().getName());
                }
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        Thread.sleep(1000);

        LOGGER.info("availablePermits: {}", semaphore.availablePermits());
        LOGGER.info("getQueueLength: {}", semaphore.getQueueLength());
        LOGGER.info("hasQueuedThreads: {}", semaphore.hasQueuedThreads());
        LOGGER.info("drainPermits: {}", semaphore.drainPermits());

        for (Thread thread : threads) {
            thread.join();
        }
        LOGGER.info("availablePermits: {}", semaphore.availablePermits());
    }
}
