package com.habitkick.home;

import android.app.Activity;
import android.support.wearable.view.WatchViewStub;
import android.view.View;

import com.habitkick.R;
import com.habitkick.shared.ListenerService;
import com.habitkick.shared.SocketActivity;
import com.habitkick.shared.UI;

public class HomeUI extends UI {

    public HomeUI(WatchViewStub stub) {
        super(stub);
    }

    @Override
    public void onDestroy(Activity activity, View stub) {
    }

    @Override
    protected int getBackgroundId() {
        return UI.NO_BACKGROUND;
    }

    @Override
    protected void onThemeChange(final View stub, final int appColor, final float hue) {
    }

    protected void onCreate(final SocketActivity activity, final View stub) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                activity.findViewById(R.id.calibrate_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.sendMessage(ListenerService.OPEN_CALIBRATION);
                    }
                });
            }
        });
    }
}
