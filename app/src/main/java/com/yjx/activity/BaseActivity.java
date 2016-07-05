package com.yjx.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * 本地activity基类
 * Created by yangjinxiao on 2016/7/3.
 */
public class BaseActivity extends FragmentActivity implements View.OnClickListener{
    protected Context that;
    protected View mDecorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        that = this;
        mDecorView = getWindow().getDecorView();
    }

    @Override
    public void onClick(View view) {

    }
}
