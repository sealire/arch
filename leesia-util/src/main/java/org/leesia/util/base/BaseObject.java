package org.leesia.util.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * @ClassName: BaseObject
 * @Description:
 * @author: leesia
 * @date: 2019/12/2 10:45
 */
public class BaseObject implements Serializable {

    private static final long serialVersionUID = 8164976526008927447L;

    private static Logger LOGGER = LoggerFactory.getLogger(BaseObject.class);

    @Override
    public String toString() {
        Class clazz = this.getClass();
        StringBuilder sb = new StringBuilder(clazz.getName() + "{");
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                sb.append(fields[i].getName() + "=" + fields[i].get(this));
                if (i < fields.length - 1) {
                    sb.append(", ");
                }
            }
        } catch (Exception e) {
            LOGGER.error("{} toString error: {}", clazz.getName(), e.getMessage(), e);
        }

        sb.append("}");

        return sb.toString();
    }
}
