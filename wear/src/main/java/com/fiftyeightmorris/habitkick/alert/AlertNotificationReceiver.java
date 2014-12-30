/*
 * Copyright (c) 2014. 58 Morris LLC All rights reserved
 */

package com.fiftyeightmorris.habitkick.alert;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.fiftyeightmorris.habitkick.R;

public class AlertNotificationReceiver extends BroadcastReceiver {
    public static final String CONTENT_KEY = "contentText";

    public AlertNotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent displayIntent = new Intent(context, AlertActivity.class);
        String text = intent.getStringExtra(CONTENT_KEY);
        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(text)
                .extend(new Notification.WearableExtender()
                        .setDisplayIntent(PendingIntent.getActivity(context, 0, displayIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT)))
                .build();
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(0, notification);
    }
}