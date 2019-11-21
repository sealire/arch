package org.leesia.test.concurrent.util;

import org.leesia.concurrent.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ListUtil
 * @Description:
 * @author: leesia
 * @date: 2019/11/21 21:15
 */
public class ListUtil {

    /**
     * 从[min, max]中返回随机count个整数
     *
     * @param min
     * @param max
     * @param count
     * @return
     */
    public static List<Integer> randomNumberList(int min, int max, int count) {
        List<Integer> target = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            target.add(RandomUtil.randomInt(min, max, true));
        }

        return target;
    }
}
