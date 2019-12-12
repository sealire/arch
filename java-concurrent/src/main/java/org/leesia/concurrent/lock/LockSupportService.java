package org.leesia.concurrent.lock;

import java.util.concurrent.locks.LockSupport;

/**
 * @ClassName: LockSupportService
 * @Description:
 * @author: leesia
 * @date: 2019/12/12 17:25
 */
public class LockSupportService {

    /**
     * 阻塞当前线程
     */
    public static void park() {
        LockSupport.park();
    }

    /**
     * 阻塞当前线程，最长不超过nanos纳秒
     *
     * @param nanos
     */
    public static void parkNanos(long nanos) {
        LockSupport.parkNanos(nanos);
    }

    /**
     * 阻塞当前线程，等待blocker对象，最长不超过nanos纳秒
     *
     * @param blocker
     * @param nanos
     */
    public static void parkNanos(Object blocker, long nanos) {
        LockSupport.parkNanos(blocker, nanos);
    }

    /**
     * 阻塞当前线程，直到deadline
     *
     * @param deadline
     */
    public static void parkUntil(long deadline) {
        LockSupport.parkUntil(deadline);
    }

    /**
     * 阻塞当前线程，等待blocker对象，直到deadline
     *
     * @param blocker
     * @param deadline
     */
    public static void parkUntil(Object blocker, long deadline) {
        LockSupport.parkUntil(blocker, deadline);
    }

    /**
     * 唤醒处于阻塞的线程
     */
    public static void unpark(Thread thread) {
        LockSupport.unpark(thread);
    }

    /**
     * 获取线程等待的对象
     *
     * @param t
     * @return
     */
    public static Object getBlocker(Thread t) {
        return LockSupport.getBlocker(t);
    }
}
