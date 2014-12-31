package com.habitkick.home;

import com.habitkick.MobileActivity;
import com.habitkick.R;
import com.habitkick.shared.core.ListenerService;

public class HomeActivity extends MobileActivity<HomeUI> {

    @Override
    protected void onCreate() {
        sendMessage("App opened");
    }

    @Override
    protected HomeUI getUIInstance() {
        return new HomeUI(findViewById(R.id.bg));
    }

    @Override
    protected int getContentViewId() {
        return R.layout.home_activity;
    }

    @Override
    protected void onMessageReceived(int id, String message) {
        switch (id) {
            case ListenerService.STORED_CALIBRATION_POSITION_ID: {
                getUI().setNextPositionEnabled(this, true);
            }
        }
    }
}
