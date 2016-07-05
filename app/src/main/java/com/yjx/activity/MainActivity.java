package com.yjx.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yjx.utils.DialogUtil;

import com.yjx.customview.PannelView;
import com.yjx.wuziqi.R;

public class MainActivity extends BaseActivity implements PannelView.OnGameOverListener,
                                                            PannelView.OnTurnChangedListener{
    private static final String IS_CUR_WHITE_TURN = "is_cur_white_turn";

    private PannelView mPannelView;
    private TextView mGoLoginText;
    private TextView mWhichTurnText;
    private ImageView mWhichTurnImage;
    private Bitmap mWhitePiece;
    private Bitmap mBlackPiece;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGoLoginText = (TextView) findViewById(R.id.tv_go_login);
        mGoLoginText.setOnClickListener(this);
        mPannelView = (PannelView) findViewById(R.id.v_pannel);
        mPannelView.registerGameOverListner(this);
        mPannelView.registerTurnChangedListener(this);
        mWhichTurnText = (TextView) findViewById(R.id.tv_which_turn);
        mWhichTurnImage = (ImageView) findViewById(R.id.iv_which_turn);
        mWhitePiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_w2);
        mBlackPiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_b1);
        mWhichTurnText.setText("白子先手");
        mWhichTurnImage.setImageBitmap(mWhitePiece);
        if (savedInstanceState != null) {//Activity销毁时保存的当前轮到谁下子
            boolean isCurWhiteTurn = savedInstanceState.getBoolean(IS_CUR_WHITE_TURN, true);
            mWhichTurnText.setText(isCurWhiteTurn ? "轮到白子" : "轮到黑子");
            mWhichTurnImage.setImageBitmap(isCurWhiteTurn ? mWhitePiece : mBlackPiece);
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        switch (id) {
            case R.id.tv_go_login:
                goLoginAct();
                break;
            default:
                break;
        }
    }

    private void goLoginAct() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onGameOver(boolean isWhiteWin) {
        DialogUtil.createDialog(this, mDecorView, isWhiteWin ? getString(R.string.white_win) :getString(R.string.black_win),
                "", getString(R.string.restart), null, new Runnable() {
                    @Override
                    public void run() {
                        mPannelView.restart();
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
                mPannelView.restart();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTurnChangeListener(boolean isWhiteTurn) {
        mWhichTurnText.setText(isWhiteTurn ? "轮到白子" : "轮到黑子");
        mWhichTurnImage.setImageBitmap(isWhiteTurn ? mWhitePiece : mBlackPiece);
    }
}
