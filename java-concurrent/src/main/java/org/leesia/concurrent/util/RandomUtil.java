package org.leesia.concurrent.util;

import java.util.*;

public class RandomUtil {

    /**
     * 返回[min, max)之间的随机数， 若containMax = true，范围则是[min, max]
     *
     * @param min
     * @param max
     * @return
     */
    public static int randomInt(int min, int max, boolean containMax) {
        if (max < min) {
            throw new RuntimeException("max must big than min");
        }

        int range = containMax ? max - min + 1 : max - min;
        // Math.random() 生成[0, 1)之间的小数
        return min + (int) (Math.random() * range);
    }

    /**
     * 返回[min, max)之间的随机数， 若containMax = true，范围则是[min, max]
     *
     * @param min
     * @param max
     * @param containMax
     * @return
     */
    public static long randomLong(long min, long max, boolean containMax) {
        if (max < min) {
            throw new RuntimeException("max must big than min");
        }

        long range = containMax ? max - min + 1 : max - min;
        // Math.random() 生成[0, 1)之间的小数
        return min + (long) (Math.random() * range);
    }

    /**
     * 返回[min, max]之间的count个随机数
     *
     * @param min
     * @param max
     * @param count
     * @return
     */
    public static Set<Integer> randomSet(int min, int max, int count) {
        if (max < min) {
            throw new RuntimeException("max must big than min");
        }
        if (count < 0 || count > (max - min + 1)) {
            throw new RuntimeException("out of range: " + count);
        }

        Set<Integer> source = new HashSet<>();
        for (int i = min; i <= max; i++) {
            source.add(i);
        }

        return randomSet(source, count);
    }

    /**
     * 返回整数集合中随机count个数
     *
     * @param source
     * @param count
     * @return
     */
    public static Set<Integer> randomSet(Set<Integer> source, int count) {
        if (source.size() < count) {
            throw new RuntimeException("out of range: " + count);
        }

        Set<Integer> target = new HashSet<>();

        List<Integer> list = new ArrayList<>(source);
        for (int i = 0; i < count; i++) {
            target.add(list.remove(randomInt(0, list.size(), false)));
        }

        return target;
    }
}
