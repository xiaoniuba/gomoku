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
    private static Context mContext = null;
    public enum DialogType {
        ONE_BTN, TWO_BTN
    }
    private static LayoutInflater mInflater = null;
    public static void init(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }
    public static void createDialog(View parentView, String msg,
                                           String leftBtnStr, String rightBtnStr,
                                           final Runnable leftClickRunnable, final Runnable righClickRunnable) {
        if (mInflater == null) {
            return;
        }
        RelativeLayout layout = (RelativeLayout) mInflater.inflate(R.layout.layout_popupwindow_container, null);
        LinearLayout containerLayout = (LinearLayout) layout.findViewById(R.id.ll_container);
        containerLayout.setBackgroundResource(R.drawable.bg_popupwindow);
        View dialogLayout = mInflater.inflate(R.layout.dialog_main, null);
        containerLayout.addView(dialogLayout);
        TextView msgText = (TextView) dialogLayout.findViewById(R.id.tv_msg);
        TextView leftBtnText = (TextView) dialogLayout.findViewById(R.id.bt_1);
        TextView rightBtnText = (TextView) dialogLayout.findViewById(R.id.bt_2);
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
        mPopupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
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
