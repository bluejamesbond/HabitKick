package com.habitkick;

import android.app.Activity;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import com.habitkick.alert.AlertNotificationReceiver;
import com.habitkick.calibrate.CalibrateActivity;
import com.habitkick.shared.core.ListenerService;
import com.habitkick.shared.core.SocketActivity;
import com.habitkick.shared.Utils;

public class WatchListenerService extends ListenerService implements IMonitorEventListener {

    private Vibrator mVibratorService;
    private PositionMonitor mPositionMonitor;
    private long mLastVibrateTime = 0;

    public void onMonitorAlert() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (System.currentTimeMillis() - mLastVibrateTime > 5000) {
                    mVibratorService.vibrate(2000);
                    mLastVibrateTime = System.currentTimeMillis();
                    Intent i = new Intent();
                    i.setAction("com.habitkick.SHOW_NOTIFICATION");
                    i.putExtra(AlertNotificationReceiver.CONTENT_KEY, getString(R.string.title));
                    sendBroadcast(i);
                    Toast.makeText(WatchListenerService.this, WatchListenerService.this.getString(R.string.alert_message), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onPositionStored(int pos, boolean done) {
        Utils.putStore(WatchListenerService.this, "CalibrationPosition", pos);

        if (done) {
            if (mPositionMonitor == null) {
                return; // error
            }

            mPositionMonitor.unregisterListeners(true);
            mPositionMonitor = null;
            mVibratorService = null;

            Utils.putStore(WatchListenerService.this, "CalibrationFinished", true);
            SocketActivity.getActive(this).sendMessage(ListenerService.FINISHED_CALIBRATION_SERVICE_MSG);

        } else {
            SocketActivity.getActive(this).sendMessage(ListenerService.STORED_CALIBRATION_POSITION_MSG);
        }
    }

    @Override
    protected void handleMessage(int id) {
        switch (id) {
            case ListenerService.OPEN_CALIBRATION_ID: {
                Intent intent = new Intent(this, CalibrateActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent);
                break;
            }
            case ListenerService.START_CALIBRATION_SERVICE_ID: {

                if (mPositionMonitor != null) {
                    if (mPositionMonitor.isRegistered()) {
                        mPositionMonitor.unregisterListeners(false);
                    }
                } else {

                    // get system sensor service
                    SensorManager sensorManager =
                            (SensorManager) this.getSystemService(Activity.SENSOR_SERVICE);

                    // get system vibrator service
                    mVibratorService = (Vibrator) this.getSystemService(Activity.VIBRATOR_SERVICE);

                    mPositionMonitor = new PositionMonitor(sensorManager, getSharedPreferences(Utils.PREFERENCE_NAME, 0), this);
                    mPositionMonitor.setState(PositionMonitor.NO_STATE);
                    mPositionMonitor.setVibratorService(mVibratorService);
                    mPositionMonitor.setState(PositionMonitor.CALIBRATING_STATE);
                }

                mPositionMonitor.registerListeners();

                break;
            }
            case ListenerService.NEXT_CALIBRATION_POSITION_ID: {
                if (mPositionMonitor == null) {
                    Log.e("TEST", "NULL!");
                    break;
                }

                if (mPositionMonitor.hasNextPosition()) {
                    mPositionMonitor.nextPosition();
                }

                break;
            }
        }
    }
}
