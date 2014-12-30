package com.habitkick.menu;

import android.support.wearable.view.WatchViewStub;

import com.habitkick.R;
import com.habitkick.WatchActivity;


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
