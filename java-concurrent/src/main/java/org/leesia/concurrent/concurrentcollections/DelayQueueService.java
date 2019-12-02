package org.leesia.concurrent.concurrentcollections;

import java.util.Collection;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;

/**
 * @ClassName: DelayQueueService
 * @Description:
 * @author: leesia
 * @date: 2019/12/2 17:03
 */
public class DelayQueueService<E extends Delayed> {

    private DelayQueue<E> delayQueue;

    public DelayQueueService() {
        delayQueue = new DelayQueue<>();
    }

    public DelayQueueService(Collection<? extends E> c) {
        delayQueue = new DelayQueue<>(c);
    }

    /**
     * 元素入队
     *
     * @param e
     */
    public void add(E e) {
        delayQueue.add(e);
    }

    /**
     * 元素出队
     *
     * @return
     * @throws InterruptedException
     */
    public E take() throws InterruptedException {
        return delayQueue.take();
    }
}
