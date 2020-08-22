package com.xin.commons.support.utils;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作
 * @author: xin
 */
public class StringUtil {

    /**
     * 生成32位随机字符串
     *
     * @return String 随机字符串
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }

    public static boolean isBlank(String str) {
        return !isNotBlank(str);
    }

    public static boolean isNotBlank(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public static String replace(String regex, String replacement) {
        if (regex == null || replacement == null) {
            return regex;
        }
        regex = regex.replaceAll(replacement, "");
        return regex;
    }

    public static long getWordCount(String str) {
        return str == null ? 0 : str.length();
    }

    public static boolean checkLengthEqual(String str, int len) {
        if (str == null) {
            return false;
        }
        if (str.length() == len) {
            return true;
        }
        return false;
    }

    public static boolean checkLengthLessThan(String str, int len) {
        if (str == null) {
            return false;
        }
        if (str.length() <= len) {
            return true;
        }
        return false;
    }

    public static boolean checkLengthGreaterThan(String str, int len) {
        if (str == null) {
            return false;
        }
        if (str.length() >= len) {
            return true;
        }
        return false;
    }

    public static boolean checkLengthBetween(String str, int minLen, int maxLen) {
        if (str == null) {
            return false;
        }
        int strLen = str.length();
        if (strLen >= minLen && strLen <= maxLen) {
            return true;
        }
        return false;
    }

    public static boolean checkIntegerFormat(String str) {
        if (str == null) {
            return false;
        }
        String regPattern = "^\\d+$";
        Pattern pattern = Pattern.compile(regPattern);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    public static boolean checkCurrencyFormat(String str) {
        if (str == null) {
            return false;
        }
        String regPattern = "^(([1-9]\\d*)|0)(\\.\\d{1,2})?$";
        Pattern pattern = Pattern.compile(regPattern);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    public static boolean checkDateFormat(String date) {
        if (date == null) {
            return false;
        }
        String regExp = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})"
                + "-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))"
                + "|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(date);
        return matcher.find();
    }

    public static boolean checkPhoneFormat(String tel) {
        if (tel == null) {
            return false;
        }
        String regExp = "((((0\\d{2,3})-?)|\\(0\\d{2,3}\\))?(\\d{7,8}))|(1\\d{10})";
        return tel.matches(regExp);
    }

}
