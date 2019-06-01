package com.voiceai.cloud.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月09日 20:20:00
 */
public class ArrayUtil {


    public static List<byte[]> sliceArray(byte[] data, Integer requiredSize) {
        if (null == data || data.length <= 0) {
            return Collections.emptyList();
        }
        List<byte[]> result = new ArrayList<>();
        if (data.length < requiredSize) {
            result.add(data);
            return result;
        }

        int fromIndex = 0;
        for (; fromIndex < data.length; ) {
            byte[] bytes = Arrays.copyOfRange(data, fromIndex, fromIndex + requiredSize);
            fromIndex += requiredSize;
            result.add(bytes);
        }
        return result;
    }
}
