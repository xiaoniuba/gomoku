package com.yjx.activity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yjx.GlobalConfig;
import com.yjx.model.User;

import com.yjx.utils.JsonUtil;
import com.yjx.utils.SharedPreferenceUtil;
import com.yjx.utils.StringUtil;
import com.yjx.wuziqi.R;

/**
 * 登陆页面(TODO:增加登陆功能时使用)
 * Created by yangjinxiao on 2016/7/4.
 */
public class LoginActivity extends BaseActivity{

    private EditText mUserNameEdit;
    private EditText mUserPasswordEdit;
    private TextView mRegisterText;
    private TextView mLoginText;
    private User mUser;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        readUserLocal();
        setContentView(R.layout.activity_login);
        mUserNameEdit = (EditText) findViewById(R.id.et_user_name);
        mUserPasswordEdit = (EditText) findViewById(R.id.et_user_password);
        mRegisterText = (TextView) findViewById(R.id.tv_register);
        mRegisterText.setOnClickListener(this);
        mLoginText = (TextView) findViewById(R.id.tv_login);
        mLoginText.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        String userName = mUserNameEdit.getText().toString();
        String userPassword = mUserPasswordEdit.getText().toString();
        if (StringUtil.isNullOrEmpty(userName) || StringUtil.isNullOrEmpty(userPassword)) {
            return;
        }
        int id = view.getId();
        mUser = new User();
        mUser.setUserName(userName);
        mUser.setUserPassword(userPassword);
        switch (id) {
            case R.id.tv_register:
                break;
            case R.id.tv_login:
                break;
            default:
                break;
        }
    }

    /**
     * 读取本地用户缓存
     */
    private void readUserLocal() {
        String userStr = SharedPreferenceUtil.getSharedPreferences(GlobalConfig.USER_HISTORY, that);
        if (StringUtil.isNullOrEmpty(userStr)) {//没有缓存，需要登陆
            return;
        }else {//已经登陆，直接跳转到游戏页面
            jumpGameMainAct();
        }
    }

    /**
     * 跳转到游戏首页
     */
    private void jumpGameMainAct() {
        Intent intent = new Intent(LoginActivity.this, GameActivity.class);
        startActivity(intent);
    }

    /**
     * 本地保存注册用户信息
     */
    private void saveUserLocal() {
        SharedPreferenceUtil.setSharedPreferences(GlobalConfig.USER_HISTORY, JsonUtil.encode(mUser), that);
    }
}
