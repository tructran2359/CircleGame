package com.media2359.tructran.circlegame.app.helper;

import android.util.Log;

import com.media2359.tructran.circlegame.app.BuildConfig;

/**
 * Created by TrucTran on 5/15/16.
 */
public class LogUtils {

    private static final String TAG_PREFIX = "Cir_";
    private static final boolean LOG_ALLOWED = BuildConfig.DEBUG;

    private static void mainLog(int type, String tag, String message) {

        tag = (tag == null ? "null" : tag.isEmpty() ? "empty" : TAG_PREFIX + tag);
        if (tag.length() > 23) {
            tag = tag.substring(0, 22);
        }

        message = (message == null ? "null" : message.isEmpty() ? "empty" : message);

        if (LOG_ALLOWED) {
            switch (type) {
                case 0:
                    Log.i (tag, message);
                    break;

                case 1:
                    Log.d(tag, message);
                    break;

                case 2:
                    Log.e(tag, message);
                    break;

                case 3:
                    Log.w(tag, message);
                    break;

                default:
                    Log.i(tag, message);
            }
        }
    }

    public static void i(String tag, String message) {
        mainLog(0, tag, message);
    }

    public static void d(String tag, String message) {
        mainLog(1, tag, message);
    }

    public static void e(String tag, String message) {
        mainLog(2, tag, message);
    }

    public static void w(String tag, String message) {
        mainLog(3, tag, message);
    }
}
