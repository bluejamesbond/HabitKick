package com.habitkick.start;

import android.app.Activity;
import android.support.wearable.view.WatchViewStub;
import android.view.View;

import com.habitkick.R;
import com.habitkick.WatchUI;
import com.habitkick.shared.core.ListenerService;
import com.habitkick.shared.core.SocketActivity;

public class StartUI extends WatchUI {
    public StartUI(WatchViewStub stub) {
        super(stub);
    }

    @Override
    public void onDestroy(Activity activity, View stub) {
    }

    @Override
    protected void onThemeChange(final View stub, final int appColor, float hue) {
        super.onThemeChange(stub, appColor, hue);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // ---
                stub.findViewById(R.id.mobile_button).setBackground(createBigButtonStateList(appColor));
            }
        });
    }

    @Override
    protected void onCreate(final SocketActivity activity, final View stub) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                stub.findViewById(R.id.mobile_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.sendMessage(ListenerService.OPEN_HOME_ACTIVITY_MSG);
                    }
                });
            }
        });
    }
}
