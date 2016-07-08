package com.yjx.utils;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yjx.wuziqi.R;

/**
 * 对话框工具类
 * Created by yangjinxiao on 2016/7/3.
 */
public class DialogUtil {
    private static PopupWindow mPopupWindow;
    public static void createDialog(Context context, View parentView, String msg,
                                           String leftBtnStr, String rightBtnStr,
                                           final Runnable leftClickRunnable, final Runnable righClickRunnable) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_main, null);
        TextView msgText = (TextView) contentView.findViewById(R.id.tv_msg);
        TextView leftBtnText = (TextView) contentView.findViewById(R.id.bt_1);
        TextView rightBtnText = (TextView) contentView.findViewById(R.id.bt_2);
        msgText.setText(msg);
        leftBtnText.setText(leftBtnStr);
        rightBtnText.setText(rightBtnStr);
        leftBtnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leftClickRunnable.run();
                mPopupWindow.dismiss();
            }
        });
        rightBtnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                righClickRunnable.run();
                mPopupWindow.dismiss();
            }
        });
        leftBtnText.setVisibility(StringUtil.isNullOrEmpty(leftBtnStr) ? View.GONE : View.VISIBLE);
        rightBtnText.setVisibility(StringUtil.isNullOrEmpty(rightBtnStr) ? View.GONE : View.VISIBLE);
        mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x33000000));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
    }

    public static PopupWindow createPopupWindowWithCustomView(View parentView, View contentView) {
        mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
        mPopupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
        return mPopupWindow;
    }
}
