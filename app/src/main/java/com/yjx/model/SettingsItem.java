package com.yjx.model;

/**
 * Created by yangjinxiao on 2016/7/8.
 */
public class SettingsItem {
    private int imgId;
    private String contentStr;

    public SettingsItem(int imgId, String contentStr) {
        this.imgId = imgId;
        this.contentStr = contentStr;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getContentStr() {
        return contentStr;
    }

    public void setContentStr(String contentStr) {
        this.contentStr = contentStr;
    }
}
