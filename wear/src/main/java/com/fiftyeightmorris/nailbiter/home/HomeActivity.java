package com.fiftyeightmorris.nailbiter.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fiftyeightmorris.nailbiter.alert.AlertNotificationReceiver;
import com.fiftyeightmorris.nailbiter.IMonitorEventListener;
import com.fiftyeightmorris.nailbiter.PositionMonitor;
import com.fiftyeightmorris.nailbiter.R;

import java.io.IOException;


public class HomeActivity extends Activity implements IMonitorEventListener {

    public static final String PREFS_NAME = "NailBiterPrefs";

    private final static int dot = 200;      // Length of a Morse Code "dot" in milliseconds
    private final static int dash = 500;     // Length of a Morse Code "dash" in milliseconds
    private final static int short_gap = 200;    // Length of Gap Between dots/dashes
    private final static int medium_gap = 500;   // Length of Gap Between Letters
    private final static int long_gap = 1000;    // Length of Gap Between Words
    private final static long[] SOSPattern = {
            0,  // Start immediately
            dot, short_gap, dot, short_gap, dot,    // s
            medium_gap,
            dash, short_gap, dash, short_gap, dash, // o
            medium_gap,
            dot, short_gap, dot, short_gap, dot,    // s
            long_gap
    };
    private final Context context = this;
    // construct SOS pattern
    private long mCalibrationDuration = 18;
    private long mMeasurementDuration = 3;
    private long mStartCalibrateTime;
    private long mLastVibrateTime = 0;
    private TextView mTextView;
    private PositionMonitor mPositionMonitor;
    private Vibrator mVibratorService;
    private View mCalibrateButton;
    private Button mMonitorButton;
    private CalibrateTask mCalibrateTask;
    private HomeUI ui;

    @Override
    public void onAttachedToWindow() {
        getWindow().setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_activity);

        // get context
        WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);

        // ui communication
        ui = HomeUI.getInstance();
        ui.create(stub);
        ui.setHue(stub, HomeUI.Theme.RANDOM);

        // get system sensor service
        SensorManager sensorManager =
                (SensorManager) this.getSystemService(Activity.SENSOR_SERVICE);

        // get system vibrator service
        mVibratorService = (Vibrator) this.getSystemService(Activity.VIBRATOR_SERVICE);

        // get the position monitor
        mPositionMonitor = new PositionMonitor(sensorManager, getSharedPreferences(PREFS_NAME, 0), this);
        mPositionMonitor.setMeasurementDuration(mMeasurementDuration);
        mPositionMonitor.setState(PositionMonitor.NO_STATE);
        mPositionMonitor.setVibratorService(mVibratorService);

        // create a calibration task
        mCalibrateTask = new CalibrateTask(this);

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
    protected void onPause() {
        super.onPause();
    }

    public void onError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView.setText(R.string.monitor_error);
                mPositionMonitor.unregisterListeners();
                mMonitorButton.setText(R.string.monitor);
            }
        });

    }

    public void onMonitorAlert() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (System.currentTimeMillis() - mLastVibrateTime > 5000) {
                    mVibratorService.vibrate(2000);
                    mLastVibrateTime = System.currentTimeMillis();
                    Intent i = new Intent();
                    i.setAction("com.fiftyeightmorris.nailbiter.SHOW_NOTIFICATION");
                    i.putExtra(AlertNotificationReceiver.CONTENT_KEY, getString(R.string.title));
                    sendBroadcast(i);
                    Toast.makeText(context, context.getString(R.string.alert_message), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    class CalibrateTask implements Runnable {

        private Thread t = null;
        private Context context = null;

        public CalibrateTask(Context context) {
            this.context = context;
        }

        public void start() throws IOException {
            t = new Thread(this);
            t.start();
        }

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // ignore
            }

            mPositionMonitor.setState(PositionMonitor.CALIBRATING_STATE);
            mPositionMonitor.registerListeners();
            mStartCalibrateTime = System.currentTimeMillis();

            // wait for calibration to finish
            while ((System.currentTimeMillis() - mStartCalibrateTime) < (mCalibrationDuration * 1000)) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // ignore
                }
            }

            // nofity user on calibration end
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mPositionMonitor.unregisterListeners();
                    mVibratorService.vibrate(500);
                    mTextView.setText(R.string.calibrate_finished);
                    Toast.makeText(context, context.getString(R.string.calibrate_finished), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
