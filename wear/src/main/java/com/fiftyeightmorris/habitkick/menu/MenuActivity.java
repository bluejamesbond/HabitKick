package com.fiftyeightmorris.habitkick.menu;

import android.support.wearable.view.WatchViewStub;

import com.fiftyeightmorris.habitkick.R;
import com.fiftyeightmorris.habitkick.UI;
import com.fiftyeightmorris.habitkick.WatchActivity;
import com.fiftyeightmorris.habitkick.calibrate.CalibrateUI;


public class MenuActivity extends WatchActivity<MenuUI> {

    @Override
    protected MenuUI getUIInstance(WatchViewStub stub) {
        return new MenuUI(stub);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.menu_activity;
    }
}
