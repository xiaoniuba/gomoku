package com.yjx.utils;

/**
 * 常量类
 * Created by yangjinxiao on 2016/7/3.
 */
public class Constants {
    public static final int MAX_LINE = 10;//棋盘的行数
    public static final int WIN_PIECES_NUM = 5;//连成几个子算赢

    public interface SharedPreferenceConstant {
        String PREFERENCE_NAME = "GomokuApp";
        String PROPERTY_THEME = "propertiy_theme";
    }

    public interface Language4Show {
        String ENGLISH = "English";
        String SIMPLED_CHINESE = "简体中文";
    }

    public interface Language {
        String ENGLISH = "english";
        String SIMPLED_CHINESE = "simple_chinese";
    }
}
