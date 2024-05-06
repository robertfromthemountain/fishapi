package com.example.fishapi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    public static final String TABLE_CATCHES = "catches";
    public static final String KEY_CATCH_ID = "id";
    public static final String KEY_USER_ID_FK = "userId";  // Foreign key from users table
    public static final String KEY_LOCATION = "location";
    public static final String KEY_WEIGHT = "weight";
    public static final String KEY_SIZE = "size";
    public static final String KEY_SPECIE = "specie";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE_URI = "imageUri";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" +
                KEY_USER_ID + " INTEGER PRIMARY KEY," +
                KEY_USER_FIRST_NAME + " TEXT," +
                KEY_USER_LAST_NAME + " TEXT," +
                KEY_USER_EMAIL + " TEXT," +
                KEY_USER_PASSWORD + " TEXT," +
                KEY_USER_DOB + " TEXT" +
                ")";
        String CREATE_CATCHES_TABLE = "CREATE TABLE " + TABLE_CATCHES +
                "(" +
                KEY_CATCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_USER_ID_FK + " INTEGER," +
                KEY_LOCATION + " TEXT," +
                KEY_WEIGHT + " TEXT," +
                KEY_SIZE + " TEXT," +
                KEY_SPECIE + " TEXT," +
                KEY_DESCRIPTION + " TEXT," +
                KEY_IMAGE_URI + " TEXT," +
                "FOREIGN KEY(" + KEY_USER_ID_FK + ") REFERENCES " + TABLE_USERS + "(" + KEY_USER_ID + ")" +
                ")";
        Log.d("Database Operation", "Creating tables...");
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_CATCHES_TABLE);
        Log.d("Database Operation", "Tables created successfully.");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATCHES);
        onCreate(db);
    }
}
