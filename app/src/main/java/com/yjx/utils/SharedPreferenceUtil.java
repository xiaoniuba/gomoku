package com.yjx.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceActivity;
import android.provider.ContactsContract;


/**
 * Created by yangjinxiao on 2016/7/4.
 */
public class SharedPreferenceUtil {

    public static String getSharedPreferences(String propertyName, Context context) {
        return context.getSharedPreferences(Constants.SharedPreferenceConstant.PREFERENCE_NAME, PreferenceActivity.MODE_MULTI_PROCESS).getString(propertyName, "");
    }

    public static String getSharedPreferences(String preferenceName, String propertyName, Context context, String defaultValue) {
        return context.getSharedPreferences(preferenceName, PreferenceActivity.MODE_PRIVATE).getString(propertyName, defaultValue);
    }

    public static int getSharedPreferences(String preferenceName, String propertyName, Context context, int defaultValue) {
        return context.getSharedPreferences(preferenceName, PreferenceActivity.MODE_PRIVATE).getInt(propertyName, defaultValue);
    }

    public static long getSharedPreferences(String preferenceName, String propertyName, Context context, long defaultValue) {
        return context.getSharedPreferences(preferenceName, PreferenceActivity.MODE_PRIVATE).getLong(propertyName, defaultValue);
    }

    public static boolean setSharedPreferences(String propertyName, String propertyValue, Context context) {
        return context.getSharedPreferences(Constants.SharedPreferenceConstant.PREFERENCE_NAME, Context.MODE_PRIVATE).edit()
                .putString(propertyName, propertyValue).commit();
    }

    public static boolean setSharedPreferences(String preferenceName, String propertyName, int propertyValue, Context context) {
        return context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE).edit()
                .putInt(propertyName, propertyValue).commit();
    }

    public static boolean setSharedPreferences(String preferenceName, String propertyName, long propertyValue, Context context) {
        return context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE).edit()
                .putLong(propertyName, propertyValue).commit();
    }

}
