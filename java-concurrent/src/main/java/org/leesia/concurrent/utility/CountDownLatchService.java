package org.leesia.concurrent.utility;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchService {

    private CountDownLatch countDownLatch;

    public CountDownLatchService(int count) {
        countDownLatch = new CountDownLatch(count);
    }

    /**
     * 等待
     *
     * @throws InterruptedException
     */
    public void await() throws InterruptedException {
        countDownLatch.await();
    }

    /**
     * 超时等待
     *
     * @param timeout
     * @param unit
     * @throws InterruptedException
     */
    public void await(long timeout, TimeUnit unit) throws InterruptedException {
        countDownLatch.await(timeout, unit);
    }

    /**
     * 计数减1
     */
    public void countDown() {
        countDownLatch.countDown();
    }

    /**
     * 获取等待数
     *
     * @return
     */
    public long getCount() {
        return countDownLatch.getCount();
    }
}
