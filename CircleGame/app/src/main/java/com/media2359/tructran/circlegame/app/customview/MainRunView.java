package com.media2359.tructran.circlegame.app.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.media2359.tructran.circlegame.app.R;
import com.media2359.tructran.circlegame.app.helper.UiUtils;
import com.media2359.tructran.circlegame.app.model.ModelPoint;

/**
 * Created by TrucTran on 5/18/16.
 */
public class MainRunView extends View {

    //x = R cos(alpha)
    //y = R sin(alpha)

    private float mAngle;
    private int mRadiusCircleView;
    private int mRadiusWholeView;
    private int mRadiusCenterRunView;

    private RectF mRectF;
    private Paint mPaintRunCircle;
    private Paint mPaintBorderCircle;
    private Paint mPaintCenter;

    // Constructor =====================================

    public MainRunView(Context context) {
        super(context);
        init();
    }

    public MainRunView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MainRunView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
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

            mRectF = new RectF(1, 1, width-1, width-1);

            mRadiusWholeView = width / 2;
            mRadiusCenterRunView = mRadiusWholeView - mRadiusCircleView;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mRadiusWholeView == 0) {
            return;
        }

        ModelPoint point = UiUtils.calculateCenterPoint(mRadiusCenterRunView, mAngle, mRadiusWholeView);
        canvas.drawCircle(point.getX(), point.getY(), mRadiusCircleView, mPaintRunCircle);
        canvas.drawCircle(point.getX(), point.getY(), mRadiusCircleView / 10, mPaintCenter);
    }


    // Additional methods ==============================

    private void init() {
        mAngle = 0;
        mRadiusCircleView = TargetZoneView.getTargetZoneWidth() / 2;

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
    }

    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float angle) {
        mAngle = angle;
        invalidate();
    }
}
