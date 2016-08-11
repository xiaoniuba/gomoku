package com.yjx.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.yjx.wuziqi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 关于页面
 * Created by yangjinxiao on 2016/7/20.
 */
public class AboutActiviy extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView mTitleText;
    @BindView(R.id.tv_content)
    TextView mContentText;


    @Override
    protected int getContentLayout() {
        return R.layout.activity_about;
    }

    @Override
    protected void initHeaderView() {
        super.initHeaderView();
        mTitleText.setText(getString(R.string.about));
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        mContentText.setText(getString(R.string.about_desc));
    }

}
