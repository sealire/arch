package org.leesia.concurrent.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName: ReentrantReadWriteLockService
 * @Description:
 * @author: leesia
 * @date: 2019/12/9 15:02
 */
public class ReentrantReadWriteLockService {

    private ReentrantReadWriteLock reentrantReadWriteLock;

    public ReentrantReadWriteLockService() {
        reentrantReadWriteLock = new ReentrantReadWriteLock();
    }

    public ReentrantReadWriteLockService(boolean fair) {
        reentrantReadWriteLock = new ReentrantReadWriteLock(fair);
    }

    /**
     * 获取读锁
     *
     * @return
     */
    public ReentrantReadWriteLock.ReadLock readLock() {
        return reentrantReadWriteLock.readLock();
    }

    /**
     * 获取写锁
     *
     * @return
     */
    public ReentrantReadWriteLock.WriteLock writeLock() {
        return reentrantReadWriteLock.writeLock();
    }

    /**
     * 获取读锁加锁的次数
     *
     * @return
     */
    public int getReadLockCount() {
        return reentrantReadWriteLock.getReadLockCount();
    }

    /**
     * 判断当前线程是否持有写锁
     *
     * @return
     */
    public boolean isWriteLockedByCurrentThread() {
        return reentrantReadWriteLock.isWriteLockedByCurrentThread();
    }

    /**
     * 判断是否有任一线程持有写锁
     *
     * @return
     */
    public boolean isWriteLocked() {
        return reentrantReadWriteLock.isWriteLocked();
    }

    /**
     * 判断是否是公平锁
     *
     * @return
     */
    public final boolean isFair() {
        return reentrantReadWriteLock.isFair();
    }

    /**
     * 判断是否有线程在等待加锁（读锁或写锁）
     *
     * @return
     */
    public final boolean hasQueuedThreads() {
        return reentrantReadWriteLock.hasQueuedThreads();
    }

    /**
     * 获取当前线程获取读锁的次数
     *
     * @return
     */
    public int getReadHoldCount() {
        return reentrantReadWriteLock.getReadHoldCount();
    }

    /**
     * 获取当前线程获取写锁的次数
     *
     * @return
     */
    public int getWriteHoldCount() {
        return reentrantReadWriteLock.getWriteHoldCount();
    }
}
