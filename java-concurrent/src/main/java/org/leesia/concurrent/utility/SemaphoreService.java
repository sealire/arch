package org.leesia.concurrent.utility;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreService {

    private Semaphore semaphore;

    public SemaphoreService(int permits, boolean fair) {
        semaphore = new Semaphore(permits, fair);
    }

    /**
     * 获取信号量
     *
     * @param permits
     * @throws InterruptedException
     */
    public void acquire(int permits) throws InterruptedException {
        if (permits < 2) {
            semaphore.acquire();
        } else {
            semaphore.acquire(permits);
        }
    }

    /**
     * 获取信号量，不可中断
     *
     * @param permits
     */
    public void acquireUninterruptibly(int permits) {
        if (permits < 2) {
            semaphore.acquireUninterruptibly();
        } else {
            semaphore.acquireUninterruptibly(permits);
        }
    }

    /**
     * 尝试获取信号量
     *
     * @param permits
     * @return
     */
    public boolean tryAcquire(int permits) {
        if (permits < 2) {
            return semaphore.tryAcquire();
        } else {
            return semaphore.tryAcquire(permits);
        }
    }

    /**
     * 在最大等待时间内尝试获取信号量
     * @param permits
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     */
    public boolean tryAcquire(int permits, long timeout, TimeUnit unit) throws InterruptedException {
        if (permits < 2) {
            return semaphore.tryAcquire(timeout, unit);
        } else {
            return semaphore.tryAcquire(permits, timeout, unit);
        }
    }

    /**
     * 释放信号量
     *
     * @param permits
     */
    public void release(int permits) {
        if (permits < 2) {
            semaphore.release();
        } else {
            semaphore.release(permits);
        }
    }

    public int availablePermits() {
        return semaphore.availablePermits();
    }

    public int drainPermits() {
        return semaphore.drainPermits();
    }

    public int getQueueLength() {
        return semaphore.getQueueLength();
    }

    public boolean hasQueuedThreads() {
        return semaphore.hasQueuedThreads();
    }
}
