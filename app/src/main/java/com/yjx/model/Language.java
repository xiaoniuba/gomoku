package com.yjx.model;

/**
 * Created by yangjinxiao on 2016/7/8.
 */
public class Language {
    private String lang;//"english" "simple_chinese"
    private String lang4Show;//"English" "简体汉字"

    public Language(String lang, String lang4Show) {
        this.lang = lang;
        this.lang4Show = lang4Show;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang4Show() {
        return lang4Show;
    }

    public void setLang4Show(String lang4Show) {
        this.lang4Show = lang4Show;
    }
}
