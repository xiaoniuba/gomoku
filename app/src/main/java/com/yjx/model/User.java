package com.yjx.model;


/**
 * 用户类
 * Created by yangjinxiao on 2016/7/4.
 */
public class User {

    public static final String USER_HISTORY = "user_history";

    private String userId;
    private String userName;
    private String userPhone;
    private String userPassword;

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
