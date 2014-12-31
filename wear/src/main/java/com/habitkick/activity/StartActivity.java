package com.habitkick.activity;

import android.os.Bundle;
import android.view.View;

import com.habitkick.R;
import com.habitkick.core.WatchActivity;
import com.habitkick.shared.core.MessageConstants;

public class StartActivity extends WatchActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.start_activity;
    }

    @Override
    protected void onThemeChange(final int appColor, float hue) {
        super.onThemeChange(appColor, hue);
        _runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View stub = getRootView();
                stub.findViewById(R.id.mobile_button).setBackground(createBigButtonStateList(appColor));
            }
        });
    }

    @Override
    protected void onCreate(Bundle bundle) {
        _runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getRootView().findViewById(R.id.mobile_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendMessage(MessageConstants.OPEN_HOME_ACTIVITY_MSG);
                    }
                });
            }
        });
    }
}
