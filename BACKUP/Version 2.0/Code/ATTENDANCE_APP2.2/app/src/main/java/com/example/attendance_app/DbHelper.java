package com.example.attendance_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final int VERSION = 4;

    //User table
    private static final String USER_TABLE_NAME = "USER_TABLE_NAME";
    public static final String U_ID = "_UID";
    public static final String USER_NAME = "USER_NAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String EMAIL = "EMAIL";
    public static final String GENDER = "GENDER";

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE " + USER_TABLE_NAME + "("
                    + U_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + USER_NAME + " TEXT NOT NULL, "
                    + PASSWORD + " TEXT NOT NULL, "
                    + EMAIL + " TEXT NOT NULL, "
                    + GENDER + " TEXT NOT NULL, "
                    + " UNIQUE (" + U_ID + "," + USER_NAME + ")" + ");";

    private static final String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + USER_TABLE_NAME;
    private static final String SELECT_USER_TABLE = "SELECT * FROM " + USER_TABLE_NAME;


    //CLass table
    private static final String CLASS_TABLE_NAME = "CLASS_TABLE";
    public static final String C_ID = "_CID";
    public static final String CLASS_NAME_KEY = "CLASS_NAME";
    public static final String SUBJECT_NAME_KEY = "SUBJECT_NAME";

    private static final String CREATE_CLASS_TABLE =
            "CREATE TABLE " + CLASS_TABLE_NAME + "("
                    + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + U_ID + " INTEGER NOT NULL, "
                    + CLASS_NAME_KEY + " TEXT NOT NULL, "
                    + SUBJECT_NAME_KEY + " TEXT NOT NULL, "
                    + " UNIQUE (" + CLASS_NAME_KEY + "," + SUBJECT_NAME_KEY + "),"
                    + " FOREIGN KEY ( " + U_ID + ") REFERENCES "
                    + USER_TABLE_NAME
                    + "( " + U_ID + ")"
                    + ");";


    private static final String DROP_CLASS_TABLE = "DROP TABLE IF EXISTS " + CLASS_TABLE_NAME;
    private static final String SELECT_CLASS_TABLE = "SELECT * FROM " + CLASS_TABLE_NAME;

    //Student table
    private static final String STUDENT_TABLE_NAME = "STUDENT_TABLE";
    public static final String S_ID = "_SID";
    public static final String STUDENT_NAME_KEY = "STUDENT_NAME";
    public static final String STUDENT_ROLL_KEY = "ROLL";

    private static final String CREATE_STUDENT_TABLE =
            "CREATE TABLE " + STUDENT_TABLE_NAME
                    + "( "
                    + S_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + C_ID + " INTEGER NOT NULL, "
                    + STUDENT_NAME_KEY + " TEXT NOT NULL, "
                    + STUDENT_ROLL_KEY + " INTEGER, "
                    + " FOREIGN KEY ( " + C_ID + ") REFERENCES "
                    + CLASS_TABLE_NAME
                    + "(" + C_ID + ")"
                    + ");";

    private static final String DROP_STUDENT_TABLE = "DROP TABLE IF EXISTS " + STUDENT_TABLE_NAME;
    private static final String SELECT_STUDENT_TABLE = "SELECT * FROM " + STUDENT_TABLE_NAME;

    //Status table
    private static final String STATUS_TABLE_NAME = "STATUS_TABLE";
    public static final String STATUS_ID = "_STATUS_ID";
    public static final String DATE_KEY = "STATUS_DATE";
    public static final String STATUS_KEY = "STATUS";

    private static final String CREATE_STATUS_TABLE =
            "CREATE TABLE " + STATUS_TABLE_NAME
                    + "("
                    + STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + S_ID + " INTEGER NOT NULL, "
                    + C_ID + " INTEGER NOT NULL, "
                    + DATE_KEY + " DATE NOT NULL, "
                    + STATUS_KEY + " TEXT NOT NULL, "
                    + " UNIQUE (" + S_ID + "," + DATE_KEY + "),"
                    + " FOREIGN KEY ( " + S_ID + ") REFERENCES " + STATUS_TABLE_NAME + "( " + S_ID + "),"
                    + " FOREIGN KEY ( " + C_ID + ") REFERENCES " + CLASS_TABLE_NAME + "( " + C_ID + ")"
                    + ");";

    private static final String DROP_STATUS_TABLE = "DROP TABLE IF EXISTS " + STATUS_TABLE_NAME;
    private static final String SELECT_STATUS_TABLE = "SELECT * FROM " + STATUS_TABLE_NAME;


    public DbHelper(@Nullable Context context) {
        super(context, "Attendance.db", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_CLASS_TABLE);
        db.execSQL(CREATE_STUDENT_TABLE);
        db.execSQL(CREATE_STATUS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_USER_TABLE);
            db.execSQL(DROP_CLASS_TABLE);
            db.execSQL(DROP_STUDENT_TABLE);
            db.execSQL(DROP_STATUS_TABLE);
            db.execSQL(CREATE_USER_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //User
    public Boolean insertData(String username, String password, String email, String gender) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("email", email);
        contentValues.put("gender", gender);
        long result = MyDB.insert("users", null, contentValues);
        if (result == -1) return false;
        else
            return true;
    }

    Boolean addUser(String username, String password, String email, String gender) {
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


    //Class
    long addClass(long uid, String className, String subjectName) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(U_ID, uid);
        values.put(CLASS_NAME_KEY, className);
        values.put(SUBJECT_NAME_KEY, subjectName);

        return database.insert(CLASS_TABLE_NAME, null, values);
    }

    Cursor getClassTable(long uid) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.query(CLASS_TABLE_NAME, null, U_ID + "=?", new String[]{String.valueOf(uid)}, null, null, null);
        //return database.rawQuery(SELECT_CLASS_TABLE, null);
    }

    int deleteClass(long cid) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.delete(CLASS_TABLE_NAME, C_ID + "=?", new String[]{String.valueOf(cid)});
    }

    long updateClass(long cid, String className, String subjectName) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CLASS_NAME_KEY, className);
        values.put(SUBJECT_NAME_KEY, subjectName);

        return database.update(CLASS_TABLE_NAME, values, C_ID + "=?", new String[]{String.valueOf(cid)});
    }

    //Student
    long addStudent(long cid, int roll, String name) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C_ID, cid);
        values.put(STUDENT_ROLL_KEY, roll);
        values.put(STUDENT_NAME_KEY, name);
        return database.insert(STUDENT_TABLE_NAME, null, values);
    }

    Cursor getStudentTable(long cid) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.query(STUDENT_TABLE_NAME, null, C_ID + "=?", new String[]{String.valueOf(cid)}, null, null, STUDENT_ROLL_KEY);
    }

    int deleteStudent(long sid) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.delete(STUDENT_TABLE_NAME, S_ID + "=?", new String[]{String.valueOf(sid)});
    }

    long updateStudent(long sid, String name) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STUDENT_NAME_KEY, name);
        return database.update(STUDENT_TABLE_NAME, values, S_ID + "=?", new String[]{String.valueOf(sid)});
    }

    //Status
    long addStatus(long sid, long cid, String date, String status) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(S_ID, sid);
        values.put(C_ID, cid);
        values.put(DATE_KEY, date);
        values.put(STATUS_KEY, status);

        return database.insert(STATUS_TABLE_NAME, null, values);

    }

    long updateStatus(long sid, String date, String status) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STATUS_KEY, status);
        String whereClause = DATE_KEY + "='" + date + "' AND " + S_ID + "=" + sid;

        return database.update(STATUS_TABLE_NAME, values, whereClause, null);

    }

    String getStatus(long sid, String date) {
        String status = null;
        SQLiteDatabase database = this.getReadableDatabase();
        String whereClause = DATE_KEY + "='" + date + "' AND " + S_ID + "=" + sid;
        Cursor cursor = database.query(STATUS_TABLE_NAME, null, whereClause, null, null, null, null);
        if (cursor.moveToFirst())
            status = cursor.getString(cursor.getColumnIndex(STATUS_KEY));
        return status;

    }

    Cursor getDistinctMonth(long cid) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.query(STATUS_TABLE_NAME, new String[]{DATE_KEY},
                C_ID + "=" + cid, null, "substr(" + DATE_KEY + ",4,7)", null, null);//01.04.2020
    }


}
