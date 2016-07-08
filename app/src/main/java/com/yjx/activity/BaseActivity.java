package com.yjx.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yjx.utils.LogUtil;
import com.yjx.wuziqi.R;

import java.util.zip.Inflater;

/**
 * 本地activity基类
 * Created by yangjinxiao on 2016/7/3.
 */
public class BaseActivity extends FragmentActivity implements View.OnClickListener{
    protected Context that;
    protected View mDecorView;
    protected LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        that = this;
        mDecorView = getWindow().getDecorView();
        mInflater = LayoutInflater.from(this);
    }

    protected void setOnclickListener(View... views) {
        for (View v : views) {
            if (v != null) {
                v.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            default:
                break;
        }
    }

}
