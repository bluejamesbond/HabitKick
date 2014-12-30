package com.habitkick.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;

import com.habitkick.R;
import com.habitkick.UI;
import com.habitkick.calibrate.CalibrateActivity;

public class MenuUI extends UI {

    public MenuUI(WatchViewStub stub) {
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
                stub.findViewById(R.id.pause_button).setBackground(createBigButtonStateList(appColor));

                // ---
                ((TextView) stub.findViewById(R.id.calibrate_button_icon)).setTextColor(appColor);
                ((TextView) stub.findViewById(R.id.mobile_button_icon)).setTextColor(appColor);
            }
        });
    }

    @Override
    protected void onCreate(final Activity activity, final WatchViewStub stub) {

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
