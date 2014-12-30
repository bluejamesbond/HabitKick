package com.habitkick.start;

import android.app.Activity;
import android.support.wearable.view.WatchViewStub;

import com.habitkick.R;
import com.habitkick.UI;

public class StartUI extends UI {
    public StartUI(WatchViewStub stub) {
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
            }
        });
    }

    @Override
    protected void onCreate(final Activity activity, final WatchViewStub stub) {
    }
}
