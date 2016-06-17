package com.media2359.tructran.circlegame.app.controller;

import android.os.CountDownTimer;

import com.media2359.tructran.circlegame.app.helper.LogUtils;
import com.media2359.tructran.circlegame.app.helper.Utils;

import java.util.Random;

/**
 * Created by TrucTran on 5/19/16.
 */
public class GameController {

    public static final float SPEED_OFFSET = 30f;
    public static final float SPEED_START = 70f;
    public static final int SCORE_EACH_LEVEL = 10;
    public static final float DEFAULT_RUN_ANGLE = 0f;
    public static final float DEFAULT_TARGET_ZONE_START_ANGLE = 150f;
    public static final float DEFAULT_TARGET_ZONE_SWEEP_ANGLE = 60f;
    public static final long COUNT_DOWN_DURATION_IN_MILLIS = 30 * 1000;
    public static final int NUMBER_OF_FRAME_PER_SECOND = 60;
    public static final long COUNT_DOWN_INTERVAL_IN_MILLIS = 1000 / NUMBER_OF_FRAME_PER_SECOND;
    public static final float TARGET_ZONE_MIN_SWEEP_ANGLE_IN_DEGREE = 60f;
    public static final float TARGET_ZONE_MAX_SWEEP_ANGLE_IN_DEGREE = 270f;


    private int mScore;
    private int mLevel;
    private int mSuccessCount;
    private float mCurrentSpeed;
    private float mRunAngle;
    private float mTargetZoneStartAngle;
    private float mTargetZoneSweepAngle;
    private boolean mIsGameRunning;
    private CountDownTimer mCountDownTimer;
    private Random mRandom;

    private static GameController mInstance;
    private ViewListener mListener;

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
        mCurrentSpeed = SPEED_START;
        mSuccessCount = 0;
        mLevel = 1;
        mRunAngle = DEFAULT_RUN_ANGLE;
        mTargetZoneStartAngle = DEFAULT_TARGET_ZONE_START_ANGLE;
        mTargetZoneSweepAngle = DEFAULT_TARGET_ZONE_SWEEP_ANGLE;
        mIsGameRunning = false;
        initCountDownTimer();
    }

    private void initCountDownTimer() {
        mCountDownTimer = new CountDownTimer(COUNT_DOWN_DURATION_IN_MILLIS, COUNT_DOWN_INTERVAL_IN_MILLIS) {
            @Override
            public void onTick(long l) {
                if (mListener != null) {
                    mRunAngle = Utils.getRealDegree(mRunAngle + mCurrentSpeed / NUMBER_OF_FRAME_PER_SECOND);
                    mListener.onGameUpdate(mRunAngle);
                }

            }

            @Override
            public void onFinish() {
                mCountDownTimer.start();
            }
        };
        mRandom = new Random();
    }

    public void setOnScreenTouchListener(ViewListener listener) {
        this.mListener = listener;
    }

    // handle user event =========================================================

    public void onScreenTouch() {
        if (mListener == null) {
            return;
        }

        if (!mIsGameRunning) {
            resetGameInfo();
            startGame();
            return;
        }

        boolean isInTargetZone =isInTargetZone(mRunAngle, mTargetZoneStartAngle, mTargetZoneSweepAngle);
        if (isInTargetZone) {
            handleTouchSuccess();
        } else {
            handleTouchFailed(mRunAngle, mTargetZoneStartAngle, mTargetZoneSweepAngle);
        }
    }

    public boolean isInTargetZone(float angleRunView, float targetZoneStartAngle, float targetZoneSweepAngle) {
        float targetZoneEndAngle = targetZoneStartAngle + targetZoneSweepAngle;

//        LogUtils.i("Game", "GameController isInTargetZone <start: " + targetZoneStartAngle + "> <pos: " + angleRunView + "> <end: " + (targetZoneStartAngle + targetZoneSweepAngle) + ">");

        if (angleRunView >= targetZoneStartAngle && angleRunView <= targetZoneEndAngle) {
            return true;
        }

        float sameAngle = angleRunView + 360f;
        return (sameAngle >= targetZoneStartAngle && sameAngle <= targetZoneEndAngle);
    }

    private void handleTouchSuccess() {
        increaseSuccessCount();
        if (mSuccessCount == 0) {
            mLevel++;
            increaseSpeed();
        }
        mScore += mLevel * SCORE_EACH_LEVEL;
        mListener.onTouchSuccess(mScore, mLevel, mSuccessCount);
        generateNewTargetZone();
    }

    private void handleTouchFailed(float angleRunView, float targetZoneStartAngle, float targetZoneSweepAngle) {
        stopGame();
        mListener.onTouchFailed(angleRunView, targetZoneStartAngle, targetZoneSweepAngle);
    }

    // handle game data ==========================================================

    public void increaseSpeed() {
        mCurrentSpeed += SPEED_OFFSET;
    }

    private void increaseSuccessCount() {
        mSuccessCount++;
        if (mSuccessCount == 5) {
            mSuccessCount = 0;
        }
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }

    public int getLevel() {
        return mLevel;
    }

    public void setLevel(int level) {
        mLevel = level;
    }

    public int getSuccessCount() {
        return mSuccessCount;
    }

    public void setSuccessCount(int successCount) {
        mSuccessCount = successCount;
    }

    public float getCurrentSpeed() {
        return mCurrentSpeed;
    }

    public void setCurrentSpeed(float currentSpeed) {
        mCurrentSpeed = currentSpeed;
    }

    public float getRunAngle() {
        return mRunAngle;
    }

    public void setRunAngle(float runAngle) {
        mRunAngle = runAngle;
    }

    public float getTargetZoneStartAngle() {
        return mTargetZoneStartAngle;
    }

    public void setTargetZoneStartAngle(float targetZoneStartAngle) {
        mTargetZoneStartAngle = targetZoneStartAngle;
    }

    public float getTargetZoneSweepAngle() {
        return mTargetZoneSweepAngle;
    }

    public void setTargetZoneSweepAngle(float targetZoneSweepAngle) {
        mTargetZoneSweepAngle = targetZoneSweepAngle;
    }

    public boolean isGameRunning() {
        return mIsGameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        mIsGameRunning = gameRunning;
    }

    // game control ==============================================================

    private void startGame() {
        if (mListener != null) {
            mIsGameRunning = true;
            mListener.onGameUpdate(mRunAngle);
            mListener.onTargetZoneUpdate(mTargetZoneStartAngle, mTargetZoneSweepAngle);
            mCountDownTimer.start();
        }
    }

    private void stopGame() {
        if (mListener != null) {
            mIsGameRunning = false;
            mCountDownTimer.cancel();
        }
    }

    private void generateNewTargetZone() {
        if (mListener != null) {
            mTargetZoneStartAngle = Math.abs(mRandom.nextInt(360));
            mTargetZoneSweepAngle = Math.abs(mRandom.nextInt((int)TARGET_ZONE_MAX_SWEEP_ANGLE_IN_DEGREE - (int)TARGET_ZONE_MIN_SWEEP_ANGLE_IN_DEGREE) + TARGET_ZONE_MIN_SWEEP_ANGLE_IN_DEGREE);
            LogUtils.i("Game", "GameController generateNewTargetZone start: " + mTargetZoneStartAngle + " sweep: " + mTargetZoneSweepAngle);
            mListener.onTargetZoneUpdate(mTargetZoneStartAngle, mTargetZoneSweepAngle);
        }
    }

    // interface =================================================================

    public interface ViewListener {
        public void onTouchSuccess(int score, int level, int successCount);
        public void onTouchFailed(float angleRunView, float targetZoneStartAngle, float targetZoneSweepAngle);
        public void onGameUpdate(float angleRunView);
        public void onTargetZoneUpdate(float startAngle, float sweepAngle);
    }

}
