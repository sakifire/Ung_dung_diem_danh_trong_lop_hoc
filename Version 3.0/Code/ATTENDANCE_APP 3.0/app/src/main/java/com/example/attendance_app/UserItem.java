package com.example.attendance_app;

public class UserItem {
    private long uid;
    private String userName;
    private String passWord;
    private String email;
    private String gender;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

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

    public UserItem(long uid, String userName, String passWord, String email, String gender) {
        this.uid = uid;
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
        this.gender = gender;
    }

    public UserItem(){}
}
