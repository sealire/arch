package org.leesia.concurrent.concurrentcollections;

import java.util.Collection;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: LinkedBlockingDequeService
 * @Description:
 * @author: leesia
 * @date: 2019/12/2 13:56
 */
public class LinkedBlockingDequeService<E> {

    private LinkedBlockingDeque<E> linkedBlockingDeque;

    public LinkedBlockingDequeService() {
        linkedBlockingDeque = new LinkedBlockingDeque<>();
    }

    public LinkedBlockingDequeService(int capacity) {
        linkedBlockingDeque = new LinkedBlockingDeque<>(capacity);
    }

    public LinkedBlockingDequeService(Collection<? extends E> c) {
        linkedBlockingDeque = new LinkedBlockingDeque<>(c);
    }

    /**
     * 元素入队，队列满时抛出异常
     *
     * @param e
     * @return
     */
    public boolean add(E e) {
        return linkedBlockingDeque.add(e);
    }

    /**
     * 元素入队，队列满时阻塞
     *
     * @param e
     * @throws InterruptedException
     */
    public void put(E e) throws InterruptedException {
        linkedBlockingDeque.put(e);
    }

    /**
     * 元素入队，队列满时返回false
     *
     * @param e
     * @return
     */
    public boolean offer(E e) {
        return linkedBlockingDeque.offer(e);
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
        return linkedBlockingDeque.offer(e, timeout, unit);
    }

    /**
     * 元素出队，没有元素时抛出异常
     *
     * @return
     */
    public E remove() {
        return linkedBlockingDeque.remove();
    }

    /**
     * 元素出队，没有元素时阻塞
     *
     * @return
     * @throws InterruptedException
     */
    public E take() throws InterruptedException {
        return linkedBlockingDeque.take();
    }

    /**
     * 元素出队，没有元素时返回null
     *
     * @return
     */
    public E poll() {
        return linkedBlockingDeque.poll();
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
        return linkedBlockingDeque.poll(timeout, unit);
    }

    /**
     * 查询队头元素，没有元素时抛出异常
     *
     * @return
     */
    public E element() {
        return linkedBlockingDeque.element();
    }

    /**
     * 查询队头元素，没有元素时返回null
     *
     * @return
     */
    public E peek() {
        return linkedBlockingDeque.peek();
    }

    /**
     * 查询队列元素个数
     *
     * @return
     */
    public int size() {
        return linkedBlockingDeque.size();
    }
}
