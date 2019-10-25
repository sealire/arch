package org.leesia.concurrent.utility;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CyclicBarrierService {

    private CyclicBarrier cyclicBarrier;

    public CyclicBarrierService(int parties, Runnable runnable) {
        if (runnable != null) {
            cyclicBarrier = new CyclicBarrier(parties, runnable);
        } else {
            cyclicBarrier = new CyclicBarrier(parties);
        }
    }

    /**
     * 等待
     *
     * @throws BrokenBarrierException
     * @throws InterruptedException
     */
    public void await() throws BrokenBarrierException, InterruptedException {
        cyclicBarrier.await();
    }

    /**
     * 在最大等待时间内等待
     *
     * @param timeout
     * @param unit
     * @throws InterruptedException
     * @throws TimeoutException
     * @throws BrokenBarrierException
     */
    public void await(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException, BrokenBarrierException {
        cyclicBarrier.await(timeout, unit);
    }

    /**
     * 获取等待的线程数
     *
     * @return
     */
    public int getNumberWaiting() {
        return cyclicBarrier.getNumberWaiting();
    }

    /**
     * 判断屏障是否损坏
     *
     * @return
     */
    public boolean isBroken() {
        return cyclicBarrier.isBroken();
    }

    /**
     * 获取parties
     *
     * @return
     */
    public int getParties() {
        return cyclicBarrier.getParties();
    }

    /**
     * 重置
     */
    public void reset() {
        cyclicBarrier.reset();
    }
}
