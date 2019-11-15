package com.miracle.cloud.util;

import java.util.Random;

/**
 * @author miracle_
 * Created at 2019-09-06 10:53
 */
public class IdUtil {


    /**
     * pos id前缀
     */
    public static final String ID_PREFIX = "p";

    /**
     * 适用于五位中间累加值
     */
    public static final Integer PAYLOAD_BIT_FIVE = 100000;

    /**
     * 两位中间累加值
     */
    public static final Integer PAYLOAD_BIT_TWO = 100;

    public static String getPosId(Integer current) {
        return getId(ID_PREFIX, current);
    }

    private static String getId(String prefix, Integer current) {
        return prefix + DateUtil.parseCurrent2DateString()
                + generatorIdPayload(PAYLOAD_BIT_FIVE, current)
                + generatorIdPayload(PAYLOAD_BIT_TWO, get2bitRandom());
    }

    private static String generatorIdPayload(Integer payload, Integer current) {
        return String.valueOf(payload + current).substring(1);
    }


    private static Integer get2bitRandom() {
        return new Random().nextInt(99);
    }

    /**
     * 生成n位补0的字符串:请注意，该函数默认最大长度为10，超出默认为10位
     *
     * @param n 长度
     * @param current   累加值
     * @return  n位前缀补0字符串
     */
    public static String vacancyBit(Integer n, Integer current) {
        return vacancyZero(bitFormat(n), current);
    }

    /**
     * 生成补零格式化串
     * @param n 总长度
     * @return  string
     */
    private static String bitFormat(Integer n) {
        return "%0" + (n > 10 ? 10 : n) + "d";
    }

    /**
     * 两位补0
     * @param current   累加值或随机值
     * @return  string
     */
    public static String vacancyBit2(Integer current) {
        return vacancyBit(2, current);
    }

    /**
     * 五位补0
     * @param current   累加值或随机值
     * @return  string
     */
    public static String vacancyBit5(Integer current) {
        return vacancyBit(5, current);
    }


    private static String vacancyZero(String format, Integer current) {
        return String.format(format, current);
    }


    private static String getPrefixUseStringFormat(String prefix, Integer current) {
        return prefix + DateUtil.parseCurrent2DateString()
                + vacancyBit5(current) + vacancyBit2(get2bitRandom());
    }


    public static String getPosIdUseStringFormat(Integer current) {
        return getPrefixUseStringFormat(ID_PREFIX, current);
    }


    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(getPosIdUseStringFormat(get2bitRandom()));
        }
    }
}
