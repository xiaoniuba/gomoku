package com.yjx;

import android.app.Application;


/**
 * 使用自定义的Application
 * Created by yangjinxiao on 2016/7/4.
 */
public class MyApplication extends Application{
    private static final String BMOB_APP_ID = "c07e666385c459f81c02ca75c9edb81e";
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
