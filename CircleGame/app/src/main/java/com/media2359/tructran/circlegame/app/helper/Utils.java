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

    /**
     * Convert from degree to radians
     * 180 (degree) = PI (radians)
     * <=> 1 (degree) = PI / 180 (radians)
     * <=> X (degree) = X * PI / 180
     * @param degree
     * @return
     */
    public static float convertFromDegreeToRadian(float degree) {
        return (float) (degree * Math.PI / 180);
    }

    public static float convertFromRadianToDegree(float radian) {
        return (float) (radian * 180 / Math.PI);
    }

    public static float standardizeAngle(float degree) {
        if (degree <= 360 && degree >= 0) {
            return degree;
        }

        while (degree > 360) {
            degree -= 360;
        }

        while (degree < 0) {
            degree += 360;
        }
        return degree;
    }

}
