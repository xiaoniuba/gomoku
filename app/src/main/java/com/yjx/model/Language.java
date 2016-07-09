package com.yjx.model;

import com.yjx.utils.StringUtil;

import java.util.ArrayList;

/**
 * Created by yangjinxiao on 2016/7/8.
 */
public class Language {
    private String type;//"english" "simple_chinese"
    private String type4Show;//"English" "简体汉字"
    public static ArrayList<Language> ALL_LANGS = new ArrayList<>();
    static {
        Language en = new Language(Language.Type.ENGLISH, Language.Type4Show.ENGLISH);
        Language zh = new Language(Language.Type.SIMPLED_CHINESE, Language.Type4Show.SIMPLED_CHINESE);
        ALL_LANGS.add(en);
        ALL_LANGS.add(zh);
    }

    public Language(String type, String type4Show) {
        this.type = type;
        this.type4Show = type4Show;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof Language) {
            Language obj = (Language) o;
            String objType = obj.getType();
            String objType4Show = obj.getType4Show();
            if ((!StringUtil.isNullOrEmpty(objType) && objType.equals(this.getType()))
                    || (!StringUtil.isNullOrEmpty(objType4Show) && objType4Show.equals(this.getType4Show()))) {
                return true;
            }
        }
        return false;
    }

    public interface Type {
        String ENGLISH = "english";
        String SIMPLED_CHINESE = "simple_chinese";
    }

    public interface Type4Show {
        String ENGLISH = "English";
        String SIMPLED_CHINESE = "简体中文";
    }

    public String getType() {
        return type;
    }

    public void setType(String lang) {
        this.type = lang;
    }

    public String getType4Show() {
        return type4Show;
    }

    public void setLang4Show(String lang4Show) {
        this.type4Show = lang4Show;
    }
}
