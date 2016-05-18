package com.media2359.tructran.circlegame.app.helper;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by TrucTran on 5/15/16.
 */
public class Utils {

    public static boolean isTextNullOrEmpty(String text) {
        return text == null || text.isEmpty();
    }

    public static void showToast(Context ctx, String msg) {
        if (ctx == null) {
            LogUtils.e("Error", "showToast ctx null");
            return;
        }

        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }

    public static String getFullTextContent(String text) {
        return text == null ? "null" : text.isEmpty() ? "empty" : text;
    }

}
