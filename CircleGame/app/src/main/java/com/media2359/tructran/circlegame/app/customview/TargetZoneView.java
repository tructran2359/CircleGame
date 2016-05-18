package com.media2359.tructran.circlegame.app.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.media2359.tructran.circlegame.app.helper.LogUtils;
import com.media2359.tructran.circlegame.app.helper.PrefHelper;

/**
 * Created by TrucTran on 5/15/16.
 */
public class TargetZoneView extends View {

    private float mStartAngle;
    private float mSweepAngle;
    private RectF mRectF;
    private int mTargetZoneWidth;


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
            int width = getWidth();
            int height = getHeight();
            int offset = mTargetZoneWidth/2;
            mRectF = new RectF(offset, offset, width - offset, height - offset);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mRectF == null) {
            return;
        }

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(mTargetZoneWidth);

        canvas.drawArc(mRectF, mStartAngle, mSweepAngle, false, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    private void init() {
        mTargetZoneWidth = PrefHelper.getScreenWidth() / 8;
        LogUtils.i("targetzone", "Width: " + mTargetZoneWidth);
    }

    public void setArcInfo(float startAngle, float sweepAngle) {
        this.mStartAngle = startAngle;
        this.mSweepAngle = sweepAngle;
        invalidate();
    }
}
