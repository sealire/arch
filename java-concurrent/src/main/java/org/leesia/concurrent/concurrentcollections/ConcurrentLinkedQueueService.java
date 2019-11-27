package org.leesia.concurrent.concurrentcollections;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @ClassName: ConcurrentLinkedQueueService
 * @Description:
 * @author: leesia
 * @date: 2019/11/27 9:00
 */
public class ConcurrentLinkedQueueService<E> {

    private ConcurrentLinkedQueue<E> concurrentLinkedQueue;

    public ConcurrentLinkedQueueService() {
        concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
    }

    public ConcurrentLinkedQueueService(Collection<? extends E> c) {
        concurrentLinkedQueue = new ConcurrentLinkedQueue<>(c);
    }

    /**
     * 插入队列
     *
     * @param e
     * @return
     */
    public boolean add(E e) {
        return concurrentLinkedQueue.add(e);
    }

    /**
     * 移除并返回队头元素，无元素时返回null
     *
     * @return
     */
    public E poll() {
        return concurrentLinkedQueue.poll();
    }

    /**
     * 返回队头元素，无元素时返回null
     *
     * @return
     */
    public E peek() {
        return concurrentLinkedQueue.peek();
    }

    /**
     * 返回队头元素，无元素时抛出NoSuchElementException
     *
     * @return
     */
    public E element() {
        return concurrentLinkedQueue.element();
    }
}
