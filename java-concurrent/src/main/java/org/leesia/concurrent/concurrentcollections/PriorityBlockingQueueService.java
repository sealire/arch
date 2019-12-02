package org.leesia.concurrent.concurrentcollections;

import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: PriorityBlockingQueueService
 * @Description:
 * @author: leesia
 * @date: 2019/12/2 13:14
 */
public class PriorityBlockingQueueService<E> {

    private PriorityBlockingQueue<E> priorityBlockingQueue;

    public PriorityBlockingQueueService() {
        priorityBlockingQueue = new PriorityBlockingQueue<>();
    }

    public PriorityBlockingQueueService(int initialCapacity) {
        priorityBlockingQueue = new PriorityBlockingQueue<>(initialCapacity);
    }

    public PriorityBlockingQueueService(int initialCapacity, Comparator<? super E> comparator) {
        priorityBlockingQueue = new PriorityBlockingQueue<>(initialCapacity, comparator);
    }

    public PriorityBlockingQueueService(Collection<? extends E> c) {
        priorityBlockingQueue = new PriorityBlockingQueue<>(c);
    }

    /**
     * 元素入队，队列满时抛出异常
     *
     * @param e
     * @return
     */
    public boolean add(E e) {
        return priorityBlockingQueue.add(e);
    }

    /**
     * 元素入队，队列满时阻塞
     *
     * @param e
     * @throws InterruptedException
     */
    public void put(E e) throws InterruptedException {
        priorityBlockingQueue.put(e);
    }

    /**
     * 元素入队，队列满时返回false
     *
     * @param e
     * @return
     */
    public boolean offer(E e) {
        return priorityBlockingQueue.offer(e);
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
        return priorityBlockingQueue.offer(e, timeout, unit);
    }

    /**
     * 元素出队，没有元素时抛出异常
     *
     * @return
     */
    public E remove() {
        return priorityBlockingQueue.remove();
    }

    /**
     * 元素出队，没有元素时阻塞
     *
     * @return
     * @throws InterruptedException
     */
    public E take() throws InterruptedException {
        return priorityBlockingQueue.take();
    }

    /**
     * 元素出队，没有元素时返回null
     *
     * @return
     */
    public E poll() {
        return priorityBlockingQueue.poll();
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
        return priorityBlockingQueue.poll(timeout, unit);
    }

    /**
     * 查询队头元素，没有元素时抛出异常
     *
     * @return
     */
    public E element() {
        return priorityBlockingQueue.element();
    }

    /**
     * 查询队头元素，没有元素时返回null
     *
     * @return
     */
    public E peek() {
        return priorityBlockingQueue.peek();
    }

    /**
     * 查询队列元素个数
     *
     * @return
     */
    public int size() {
        return priorityBlockingQueue.size();
    }
}
