package com.media2359.tructran.circlegame.app.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.media2359.tructran.circlegame.app.R;
import com.media2359.tructran.circlegame.app.helper.LogUtils;
import com.media2359.tructran.circlegame.app.helper.PrefHelper;
import com.media2359.tructran.circlegame.app.helper.UiUtils;
import com.media2359.tructran.circlegame.app.helper.Utils;
import com.media2359.tructran.circlegame.app.model.ModelPoint;

/**
 * Created by TrucTran on 5/15/16.
 */
public class TargetZoneView extends View {

    private float mStartAngle;
    private float mSweepAngle;
    private RectF mRectF;
    private int mWidth;
    private int mTargetZoneWidth;
    private int mBigRadius;
    private int mRadiusCenterOfArc;
    private int mRadiusOfCircleAtStartAndStop;
    private float mDecreaseAngle;

    private Paint mPaintArc;
    private Paint mPaintCircle;
    private Paint mPaintFailedZone;


    public TargetZoneView(Context context) {
        super(context);
        init();
    }

    public TargetZoneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TargetZoneView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mWidth = getWidth();
            mBigRadius = mWidth / 2;

            // need to + 1 because if not, UI will has a bug which has a extra line at point arc touch the bound
            mRadiusOfCircleAtStartAndStop = mTargetZoneWidth / 2 + 2;

            mRadiusCenterOfArc = mBigRadius - mRadiusOfCircleAtStartAndStop;

            double sinBeta = (double)mRadiusOfCircleAtStartAndStop / (double) mRadiusCenterOfArc;
            double betaInRad = Math.asin(sinBeta);
            mDecreaseAngle = Utils.convertFromRadianToDegree((float) betaInRad);
            LogUtils.i("TargetZone", "Beta: " + mDecreaseAngle);
            LogUtils.i("TargetZone", "SinB: " + sinBeta);


            mRectF = new RectF(
                    mRadiusOfCircleAtStartAndStop,
                    mRadiusOfCircleAtStartAndStop,
                    mWidth - mRadiusOfCircleAtStartAndStop,
                    mWidth - mRadiusOfCircleAtStartAndStop);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mRectF == null) {
            return;
        }

        float realStartAngle = mStartAngle + mDecreaseAngle;
        float realSweepAngle = mSweepAngle - (2 * mDecreaseAngle);

        ModelPoint pointAtStart = UiUtils.calculateCenterPoint(
                mRadiusCenterOfArc,
                realStartAngle,
                mBigRadius
        );
        ModelPoint pointAtStop = UiUtils.calculateCenterPoint(
                mRadiusCenterOfArc,
                realStartAngle + realSweepAngle,
                mBigRadius
        );


//        canvas.drawArc(mRectF, 0, 360, false, mPaintFailedZone);

        canvas.drawCircle(pointAtStart.getX(), pointAtStart.getY(), mTargetZoneWidth / 2, mPaintCircle);
        canvas.drawCircle(pointAtStop.getX(), pointAtStop.getY(), mTargetZoneWidth / 2, mPaintCircle);

        canvas.drawArc(mRectF, realStartAngle, realSweepAngle, false, mPaintArc);

        //for testing the range of target zone
//        Paint test = new Paint();
//        test.setStyle(Paint.Style.STROKE);
//        test.setAntiAlias(true);
//        test.setColor(getContext().getResources().getColor(R.color.yellow));
//        test.setStrokeWidth(10);
//        canvas.drawArc(mRectF, mStartAngle, mSweepAngle, true, test);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    private void init() {
        mTargetZoneWidth = getTargetZoneWidth();

        mPaintArc = new Paint();
        mPaintArc.setStyle(Paint.Style.STROKE);
        mPaintArc.setAntiAlias(true);
        mPaintArc.setColor(getContext().getResources().getColor(R.color.blue));
        mPaintArc.setStrokeWidth(mTargetZoneWidth);

        mPaintCircle = new Paint();
        mPaintCircle.setStyle(Paint.Style.FILL);
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setColor(getContext().getResources().getColor(R.color.blue));

        mPaintFailedZone = new Paint();
        mPaintFailedZone.setStyle(Paint.Style.STROKE);
        mPaintFailedZone.setAntiAlias(true);
        mPaintFailedZone.setColor(getContext().getResources().getColor(R.color.red));
        mPaintFailedZone.setStrokeWidth(mTargetZoneWidth);

        LogUtils.i("targetzone", "Width: " + mTargetZoneWidth);

    }

    public void setArcInfo(float startAngle, float sweepAngle) {
        this.mStartAngle = startAngle;
        this.mSweepAngle = sweepAngle;
        invalidate();
    }

    public float getStartAngle() {
        return mStartAngle;
    }

    public float getSweepAngle() {
        return mSweepAngle;
    }

    public static int getTargetZoneWidth() {
        return PrefHelper.getScreenWidth() / 8;
    }

}
