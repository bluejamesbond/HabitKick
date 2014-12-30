package com.habitkick;

import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;

import com.google.android.gms.common.api.GoogleApiClient;
import com.habitkick.shared.SocketActivity;

public abstract class WatchActivity<T extends UI> extends SocketActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private T mUi;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get context
        WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);

        mUi = getUIInstance(stub);
        mUi.create(this);
        mUi.setTheme(UI.Theme.RANDOM);

        onCreate(stub, mUi);
    }

    protected void onCreate(WatchViewStub stub, T ui) {
    }

    protected void onDestroy() {
        super.onDestroy();
        mUi.destroy(this);
    }

    protected abstract T getUIInstance(WatchViewStub stub);

}
