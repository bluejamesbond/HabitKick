/*
 * Copyright (c) 2014. 58 Morris LLC All rights reserved
 */

package com.fiftyeightmorris.nailbiter;

import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.util.Log;

/**
 * Created by dmitriyblok on 10/12/14.
 */


/**
 * PositionMonitor class helps track hand motion. During calibration, the class first uses the system
 * sensor to get the rotation matrix which is used to find the azimuth/pitch/roll of the device. A
 * combination of getRotationMatrix and getOrientation does the trick. The calibration phase stores
 * these readings or "device-angles". During the monitoring phase, the sensor data is checked to see
 * if it matches any of these "device-angles."
 */
public class PositionMonitor implements SensorEventListener {

    private static final int updateFrequency = 100 * 1000 * 10;

    public static final int NO_STATE = 0;
    public static final int CALIBRATING_STATE = 1;
    public static final int MONITORING_STATE = 2;
    public static final int MAX_POSITIONS = 40;
    public static final float NO_VALUE = 180;
    public static final String TAG = PositionMonitor.class.getSimpleName();

    private static float EPSILON = 0.3f;
    private final float[] mRotationMatrix = new float[16];

    private int mState;
    private int mCurrPos;
    private long mMeasurementDuration;
    private long mPositionSetTime = 0;
    private long mLastVibrateTime;
    private final float[] mSavedOrientation = new float[3];
    private final float[][] mSavedOrientationMatrix = new float[MAX_POSITIONS][3];
    private final float[] mOrientation = new float[3];

    private SensorManager mSensorManager;
    private Sensor mRotationVectorSensor;
    private IMonitorEventListener mMonitorEventListener = null;
    private SharedPreferences mSettings;
    private Vibrator mVibratorService;

    public PositionMonitor(SensorManager sensorManager, SharedPreferences settings,
                           IMonitorEventListener monitorEventListener) {
        mSensorManager = sensorManager;
        mSettings = settings;
        mMonitorEventListener = monitorEventListener;
        mRotationVectorSensor = mSensorManager.getDefaultSensor(
                Sensor.TYPE_ROTATION_VECTOR);
    }

    public void unregisterListeners() {
        mSensorManager.unregisterListener(this);
        if (isCalibrate()) {
            saveRotationMatrix();
        }
    }

    public void registerListeners() {

        mCurrPos = 0;

        if (isCalibrate()) {
            Log.d(TAG, "Calibration started");
            mLastVibrateTime = 0;
            for (int j = 0; j < MAX_POSITIONS; j++) {
                for (int i = 1; i < 3; i++) {
                    mSavedOrientationMatrix[j][i] = NO_VALUE;
                }
            }
        } else {
            mPositionSetTime = 0;
            restoreRotationMatrix();
        }

        mSensorManager.registerListener(this, mRotationVectorSensor, updateFrequency);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        switch (event.sensor.getType()) {
            case Sensor.TYPE_ROTATION_VECTOR: {

                // Get the rotation matrix from the ground to the watch - all the information on how
                // the watch is oriented from the ground
                SensorManager.getRotationMatrixFromVector(mRotationMatrix, event.values);

                // store the rotation during calibration
                if (isCalibrate()) {
                    calculateRotation();
                } else {
                    if (isPositionReached()) {
                        if (mPositionSetTime == 0) {
                            mPositionSetTime = System.currentTimeMillis();
                        } else {
                            if (System.currentTimeMillis() - mPositionSetTime >= getMeasurementDuration()) {
                                mMonitorEventListener.onMonitorAlert();
                            }
                        }
                    } else {
                        mPositionSetTime = 0;
                    }
                }
                break;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @SuppressWarnings("unused")
    private void logRotationMatrix() {
        Log.d(TAG, "=======================");
        for (int i = 0; i < 16; i++) {
            Log.d(TAG, String.format("i=%d %f", i, mRotationMatrix[i]));
        }
        Log.d(TAG, "=======================");
    }

    private void saveRotationMatrix() {

        SharedPreferences.Editor editor = mSettings.edit();

        if (mCurrPos >= MAX_POSITIONS) {
            mCurrPos = MAX_POSITIONS;
        }

        for (int j = 0; j < mCurrPos; j++) {
            for (int i = 0; i < 3; i++) {
                editor.putFloat(String.format("matrix%d_%d", j, i), mSavedOrientationMatrix[j][i]);
                Log.d(TAG, String.format("mSavedOrientationMatrix[%d][%d]= %f", j,
                        i, mSavedOrientationMatrix[j][i]));
            }
        }

        editor.apply();
    }

    private void restoreRotationMatrix() {
        for (int j = 0; j < MAX_POSITIONS; j++) {
            for (int i = 0; i < 3; i++) {
                mSavedOrientationMatrix[j][i] =
                        mSettings.getFloat(String.format("matrix%d_%d", j, i), NO_VALUE);
            }
        }
    }

    private synchronized void calculateRotation() {

        // get the orientation of the watch azimuth/roll/pitch
        SensorManager.getOrientation(mRotationMatrix, mSavedOrientation);

        if (System.currentTimeMillis() - mLastVibrateTime > 1000) {
            mVibratorService.vibrate(100);
            mLastVibrateTime = System.currentTimeMillis();
        }

        // first value seems to be garbage
        if (mCurrPos >= MAX_POSITIONS || mCurrPos == 0) {
            mCurrPos++;
            return;
        }

        for (int i = 0; i < 3; i++) {
            mSavedOrientationMatrix[mCurrPos][i] = mSavedOrientation[i];
        }

        mCurrPos++;
    }

    private synchronized boolean isPositionReached() {

        // get the orientation of the watch azimuth/roll/pitch
        SensorManager.getOrientation(mRotationMatrix, mOrientation);

        // check if any "device-angles" match up
        for (int j = 0; j < MAX_POSITIONS; j++) {
            int diffCounter = 0;
            for (int i = 1; i < 3; i++) {
                float diff = java.lang.Math.abs(mSavedOrientationMatrix[j][i] - mOrientation[i]);
                if (diff > EPSILON) {
                    diffCounter++;
                }
            }

            Log.d(TAG, "=======================");
            Log.d(TAG, String.format("diff counter=%d", diffCounter));
            Log.d(TAG, "=======================");

            if (diffCounter == 0) {
                Log.d(TAG, String.format("mSavedOrientationMatrix[%d][%d]= %f", j, 1, mSavedOrientationMatrix[j][1]));
                Log.d(TAG, String.format("mSavedOrientationMatrix[%d][%d]= %f", j, 2, mSavedOrientationMatrix[j][2]));
                return true;
            }

        }

        return false;
    }

    public boolean isCalibrate() {
        return mState == CALIBRATING_STATE;
    }

    public boolean isMonitor() {
        return mState == MONITORING_STATE;
    }

    public void setState(int state) {
        this.mState = state;
    }

    public long getMeasurementDuration() {
        return mMeasurementDuration;
    }

    public void setMeasurementDuration(long mMeasurementDuration) {
        this.mMeasurementDuration = mMeasurementDuration * 1000;
    }

    public void setVibratorService(Vibrator vibratorService) {
        mVibratorService = vibratorService;
    }
}
