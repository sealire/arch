package org.leesia.util.regex;

/**
 * @ClassName: RegexUtil
 * @Description:
 * @author: leesia
 * @date: 2019/12/18 17:14
 */
public class RegexUtil {

    private RegexUtil() {
    }

    /**
     * 单个汉字的正则表达式
     */
    public static final String REGEX_HANZI_SINGLE = "[\\u4e00-\\u9fa5]";

    /**
     * 多个汉字的正则表达式
     */
    public static final String REGEX_HANZI_MULTIPLE = "[\\u4e00-\\u9fa5]+";

    /**
     * 判断字符是否是汉字
     *
     * @param c
     * @return
     */
    public static boolean isChineseChar(char c) {
        return Character.toString(c).matches(REGEX_HANZI_SINGLE);
    }

    /**
     * 判断字符串是否全是汉字
     *
     * @param source
     * @return
     */
    public static boolean isChineseChar(String source) {
        return source.matches(REGEX_HANZI_MULTIPLE);
    }
}
