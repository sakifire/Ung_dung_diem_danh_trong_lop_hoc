package com.example.attendance_app.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.attendance_app.DbHelper;
import com.example.attendance_app.Model.UserItem;

public class UserService extends DbHelper {
    private DbHelper dbHelper;

    public UserService(@Nullable Context context) {
        super(context);
        this.dbHelper = new DbHelper(context);
    }

    public DbHelper getDbHelper() {
        return dbHelper;
    }

    public Boolean addUser(String username, String password, String email, String gender) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, username);
        values.put(PASSWORD, password);
        values.put(EMAIL, email);
        values.put(GENDER, gender);
        long result = database.insert(USER_TABLE_NAME, null, values);
        if (result == -1) return false;
        else
            return true;

    }

    public int getUser(String username) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE " + USER_NAME +
                " = ? ", new String[]{username});
        if (cursor.getCount() > 0)
            cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public String getEmail(String username) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE " + USER_NAME +
                " = ? ", new String[]{username});
        if (cursor.getCount() > 0)
            cursor.moveToFirst();
        return cursor.getString(3);
    }

    public String getGender(String username) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE " + USER_NAME +
                " = ? ", new String[]{username});
        if (cursor.getCount() > 0)
            cursor.moveToFirst();
        return cursor.getString(4);
    }

    public UserItem getUserById(String username) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE " + USER_NAME +
                " = ? ", new String[]{username});
        if (cursor.getCount() > 0)
            cursor.moveToFirst();

        return new UserItem(cursor.getInt(0), cursor.getString(1),
                cursor.getString(2), cursor.getString(3),
                cursor.getString(4));
    }

    public Boolean checkUserName(String username) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE " + USER_NAME + " = ? ", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkUserNamePassword(String username, String password) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE " + USER_NAME +
                " = ? AND " + PASSWORD + " = ?", new String[]{username, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public long updateUser(String uid, String userName, String pass, String email, String gender) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, userName);
        values.put(PASSWORD, pass);
        values.put(EMAIL, email);
        values.put(GENDER, gender);

        return database.update(USER_TABLE_NAME, values, U_ID + "=?", new String[]{String.valueOf(uid)});
    }
}
