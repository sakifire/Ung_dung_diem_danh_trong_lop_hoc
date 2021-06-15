package com.example.attendance_app.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.attendance_app.DbHelper;

public class StudentService extends DbHelper {
    private DbHelper dbHelper;
    public StudentService(@Nullable Context context) {
        super(context);
        this.dbHelper = new DbHelper(context);
    }

    public DbHelper getDbHelper() {
        return dbHelper;
    }

    //Student
    public long addStudent(long cid, int roll, String name) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C_ID, cid);
        values.put(STUDENT_ROLL_KEY, roll);
        values.put(STUDENT_NAME_KEY, name);
        return database.insert(STUDENT_TABLE_NAME, null, values);
    }

    public Cursor getStudentTable(long cid) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.query(STUDENT_TABLE_NAME, null, C_ID + "=?", new String[]{String.valueOf(cid)}, null, null, STUDENT_ROLL_KEY);
    }

    public long deleteStudent(long sid) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.delete(STUDENT_TABLE_NAME, S_ID + "=?", new String[]{String.valueOf(sid)});
    }

    public long updateStudent(long sid, String name) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STUDENT_NAME_KEY, name);
        return database.update(STUDENT_TABLE_NAME, values, S_ID + "=?", new String[]{String.valueOf(sid)});
    }

    //Status
    public long addStatus(long sid, long cid, String date, String status) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(S_ID, sid);
        values.put(C_ID, cid);
        values.put(DATE_KEY, date);
        values.put(STATUS_KEY, status);

        return database.insert(STATUS_TABLE_NAME, null, values);

    }

    public long updateStatus(long sid, String date, String status) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STATUS_KEY, status);
        String whereClause = DATE_KEY + "='" + date + "' AND " + S_ID + "=" + sid;

        return database.update(STATUS_TABLE_NAME, values, whereClause, null);

    }

    public String getStatus(long sid, String date) {
        String status = null;
        SQLiteDatabase database = this.getReadableDatabase();
        String whereClause = DATE_KEY + "='" + date + "' AND " + S_ID + "=" + sid;
        Cursor cursor = database.query(STATUS_TABLE_NAME, null, whereClause, null, null, null, null);
        if (cursor.moveToFirst())
            status = cursor.getString(cursor.getColumnIndex(STATUS_KEY));
        return status;

    }

    public Cursor getDistinctMonth(long cid) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.query(STATUS_TABLE_NAME, new String[]{DATE_KEY},
                C_ID + "=" + cid, null, "substr(" + DATE_KEY + ",4,7)", null, null);//01.04.2020
    }
}
