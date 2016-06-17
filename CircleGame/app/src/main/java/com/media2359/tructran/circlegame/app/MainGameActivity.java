package com.media2359.tructran.circlegame.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.media2359.tructran.circlegame.app.controller.GameController;
import com.media2359.tructran.circlegame.app.customview.MainRunView;
import com.media2359.tructran.circlegame.app.customview.TargetZoneView;
import com.media2359.tructran.circlegame.app.helper.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainGameActivity extends AppCompatActivity implements GameController.ViewListener {

    @Bind(R.id.act_main_game_target_zone_view)
    TargetZoneView mTargetZoneView;

    @Bind(R.id.act_main_game_run_view)
    MainRunView mMainRunView;

    @Bind(R.id.act_main_game_rl_root)
    RelativeLayout mRlRoot;

    @Bind(R.id.act_main_game_v_level_1)
    View mVLevel1;

    @Bind(R.id.act_main_game_v_level_2)
    View mVLevel2;

    @Bind(R.id.act_main_game_v_level_3)
    View mVLevel3;

    @Bind(R.id.act_main_game_v_level_4)
    View mVLevel4;

    @Bind(R.id.act_main_game_tv_score)
    TextView mTvScore;

    @Bind(R.id.act_main_game_tv_level)
    TextView mTvLevel;

    private GameController mGameController;

    private boolean mIsTouchedDown;

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

        mGameController = GameController.getInstance();
        mGameController.setOnScreenTouchListener(this);

        mTargetZoneView.setArcInfo(mGameController.getTargetZoneStartAngle(), mGameController.getTargetZoneSweepAngle());
        mMainRunView.setAngleInDegree(mGameController.getRunAngle());

        mTvLevel.setText(mGameController.getLevel() + "");
        mTvScore.setText(mGameController.getScore() + "");

        mIsTouchedDown = false;
        mRlRoot.setOnTouchListener(mOntouchListener);

    }

    private void setSelection(boolean v1, boolean v2, boolean v3, boolean v4) {
        mVLevel1.setSelected(v1);
        mVLevel2.setSelected(v2);
        mVLevel3.setSelected(v3);
        mVLevel4.setSelected(v4);
    }

    private void showCurrentLevel(int level) {
        switch (level) {
            case 0:
                setSelection(false, false, false, false);
                break;

            case 1:
                setSelection(true, false, false, false);
                break;

            case 2:
                setSelection(true, true, false, false);
                break;

            case 3:
                setSelection(true, true, true, false);
                break;

            case 4:
                setSelection(true, true, true, true);
                break;
        }
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

                    mGameController.onScreenTouch();
                    return true;
                }
            } else {
                mIsTouchedDown = false;
                return true;
            }
        }
    };

    // GameController callback ========================================

    @Override
    public void onTouchSuccess(int score, int level, int successCount) {
        showCurrentLevel(successCount);
        mTvScore.setText(score + "");
        mTvLevel.setText(level + "");
    }

    @Override
    public void onTouchFailed(float angleRunView, float targetZoneStartAngle, float targetZoneSweepAngle) {
        mTargetZoneView.setArcInfo(targetZoneStartAngle, targetZoneSweepAngle);
        mMainRunView.setAngleInDegree(angleRunView);

        Utils.showToast(this, "Failed!");
    }

    @Override
    public void onGameUpdate(float angleRunView) {
        mMainRunView.setAngleInDegree(angleRunView);
    }

    @Override
    public void onTargetZoneUpdate(float startAngle, float sweepAngle) {
        mTargetZoneView.setArcInfo(startAngle, sweepAngle);
    }
}
