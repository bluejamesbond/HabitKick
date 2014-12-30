package com.habitkick.home;

import com.habitkick.MobileActivity;
import com.habitkick.R;

public class HomeActivity extends MobileActivity {

    @Override
    protected void onCreate() {
        sendMessage("App opened");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.home_activity;
    }
}
