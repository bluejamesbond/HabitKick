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
public class PositionMonitor implements SensorEventListener {
    private SensorManager mSensorManager;
    public static final int NO_STATE = 0;
    public static final int CALIBRATING_STATE = 1;
    public static final int MONITORING_STATE = 2;
    public static final int MAX_POSITIONS = 40;
    public static final float NO_VALUE = 180;
    private final float[] mRotationMatrix = new float[16];
    private final boolean[] mDiffMatrix = new boolean[16];
    public static final String TAG = PositionMonitor.class.getSimpleName();
    private Sensor mRotationVectorSensor;
    private static final int updateFrequency = 100*1000*10;
    private int mState;
    private int mMeasurementCounter;
    private SharedPreferences mSettings;
    private long mMeasurementDuration;
    private static float EPSILON = 0.3f;
    private IMonitorEventListener monitorEventListener = null;
    private long mPositionSetTime = 0;
    private final float[] mSavedOrientation = new float[3];
    private final float[][] mSavedOrientationMatrix = new float[MAX_POSITIONS][3];
    private final float[] mOrientation = new float[3];
    private Vibrator mVibratorService;
    private long mLastVibrateTime;
    public PositionMonitor(SensorManager sensorManager, SharedPreferences settings, IMonitorEventListener monitorEventListener)  {
        mSensorManager = sensorManager;
        mSettings = settings;
        this.monitorEventListener = monitorEventListener;

        mRotationVectorSensor = mSensorManager.getDefaultSensor(
                Sensor.TYPE_ROTATION_VECTOR);

    }

    public void unregisterListeners () {
        mSensorManager.unregisterListener(this);
        if ( isCalibrate()) {
            saveRotationMatrix();
        }
    }

    public void registerListeners () {
        mMeasurementCounter=0;
        if (isCalibrate() ) {
            mLastVibrateTime = 0;
            Log.d(TAG, "Calibration started");
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

        switch( event.sensor.getType() ) {
            case Sensor.TYPE_ROTATION_VECTOR:
                mSensorManager.getRotationMatrixFromVector(
                    mRotationMatrix , event.values);

                if ( isCalibrate()) {

                    calculateRotation();
                } else {
                    if (isPositionReached() ) {
                        if (mPositionSetTime==0 ) {
                            mPositionSetTime = System.currentTimeMillis();
                        } else {
                            if (System.currentTimeMillis() - mPositionSetTime >= mMeasurementDuration) {
                                monitorEventListener.onMonitorAlert();
                            }
                        }
                    } else {
                        mPositionSetTime = 0;
                    }
                }
                break;

            default:
                return;
        }

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void logRotationMatrix() {
        Log.d(TAG, "=======================");
        for ( int i=0; i<16; i++) {
            Log.d(TAG, String.format("i=%d %f", i, mRotationMatrix[i]));
        }
        Log.d(TAG, "=======================");
    }

    private void saveRotationMatrix () {

        SharedPreferences.Editor editor = mSettings.edit();
        if ( mMeasurementCounter >= MAX_POSITIONS) {
            mMeasurementCounter = MAX_POSITIONS;
        }
        for (int j = 0; j < mMeasurementCounter; j++) {
            for (int i = 0; i < 3; i++) {

                editor.putFloat(String.format("matrix%d_%d", j, i), mSavedOrientationMatrix[j][i]);
                Log.d(TAG, String.format("mSavedOrientationMatrix[%d][%d]= %f",j, i, mSavedOrientationMatrix[j][i]));
            }
        }

        editor.apply();

    }
    private void restoreRotationMatrix () {
        for (int j = 0; j < MAX_POSITIONS; j++) {
            for (int i = 0; i < 3; i++) {
                mSavedOrientationMatrix[j][i] = mSettings.getFloat(String.format("matrix%d_%d", j, i), NO_VALUE);
            }
        }
    }
    private synchronized void calculateRotation () {
        mSensorManager.getOrientation(mRotationMatrix, mSavedOrientation);

        if ( System.currentTimeMillis() - mLastVibrateTime > 1000) {
            mVibratorService.vibrate(100);
            mLastVibrateTime = System.currentTimeMillis();
        }
        //first value seems to be garbage
        if ( mMeasurementCounter >= MAX_POSITIONS || mMeasurementCounter == 0) {
            mMeasurementCounter++;
            return;
        }
        for ( int i=0; i<3; i++) {
            mSavedOrientationMatrix[mMeasurementCounter][i]=mSavedOrientation[i];
        }
        mMeasurementCounter++;

    }

    private synchronized boolean isPositionReached() {

        mSensorManager.getOrientation(mRotationMatrix, mOrientation);
        for (int j = 0; j < MAX_POSITIONS; j++) {
            int diffCounter = 0;
            for (int i = 1; i < 3; i++) {
                float diff = java.lang.Math.abs(mSavedOrientationMatrix[j][i] - mOrientation[i]);

                //Log.d(TAG, String.format("mSavedOrientationMatrix[%d][%d]= %f",j, i, mSavedOrientationMatrix[j][i]));

                if (diff > EPSILON) {
                    diffCounter++;
                }
            }
            Log.d(TAG, "=======================");
            Log.d(TAG, String.format("diff counter=%d", diffCounter));
            Log.d(TAG, "=======================");

            if ( diffCounter == 0) {
                Log.d(TAG, String.format("mSavedOrientationMatrix[%d][%d]= %f",j, 1, mSavedOrientationMatrix[j][1]));
                Log.d(TAG, String.format("mSavedOrientationMatrix[%d][%d]= %f",j, 2, mSavedOrientationMatrix[j][2]));
                return true;
            }

        }


//        String tm = mSettings.getString("timestamp", "");
//        SharedPreferences.Editor editor = mSettings.edit();
//        editor.putString("timestamp", tm+"\n"+DateFormat.getDateTimeInstance().format(new Date(System.currentTimeMillis())));
//        editor.commit();

        return false;
    }

    public boolean isCalibrate() {
        return mState==CALIBRATING_STATE;
    }
    public boolean isMonitor() {
        return mState==MONITORING_STATE;
    }

    public void setState(int state) {
        this.mState = state;
    }

    public long getMeasurementDuration() {
        return mMeasurementDuration;
    }

    public void setMeasurementDuration(long mMeasurementDuration) {
        this.mMeasurementDuration = mMeasurementDuration*1000;
    }

    public void setVibratorService(Vibrator vibratorService) {
        mVibratorService = vibratorService;
    }
}
