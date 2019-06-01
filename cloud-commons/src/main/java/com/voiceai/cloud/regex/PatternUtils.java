package com.voiceai.cloud.regex;

import com.google.common.base.Strings;

import java.util.regex.Pattern;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月05日 14:02:00
 */
public class PatternUtils {


    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-z0-9A-Z]+[- |a-z0-9A-Z._]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$");

    public static final Pattern PHONE_PATTERN = Pattern.compile("^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\\\D])|(18[0,5-9]))\\\\d{8}$");

    public static final Pattern IDCARD_PATTERN = Pattern.compile("(^\\d{18}$)|(^\\d{15}$)");

    public static boolean regexTestEmail(String email) {
        return !Strings.isNullOrEmpty(email) && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean regexTestPhone(String phone) {
        return !Strings.isNullOrEmpty(phone) && PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean regexTestID(String idNo) {
        return !Strings.isNullOrEmpty(idNo) && IDCARD_PATTERN.matcher(idNo).matches();
    }
}
