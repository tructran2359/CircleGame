package com.media2359.tructran.circlegame.app.helper;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.media2359.tructran.circlegame.app.model.ModelPoint;

/**
 * Created by TrucTran on 5/19/16.
 */
public class UiUtils {

    public static ModelPoint calculateCenterPoint(
            int radiusCenterOfArc,
            float angle,
            int radiusOfWholeView) {

        float x = (float)(radiusCenterOfArc * Math.cos((double) Utils.convertFromDegreeToRadian(angle))) + radiusOfWholeView;
        float y = (float)(radiusCenterOfArc * Math.sin((double) Utils.convertFromDegreeToRadian(angle))) + radiusOfWholeView;
        return new ModelPoint(x, y);
    }

    /**
     * Get the largest sample size that can help scale the image to fit the required width and height
     * @param options {@link android.graphics.BitmapFactory.Options} should have {@link android.graphics.BitmapFactory.Options#inJustDecodeBounds} is set to true
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        LogUtils.i("Bug", "Height: " + height);

        // Calculate the largest inSampleSize value that  keeps both
        // height and width larger than the requested height and width.
        while ((height / inSampleSize) >= reqHeight
                && (width / inSampleSize) >= reqWidth) {
            inSampleSize++;
        }
        LogUtils.i("Bug", "Calculated: " + inSampleSize);

        return inSampleSize == 1 ? 1 : inSampleSize - 1;
    }

    /**
     * Get Smallest scaled bitmap that still larger than the requirement to optimise the memory usage
     * @param res
     * @param resId Drawable resource id
     * @param reqWidth required width
     * @param reqHeight required height
     * @return
     */
    public static Bitmap getSmallestScaledBitmapThatStillLargerThanRequirementFromResource(Resources res, int resId,
                                                                                           int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(res, resId, options);
    }

}
