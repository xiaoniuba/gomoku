package com.yjx.utils;

/**
 * 字符串工具类
 * Created by yangjinxiao on 2016/7/3.
 */
public class StringUtil {
    public static boolean isNullOrEmpty(String str) {
            return str == null || str.length() == 0;
    }
    public static boolean isAllNullOrEmpty(String... strs) {
        for (String str : strs) {
            if (str != null && str.length() > 0) {
                return false;
            }
        }
        return true;
    }
}
