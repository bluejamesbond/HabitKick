package com.habitkick.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.habitkick.R;
import com.habitkick.core.WatchActivity;


public class MenuActivity extends WatchActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.menu_activity;
    }

    @Override
    protected void onThemeChange(final int appColor, float hue) {
        super.onThemeChange(appColor, hue);
        _runOnUiThread(new Runnable() {
            @Override
            public void run() {

                View stub = getRootView();
                // ---
                stub.findViewById(R.id.pause_button).setBackground(createBigButtonStateList(appColor));

                // ---
                ((TextView) stub.findViewById(R.id.calibrate_button_icon)).setTextColor(appColor);
                ((TextView) stub.findViewById(R.id.mobile_button_icon)).setTextColor(appColor);
            }
        });
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        final Intent calibrateIntent = new Intent(this, CalibrateActivity.class);

        _runOnUiThread(new Runnable() {

            @Override
            public void run() {

                getRootView().findViewById(R.id.pause_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

                getRootView().findViewById(R.id.calibrate_button).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startActivity(calibrateIntent);
                    }
                });
            }
        });
    }
}
