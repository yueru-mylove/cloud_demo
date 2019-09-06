package com.voiceai.cloud.collection;

import com.google.common.base.Strings;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月09日 20:21:00
 */
public class BeanUtil {


    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }


    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }


    public static boolean isNull(Object object) {
        return null == object;
    }


    /**
     * 对象判空
     *
     * @param object
     * @return
     */
    public static boolean isEmpty(Object object) {
        if (null == object) {
            return true;
        } else if (object instanceof String) {
            String s = (String) object;
            return Strings.isNullOrEmpty(s);
        }else if (object instanceof List) {
            List list = (List) object;
            return list.isEmpty();
        } else if (object instanceof Map) {
            Map map = (Map) object;
            return map.isEmpty();
        } else if (object.getClass().isArray()) {
            return Array.getLength(object) == 0;
        }
        return false;
    }



}
