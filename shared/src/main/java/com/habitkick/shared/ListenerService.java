package com.habitkick.shared;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public abstract class ListenerService extends WearableListenerService {

    public static final int OPEN_HOME_ACTIVITY_ID = 0xe5c0be69;
    public static final String OPEN_HOME_ACTIVITY_MSG = "OpenHome";

    public static final int OPEN_CALIBRATION_ID = 0x95f964bf;
    public static final String OPEN_CALIBRATION_MSG = "OpenCalibrationWear";

    public static final int START_CALIBRATION_SERVICE_ID = 0xe13e64ec;
    public static final String START_CALIBRATION_SERVICE_MSG = "StartCalibrationPos";

    public static final int NEXT_CALIBRATION_POSITION_ID = 0x916bcdfd;
    public static final String NEXT_CALIBRATION_POSITION_MSG = "NextCalibrationPos";

    public static final int FINISHED_CALIBRATION_SERVICE_ID = 0x794fef9d;
    public static final String FINISHED_CALIBRATION_SERVICE_MSG = "FinishCalibrationPos";

    public static final int STORED_CALIBRATION_POSITION_ID = 0xeffceaad;
    public static final String STORED_CALIBRATION_POSITION_MSG = "StoredCalibrationPos";

    private Handler mHandler;

    public ListenerService(){
        super();
        mHandler = new Handler();
    }

    public final void runOnUiThread(Runnable action) {
        mHandler.post(action);
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

        if (messageEvent.getPath().equals("/message_path")) {
            final String message = new String(messageEvent.getData());
            final int id = message.hashCode();

            handleMessage(id);

            Intent messageIntent = new Intent();
            messageIntent.setAction(Intent.ACTION_SEND);
            messageIntent.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);
        } else {
            super.onMessageReceived(messageEvent);
        }
    }

    protected abstract void handleMessage(int id);
}
