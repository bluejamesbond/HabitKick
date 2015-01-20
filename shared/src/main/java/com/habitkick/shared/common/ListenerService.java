package com.habitkick.shared.common;

import android.app.Activity;
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

    protected void sendMessage(MessageId id) {
        getActivity().sendMessage(id);
    }

    protected SocketActivity getActivity(){
        return SocketActivity.getActive(this);
    }

    protected void sendMessage(MessageId id, String msg) {
        getActivity().sendMessage(id, msg);
    }

    public void startActivity(Class actvity){
        Intent intent = new Intent(this, actvity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    protected abstract void handleMessage(MessageId id, String msg);
}
