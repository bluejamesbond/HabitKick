package com.habitkick;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class ListenerService extends WearableListenerService {

    private static final int OPEN_HOME_ACTIVITY = 0xe5c0be69;

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

    private void handleMessage(int id) {
        switch (id) {
            case OPEN_HOME_ACTIVITY: {
                this.startActivity(new Intent(this, HomeActivity.class));
                break;
            }
        }
    }
}
