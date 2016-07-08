package com.yjx.utils;

/**
 * Created by yangjinxiao on 2016/7/6.
 */
public class LogUtil {
    private static final String TAG = "yjx";
    public static final void e(String content) {
        android.util.Log.e(TAG, content);
    }
}
