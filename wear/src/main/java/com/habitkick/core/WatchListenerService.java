package com.habitkick.core;

import android.app.Activity;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import com.habitkick.R;
import com.habitkick.activity.CalibrateActivity;
import com.habitkick.activity.HomeActivity;
import com.habitkick.alert.AlertNotificationReceiver;
import com.habitkick.shared.common.ListenerService;
import com.habitkick.shared.common.Utils;
import com.habitkick.shared.core.MessageId;

import java.util.Timer;

public class WatchListenerService extends ListenerService implements IMonitorEventListener {

    private PositionMonitor positionMonitor;
    private long lastVibrateTime = 0;
    private Timer timer = new Timer();

    @Override
    public void onCreate() {
        super.onCreate();
//        timer.scheduleAtFixedRate(
//                new TimerTask() {
//                    public void run() {
//                    }
//                }, 0, 1000);

        generateAlerts();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        destroyPositionMonitor();

        if (timer != null) {
            timer.cancel();
        }
    }

    public void generateAlerts() {
        Utils.setTimeout(new Runnable() {
            @Override
            public void run() {
                onMonitorAlert();
                generateAlerts();
            }
        }, 10000);
    }

    public void onMonitorAlert() {

        sendMessage(MessageId.MONITOR_ALERT);

        if (!Utils.IS_EMULATOR) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (System.currentTimeMillis() - lastVibrateTime > 5000) {
                        positionMonitor.getVibratorService().vibrate(2000);
                        lastVibrateTime = System.currentTimeMillis();
                        Intent i = new Intent();
                        i.setAction("com.habitkick.SHOW_NOTIFICATION");
                        i.putExtra(AlertNotificationReceiver.CONTENT_KEY, getString(R.string.title));
                        sendBroadcast(i);
                        Toast.makeText(WatchListenerService.this, WatchListenerService.this.getString(R.string.alert_message), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    @Override
    public void onPositionStored(int pos, boolean done) {
        Utils.putStore(WatchListenerService.this, "CalibrationPosition", pos);

        if (done) {
            if (positionMonitor == null) {
                return; // error
            }

            destroyPositionMonitor();

            Utils.putStore(WatchListenerService.this, "CalibrationFinished", true);

            sendMessage(MessageId.FINISHED_CALIBRATION_SERVICE);

        } else {
            sendMessage(MessageId.STORED_CALIBRATION_POSITION);
        }
    }

    private void destroyPositionMonitor() {
        if (positionMonitor != null) {
            positionMonitor.unregisterListeners(true);
            positionMonitor.getVibratorService().cancel();
            positionMonitor.setVibratorService(null);
            positionMonitor = null;
        }
    }

    private PositionMonitor createPositionMonitor() {
        // get system sensor service
        SensorManager sensorManager =
                (SensorManager) this.getSystemService(Activity.SENSOR_SERVICE);

        // get system vibrator service
        Vibrator vibrator = (Vibrator) this.getSystemService(Activity.VIBRATOR_SERVICE);

        PositionMonitor positionMonitor = new PositionMonitor(sensorManager, getSharedPreferences(Utils.PREFERENCE_NAME, 0), this);
        positionMonitor.setVibratorService(vibrator);
        positionMonitor.setState(PositionMonitor.NO_STATE);

        return positionMonitor;
    }

    @Override
    protected void handleMessage(MessageId id, String msg) {
        switch (id) {
            case START_MONITOR_SERVICE:
                if (positionMonitor == null) {
                    positionMonitor = createPositionMonitor();
                } else if (positionMonitor.isRegistered()) {
                    destroyPositionMonitor();
                    positionMonitor = createPositionMonitor();
                }

                positionMonitor.setState(PositionMonitor.MONITORING_STATE);
                positionMonitor.registerListeners();

                break;
            case OPEN_HOME_ACTIVITY:
                startActivity(HomeActivity.class);
                break;
            case OPEN_CALIBRATION:
                startActivity(CalibrateActivity.class);
                break;
            case START_CALIBRATION_SERVICE:
                if (positionMonitor == null) {
                    positionMonitor = createPositionMonitor();
                } else if (positionMonitor.isRegistered()) {
                    destroyPositionMonitor();
                    positionMonitor = createPositionMonitor();
                }

                positionMonitor.setState(PositionMonitor.CALIBRATING_STATE);
                positionMonitor.registerListeners();

                break;
            case NEXT_CALIBRATION_POSITION:
                if (positionMonitor == null) {
                    Log.e("TEST", "NULL!");
                    break;
                }

                if (positionMonitor.hasNextPosition()) {
                    positionMonitor.nextPosition();
                }

                break;
        }
    }
}
