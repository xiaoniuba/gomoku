package com.yjx.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yjx.com.yjx.adapter.SettingsAdapter;
import com.yjx.model.Language;
import com.yjx.model.Settings;
import com.yjx.model.SettingsItem;
import com.yjx.utils.Constants;
import com.yjx.utils.DialogUtil;
import com.yjx.utils.JsonUtil;
import com.yjx.utils.LogUtil;
import com.yjx.utils.SharedPreferenceUtil;
import com.yjx.utils.StringUtil;
import com.yjx.utils.Util;
import com.yjx.wuziqi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置页面
 * Created by yangjinxiao on 2016/7/8.
 */
public class SettingsActivity extends BaseActivity {

    private static final int CHOOSE_MODEL = 1;
    private static final int CHOOSE_LANG = 2;
    public static final int RESULT_CODE_DONE = 1;
    public static final String MODEL_PROPERTIES = "model_properties";

    private ListView mListVeiw;
    private SettingsAdapter mAdapter;
    private TextView mDoneText;
    private LinearLayout mChooseModelLayout;
    private PopupWindow mPopupWindow;
    private LayoutInflater mInflater;
    private Settings mSettings = new Settings();
    private String[] mThemeModelStrs =
            {Constants.ThemeModelStrs.DAY, Constants.ThemeModelStrs.NIGHT, Constants.ThemeModelStrs.DOWN,};
    private List<Language> mLangs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initData();
        mInflater = LayoutInflater.from(this);
        initList();
        mDoneText = (TextView) findViewById(R.id.tv_right_function);
        mDoneText.setText(getString(R.string.done));
        View view = findViewById(R.id.iv_back);
        setOnclickListener(mDoneText, view);
    }

    private void initData() {
        mLangs = new ArrayList<>();
        Language en = new Language(Constants.Language.ENGLISH, Constants.Language4Show.ENGLISH);
        Language zh = new Language(Constants.Language.SIMPLED_CHINESE, Constants.Language4Show.SIMPLED_CHINESE);
        mLangs.add(en);
        mLangs.add(zh);
    }

    private void initList() {
        mListVeiw = (ListView) findViewById(R.id.lv);
        mAdapter = new SettingsAdapter(this);
        mAdapter.setOnItemClickListener(new SettingsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View convertView, String contentStr) {
                if (getString(R.string.model).equals(contentStr)) {
                    showChooseWindow(CHOOSE_MODEL);
                }else if (getString(R.string.multilang).equals(contentStr)) {
                    showChooseWindow(CHOOSE_LANG);
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

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        switch (id) {
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
        String cacheModel = readModelCache();
        Drawable checkedDrawable = getResources().getDrawable(R.drawable.icon_checked);
        if (flag == CHOOSE_MODEL) {
            for (final String modelStr : mThemeModelStrs) {
                if (StringUtil.isNullOrEmpty(modelStr)) {
                    continue;
                }
                final View childView = mInflater.inflate(R.layout.item_settings_model, null);
                TextView tv = (TextView) childView.findViewById(R.id.tv);
                tv.setText(modelStr);
                ImageView iv = (ImageView) childView.findViewById(R.id.iv);
                iv.setImageDrawable(checkedDrawable);
                iv.setVisibility(modelStr.equals(cacheModel) ? View.VISIBLE : View.INVISIBLE);
                Constants.ThemeModel model;
                switch (modelStr) {
                    case Constants.ThemeModelStrs.DAY:
                        model = Constants.ThemeModel.DAY;
                        break;
                    case Constants.ThemeModelStrs.NIGHT:
                        model = Constants.ThemeModel.NIGHT;
                        break;
                    case Constants.ThemeModelStrs.DOWN:
                        model = Constants.ThemeModel.DOWN;
                        break;
                    default:
                        model = Constants.ThemeModel.DAY;
                        break;
                }
                childView.setTag(model);
                childView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSettings.setModel((Constants.ThemeModel) view.getTag());
                        buildModelCache(modelStr);
                        refreshChooseLayout(modelStr);
                        dismissPopupWindow();
                    }
                });
                mChooseModelLayout.addView(childView);
                View divider = new View(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Util.dp2px(1, this));
                divider.setLayoutParams(params);
                divider.setBackgroundColor(getResources().getColor(R.color.grey61));
                mChooseModelLayout.addView(divider);
            }
        }else if (flag == CHOOSE_LANG) {
            for (final Language lang : mLangs) {
                if (mLangs == null) {
                    continue;
                }
                final View childView = mInflater.inflate(R.layout.item_settings_model, null);
                TextView tv = (TextView) childView.findViewById(R.id.tv);
                tv.setText(lang.getLang4Show());
                ImageView iv = (ImageView) childView.findViewById(R.id.iv);
                iv.setImageDrawable(checkedDrawable);
                iv.setVisibility(mLang.equals(lang.getLang()) ? View.VISIBLE : View.INVISIBLE);
                childView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switchLanguage(lang.getLang());
                        refreshChooseLayout(lang.getLang4Show());
                        //更新语言后，destroy当前页面，重新绘制
                        finish();
                        Intent intent = new Intent(SettingsActivity.this, WelcomActivity.class);
                        startActivity(intent);
                        dismissPopupWindow();
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
                ImageView iv = (ImageView)childView.findViewById(R.id.iv);
                if (chooseedStr.equals(tv.getText().toString())) {
                    iv.setVisibility(View.VISIBLE);
                }else {
                    iv.setVisibility(View.INVISIBLE);
                }
            }catch (Exception e){
                continue;
            }
        }
    }

    /**
     * 读取本地缓存存储的模式
     * @return
     */
    private String readModelCache() {
        return SharedPreferenceUtil.getSharedPreferences(MODEL_PROPERTIES, Constants.ThemeModelStrs.DAY, this);
    }

    /**
     * 存储模式到本地缓存
     * @param model
     */
    private void buildModelCache(String model) {
        SharedPreferenceUtil.setSharedPreferences(MODEL_PROPERTIES, model, this);
    }

}
