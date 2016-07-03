package com.yjx.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by yangjinxiao on 2016/7/3.
 */
public class Util {
    public static float dp2px(int dpVal, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }
}
