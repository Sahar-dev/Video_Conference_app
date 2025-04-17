package com.android.avempace.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.avempace.App;

import timber.log.Timber;

public class SharedPreferenceHelper {


    private static final String TAG = SharedPreferenceHelper.class.getSimpleName();
    private SharedPreferences prefs;

    public SharedPreferenceHelper() {
        this.prefs = App.getContext().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
    }

    public void insertString(String key, String value) {
        Timber.d("Inserting "+value+" in SharedPref");
        prefs.edit().putString(key, value).apply();
    }

    public void removeSharedPref(String key){
        prefs.edit().remove(key).apply();
    }

    public void insertLong(String key, long value) {
        prefs.edit().putLong(key, value).apply();
    }

    public long getLong(String key) {
        return prefs.getLong(key, 0);
    }

    public void insertBool(String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }

    public boolean getBool(String key) {
        return prefs.getBoolean(key, false);
    }

    public void insertInt(String key, int value) {
        prefs.edit().putInt(key, value).apply();
    }


    public int getInt(String key) {
        return prefs.getInt(key, 0);
    }

    public String getString(String key) {
        return prefs.getString(key, "");
    }

    public  String getString(String key, String defaultOne) {
        return prefs.getString(key, defaultOne);
    }



    public void insertToken(String token) {
        prefs.edit().putString(MyUtils.TOKEN_KEY, token).apply();

    }

    public String getToken() {

        return prefs.getString(MyUtils.TOKEN_KEY, "");
    }
    public String getFireBaseToken() {

        return prefs.getString(MyUtils.FIREBASE_TOKEN_KEY, "");
    }


}
