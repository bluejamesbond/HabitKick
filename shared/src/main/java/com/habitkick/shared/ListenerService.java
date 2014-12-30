package com.habitkick.shared;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public abstract class ListenerService extends WearableListenerService {

    public static final int OPEN_HOME_ACTIVITY = 0xe5c0be69;

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

        if (messageEvent.getPath().equals("/message_path")) {
            final String message = new String(messageEvent.getData());
            final int id = message.hashCode();

            handleMessage(id);

            Intent messageIntent = new Intent();
            messageIntent.setAction(Intent.ACTION_SEND);
            messageIntent.putExtra("message", message);
            messageIntent.putExtra("id", id);
            LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);
        } else {
            super.onMessageReceived(messageEvent);
        }
    }

    protected abstract void handleMessage(int id);
}
