package com.media2359.tructran.circlegame.app.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.media2359.tructran.circlegame.app.R;
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
            mRadiusCenterRunView = mRadiusWholeView - mRadiusCircleView - mPaddingToTargetZone;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mRadiusWholeView == 0) {
            return;
        }

        ModelPoint point = UiUtils.calculateCenterPoint(mRadiusCenterRunView, mAngleInDegree, mRadiusWholeView);
        canvas.drawCircle(point.getX(), point.getY(), mRadiusCircleView, mPaintRunCircle);
        canvas.drawCircle(point.getX(), point.getY(), mRadiusCircleView / 20, mPaintCenter);
    }


    // Additional methods ==============================

    private void init() {
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
    }

    public float getAngleInDegree() {
        return Utils.getRealDegree(mAngleInDegree);
    }

    public void setAngleInDegree(float angleInDegree) {
        mAngleInDegree = Utils.getRealDegree(angleInDegree);
        invalidate();
    }
}
