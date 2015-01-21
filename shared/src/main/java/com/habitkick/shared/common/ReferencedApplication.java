package com.habitkick.shared.common;

import android.app.Activity;
import android.app.Application;

import com.orm.SugarApp;

public class ReferencedApplication extends SugarApp {
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