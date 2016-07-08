package com.yjx.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by yangjinxiao on 2016/7/3.
 */
public class Util {
    public static float dp2px(int dpVal, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }

    public static int[] getScrrenPixels(Context context) {
        int[] screen = new int[2];
        if (context  == null) {
            return screen;
        }
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display ds = wm.getDefaultDisplay();
        DisplayMetrics  dm = new DisplayMetrics();
        ds.getMetrics(dm);
        screen[0] = dm.widthPixels;
        screen[1] = dm.heightPixels;
        return screen;
    }
}
