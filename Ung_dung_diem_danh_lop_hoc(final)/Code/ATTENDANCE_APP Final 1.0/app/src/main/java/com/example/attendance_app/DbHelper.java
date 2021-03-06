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
    private Context context;

    //User table
    public static final String USER_TABLE_NAME = "USER_TABLE_NAME";
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
    public static final String CLASS_TABLE_NAME = "CLASS_TABLE";
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
    public static final String STUDENT_TABLE_NAME = "STUDENT_TABLE";
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
    public static final String STATUS_TABLE_NAME = "STATUS_TABLE";
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
