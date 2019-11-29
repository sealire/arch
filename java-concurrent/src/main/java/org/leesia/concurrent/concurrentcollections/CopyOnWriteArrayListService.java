package org.leesia.concurrent.concurrentcollections;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @ClassName: CopyOnWriteArrayListService
 * @Description: TODO
 * @author: leesia
 * @date: 2019/11/27 9:53
 */
public class CopyOnWriteArrayListService<E> {

    private CopyOnWriteArrayList<E> copyOnWriteArrayList;

    public CopyOnWriteArrayListService() {
        copyOnWriteArrayList = new CopyOnWriteArrayList<>();
    }

    public CopyOnWriteArrayListService(Collection<? extends E> c) {
        copyOnWriteArrayList = new CopyOnWriteArrayList<>(c);
    }

    public CopyOnWriteArrayListService(E[] toCopyIn) {
        copyOnWriteArrayList = new CopyOnWriteArrayList<>(toCopyIn);
    }

    /**
     * 添加元素
     *
     * @param e
     * @return
     */
    public boolean add(E e) {
        return copyOnWriteArrayList.add(e);
    }

    /**
     * 在index位置插入元素
     *
     * @param index
     * @param element
     */
    public void add(int index, E element) {
        copyOnWriteArrayList.add(index, element);
    }

    /**
     * 获取元素
     *
     * @param index
     * @return
     */
    public E get(int index) {
        return copyOnWriteArrayList.get(index);
    }

    /**
     * 移除元素
     *
     * @param index
     * @return
     */
    public E remove(int index) {
        return copyOnWriteArrayList.remove(index);
    }

    /**
     * 获取元素个数
     *
     * @return
     */
    public int size() {
        return copyOnWriteArrayList.size();
    }
}
