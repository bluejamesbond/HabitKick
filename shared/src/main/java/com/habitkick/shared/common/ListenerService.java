package com.habitkick.shared.common;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public abstract class ListenerService extends WearableListenerService {

    private Handler mHandler;

    public ListenerService() {
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
