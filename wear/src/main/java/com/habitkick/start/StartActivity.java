package com.habitkick.start;

import android.support.wearable.view.WatchViewStub;

import com.habitkick.R;
import com.habitkick.WatchActivity;

public class StartActivity extends WatchActivity<StartUI> {

    @Override
    protected StartUI getUIInstance(WatchViewStub stub) {
        return new StartUI(stub);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.start_activity;
    }
}
