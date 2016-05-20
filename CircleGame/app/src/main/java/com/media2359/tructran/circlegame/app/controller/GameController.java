package com.media2359.tructran.circlegame.app.controller;

import com.media2359.tructran.circlegame.app.helper.ConstantHelper;
import com.media2359.tructran.circlegame.app.helper.LogUtils;

/**
 * Created by TrucTran on 5/19/16.
 */
public class GameController {

    private int mScore;
    private int mLevel;
    private int mSuccessCount;
    private float mCurrentSpeed;

    private static GameController mInstance;
    private OnScreenTouchListener mListener;

    private GameController() {
        resetGameInfo();
    }

    public static GameController getInstance() {
        if (mInstance == null) {
            mInstance = new GameController();
        }

        return mInstance;
    }

    public void resetGameInfo() {
        mScore = 0;
        mCurrentSpeed = ConstantHelper.SPEED_START;
        mSuccessCount = 0;
        mLevel = 0;
    }

    public void onScreenTouch(float angleRunView, float targetZoneStartAngle, float targetZoneSweepAngle) {
        if (mListener == null) {
            return;
        }
        boolean isInTargetZone =isInTargetZone(angleRunView, targetZoneStartAngle, targetZoneSweepAngle);
        if (isInTargetZone) {
            mListener.onTouchSuccess();
        } else {
            mListener.onTouchFailed(angleRunView, targetZoneStartAngle, targetZoneSweepAngle);
        }
    }

    public void setOnScreenTouchListener(OnScreenTouchListener listener) {
        this.mListener = listener;
    }

    public boolean isInTargetZone(float angleRunView, float targetZoneStartAngle, float targetZoneSweepAngle) {
        float targetZoneEndAngle = targetZoneStartAngle + targetZoneSweepAngle;

        LogUtils.i("Game", "GameController isInTargetZone <start: " + targetZoneStartAngle + "> <pos: " + angleRunView + "> <end: " + (targetZoneStartAngle + targetZoneSweepAngle) + ">");

        if (angleRunView >= targetZoneStartAngle && angleRunView <= targetZoneEndAngle) {
            return true;
        }

        float sameAngle = angleRunView + 360f;
        return (sameAngle >= targetZoneStartAngle && sameAngle <= targetZoneEndAngle);
    }

    public interface OnScreenTouchListener {
        public void onTouchSuccess();
        public void onTouchFailed(float angleRunView, float targetZoneStartAngle, float targetZoneSweepAngle);
    }

    public void increaseSpeed() {
        mCurrentSpeed += ConstantHelper.SPEED_OFFSET;
    }

    public float getSpeed() {
        return mCurrentSpeed;
    }

}
