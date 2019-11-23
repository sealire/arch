package org.leesia.util.list;

import org.leesia.util.common.GeneralTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: LListUtil
 * @Description:
 * @author: leesia
 * @date: 2019/11/22 15:17
 */
public class LListUtil {

    /**
     * 连接List中各元素，并返回连接后的字符串
     *
     * @param delimiter
     * @param source
     * @param <T>
     * @return
     */
    public static <T> String join(String delimiter, List<T> source) {
        if (GeneralTools.isEmpty(source)) {
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
        if (GeneralTools.isEmpty(nums)) {
            return 0L;
        }

        long sum = 0L;
        for (Integer num : nums) {
            sum += num;
        }
        return sum;
    }

    /**
     * 转List
     *
     * @param ts
     * @param <T>
     * @return
     */
    public static <T> List<T> asList(T... ts) {
        return new ArrayList<>(Arrays.asList(ts));
    }
}
