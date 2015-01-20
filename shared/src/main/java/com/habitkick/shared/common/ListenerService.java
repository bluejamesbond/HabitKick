package com.habitkick.shared.common;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;
import com.habitkick.shared.core.MessageId;

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
            final String raw = new String(messageEvent.getData());
            int separator = raw.indexOf('|');
            final MessageId id = MessageId.valueOf(raw.substring(0, separator));
            final String msg = raw.substring(separator);

            handleMessage(id, msg);

            Intent messageIntent = new Intent();
            messageIntent.setAction(Intent.ACTION_SEND);
            messageIntent.putExtra("id", id.name());
            messageIntent.putExtra("message", msg);
            LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);
        } else {
            super.onMessageReceived(messageEvent);
        }
    }

    protected void sendMessage(MessageId id){
        SocketActivity.getActive(this).sendMessage(id);
    }

    protected void sendMessage(MessageId id, String msg){
        SocketActivity.getActive(this).sendMessage(id, msg);
    }

    protected abstract void handleMessage(MessageId id, String msg);
}
