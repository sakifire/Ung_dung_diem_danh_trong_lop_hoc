package com.example.attendance_app.Controller;

import android.content.Context;

import androidx.annotation.Nullable;

import com.example.attendance_app.DbHelper;
import com.example.attendance_app.Service.UserService;
import com.example.attendance_app.Model.UserItem;

public class UserController extends DbHelper {
    private UserService userService;

    public UserController(@Nullable Context context) {
        super(context);
        this.userService = new UserService(context);
    }

    public UserService getUserModel() {
        return userService;
    }

    public Boolean addUser(String username, String password, String email, String gender) {
        return userService.addUser(username, password, email, gender);
    }

    public int getUser(String username) {
        return userService.getUser(username);
    }

    public String getEmail(String username) {
        return userService.getEmail(username);
    }

    public String getGender(String username) {
        return userService.getGender(username);
    }

    public UserItem getUserById(String username) {
        return userService.getUserById(username);
    }

    public Boolean checkUserName(String username) {
        return userService.checkUserName(username);
    }

    public Boolean checkUserNamePassword(String username, String password) {
        return userService.checkUserNamePassword(username, password);
    }

    public long updateUser(String uid, String userName, String pass, String email, String gender) {
        return userService.updateUser(uid, userName, pass, email, gender);
    }
}
