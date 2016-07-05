package com.yjx.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by yangjinxiao on 2016/7/4.
 */
public class ToastUtil {
    public static void makeToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
