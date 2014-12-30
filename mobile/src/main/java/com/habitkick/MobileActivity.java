package com.habitkick;

import android.os.Bundle;

import com.habitkick.shared.SocketActivity;

public abstract class MobileActivity extends SocketActivity {

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreate();
    }

    protected void onCreate() {
    }

}
