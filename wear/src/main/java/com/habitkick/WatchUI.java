package com.habitkick;

import android.support.wearable.view.WatchViewStub;

import com.habitkick.shared.UI;

/**
 * Created by Mathew on 12/30/2014.
 */
public abstract class WatchUI extends UI {

    public WatchUI(WatchViewStub stub) {
        super(stub);
    }

    @Override
    protected int getBackgroundId() {
        return R.id.bg;
    }
}
