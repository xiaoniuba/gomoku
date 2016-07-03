package com.yjx.wuziqi;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import customview.PannalView;

public class MainActivity extends ActionBarActivity implements PannalView.OnGameOverListener {
    private PannalView mPannelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPannelView = (PannalView) findViewById(R.id.v_pannel);
        mPannelView.registerListner(this);
    }

    @Override
    public void onGameOver(boolean isWhiteWin) {
        if (isWhiteWin) {
            Toast.makeText(this, "white win!", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "black win!", Toast.LENGTH_LONG).show();
        }
    }
}
