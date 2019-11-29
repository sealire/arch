package org.leesia.concurrent.concurrentcollections;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: ArrayBlockingQueueService
 * @Description:
 * @author: leesia
 * @date: 2019/11/27 14:48
 */
public class ArrayBlockingQueueService<E> {

    private ArrayBlockingQueue<E> arrayBlockingQueue;

    public ArrayBlockingQueueService(int capacity) {
        arrayBlockingQueue = new ArrayBlockingQueue<>(capacity);
    }

    public ArrayBlockingQueueService(int capacity, boolean fair) {
        arrayBlockingQueue = new ArrayBlockingQueue<>(capacity, fair);
    }

    public ArrayBlockingQueueService(int capacity, boolean fair, Collection<? extends E> c) {
        arrayBlockingQueue = new ArrayBlockingQueue<>(capacity, fair, c);
    }

    /**
     * 元素入队，队列满时抛出异常
     *
     * @param e
     * @return
     */
    public boolean add(E e) {
        return arrayBlockingQueue.add(e);
    }

    /**
     * 元素入队，队列满时阻塞
     *
     * @param e
     * @throws InterruptedException
     */
    public void put(E e) throws InterruptedException {
        arrayBlockingQueue.put(e);
    }

    /**
     * 元素入队，队列满时返回false
     *
     * @param e
     * @return
     */
    public boolean offer(E e) {
        return arrayBlockingQueue.offer(e);
    }

    /**
     * 元素入队，在最大等待时间内阻塞
     *
     * @param e
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     */
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        return arrayBlockingQueue.offer(e, timeout, unit);
    }
}
