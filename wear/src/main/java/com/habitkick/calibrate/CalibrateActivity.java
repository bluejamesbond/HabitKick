package com.habitkick.calibrate;

import android.support.wearable.view.WatchViewStub;

import com.habitkick.R;
import com.habitkick.WatchActivity;

public class CalibrateActivity extends WatchActivity<CalibrateUI> {

    @Override
    protected CalibrateUI getUIInstance(WatchViewStub stub) {
        return new CalibrateUI(stub);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.calibrate_activity;
    }
}
