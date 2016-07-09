package com.yjx.model;

import com.yjx.utils.Constants;
import com.yjx.utils.JsonUtil;
import com.yjx.utils.LogUtil;
import com.yjx.wuziqi.R;

import java.util.ArrayList;

/**
 * Created by yangjinxiao on 2016/7/8.
 */
public class ThemeModel {
    private ThemeType type;
    private String type4Show;//用于显示
    private String type4ShowEn;//用于显示（英文环境下）

    public static ArrayList<ThemeModel> ALL_THEMES = new ArrayList<>();
    static {
        ThemeModel day = new ThemeModel(ThemeModel.ThemeType.DAY);
        ThemeModel night = new ThemeModel(ThemeModel.ThemeType.NIGHT);
        ThemeModel down = new ThemeModel(ThemeModel.ThemeType.DAWN);
        ThemeModel sexy = new ThemeModel(ThemeModel.ThemeType.SEXY);
        ALL_THEMES.add(day);
        ALL_THEMES.add(night);
        ALL_THEMES.add(down);
        ALL_THEMES.add(sexy);
    }

    public enum ThemeType {
        DAY, NIGHT, DAWN, SEXY
    }

    public interface ThemeResId {
        int DAY = R.style.DayTheme;
        int NIGHT = R.style.NightTheme;
        int SEXY = R.style.SexyTheme;
    }

    public interface Type4Show {
        String DAY = "日间模式";
        String NIGHT = "夜间模式";
        String DAWN = "傍晚模式";
        String SEXY = "紫色魅惑";

        String DAY_EN = "Day theme";
        String NIGHT_EN = "Night theme";
        String DAWN_EN = "Down theme";
        String SEXY_EN = "Plum theme";
    }

    public ThemeModel(ThemeType type) {
        this.type = type;
        switch (type) {
            case DAY:
                this.type4Show = Type4Show.DAY;
                this.type4ShowEn = Type4Show.DAY_EN;
                break;
            case NIGHT:
                this.type4Show = Type4Show.NIGHT;
                this.type4ShowEn = Type4Show.NIGHT_EN;
                break;
            case DAWN:
                this.type4Show = Type4Show.DAWN;
                this.type4ShowEn = Type4Show.DAWN_EN;
                break;
            case SEXY:
                this.type4Show = Type4Show.SEXY;
                this.type4ShowEn = Type4Show.SEXY_EN;
                break;
        }
    }

    public ThemeType getType() {
        return type;
    }

    public void setType(ThemeType type) {
        this.type = type;
    }

    public String getType4Show() {
        return type4Show;
    }

    public void setType4Show(String themeModel4Show) {
        this.type4Show = themeModel4Show;
    }

    public String getType4ShowEn() {
        return type4ShowEn;
    }

    public void setType4ShowEn(String themeModel4ShowEn) {
        this.type4ShowEn = themeModel4ShowEn;
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
            return this.getType().equals(otherModel.getType())
                    && this.getType4Show().equals(otherModel.getType4Show())
                    && this.getType4ShowEn().equals(otherModel.getType4ShowEn());
        }
        return false;
    }
}
