package com.fiftyeightmorris.habitkick.calibrate;

import android.support.wearable.view.WatchViewStub;

import com.fiftyeightmorris.habitkick.R;
import com.fiftyeightmorris.habitkick.UI;
import com.fiftyeightmorris.habitkick.WatchActivity;

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
