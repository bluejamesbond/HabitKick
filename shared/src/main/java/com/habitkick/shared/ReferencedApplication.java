package com.habitkick.shared;

import android.app.Activity;
import android.app.Application;

/**
 * Created by Mathew on 12/30/2014.
 */
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