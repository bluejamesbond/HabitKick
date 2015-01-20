package com.habitkick.activity;

import android.view.View;
import android.widget.TextView;

import com.habitkick.R;
import com.habitkick.core.WatchActivity;
import com.habitkick.shared.common.Utils;
import com.habitkick.shared.core.MessageId;

public class CalibrateActivity extends WatchActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.calibrate_activity;
    }

    @Override
    protected void onMessageReceived(MessageId id, String message) {
        switch (id) {
            case NEXT_CALIBRATION_POSITION: {
                setPosition(Utils.getStore(this, "CalibrationPosition", 0));
            }
        }
    }

    @Override
    protected void onThemeChange(final int appColor, float hue) {
        super.onThemeChange(appColor, hue);

        _runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View stub = getRootView();
                stub.findViewById(R.id.mobile_button).setBackground(createBigButtonStateList(appColor));
                ((TextView) stub.findViewById(R.id.position_label)).setTextColor(appColor);
            }
        });
    }

    protected void setPosition(final int position) {
        ((TextView) getRootView().findViewById(R.id.position_value)).setText(Integer.toString(position));
    }
}
