package org.leesia.concurrent.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ListUtil
 * @Description: List工具类
 * @author: leesia
 * @date: 2019/11/19 16:28
 */
public class ListUtil {

    /**
     * 连接List中各元素，并返回连接后的字符串
     *
     * @param delimiter
     * @param source
     * @param <T>
     * @return
     */
    public static <T> String join(String delimiter, List<T> source) {
        if (source == null || source.isEmpty()) {
            return "";
        }

        String target = source.get(0).toString();
        for (int i = 1; i < source.size(); i++) {
            target += delimiter + source.get(i).toString();
        }

        return target;
    }

    /**
     * 整数列表求和
     *
     * @param nums
     * @return
     */
    public static Long sum(List<Integer> nums) {
        if (nums == null || nums.isEmpty()) {
            return 0L;
        }

        long sum = 0L;
        for (Integer num : nums) {
            sum += num;
        }
        return sum;
    }

    public static <T> List<List<T>> split(List<T> source, int count) {
        if (count < 0 || source == null || source.isEmpty()) {
            return new ArrayList<>();
        }

        int perSize = source.size() / count;
        if (perSize < 1) {
            perSize = 1;
        }

        int index = 0;
        int size = 0;
        List<List<T>> target = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            if (index >= source.size()) {
                target.add(new ArrayList<>());
                continue;
            }

            for (int k = 0; k < perSize; k++) {

            }
        }

        return target;
    }
}
