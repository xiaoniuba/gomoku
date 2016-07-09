package com.yjx.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;

import com.yjx.model.Language;
import com.yjx.model.ThemeModel;
import com.yjx.utils.Constants;
import com.yjx.utils.DialogUtil;
import com.yjx.utils.JsonUtil;
import com.yjx.utils.LogUtil;
import com.yjx.utils.SharedPreferenceUtil;
import com.yjx.wuziqi.R;

import java.util.Locale;

/**
 * 本地activity基类
 * Created by yangjinxiao on 2016/7/3.
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener{
    private static final String LANGUAGE = "language";

    protected Context that;
    protected View mDecorView;
    protected LayoutInflater mInflater;
    protected Language mLang;//当前的语言环境
    protected ThemeModel mTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DialogUtil.init(this);
        that = this;
        mDecorView = getWindow().getDecorView();
        mInflater = LayoutInflater.from(this);
        initLang();
        initTheme();
    }

    private void initTheme() {
        String cacheTheme = readThemeCache();
        try {
            mTheme = (ThemeModel) JsonUtil.decode(cacheTheme, ThemeModel.class);
        }catch(Exception e) {
            mTheme = new ThemeModel(ThemeModel.ThemeType.DAY);
        }
        int themeId = ThemeModel.ThemeResId.DAY;//默认是白天主题
        switch (mTheme.getType()) {
            case DAY:
                themeId = ThemeModel.ThemeResId.DAY;
                break;
            case NIGHT:
                themeId = ThemeModel.ThemeResId.NIGHT;
                break;
            case SEXY:
                themeId = ThemeModel.ThemeResId.SEXY;
                break;
        }
        setTheme(themeId);
    }

    private void initLang() {
        //读取缓存中存储的语言信息
        String cacheLang = SharedPreferenceUtil.getSharedPreferences(LANGUAGE, Constants.Language.SIMPLED_CHINESE, this);
        try {
            mLang = (Language) JsonUtil.decode(cacheLang, Language.class);
        }catch (Exception e) {
            mLang = new Language(Language.Type.SIMPLED_CHINESE, Language.Type4Show.SIMPLED_CHINESE);
        }
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
    protected void switchLanguage(Language language) {
        if (language == null) {
            return;
        }
        // 设置应用语言类型
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Locale local = config.locale;
        if (Language.Type.ENGLISH.equals(language.getType())) {
            if (local != Locale.ENGLISH) {
                config.locale = Locale.ENGLISH;
            }
        }else if ((Language.Type.SIMPLED_CHINESE).equals(language.getType())){
            if (local != Locale.SIMPLIFIED_CHINESE) {
                config.locale = Locale.SIMPLIFIED_CHINESE;
            }
        }
        resources.updateConfiguration(config, dm);
        // 保存设置语言的类型
        SharedPreferenceUtil.setSharedPreferences(LANGUAGE, JsonUtil.encode(language), this);
    }

    /**
     * 读取本地缓存存储的主题
     * @return
     */
    protected String readThemeCache() {
        return SharedPreferenceUtil.getSharedPreferences(Constants.SharedPreferenceConstant.PROPERTY_THEME, JsonUtil.encode(new ThemeModel(ThemeModel.ThemeType.DAY)), this);
    }
}
