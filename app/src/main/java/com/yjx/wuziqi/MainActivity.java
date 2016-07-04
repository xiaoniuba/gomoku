package com.yjx.wuziqi;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.yjx.utils.DialogUtil;

import com.yjx.customview.PannelView;

public class MainActivity extends BaseActivity implements PannelView.OnGameOverListener {

    private PannelView mPannelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPannelView = (PannelView) findViewById(R.id.v_pannel);
        mPannelView.registerListner(this);
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
}
