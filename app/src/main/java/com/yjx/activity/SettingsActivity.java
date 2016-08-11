package com.yjx.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yjx.adapter.SettingsAdapter;
import com.yjx.model.Language;
import com.yjx.model.Settings;
import com.yjx.model.SettingsItem;
import com.yjx.model.ThemeModel;
import com.yjx.utils.Constants;
import com.yjx.utils.DialogUtil;
import com.yjx.utils.JsonUtil;
import com.yjx.utils.SharedPreferenceUtil;
import com.yjx.utils.StringUtil;
import com.yjx.utils.Util;
import com.yjx.wuziqi.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置页面
 * Created by yangjinxiao on 2016/7/8.
 */
public class SettingsActivity extends BaseActivity {

    private static final int CHOOSE_MODEL = 1;
    private static final int CHOOSE_LANG = 2;
    public static final int RESULT_CODE_DONE = 1;

    @BindView(R.id.tv_right_function)
    TextView mDoneText;
    @BindView(R.id.lv)
    ListView mListVeiw;

    private SettingsAdapter mAdapter;
    private LinearLayout mChooseModelLayout;
    private PopupWindow mPopupWindow;
    private Settings mSettings = new Settings();

    @Override
    protected int getContentLayout() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initHeaderView() {
        mDoneText = (TextView) findViewById(R.id.tv_right_function);
        mDoneText.setText(getString(R.string.done));
        ((TextView) findViewById(R.id.tv_title)).setText(getString(R.string.settings));
        View backView = findViewById(R.id.iv_back);
        setOnclickListener(mDoneText, backView);
    }

    @Override
    protected void initContentView() {
        initList();
    }

    private void initList() {
        mListVeiw = (ListView) findViewById(R.id.lv);
        mAdapter = new SettingsAdapter(this);
        mAdapter.setOnItemClickListener(new SettingsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View convertView, String contentStr) {
                if (getString(R.string.model).equals(contentStr)) {
                    showChooseWindow(CHOOSE_MODEL);
                } else if (getString(R.string.multilang).equals(contentStr)) {
                    showChooseWindow(CHOOSE_LANG);
                } else if (getString(R.string.about).equals(contentStr)) {
                    jumpAboutAct();
                }
            }
        });
        List<SettingsItem> settingsList = new ArrayList<>();
        SettingsItem modelItem = new SettingsItem(R.drawable.model, getString(R.string.model));
        settingsList.add(modelItem);
        SettingsItem multiLangItem = new SettingsItem(R.drawable.multilang, getString(R.string.multilang));
        settingsList.add(multiLangItem);
        SettingsItem aboutItem = new SettingsItem(R.drawable.about, getString(R.string.about));
        settingsList.add(aboutItem);
        mAdapter.setData(settingsList);
        mListVeiw.setAdapter(mAdapter);
    }

    private void jumpAboutAct() {
        Intent intent = new Intent(SettingsActivity.this, AboutActiviy.class);
        startActivity(intent);
    }

    @Override

    @OnClick({R.id.tv_right_function, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right_function:
                backToWelcomAct();
                break;
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void backToWelcomAct() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(WelcomActivity.SETTINGS, JsonUtil.encode(mSettings));
        setResult(RESULT_CODE_DONE, intent);
        finish();
    }

    private void showChooseWindow(int flag) {
        if (mChooseModelLayout == null) {
            RelativeLayout layout = (RelativeLayout) mInflater.inflate(R.layout.layout_popupwindow_container, null);
            mChooseModelLayout = (LinearLayout) layout.findViewById(R.id.ll_container);
            mChooseModelLayout.setBackgroundResource(R.drawable.bg_popupwindow);
        }
        if (mChooseModelLayout != null) {
            mChooseModelLayout.removeAllViews();
        }
        final ThemeModel cacheTheme = mTheme;
        Drawable checkedDrawable = getResources().getDrawable(R.drawable.icon_checked);
        if (flag == CHOOSE_MODEL) {
            List<ThemeModel> allThemes = ThemeModel.ALL_THEMES;
            if (allThemes == null) {
                return;
            }
            final boolean isCurZHLang = mLang.getType().equals(Language.Type.SIMPLED_CHINESE);
            for (final ThemeModel theme : allThemes) {
                if (theme == null) {
                    continue;
                }
                final View childView = mInflater.inflate(R.layout.item_settings_model, null);
                TextView tv = (TextView) childView.findViewById(R.id.tv);
                tv.setText(isCurZHLang ? theme.getType4Show() : theme.getType4ShowEn());
                ImageView iv = (ImageView) childView.findViewById(R.id.iv);
                iv.setImageDrawable(checkedDrawable);
                iv.setVisibility(cacheTheme.equals(theme) ? View.VISIBLE : View.INVISIBLE);
                childView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismissPopupWindow();
                        if (theme.equals(cacheTheme)) {
                            return;
                        }
                        mSettings.setModel(theme);
                        buildCacheTheme(theme);
                        refreshChooseLayout(isCurZHLang ? theme.getType4Show() : theme.getType4ShowEn());

                        //更新主题后，destroy当前页面，重新绘制
                        finish();
                        Intent intent = new Intent(SettingsActivity.this, WelcomActivity.class);
                        startActivity(intent);

                    }
                });
                mChooseModelLayout.addView(childView);
                View divider = new View(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Util.dp2px(1, this));
                divider.setLayoutParams(params);
                divider.setBackgroundColor(getResources().getColor(R.color.grey61));
                mChooseModelLayout.addView(divider);
            }
        } else if (flag == CHOOSE_LANG) {
            List<Language> allLangs = Language.ALL_LANGS;
            if (allLangs == null || allLangs.isEmpty()) {
                return;
            }
            final Language cacheLang = mLang;
            for (final Language lang : allLangs) {
                if (lang == null) {
                    continue;
                }
                final View childView = mInflater.inflate(R.layout.item_settings_model, null);
                TextView tv = (TextView) childView.findViewById(R.id.tv);
                tv.setText(lang.getType4Show());
                ImageView iv = (ImageView) childView.findViewById(R.id.iv);
                iv.setImageDrawable(checkedDrawable);
                iv.setVisibility(cacheLang.equals(lang) ? View.VISIBLE : View.INVISIBLE);
                childView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismissPopupWindow();
                        if (lang.equals(cacheLang)) {
                            return;
                        }
                        switchLanguage(lang);
                        refreshChooseLayout(lang.getType4Show());
                        //更新语言后，destroy当前页面，重新绘制
                        finish();
                        Intent intent = new Intent(SettingsActivity.this, WelcomActivity.class);
                        startActivity(intent);
                    }
                });
                mChooseModelLayout.addView(childView);
                View divider = new View(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Util.dp2px(1, this));
                divider.setLayoutParams(params);
                divider.setBackgroundColor(getResources().getColor(R.color.grey61));
                mChooseModelLayout.addView(divider);
            }
        }
        mPopupWindow = DialogUtil.createPopupWindowWithCustomView(mDecorView, (View) mChooseModelLayout.getParent());
    }

    private void dismissPopupWindow() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    private void refreshChooseLayout(String chooseedStr) {
        if (mChooseModelLayout == null || StringUtil.isNullOrEmpty(chooseedStr)) {
            return;
        }
        int childCount = mChooseModelLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = mChooseModelLayout.getChildAt(i);
            if (childView == null) {
                continue;
            }
            try {
                TextView tv = (TextView) childView.findViewById(R.id.tv);
                ImageView iv = (ImageView) childView.findViewById(R.id.iv);
                if (chooseedStr.equals(tv.getText().toString())) {
                    iv.setVisibility(View.VISIBLE);
                } else {
                    iv.setVisibility(View.INVISIBLE);
                }
            } catch (Exception e) {
                continue;
            }
        }
    }

    /**
     * 存储模式到本地缓存
     *
     * @param model
     */
    protected void buildCacheTheme(ThemeModel model) {
        SharedPreferenceUtil.setSharedPreferences(Constants.SharedPreferenceConstant.PROPERTY_THEME, JsonUtil.encode(model), this);
    }
}
