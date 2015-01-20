package com.habitkick.activity;

import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.habitkick.R;
import com.habitkick.core.MobileActivity;
import com.habitkick.shared.common.Global;
import com.habitkick.shared.common.Utils;
import com.habitkick.shared.common.view.HoloCircularProgressBar;
import com.habitkick.shared.common.view.HueShiftImageView;
import com.habitkick.shared.core.MessageId;

public class CalibrateActivity extends MobileActivity {

    private static final int maxSteps = 40;
    private static final int incrementSteps = 10;
    private int storedSteps;
    private Object timeout;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        storedSteps = 0;

        _runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.calibrate_next_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (storedSteps >= maxSteps) {
                            startActivity(DashboardActivity.class);
                        } else {
                            sendMessage(MessageId.NEXT_CALIBRATION_POSITION);
                            setNextPositionEnabled(false);

                            Utils.setTimeout(new Runnable() {
                                @Override
                                public void run() {
                                    setNextPositionEnabled(true);
                                }
                            }, 5000);

                        }
                    }
                });
            }
        });
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

    @Override
    protected int getContentViewId() {
        return R.layout.calibrate_activity;
    }

    @Override
    protected void onMessageReceived(MessageId id, String message) {
        switch (id) {

            case FINISHED_CALIBRATION_SERVICE:
            case STORED_CALIBRATION_POSITION: {

                Utils.cancelTimout(timeout);

                storedSteps += incrementSteps;

                // update progress
                ((HoloCircularProgressBar) findViewById(R.id.progress)).setProgress((float) storedSteps / (float) maxSteps);
                ((TextView) findViewById(R.id.progress_value)).setText(Integer.toString(storedSteps));

                if (storedSteps >= maxSteps) {

                    // store that a calibration is complete
                    Utils.putStore(CalibrateActivity.this, Global.CALIBRATED_FLAG_STORE_KEY, true);

                    // change button text on done
                    ((TextView) findViewById(R.id.calibrate_next_button_left)).setText("view");
                    ((TextView) findViewById(R.id.calibrate_next_button_right)).setText("dashboard");
                }

                // enable the button
                setNextPositionEnabled(true);

                break;
            }
        }
    }

    public void setNextPositionEnabled(final boolean enable) {
        View calibrateButton = findViewById(R.id.calibrate_next_button);

        calibrateButton.setSelected(enable);
        calibrateButton.setEnabled(enable);

        if(enable) {
            calibrateButton.getBackground().clearColorFilter();
        } else {
            calibrateButton.getBackground().setColorFilter(Color.parseColor("#888888"), PorterDuff.Mode.MULTIPLY);
        }
    }
}
