package com.habitkick.home;

import android.support.wearable.view.WatchViewStub;

import com.habitkick.R;
import com.habitkick.WatchActivity;


public class HomeActivity extends WatchActivity<HomeUI> {

    @Override
    protected HomeUI getUIInstance(WatchViewStub stub) {
        return new HomeUI(stub);
    }

    @Override
    protected void onCreate(WatchViewStub stub, HomeUI ui) {
//
//        // get system sensor service
//        SensorManager sensorManager =
//                (SensorManager) this.getSystemService(Activity.SENSOR_SERVICE);
//
//        // get system vibrator service
//        mVibratorService = (Vibrator) this.getSystemService(Activity.VIBRATOR_SERVICE);
//
//        // get the position monitor
//        mPositionMonitor = new PositionMonitor(sensorManager, getSharedPreferences(PREFS_NAME, 0), this);
//        mPositionMonitor.setMeasurementDuration(mMeasurementDuration);
//        mPositionMonitor.setState(PositionMonitor.NO_STATE);
//        mPositionMonitor.setVibratorService(mVibratorService);
//
//        // create a calibration task
//        mCalibrateTask = new CalibrateTask(this);

//        // register listeners for the buttons
//        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
//            @Override
//            public void onLayoutInflated(WatchViewStub stub) {
//
//                mTextView = (TextView) stub.findViewById(R.id.text);
//                mTextView.setText("");
//                mCalibrateButton = stub.findViewById(R.id.calibrateButton);
//                mCalibrateButton.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View view) {
//
//                        mTextView.setText(R.string.calibrate_message);
//                        mMonitorButton.setText(R.string.monitor);
//                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//
//                        try {
//                            mCalibrateTask.start();
//                        } catch (IOException e) {
//                            mPositionMonitor.unregisterListeners();
//                        }
//                    }
//                });
//
//                mMonitorButton = (Button) stub.findViewById(R.id.monitorButton);
//                mMonitorButton.setText(R.string.monitor);
//                mMonitorButton.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View view) {
//                        if (mPositionMonitor.isMonitor()) {
//                            mPositionMonitor.setState(PositionMonitor.NO_STATE);
//                            mPositionMonitor.unregisterListeners();
//                            mTextView.setText(R.string.monitor_stopped);
//                            mMonitorButton.setText(R.string.monitor);
//                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//                        } else {
//                            mPositionMonitor.setState(PositionMonitor.MONITORING_STATE);
//                            mMonitorButton.setText(R.string.monitor_stop);
//                            mPositionMonitor.registerListeners();
//                            mTextView.setText(R.string.monitor_message);
//                            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//                        }
//
//                    }
//                });
//
//            }
//        });

    }

    @Override
    protected int getContentViewId() {
        return R.layout.home_activity;
    }
}
