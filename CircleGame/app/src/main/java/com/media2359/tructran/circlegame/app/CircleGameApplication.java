package com.media2359.tructran.circlegame.app;

import android.app.Application;

/**
 * Created by TrucTran on 5/15/16.
 */
public class CircleGameApplication extends Application {

    private static CircleGameApplication mInstance;

    public static CircleGameApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
