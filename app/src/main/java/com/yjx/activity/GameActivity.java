package com.yjx.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.yjx.utils.DialogUtil;

import com.yjx.customview.PannelView;
import com.yjx.wuziqi.R;

public class GameActivity extends BaseActivity implements PannelView.OnGameOverListener,
                                                            PannelView.OnTurnChangedListener{
    private static final String IS_CUR_WHITE_TURN = "is_cur_white_turn";

    private PannelView mPannelView;
    private TextView mGoLoginText;
    private TextView mNewGameText;
    private TextView mRegretText;
    private TextView mSettingsText;
    private ImageView mWhichTurnImage;
    private Bitmap mWhitePiece;
    private Bitmap mBlackPiece;
    private AnimationSet mAnimSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mGoLoginText = (TextView) findViewById(R.id.tv_go_login);
        mGoLoginText.setOnClickListener(this);
        mPannelView = (PannelView) findViewById(R.id.v_pannel);
        mPannelView.registerGameOverListner(this);
        mPannelView.registerTurnChangedListener(this);
        mNewGameText = (TextView) findViewById(R.id.tv_new_game);
        mNewGameText.setOnClickListener(this);
        mNewGameText = (TextView) findViewById(R.id.tv_regret);
        mNewGameText.setOnClickListener(this);
        mSettingsText = (TextView) findViewById(R.id.tv_settings);
        mSettingsText.setOnClickListener(this);
        mWhichTurnImage = (ImageView) findViewById(R.id.iv_which_turn);
        mWhitePiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_w2);
        mBlackPiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_b1);
        mWhichTurnImage.setImageBitmap(mWhitePiece);
        if (savedInstanceState != null) {//Activity销毁时保存的当前轮到谁下子
            boolean isCurWhiteTurn = savedInstanceState.getBoolean(IS_CUR_WHITE_TURN, true);
            mWhichTurnImage.setImageBitmap(isCurWhiteTurn ? mWhitePiece : mBlackPiece);
        }
        initAnim();
    }

    private void initAnim() {
        mAnimSet = new AnimationSet(true);
        AlphaAnimation alphaAnim = new AlphaAnimation(0.5f, 1.0f);
        ScaleAnimation scaleAnim = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f, 0.5f, 0.5f);
        mAnimSet.addAnimation(alphaAnim);
        mAnimSet.addAnimation(scaleAnim);
        mAnimSet.setDuration(500);
        mAnimSet.setRepeatMode(Animation.RESTART);
        mAnimSet.setRepeatCount(Animation.INFINITE);
        mWhichTurnImage.startAnimation(mAnimSet);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        switch (id) {
            case R.id.tv_go_login:
                goLoginAct();
                break;
            case R.id.tv_new_game:
                if (mPannelView != null) {
                    mPannelView.restart();
                }
                break;
            case R.id.tv_regret:
                if (mPannelView != null) {
                    mPannelView.regretLastStep();
                }
                break;
            case R.id.tv_settings:
                //todo 等待添加
                break;
            default:
                break;
        }
    }

    private void goLoginAct() {
        Intent intent = new Intent(GameActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onGameOver(boolean isWhiteWin) {
        DialogUtil.createDialog(mDecorView, isWhiteWin ? getString(R.string.white_win) :getString(R.string.black_win),
                "", getString(R.string.restart), null, new Runnable() {
                    @Override
                    public void run() {
                        if (mPannelView != null) {
                            mPannelView.restart();
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Activity销毁时存储当前轮到谁下子
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_CUR_WHITE_TURN, mPannelView.getIsCurWhiteTurn());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_restart_game:
                if (mPannelView != null) {
                    mPannelView.restart();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTurnChangeListener(boolean isWhiteTurn) {
        mWhichTurnImage.setImageBitmap(isWhiteTurn ? mWhitePiece : mBlackPiece);
        mWhichTurnImage.startAnimation(mAnimSet);
    }
}
