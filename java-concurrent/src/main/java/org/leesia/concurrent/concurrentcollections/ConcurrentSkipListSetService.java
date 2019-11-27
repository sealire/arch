package org.leesia.concurrent.concurrentcollections;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @ClassName: ConcurrentSkipListSetService
 * @Description:
 * @author: leesia
 * @date: 2019/11/26 19:55
 */
public class ConcurrentSkipListSetService<E> {

    private ConcurrentSkipListSet<E> concurrentSkipListSet;

    public ConcurrentSkipListSetService() {
        concurrentSkipListSet = new ConcurrentSkipListSet<>();
    }

    public ConcurrentSkipListSetService(Comparator<? super E> comparator) {
        concurrentSkipListSet = new ConcurrentSkipListSet(comparator);
    }

    public ConcurrentSkipListSetService(Collection<? extends E> c) {
        concurrentSkipListSet = new ConcurrentSkipListSet(c);
    }

    public ConcurrentSkipListSetService(SortedSet<E> s) {
        concurrentSkipListSet = new ConcurrentSkipListSet(s);
    }

    /**
     * 添加数据
     *
     * @param e
     * @return
     */
    public boolean add(E e) {
        return concurrentSkipListSet.add(e);
    }

    /**
     * 移除第一个数据
     *
     * @return
     */
    public E pollFirst() {
        return concurrentSkipListSet.pollFirst();
    }

    /**
     * 移除最后一个数据
     *
     * @return
     */
    public E pollLast() {
        return concurrentSkipListSet.pollLast();
    }
}
