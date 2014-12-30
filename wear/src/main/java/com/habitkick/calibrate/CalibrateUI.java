package com.habitkick.calibrate;

import android.app.Activity;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;

import com.habitkick.R;
import com.habitkick.WatchUI;
import com.habitkick.shared.SocketActivity;

public class CalibrateUI extends WatchUI {
    public CalibrateUI(WatchViewStub stub) {
        super(stub);
    }

    @Override
    public void onDestroy(Activity activity, View stub) {
    }

    @Override
    protected void onThemeChange(final View stub, final int appColor, float hue) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // ---
                stub.findViewById(R.id.mobile_button).setBackground(createBigButtonStateList(appColor));

                // ---
                ((TextView) stub.findViewById(R.id.position_label)).setTextColor(appColor);
            }
        });
    }

    @Override
    protected void onCreate(final SocketActivity activity, final View stub) {
    }
}
