package com.media2359.tructran.circlegame.app.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.media2359.tructran.circlegame.app.R;
import com.media2359.tructran.circlegame.app.helper.LogUtils;
import com.media2359.tructran.circlegame.app.helper.UiUtils;
import com.media2359.tructran.circlegame.app.helper.Utils;
import com.media2359.tructran.circlegame.app.model.ModelPoint;

/**
 * Created by TrucTran on 5/18/16.
 */
public class MainRunView extends View {

    //x = R cos(alpha)
    //y = R sin(alpha)

    private float mAngleInDegree;
    private int mRadiusCircleView;
    private int mRadiusWholeView;
    private int mRadiusCenterRunView;
    private int mPaddingToTargetZone;

    private Paint mPaintRunCircle;
    private Paint mPaintBorderCircle;
    private Paint mPaintCenter;
    private Paint mPaintBitmap;

    private Bitmap mBitmap;

    // Constructor =====================================

    public MainRunView(Context context) {
        super(context);
        init(context);
    }

    public MainRunView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MainRunView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // Override methods ===============================


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            int width = getWidth();

            mRadiusWholeView = width / 2;
            mRadiusCenterRunView = mRadiusWholeView - mRadiusCircleView - mPaddingToTargetZone;

            Bitmap bmp = UiUtils.getSmallestScaledBitmapThatStillLargerThanRequirementFromResource(getContext().getResources(), R.drawable.ic_test, mRadiusCircleView, mRadiusCircleView);
            mBitmap = Bitmap.createScaledBitmap(bmp, mRadiusCircleView * 2, mRadiusCircleView * 2, true);
            LogUtils.i("Bug", "mRadiusCenterRunView: " + mRadiusCircleView);
            LogUtils.i("Bug", "Bitmap loaded: " + bmp.getWidth());
            LogUtils.i("Bug", "Scaled: " + mBitmap.getWidth());
            bmp.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mRadiusWholeView == 0 || getContext() == null) {
            return;
        }

        ModelPoint point = UiUtils.calculateCenterPoint(
                mRadiusCenterRunView,
                mAngleInDegree,
                mRadiusWholeView);

//        canvas.drawCircle(point.getX(), point.getY(), mRadiusCircleView, mPaintRunCircle);
//        canvas.drawCircle(point.getX(), point.getY(), mRadiusCircleView / 20, mPaintCenter);


        float left = point.getX() - mRadiusCircleView;
        float top = point.getY() - mRadiusCircleView;
        canvas.drawBitmap(mBitmap, left, top, mPaintBitmap);
    }


    // Additional methods ==============================

    private void init(Context context) {
        mAngleInDegree = 0;
        int targetZoneWidth = TargetZoneView.getTargetZoneWidth();

        mRadiusCircleView = (int) (( targetZoneWidth * 0.8) / 2) ;
        mPaddingToTargetZone = targetZoneWidth / 2 - mRadiusCircleView;

        mPaintRunCircle = new Paint();
        mPaintRunCircle.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintRunCircle.setAntiAlias(true);
        mPaintRunCircle.setColor(getContext().getResources().getColor(R.color.bg_run_circle));

        mPaintBorderCircle = new Paint();
        mPaintBorderCircle.setStyle(Paint.Style.STROKE);
        mPaintBorderCircle.setAntiAlias(true);
        mPaintBorderCircle.setColor(getContext().getResources().getColor(R.color.green));

        mPaintCenter = new Paint();
        mPaintCenter.setStyle(Paint.Style.FILL);
        mPaintCenter.setAntiAlias(true);
        mPaintCenter.setColor(getContext().getResources().getColor(R.color.bg_run_circle_center));

        mPaintBitmap = new Paint();
        mPaintBitmap.setAntiAlias(true);
    }

    public float getAngleInDegree() {
        return Utils.standardizeAngle(mAngleInDegree);
    }

    public void setAngleInDegree(float angleInDegree) {
        mAngleInDegree = Utils.standardizeAngle(angleInDegree);
        invalidate();
    }
}
