package com.voiceai.cloud.util;

import com.google.common.base.Strings;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * @author miracle_
 * Created at 2019-09-06 16:58
 */
public class BeanUtil {


    public static boolean isNullOrEmpty(Object object) {
        if (null == object) {
            return true;
        }
        if (object instanceof String) {
            String s = (String) object;
            return Strings.isNullOrEmpty(s);
        } else if (object instanceof Collection) {
            Collection c = (Collection) object;
            return c.isEmpty();
        } else if (object instanceof Map) {
            Map m = (Map) object;
            return m.isEmpty();
        } else if (object.getClass().isArray()) {
            return Array.getLength(object) == 0;
        }
        return false;
    }

    public static boolean isNotNullOrEmpty(Object obj) {
        return !isNullOrEmpty(obj);
    }
}
