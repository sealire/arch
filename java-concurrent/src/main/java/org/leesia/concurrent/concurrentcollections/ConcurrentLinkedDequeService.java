package org.leesia.concurrent.concurrentcollections;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @ClassName: ConcurrentLinkedDequeService
 * @Description:
 * @author: leesia
 * @date: 2019/11/27 9:29
 */
public class ConcurrentLinkedDequeService<E> {

    private ConcurrentLinkedDeque<E> concurrentLinkedDeque;

    public ConcurrentLinkedDequeService() {
        concurrentLinkedDeque = new ConcurrentLinkedDeque<>();
    }

    public ConcurrentLinkedDequeService(Collection<? extends E> c) {
        concurrentLinkedDeque = new ConcurrentLinkedDeque<>(c);
    }

    /**
     * 插入队列
     *
     * @param e
     * @return
     */
    public boolean add(E e) {
        return concurrentLinkedDeque.add(e);
    }

    /**
     * 插入队头
     *
     * @param e
     */
    public void addFirst(E e) {
        concurrentLinkedDeque.addFirst(e);
    }

    /**
     * 插入队尾
     *
     * @param e
     */
    public void addLast(E e) {
        concurrentLinkedDeque.addLast(e);
    }

    /**
     * 移除并返回队头元素，无元素时返回null
     *
     * @return
     */
    public E poll() {
        return concurrentLinkedDeque.poll();
    }

    /**
     * 移除并返回队头元素，无元素时返回null
     *
     * @return
     */
    public E pollFirst() {
        return concurrentLinkedDeque.pollFirst();
    }

    /**
     * 移除并返回队尾元素，无元素时返回null
     *
     * @return
     */
    public E pollLast() {
        return concurrentLinkedDeque.pollLast();
    }

    /**
     * 返回队头元素，无元素时返回null
     *
     * @return
     */
    public E peek() {
        return concurrentLinkedDeque.peek();
    }

    /**
     * 返回队头元素，无元素时返回null
     *
     * @return
     */
    public E peekFirst() {
        return concurrentLinkedDeque.peekFirst();
    }

    /**
     * 返回队尾元素，无元素时返回null
     *
     * @return
     */
    public E peekLast() {
        return concurrentLinkedDeque.peekLast();
    }

    /**
     * 返回队头元素，无元素时抛出NoSuchElementException
     *
     * @return
     */
    public E element() {
        return concurrentLinkedDeque.element();
    }
}
