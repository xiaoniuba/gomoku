package com.yjx.model;

import com.yjx.utils.Constants;

/**
 * Created by yangjinxiao on 2016/7/8.
 */
public class ThemeModel {
    private ThemeType themeType;
    private String themeModel4Show;//用于显示
    private String themeModel4ShowEn;//用于显示（英文环境下）

    public static final int DAY = 1;
    public static final int NIGHT = 2;
    public static final int DOWN = 3;

    public static enum ThemeType {
        DAY, NIGHT, DOWN;
    }

    public ThemeModel(ThemeType themeModel, String themeModel4Show, String themeModel4ShowEn) {
        this.themeType = themeModel;
        this.themeModel4Show = themeModel4Show;
        this.themeModel4ShowEn = themeModel4ShowEn;
    }

    public ThemeType getThemeType() {
        return themeType;
    }

    public void setThemeType(ThemeType themeModel) {
        this.themeType = themeModel;
    }

    public String getThemeModel4Show() {
        return themeModel4Show;
    }

    public void setThemeModel4Show(String themeModel4Show) {
        this.themeModel4Show = themeModel4Show;
    }

    public String getThemeModel4ShowEn() {
        return themeModel4ShowEn;
    }

    public void setThemeModel4ShowEn(String themeModel4ShowEn) {
        this.themeModel4ShowEn = themeModel4ShowEn;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        ThemeModel otherModel = null;
        if (obj instanceof ThemeModel) {
            otherModel = (ThemeModel) obj;
        }
        if (otherModel != null) {
            return this.getThemeType().equals(otherModel.getThemeType())
                    && this.getThemeModel4Show().equals(otherModel.getThemeModel4Show())
                    && this.getThemeModel4ShowEn().equals(otherModel.getThemeModel4ShowEn());
        }
        return false;
    }
}
