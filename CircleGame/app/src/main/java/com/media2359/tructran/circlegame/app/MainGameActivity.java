package com.media2359.tructran.circlegame.app;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.media2359.tructran.circlegame.app.customview.MainRunView;
import com.media2359.tructran.circlegame.app.customview.TargetZoneView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainGameActivity extends AppCompatActivity {

    public static final int WHAT_SIMULATE = 1;
    public static final long MAX_SIMULATE_DURATION_IN_MILLIS = 10 * 1000;
    public static final int NUMBER_OF_FRAME_PER_SECOND = 60;
    public static final long INTERVAL_SIMULATE_IN_MILLIS = 1000 / NUMBER_OF_FRAME_PER_SECOND;
    public static final float SPEED_DEGREE_PER_SECOND = 100f;
    public static final float SPEED_PER_FRAME = SPEED_DEGREE_PER_SECOND / NUMBER_OF_FRAME_PER_SECOND;

    private static final float SPEED_MAX = 500f;
    private static final float SPEED_MIN = 10f;
    private static final float SPEED_OFFSET = 30f;

    @Bind(R.id.act_main_game_target_zone_view)
    TargetZoneView mTargetZoneView;

    @Bind(R.id.act_main_game_run_view)
    MainRunView mMainRunView;

    @Bind(R.id.act_main_game_tv_countdown)
    TextView mTvCountDown;

    @Bind(R.id.act_main_game_btn_start)
    Button mBtnStart;

    private CountDownTimer mCountDownTimer;
    private long mTimeDebug = 0;

    private boolean mIsTimerRunning;

    private float mCurrentSpeed;

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
        mMainRunView.setAngle(270f);
        mIsTimerRunning = false;
        mCurrentSpeed = SPEED_DEGREE_PER_SECOND;
        mTvCountDown.setText("Speed: " + mCurrentSpeed);
        mCountDownTimer = new CountDownTimer(MAX_SIMULATE_DURATION_IN_MILLIS, INTERVAL_SIMULATE_IN_MILLIS) {
            @Override
            public void onTick(long l) {
                mMainRunView.setAngle(mMainRunView.getAngle() + (mCurrentSpeed / NUMBER_OF_FRAME_PER_SECOND));
            }

            @Override
            public void onFinish() {
                mCountDownTimer.start();
            }
        };
    }

    // Listeners ===========================================

    @OnClick(R.id.act_main_game_btn_start)
    public void onStartClicked() {
        if (mIsTimerRunning) {
            mCountDownTimer.cancel();
            mIsTimerRunning = false;
            mBtnStart.setText("START");
        } else {
            mCountDownTimer.start();
            mIsTimerRunning = true;
            mBtnStart.setText("STOP");
        }
    }

    @OnClick(R.id.act_main_game_btn_increase)
    public void onIncreseClicked() {
        mCurrentSpeed += SPEED_OFFSET;
        if (mCurrentSpeed > SPEED_MAX) {
            mCurrentSpeed = SPEED_MAX;
        }
        mTvCountDown.setText("Speed: " + mCurrentSpeed);
    }

    @OnClick(R.id.act_main_game_btn_decrease)
    public void onDecreaseClicked() {
        mCurrentSpeed -= SPEED_OFFSET;
        if (mCurrentSpeed < SPEED_MIN) {
            mCurrentSpeed = SPEED_MIN;
        }
        mTvCountDown.setText("Speed: " + mCurrentSpeed);
    }
}
