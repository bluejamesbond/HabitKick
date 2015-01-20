package com.habitkick.shared.common;

import android.app.Activity;
import android.app.Application;

public class ReferencedApplication extends Application {
    private Activity mCurrentActivity = null;

    public void onCreate() {
        super.onCreate();
    }

    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public void setCurrentActivity(Activity mCurrentActivity) {
        this.mCurrentActivity = mCurrentActivity;
    }
}