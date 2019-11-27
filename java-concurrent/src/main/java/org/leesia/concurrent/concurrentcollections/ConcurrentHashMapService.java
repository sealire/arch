package org.leesia.concurrent.concurrentcollections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: ConcurrentHashMapService
 * @Description:
 * @author: leesia
 * @date: 2019/11/26 11:09
 */
public class ConcurrentHashMapService<K, V> {

    private ConcurrentHashMap<K, V> concurrentHashMap;

    public ConcurrentHashMapService() {
        concurrentHashMap = new ConcurrentHashMap<>();
    }

    public ConcurrentHashMapService(int initialCapacity) {
        concurrentHashMap = new ConcurrentHashMap<>(initialCapacity);
    }

    public ConcurrentHashMapService(Map<? extends K, ? extends V> m) {
        concurrentHashMap = new ConcurrentHashMap<>(m);
    }

    /**
     * 插入数据
     *
     * @param key
     * @param value
     * @return
     */
    public V put(K key, V value) {
        return concurrentHashMap.put(key, value);
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public V get(Object key) {
        return concurrentHashMap.get(key);
    }

    /**
     * 移除数据
     *
     * @param key
     * @return
     */
    public V remove(Object key) {
        return concurrentHashMap.remove(key);
    }

    /**
     * 获取map元素个数
     *
     * @return
     */
    public int size() {
        return concurrentHashMap.size();
    }

    /**
     * 判断map是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return concurrentHashMap.isEmpty();
    }
}
