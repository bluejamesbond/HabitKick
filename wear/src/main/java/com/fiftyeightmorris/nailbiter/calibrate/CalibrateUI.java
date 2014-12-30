package com.fiftyeightmorris.nailbiter.calibrate;

import android.app.Activity;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

import com.fiftyeightmorris.nailbiter.IUserInterface;
import com.fiftyeightmorris.nailbiter.R;

public class CalibrateUI extends IUserInterface {
    public CalibrateUI(WatchViewStub stub) {
        super(stub);
    }

    @Override
    protected void onThemeChange(final WatchViewStub stub, final int appColor, float hue) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // ---
                stub.findViewById(R.id.start_button).setBackground(createBigButtonStateList(stub, appColor));

                // ---
                ((TextView) stub.findViewById(R.id.position_label)).setTextColor(appColor);
            }
        });
    }

    @Override
    protected void onCreate(final Activity activity, final WatchViewStub stub) {
    }
}
