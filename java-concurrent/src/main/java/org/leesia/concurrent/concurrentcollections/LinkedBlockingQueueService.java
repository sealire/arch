package org.leesia.concurrent.concurrentcollections;

import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: LinkedBlockingQueueService
 * @Description:
 * @author: leesia
 * @date: 2019/12/2 13:49
 */
public class LinkedBlockingQueueService<E> {

    private LinkedBlockingQueue<E> linkedBlockingQueue;

    public LinkedBlockingQueueService() {
        linkedBlockingQueue = new LinkedBlockingQueue<>();
    }

    public LinkedBlockingQueueService(int capacity) {
        linkedBlockingQueue = new LinkedBlockingQueue<>(capacity);
    }

    public LinkedBlockingQueueService(Collection<? extends E> c) {
        linkedBlockingQueue = new LinkedBlockingQueue<>(c);
    }

    /**
     * 元素入队，队列满时抛出异常
     *
     * @param e
     * @return
     */
    public boolean add(E e) {
        return linkedBlockingQueue.add(e);
    }

    /**
     * 元素入队，队列满时阻塞
     *
     * @param e
     * @throws InterruptedException
     */
    public void put(E e) throws InterruptedException {
        linkedBlockingQueue.put(e);
    }

    /**
     * 元素入队，队列满时返回false
     *
     * @param e
     * @return
     */
    public boolean offer(E e) {
        return linkedBlockingQueue.offer(e);
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
        return linkedBlockingQueue.offer(e, timeout, unit);
    }

    /**
     * 元素出队，没有元素时抛出异常
     *
     * @return
     */
    public E remove() {
        return linkedBlockingQueue.remove();
    }

    /**
     * 元素出队，没有元素时阻塞
     *
     * @return
     * @throws InterruptedException
     */
    public E take() throws InterruptedException {
        return linkedBlockingQueue.take();
    }

    /**
     * 元素出队，没有元素时返回null
     *
     * @return
     */
    public E poll() {
        return linkedBlockingQueue.poll();
    }

    /**
     * 元素出队，在最大等待时间内阻塞
     *
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     */
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        return linkedBlockingQueue.poll(timeout, unit);
    }

    /**
     * 查询队头元素，没有元素时抛出异常
     *
     * @return
     */
    public E element() {
        return linkedBlockingQueue.element();
    }

    /**
     * 查询队头元素，没有元素时返回null
     *
     * @return
     */
    public E peek() {
        return linkedBlockingQueue.peek();
    }

    /**
     * 查询队列元素个数
     *
     * @return
     */
    public int size() {
        return linkedBlockingQueue.size();
    }
}
