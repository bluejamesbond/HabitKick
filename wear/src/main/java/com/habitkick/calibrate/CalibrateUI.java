package com.habitkick.calibrate;

import android.app.Activity;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

import com.habitkick.R;
import com.habitkick.UI;

public class CalibrateUI extends UI {
    public CalibrateUI(WatchViewStub stub) {
        super(stub);
    }

    @Override
    public void onDestroy(Activity activity, WatchViewStub stub) {
    }

    @Override
    protected void onThemeChange(final WatchViewStub stub, final int appColor, float hue) {

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
    protected void onCreate(final Activity activity, final WatchViewStub stub) {
    }
}
