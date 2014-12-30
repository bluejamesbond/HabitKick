package com.habitkick;

public class HomeActivity extends MobileActivity {

    @Override
    protected void onCreate() {
        sendMessage(0, "App opened");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }
}
