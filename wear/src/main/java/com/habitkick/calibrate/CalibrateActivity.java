package com.habitkick.calibrate;

import android.support.wearable.view.WatchViewStub;

import com.habitkick.R;
import com.habitkick.WatchActivity;
import com.habitkick.shared.Utils;
import com.habitkick.shared.core.MessageConstants;

public class CalibrateActivity extends WatchActivity<CalibrateUI> {

    @Override
    protected CalibrateUI getUIInstance(WatchViewStub stub) {
        return new CalibrateUI(stub);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.calibrate_activity;
    }

    @Override
    protected void onMessageReceived(int id, String message) {
        switch (id) {
            case MessageConstants.NEXT_CALIBRATION_POSITION_ID: {
                getUI().setPosition(Utils.getStore(this, "CalibrationPosition", 0));
            }
        }

    }
}
