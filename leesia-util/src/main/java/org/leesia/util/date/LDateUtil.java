package org.leesia.util.date;

import org.leesia.util.common.GeneralTools;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @ClassName: LDateUtil
 * @Description:
 * @author: leesia
 * @date: 2019/11/22 15:17
 */
public class LDateUtil {

    /**
     * 默认格式
     */
    private static final String DEFAULT_PATTERN = "YYYY-MM-dd HH:mm:ss";

    /**
     * 默认格式工具
     */
    private static final SimpleDateFormat DEFAULT_FORMAT = new SimpleDateFormat(DEFAULT_PATTERN);

    /**
     * JDK 8默认格式工具
     */
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_PATTERN);

    /**
     * 格式化时间
     *
     * @param date
     * @param pattern 默认按YYYY-MM-dd HH:mm:ss格式化
     * @return
     */
    public static String format(Date date, String pattern) {
        if (GeneralTools.isEmpty(pattern)) {
            return DEFAULT_FORMAT.format(date);
        }

        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    /**
     * 格式化时间
     *
     * @param date
     * @param pattern 默认按YYYY-MM-dd HH:mm:ss格式化
     * @return
     */
    public static String format(LocalDateTime date, String pattern) {
        if (GeneralTools.isEmpty(pattern)) {
            return date.format(DEFAULT_FORMATTER);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }
}
