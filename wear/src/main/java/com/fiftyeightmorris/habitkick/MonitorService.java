/*
 * Copyright (c) 2014. 58 Morris LLC All rights reserved
 */

package com.fiftyeightmorris.habitkick;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

public class MonitorService extends Service {
    private static PositionMonitor mPositionMonitor;
    private Timer timer = new Timer();

    public MonitorService() {
    }

    public static void setPositionMonitor(PositionMonitor positionMonitor) {
        mPositionMonitor = positionMonitor;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {
                    }
                }, 0, 1000);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPositionMonitor.unregisterListeners();
        if (timer != null) {
            timer.cancel();
        }
    }
}
