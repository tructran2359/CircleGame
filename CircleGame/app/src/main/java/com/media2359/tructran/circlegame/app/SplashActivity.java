package com.media2359.tructran.circlegame.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;

import com.media2359.tructran.circlegame.app.helper.PrefHelper;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        saveScreenSize();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainGameActivity.class));
                finish();
            }
        }, 2000);

    }

    private void saveScreenSize() {
        int width = PrefHelper.getScreenWidth();
        int height = PrefHelper.getScreenHeight();
        if (width == 0 || height == 0) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            width = displayMetrics.widthPixels;
            height = displayMetrics.heightPixels;
            PrefHelper.setScreenSize(width, height);
        }
    }
}
