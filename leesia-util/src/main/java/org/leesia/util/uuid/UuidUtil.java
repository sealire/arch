package org.leesia.util.uuid;

import java.util.UUID;

/**
 * @ClassName: UuidUtil
 * @Description:
 * @author: leesia
 * @date: 2019/12/18 14:32
 */
public class UuidUtil {

    /**
     * 获取UUID
     *
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
