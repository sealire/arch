package org.leesia.util;

import org.leesia.util.constants.CharacterSet;
import org.leesia.util.list.ListUtil;

import java.util.*;

/**
 * @ClassName: RandomUtil
 * @Description: 随机方法工具类
 * @author: leesia
 * @date: 2019/11/19 15:00
 */
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

        long range = containMax ? max - min + 1 : max - min;

        /**
         * Math.random() 生成[0, 1)之间的小数
         */
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

        double range = containMax ? max - min + 1 : max - min;

        /**
         * Math.random() 生成[0, 1)之间的小数
         */
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
     * 从集合中随机取count个对象
     *
     * @param source
     * @param count
     * @return
     */
    public static <T> Set<T> randomSet(Set<T> source, int count) {
        if (source.size() < count) {
            throw new RuntimeException("out of range: " + count);
        }

        Set<T> target = new HashSet<>();

        List<T> list = new ArrayList<>(source);
        for (int i = 0; i < count; i++) {
            target.add(list.remove(randomInt(0, list.size(), false)));
        }

        return target;
    }

    /**
     * 从List中返回随机序列，序列长度为length
     *
     * @param source
     * @param length
     * @param <T>
     * @return
     */
    public static <T> List<T> randomSequence(List<T> source, int length) {
        if (source == null || source.isEmpty() || length < 1) {
            return new ArrayList<>();
        }

        List<T> target = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            target.add(source.get(randomInt(0, source.size(), false)));
        }

        return target;
    }

    /**
     * 返回随机概率
     *
     * @param prop 比例，[0, 1]
     * @return
     */
    public static boolean randomProb(double prop) {
        return Math.random() < prop;
    }

    /**
     * 返回随机字符串（包含小写、大写字母）
     *
     * @param length 字符串长度
     * @return
     */
    public static String randomString(int length) {
        if (length < 1) {
            return "";
        }

        List<Character> characters = new ArrayList<>();
        Collections.addAll(characters, CharacterSet.LOWERCASE);
        Collections.addAll(characters, CharacterSet.UPPERCASE);

        List<Character> target = randomSequence(characters, length);
        Collections.shuffle(target);

        return ListUtil.join("", target);
    }

    /**
     * 返回小写字母的随机字符串
     *
     * @param length 字符串长度
     * @return
     */
    public static String randomLowerCaseString(int length) {
        if (length < 1) {
            return "";
        }

        List<Character> characters = Arrays.asList(CharacterSet.LOWERCASE);

        List<Character> target = randomSequence(characters, length);
        Collections.shuffle(target);

        return ListUtil.join("", target);
    }

    /**
     * 返回大写字母的随机字符串
     *
     * @param length 字符串长度
     * @return
     */
    public static String randomUpperCaseString(int length) {
        if (length < 1) {
            return "";
        }

        List<Character> characters = Arrays.asList(CharacterSet.UPPERCASE);

        List<Character> target = randomSequence(characters, length);
        Collections.shuffle(target);

        return ListUtil.join("", target);
    }

    /**
     * 返回数字的随机字符串
     *
     * @param length 字符串长度
     * @return
     */
    public static String randomNumberString(int length) {
        if (length < 1) {
            return "";
        }

        List<Character> characters = Arrays.asList(CharacterSet.NUMBER);

        List<Character> target = randomSequence(characters, length);
        Collections.shuffle(target);

        target = normalizeNumberSequence(target);

        return ListUtil.join("", target);
    }

    /**
     * 数字序列规范化，将开始的0移到序列末尾
     *
     * @param numbers
     * @return
     */
    private static List<Character> normalizeNumberSequence(List<Character> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return numbers;
        }

        int notZeroIndex = 0;
        for (; notZeroIndex < numbers.size(); notZeroIndex++) {
            if (!numbers.get(notZeroIndex).toString().equals("0")) {
                break;
            }
        }
        if (notZeroIndex == 0 || notZeroIndex >= numbers.size()) {
            return numbers;
        }

        List<Character> target = new ArrayList<>();
        for (int i = notZeroIndex; i < numbers.size(); i++) {
            target.add(numbers.get(i));
        }
        for (int i = 0; i < notZeroIndex; i++) {
            target.add(numbers.get(i));
        }

        return target;
    }
}
