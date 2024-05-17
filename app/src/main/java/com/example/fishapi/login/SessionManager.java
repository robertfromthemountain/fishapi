package com.example.fishapi.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SessionManager {
    private static final String PREF_NAME = "AppPrefs";
    private static final String IS_LOGGED_IN = "IsLoggedIn";
    private SharedPreferences prefs;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setLogin(boolean isLoggedIn, int userId) {
        Log.d("SessionManager", "Setting login state to: " + isLoggedIn + ", user ID: " + userId);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn);
        if (isLoggedIn) {
            editor.putInt("UserId", userId);
        } else {
            editor.remove("UserId");
        }
        editor.apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(IS_LOGGED_IN, false);
    }

    public int getUserId() {
        return prefs.getInt("UserId", -1);
    }
}

