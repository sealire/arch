package org.leesia.concurrent.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: DataUtil
 * @Description:
 * @author: leesia
 * @date: 2019/11/21 20:59
 */
public class DateUtil {

    private static final String PATTERN = "YYYY-MM-dd HH:mm:ss";

    /**
     * 格式化时间
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }
}
