package com.yjx.wuziqi;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by Administrator on 2016/7/3.
 */
public class BaseActivity extends FragmentActivity {
    protected View mDecorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDecorView = getWindow().getDecorView();
    }
}
