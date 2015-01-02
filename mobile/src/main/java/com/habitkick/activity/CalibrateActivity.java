package com.habitkick.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.habitkick.R;
import com.habitkick.core.MobileActivity;
import com.habitkick.shared.common.view.HoloCircularProgressBar;
import com.habitkick.shared.common.view.HueShiftImageView;
import com.habitkick.shared.core.MessageConstants;

public class CalibrateActivity extends MobileActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        sendMessage("App opened");

        _runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.calibrate_next_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendMessage(MessageConstants.NEXT_CALIBRATION_POSITION_MSG);
                        setNextPositionEnabled(false);
                    }
                });
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.calibrate_activity;
    }

    @Override
    protected void onMessageReceived(int id, String message) {
        switch (id) {
            case MessageConstants.STORED_CALIBRATION_POSITION_ID: {
                setNextPositionEnabled(true);
            }
        }
    }


    @Override
    protected void onThemeChange(final int appColor, final float hue) {
        super.onThemeChange(appColor, hue);
        _runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((HueShiftImageView) findViewById(R.id.logo)).shiftHue(hue);
                findViewById(R.id.calibrate_next_button).setBackground(createBigButtonStateList(appColor));
                ((TextView) findViewById(R.id.progress_label)).setTextColor(appColor);
                ((HoloCircularProgressBar) findViewById(R.id.progress)).setProgressColor(appColor);
            }
        });
    }

    public void setNextPositionEnabled(final boolean enable) {
        findViewById(R.id.calibrate_next_button).setSelected(enable);
        findViewById(R.id.calibrate_next_button).setEnabled(enable);
    }
}
