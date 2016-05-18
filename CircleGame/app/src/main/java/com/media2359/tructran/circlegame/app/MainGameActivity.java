package com.media2359.tructran.circlegame.app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.media2359.tructran.circlegame.app.customview.TargetZoneView;
import com.media2359.tructran.circlegame.app.helper.LogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainGameActivity extends AppCompatActivity {

    public static final int WHAT_SIMULATE = 1;
    public static final long MAX_SIMULATE_DURATION_IN_MILLIS = 30 * 1000;
    public static final long INTERVAL_SIMULATE_IN_MILLIS = 1000;

    @Bind(R.id.main_game_act_target_zone_view)
    TargetZoneView mTargetZoneView;

    private Handler mHandler;
    private long mStartTimestamp;
    private float mStartAngle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        ButterKnife.bind(this);

//        mapViews();
        init();

        simulateRandomTarget();
    }

    private void mapViews() {
        mTargetZoneView = (TargetZoneView) findViewById(R.id.main_game_act_target_zone_view);
    }

    private void init() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == WHAT_SIMULATE) {
                    if (System.currentTimeMillis() - mStartTimestamp < MAX_SIMULATE_DURATION_IN_MILLIS) {
                        mStartAngle = (float) (((int)mStartAngle + 30) % 360);
                        mTargetZoneView.setArcInfo(mStartAngle, 60);
                        mHandler.sendEmptyMessageDelayed(WHAT_SIMULATE, INTERVAL_SIMULATE_IN_MILLIS);
                    } else {
                        LogUtils.i("Simulate", "Over duration. Stop");
                    }
                }
            }
        };
    }

    private void simulateRandomTarget() {
        mStartTimestamp = System.currentTimeMillis();
        mHandler.sendEmptyMessageDelayed(WHAT_SIMULATE, INTERVAL_SIMULATE_IN_MILLIS);
    }
}
