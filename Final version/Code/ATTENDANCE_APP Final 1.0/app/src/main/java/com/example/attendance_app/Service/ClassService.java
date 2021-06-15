package com.example.attendance_app.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.attendance_app.DbHelper;

public class ClassService extends DbHelper {
    private DbHelper dbHelper;

    public ClassService(@Nullable Context context) {
        super(context);
        this.dbHelper = new DbHelper(context);
    }

    public DbHelper getDbHelper() {
        return dbHelper;
    }

    public long addClass(long uid, String className, String subjectName) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(U_ID, uid);
        values.put(CLASS_NAME_KEY, className);
        values.put(SUBJECT_NAME_KEY, subjectName);

        return database.insert(CLASS_TABLE_NAME, null, values);
    }

    public Cursor getClassTable(long uid) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.query(CLASS_TABLE_NAME, null, U_ID + "=?", new String[]{String.valueOf(uid)}, null, null, null);
        //return database.rawQuery(SELECT_CLASS_TABLE, null);
    }

    public int deleteClass(long cid) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.delete(CLASS_TABLE_NAME, C_ID + "=?", new String[]{String.valueOf(cid)});
    }

    public long updateClass(long cid, String className, String subjectName) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CLASS_NAME_KEY, className);
        values.put(SUBJECT_NAME_KEY, subjectName);

        return database.update(CLASS_TABLE_NAME, values, C_ID + "=?", new String[]{String.valueOf(cid)});
    }

    public Boolean checkClassName(String classname) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM " + CLASS_TABLE_NAME + " WHERE " + CLASS_NAME_KEY + " = ? ", new String[]{classname});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
}
