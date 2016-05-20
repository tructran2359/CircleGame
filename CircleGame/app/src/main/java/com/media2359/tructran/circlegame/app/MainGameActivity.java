package com.media2359.tructran.circlegame.app;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.media2359.tructran.circlegame.app.controller.GameController;
import com.media2359.tructran.circlegame.app.customview.MainRunView;
import com.media2359.tructran.circlegame.app.customview.TargetZoneView;
import com.media2359.tructran.circlegame.app.helper.ConstantHelper;
import com.media2359.tructran.circlegame.app.helper.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainGameActivity extends AppCompatActivity implements GameController.OnScreenTouchListener {

    @Bind(R.id.act_main_game_target_zone_view)
    TargetZoneView mTargetZoneView;

    @Bind(R.id.act_main_game_run_view)
    MainRunView mMainRunView;

    @Bind(R.id.act_main_game_rl_root)
    RelativeLayout mRlRoot;

    private CountDownTimer mCountDownTimer;
    private GameController mGameController;

    private boolean mIsTouchedDown;
    private boolean mIsGameRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        ButterKnife.bind(this);

//        mapViews();
        init();
    }

    // Additonal methods ===================================

    private void init() {
        mTargetZoneView.setArcInfo(0, 120);
        mMainRunView.setAngleInDegree(270f);
        mGameController = GameController.getInstance();
        mGameController.setOnScreenTouchListener(this);
        mIsTouchedDown = false;
        mRlRoot.setOnTouchListener(mOntouchListener);

        mCountDownTimer = new CountDownTimer(ConstantHelper.MAX_SIMULATE_DURATION_IN_MILLIS, ConstantHelper.INTERVAL_SIMULATE_IN_MILLIS) {
            @Override
            public void onTick(long l) {
                mMainRunView.setAngleInDegree(mMainRunView.getAngleInDegree() + (mGameController.getSpeed() / ConstantHelper.NUMBER_OF_FRAME_PER_SECOND));
            }

            @Override
            public void onFinish() {
                mCountDownTimer.start();
            }
        };
    }

    // Listeners ===========================================
    View.OnTouchListener mOntouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                if (mIsTouchedDown) {
                    return false;
                } else {
                    mIsTouchedDown = true;
                    if (!mIsGameRunning) {
                        //start game if game is not running
                        mCountDownTimer.start();
                        mIsGameRunning = true;
                        return true;
                    }

                    float angleRunView = mMainRunView.getAngleInDegree();
                    float targetZoneStartAngle = mTargetZoneView.getStartAngle();
                    float targetZoneSweepAngle = mTargetZoneView.getSweepAngle();

                    mGameController.onScreenTouch(angleRunView, targetZoneStartAngle, targetZoneSweepAngle);
                    return true;
                }
            } else {
                mIsTouchedDown = false;
                return true;
            }
        }
    };


    @Override
    public void onTouchSuccess() {
        Utils.showToast(this, "Nice!");
        mGameController.increaseSpeed();
    }

    @Override
    public void onTouchFailed(float angleRunView, float targetZoneStartAngle, float targetZoneSweepAngle) {
        mTargetZoneView.setArcInfo(targetZoneStartAngle, targetZoneSweepAngle);
        mMainRunView.setAngleInDegree(angleRunView);
        mCountDownTimer.cancel();
        mIsGameRunning = false;

        Utils.showToast(this, "Failed!");
    }
}
