package org.leesia.concurrent.concurrentcollections;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: SynchronousQueueService
 * @Description:
 * @author: leesia
 * @date: 2019/12/2 14:03
 */
public class SynchronousQueueService<E> {

    private SynchronousQueue<E> synchronousQueue;

    public SynchronousQueueService() {
        synchronousQueue = new SynchronousQueue<>();
    }

    public SynchronousQueueService(boolean fair) {
        synchronousQueue = new SynchronousQueue<>(fair);
    }

    /**
     * 元素入队，队列有数据时抛出异常
     *
     * @param e
     * @return
     */
    public boolean add(E e) {
        return synchronousQueue.add(e);
    }

    /**
     * 元素入队，队列有数据时阻塞
     *
     * @param e
     * @throws InterruptedException
     */
    public void put(E e) throws InterruptedException {
        synchronousQueue.put(e);
    }

    /**
     * 元素入队，队列有数据时返回false
     *
     * @param e
     * @return
     */
    public boolean offer(E e) {
        return synchronousQueue.offer(e);
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
        return synchronousQueue.offer(e, timeout, unit);
    }

    /**
     * 元素出队，没有元素时抛出异常
     *
     * @return
     */
    public E remove() {
        return synchronousQueue.remove();
    }

    /**
     * 元素出队，没有元素时阻塞
     *
     * @return
     * @throws InterruptedException
     */
    public E take() throws InterruptedException {
        return synchronousQueue.take();
    }

    /**
     * 元素出队，没有元素时返回null
     *
     * @return
     */
    public E poll() {
        return synchronousQueue.poll();
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
        return synchronousQueue.poll(timeout, unit);
    }

    /**
     * 查询队头元素，没有元素时抛出异常
     *
     * @return
     */
    public E element() {
        return synchronousQueue.element();
    }

    /**
     * 查询队头元素，没有元素时返回null
     *
     * @return
     */
    public E peek() {
        return synchronousQueue.peek();
    }

    /**
     * 查询队列元素个数
     *
     * @return
     */
    public int size() {
        return synchronousQueue.size();
    }
}
