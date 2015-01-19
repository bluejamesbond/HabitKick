package com.habitkick.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.habitkick.R;
import com.habitkick.core.MobileActivity;
import com.habitkick.shared.common.view.HueShiftImageView;
import com.habitkick.shared.core.MessageConstants;

public class StartActivity extends MobileActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        _runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.calibrate_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(CalibrateActivity.class);
                        sendMessage(MessageConstants.OPEN_CALIBRATION_MSG);
                        sendMessage(MessageConstants.START_CALIBRATION_SERVICE_MSG);
                    }
                });
            }
        });
    }

    @Override
    protected void onConnectionChange(final boolean connected) {
        super.onConnectionChange(connected);
        _runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateConnectionStatus(connected);
            }
        });
    }

    protected void updateConnectionStatus(boolean status) {
        ((TextView) findViewById(R.id.connection_status)).setText(getResources().getText(status ? R.string.wear_connected : R.string.wear_disconnected));
    }

    @Override
    protected int getContentViewId() {
        return R.layout.start_activity;
    }

    @Override
    protected void onMessageReceived(int id, String message) {
        switch (id) {
            case MessageConstants.STORED_CALIBRATION_POSITION_ID: {
                setNextPositionEnabled(this, true);
            }
        }
    }


    @Override
    protected void onThemeChange(final int appColor, final float hue) {
        super.onThemeChange(appColor, hue);
        _runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView) findViewById(R.id.tagline)).setTextColor(appColor);
                ((HueShiftImageView) findViewById(R.id.logo)).shiftHue(hue);
                findViewById(R.id.calibrate_button).setBackground(createBigButtonStateList(appColor));
            }
        });
    }

    public void setNextPositionEnabled(final Activity activity, final boolean enable) {
        //    activity.findViewById(R.id.calibrate_next_button).setEnabled(enable)
    }
}