package com.yjx;

import android.content.Context;

import com.yjx.model.User;
import com.yjx.utils.SharedPreferenceUtil;
import com.yjx.utils.StringUtil;

/**
 * Created by yangjinxiao on 2016/7/4.
 */
public class GlobalConfig {
    public static final String USER_HISTORY = "user_history";

    public static boolean isUserLogin(Context context) {
        String localUserInfo = SharedPreferenceUtil.getSharedPreferences(USER_HISTORY, context);
        return StringUtil.isNullOrEmpty(localUserInfo) ? false : true;
    }
}
