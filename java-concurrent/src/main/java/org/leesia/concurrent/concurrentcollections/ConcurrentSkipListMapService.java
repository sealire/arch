package org.leesia.concurrent.concurrentcollections;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @ClassName: ConcurrentSkipListMapService
 * @Description:
 * @author: leesia
 * @date: 2019/11/26 14:20
 */
public class ConcurrentSkipListMapService<K, V> {

    private ConcurrentSkipListMap<K, V> concurrentSkipListMap;

    public ConcurrentSkipListMapService() {
        concurrentSkipListMap = new ConcurrentSkipListMap<>();
    }

    public ConcurrentSkipListMapService(Map<? extends K, ? extends V> m) {
        concurrentSkipListMap = new ConcurrentSkipListMap<>(m);
    }

    public ConcurrentSkipListMapService(SortedMap<K, ? extends V> m) {
        concurrentSkipListMap = new ConcurrentSkipListMap<>(m);
    }

    public ConcurrentSkipListMapService(Comparator<? super K> comparator) {
        concurrentSkipListMap = new ConcurrentSkipListMap<>(comparator);
    }

    /**
     * 插入数据
     *
     * @param key
     * @param value
     * @return
     */
    public V put(K key, V value) {
        return concurrentSkipListMap.put(key, value);
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public V get(Object key) {
        return concurrentSkipListMap.get(key);
    }

    /**
     * 移除第一个数据
     *
     * @return
     */
    public Map.Entry<K, V> pollFirstEntry() {
        return concurrentSkipListMap.pollFirstEntry();
    }

    /**
     * 移除最后一个数据
     */
    public Map.Entry<K, V> pollLastEntry() {
        return concurrentSkipListMap.pollLastEntry();
    }
}
