package com.habitkick;

import android.content.Intent;

import com.habitkick.calibrate.CalibrateActivity;
import com.habitkick.shared.ListenerService;

public class WatchListenerService extends ListenerService {

    @Override
    protected void handleMessage(int id) {
        switch (id) {
            case ListenerService.OPEN_CALIBRATION_ID: {
                Intent intent = new Intent(this, CalibrateActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent);
                break;
            }
        }
    }
}
