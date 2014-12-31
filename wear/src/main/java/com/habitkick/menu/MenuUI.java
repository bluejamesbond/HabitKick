package com.habitkick.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;

import com.habitkick.R;
import com.habitkick.WatchUI;
import com.habitkick.calibrate.CalibrateActivity;
import com.habitkick.shared.core.SocketActivity;

public class MenuUI extends WatchUI {

    public MenuUI(WatchViewStub stub) {
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
                stub.findViewById(R.id.pause_button).setBackground(createBigButtonStateList(appColor));

                // ---
                ((TextView) stub.findViewById(R.id.calibrate_button_icon)).setTextColor(appColor);
                ((TextView) stub.findViewById(R.id.mobile_button_icon)).setTextColor(appColor);
            }
        });
    }

    @Override
    protected void onCreate(final SocketActivity activity, final View stub) {

        final Context context = stub.getContext();
        final Intent calibrateIntent = new Intent(context, CalibrateActivity.class);

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                stub.findViewById(R.id.pause_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.finish();
                    }
                });

                stub.findViewById(R.id.calibrate_button).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        context.startActivity(calibrateIntent);
                    }
                });
            }
        });
    }
}
