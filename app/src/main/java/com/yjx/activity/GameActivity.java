package com.yjx.activity;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yjx.customview.PannelView;
import com.yjx.utils.DialogUtil;
import com.yjx.wuziqi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameActivity extends BaseActivity implements PannelView.OnGameOverListener,
        PannelView.OnTurnChangedListener {

    private static final String IS_CUR_WHITE_TURN = "is_cur_white_turn";

    @BindView(R.id.v_pannel)
    PannelView mPannelView;
    @BindView(R.id.iv_which_turn)
    ImageView mWhichTurnImage;
    @BindView(R.id.tv_new_game)
    TextView mNewGameText;
    @BindView(R.id.tv_regret)
    TextView mRegretText;

    private Bitmap mWhitePiece;
    private Bitmap mBlackPiece;
    private AnimationSet mAnimSet;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_game;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        mPannelView.registerGameOverListner(this);
        mPannelView.registerTurnChangedListener(this);
        mWhitePiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_w2);
        mBlackPiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_b1);
        mWhichTurnImage.setImageBitmap(mWhitePiece);
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
            default:
                break;
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {//Activity销毁时保存的当前轮到谁下子
            boolean isCurWhiteTurn = savedInstanceState.getBoolean(IS_CUR_WHITE_TURN, true);
            mWhichTurnImage.setImageBitmap(isCurWhiteTurn ? mWhitePiece : mBlackPiece);
        }
    }

    /**
     * Activity销毁时存储当前轮到谁下子
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_CUR_WHITE_TURN, mPannelView.getIsCurWhiteTurn());
    }

    @Override
    public void onGameOver(boolean isWhiteWin) {
        DialogUtil.createDialog(mDecorView, isWhiteWin ? getString(R.string.white_win) : getString(R.string.black_win),
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
