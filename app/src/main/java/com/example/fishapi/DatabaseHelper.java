package com.example.fishapi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DatabaseHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "fishApiDatabase";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";

    public static final String KEY_USER_ID = "id";
    public static final String KEY_USER_FIRST_NAME = "firstName";
    public static final String KEY_USER_LAST_NAME = "lastName";
    public static final String KEY_USER_EMAIL = "email";
    public static final String KEY_USER_PASSWORD = "password";
    public static final String KEY_USER_DOB = "dateOfBirth";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE "+TABLE_USERS +
                "("+
                KEY_USER_ID + " INTEGER PRIMARY KEY," +
                KEY_USER_FIRST_NAME+ " TEXT," +
                KEY_USER_LAST_NAME + " TEXT," +
                KEY_USER_EMAIL + " TEXT," +
                KEY_USER_PASSWORD + " TEXT," +
                KEY_USER_DOB + " TEXT" +
                ")";

        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}
