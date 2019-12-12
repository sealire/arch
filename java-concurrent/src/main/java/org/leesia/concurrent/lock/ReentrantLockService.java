package org.leesia.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName: ReentrantLockService
 * @Description:
 * @author: leesia
 * @date: 2019/12/6 11:31
 */
public class ReentrantLockService {

    private ReentrantLock reentrantLock;

    public ReentrantLockService() {
        reentrantLock = new ReentrantLock();
    }

    public ReentrantLockService(boolean fair) {
        reentrantLock = new ReentrantLock(fair);
    }

    /**
     * 加锁
     */
    public void lock() {
        reentrantLock.lock();
    }

    /**
     * 可中断的加锁
     *
     * @throws InterruptedException
     */
    public void lockInterruptibly() throws InterruptedException {
        reentrantLock.lockInterruptibly();
    }

    /**
     * 尝试加锁
     *
     * @return
     */
    public boolean tryLock() {
        return reentrantLock.tryLock();
    }

    /**
     * 在最大等待时间内尝试加锁
     *
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     */
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        return reentrantLock.tryLock(timeout, unit);
    }

    /**
     * 解锁
     */
    public void unlock() {
        reentrantLock.unlock();
    }

    /**
     * 获取等待通知组件
     *
     * @return
     */
    public Condition newCondition() {
        return reentrantLock.newCondition();
    }

    /**
     * 查询当前线程保持此锁定的个数，重入次数
     *
     * @return
     */
    public int getHoldCount() {
        return reentrantLock.getHoldCount();
    }

    /**
     * 判断当前线程是否持有锁
     *
     * @return
     */
    public boolean isHeldByCurrentThread() {
        return reentrantLock.isHeldByCurrentThread();
    }


    /**
     * 锁是否被任一线程持有
     *
     * @return
     */
    public boolean isLocked() {
        return reentrantLock.isLocked();
    }

    /**
     * 判断锁是否是公平锁
     *
     * @return
     */
    public final boolean isFair() {
        return reentrantLock.isFair();
    }

    /**
     * 判断是否有线程在等待锁
     *
     * @return
     */
    public final boolean hasQueuedThreads() {
        return reentrantLock.hasQueuedThreads();
    }

    /**
     * 判断线程是否在等待锁
     *
     * @param thread
     * @return
     */
    public final boolean hasQueuedThread(Thread thread) {
        return reentrantLock.hasQueuedThread(thread);
    }

    /**
     * 获取等待锁的线程
     *
     * @return
     */
    public final int getQueueLength() {
        return reentrantLock.getQueueLength();
    }
}