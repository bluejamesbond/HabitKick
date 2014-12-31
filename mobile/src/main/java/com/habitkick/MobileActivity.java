package com.habitkick;

import android.os.Bundle;

import com.habitkick.shared.core.SocketActivity;
import com.habitkick.shared.style.Theme;

public abstract class MobileActivity<T extends MobileUI> extends SocketActivity {

    private T mUi;

    protected T getUI() {
        return mUi;
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUi = getUIInstance();
        mUi.create(this);
        mUi.setTheme(Theme.RANDOM);

        onCreate();
    }

    protected void onCreate() {
    }


    protected abstract T getUIInstance();
}
