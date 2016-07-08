package com.yjx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yjx.model.Settings;
import com.yjx.utils.JsonUtil;
import com.yjx.wuziqi.R;

/**
 * 首页欢迎页面
 * Created by yangjixniao on 2016/7/8.
 */
public class WelcomActivity extends BaseActivity {

    public static final String SETTINGS = "settings";
    private static final int REQUEST4SETTINGS = 1;

    private TextView mBeginGameText;
    private ImageView mSettingsImage;
    private Settings mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mBeginGameText = (TextView) findViewById(R.id.tv_begin_game);
        mBeginGameText.setOnClickListener(this);
        mSettingsImage = (ImageView) findViewById(R.id.iv_settings);
        mSettingsImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        switch (id) {
            case R.id.tv_begin_game:
                jumpToGameAct();
                break;
            case R.id.iv_settings:
                jumpToSettingsAct();
                break;
            default:
                break;
        }
    }

    private void jumpToGameAct() {
        Intent intent = new Intent();
        intent.setClass(this, GameActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(SETTINGS, JsonUtil.encode(mSettings));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void jumpToSettingsAct() {
        Intent intent = new Intent();
        intent.setClass(this, SettingsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(SETTINGS, JsonUtil.encode(mSettings));
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST4SETTINGS);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        finish();
        Intent intent1 = new Intent(WelcomActivity.this, WelcomActivity.class);
        startActivity(intent1);
    }
}
