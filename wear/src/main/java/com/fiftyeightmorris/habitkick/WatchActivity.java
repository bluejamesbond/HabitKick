package com.fiftyeightmorris.habitkick;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.wearable.view.WatchViewStub;

public abstract class WatchActivity<T extends UI> extends Activity {

    private T mUi;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        MessageReceiver messageReceiver = new MessageReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);

        setContentView(getContentViewId());

        // get context
        WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);

        mUi = getUIInstance(stub);
        mUi.create(this);
        mUi.setTheme(UI.Theme.RANDOM);

        onCreate(stub, mUi);
    }

    protected void onCreate(WatchViewStub stub, T ui) {}

    protected void onDestroy() {
        super.onStop();
        mUi.destroy(this);
    }

    protected abstract T getUIInstance(WatchViewStub stub);

    protected abstract int getContentViewId();

    @SuppressWarnings("unused")
    protected void onMessageReceived(int id, String message) {}

    private class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            onMessageReceived(0, message);
        }
    }
}
