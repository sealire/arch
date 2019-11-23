package org.leesia.util.common;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @ClassName: GeneralTools
 * @Description:
 * @author: leesia
 * @date: 2019/11/22 15:20
 */
public class GeneralTools {

    /**
     * 判断对象是否为空
     *
     * @param o 可能是对象，字符串，Collection, Map
     * @return
     */
    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        }

        if (o instanceof CharSequence) {
            return StringUtils.isEmpty((CharSequence) o);
        }

        if (o instanceof Collection) {
            return ((Collection) o).isEmpty();
        }

        if (o instanceof Map) {
            return ((Map) o).isEmpty();
        }

        return true;
    }
}
