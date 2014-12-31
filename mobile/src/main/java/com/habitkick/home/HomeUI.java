package com.habitkick.home;

import android.app.Activity;
import android.view.View;

import com.habitkick.MobileUI;
import com.habitkick.R;
import com.habitkick.shared.ListenerService;
import com.habitkick.shared.SocketActivity;

public class HomeUI extends MobileUI {

    public HomeUI(View v) {
        super(v);
    }

    @Override
    public void onDestroy(Activity activity, View stub) {
    }

    @Override
    protected void onThemeChange(final View stub, final int appColor, final float hue) {
        super.onThemeChange(stub, appColor, hue);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                stub.findViewById(R.id.center_banner).setBackgroundColor(appColor);
                stub.findViewById(R.id.calibrate_button).setBackground(createBigButtonStateList(appColor));
                //    stub.findViewById(R.id.calibrate_next_button).setBackground(createBigButtonLowRadStateList(appColor));
            }
        });
    }

    protected void onCreate(final SocketActivity activity, final View stub) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                activity.findViewById(R.id.calibrate_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.sendMessage(ListenerService.OPEN_CALIBRATION_MSG);
                        activity.sendMessage(ListenerService.START_CALIBRATION_SERVICE_MSG);
                    }
                });

//                activity.findViewById(R.id.calibrate_next_button).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        activity.sendMessage(ListenerService.NEXT_CALIBRATION_POSITION_MSG);
//                        setNextPositionEnabled(activity, false);
//                    }
//                });
            }
        });
    }

    public void setNextPositionEnabled(final Activity activity, final boolean enable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //    activity.findViewById(R.id.calibrate_next_button).setEnabled(enable);
            }
        });
    }
}
