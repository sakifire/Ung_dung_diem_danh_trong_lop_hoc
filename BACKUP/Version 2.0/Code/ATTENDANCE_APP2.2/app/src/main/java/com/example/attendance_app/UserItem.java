package com.example.attendance_app;

public class UserItem {
    private long uid;
    private String userName;
    private String passWord;



    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public UserItem(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public UserItem(long uid, String userName, String passWord) {
        this.uid = uid;
        this.userName = userName;
        this.passWord = passWord;
    }

    public UserItem(){}
}
