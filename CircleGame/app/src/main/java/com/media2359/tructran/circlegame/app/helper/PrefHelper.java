package com.media2359.tructran.circlegame.app.helper;

import android.content.SharedPreferences;

import com.media2359.tructran.circlegame.app.CircleGameApplication;

/**
 * Created by TrucTran on 5/15/16.
 */
public class PrefHelper {

    public static final String CIRCLE_GAME_PREF_NAME = "CircleGamePref";
    public static final String PREF_SCREEN_WIDTH = "PrefScreenWidth";
    public static final String PREF_SCREEN_HEIGHT = "PrefScreenHeight";

    private static SharedPreferences mSharedPref = CircleGameApplication.getInstance().getSharedPreferences(CIRCLE_GAME_PREF_NAME, 0);

    private static void putBoolean(String key, boolean value) {
        mSharedPref.edit().putBoolean(key, value).apply();
    }

    private static void putInt(String key, int value) {
        mSharedPref.edit().putInt(key, value).apply();
    }

    private static void putString(String key, String value) {
        mSharedPref.edit().putString(key, value);
    }

    private static boolean getBoolean(String key, boolean defaultValue) {
        return mSharedPref.getBoolean(key, defaultValue);
    }

    private static int getInt(String key, int defaultValue) {
        return mSharedPref.getInt(key, defaultValue);
    }

    private static String getString(String key, String defaultValue) {
        return mSharedPref.getString(key, defaultValue);
    }

    public static void setScreenSize(int width, int height) {
        putInt(PREF_SCREEN_WIDTH, width);
        putInt(PREF_SCREEN_HEIGHT, height);
    }

    public static int getScreenWidth() {
        return getInt(PREF_SCREEN_WIDTH, 0);
    }

    public static int getScreenHeight() {
        return getInt(PREF_SCREEN_HEIGHT, 0);
    }
}
