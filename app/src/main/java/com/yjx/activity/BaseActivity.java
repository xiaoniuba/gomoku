package com.yjx.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yjx.utils.Constants;
import com.yjx.utils.LogUtil;
import com.yjx.utils.SharedPreferenceUtil;
import com.yjx.wuziqi.R;

import java.util.Locale;
import java.util.zip.Inflater;

/**
 * 本地activity基类
 * Created by yangjinxiao on 2016/7/3.
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener{
    private static final String LANGUAGE = "language";

    protected Context that;
    protected View mDecorView;
    protected LayoutInflater mInflater;
    protected String mLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        that = this;
        mDecorView = getWindow().getDecorView();
        mInflater = LayoutInflater.from(this);
        //读取缓存中存储的语言信息
        mLang = SharedPreferenceUtil.getSharedPreferences(LANGUAGE, Constants.Language.SIMPLED_CHINESE, this);
        switchLanguage(mLang);
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

    /**
     * 切换语言
     * @param language
     */
    protected void switchLanguage(String language) {
        // 设置应用语言类型
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        if (language.equals(Constants.Language.ENGLISH)) {
            config.locale = Locale.ENGLISH;
        }else {
            config.locale = Locale.SIMPLIFIED_CHINESE;
        }
        resources.updateConfiguration(config, dm);
        // 保存设置语言的类型
        SharedPreferenceUtil.setSharedPreferences(LANGUAGE, language, this);
    }
}
