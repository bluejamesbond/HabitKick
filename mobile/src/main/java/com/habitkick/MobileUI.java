package com.habitkick;

import android.view.View;

import com.habitkick.shared.UI;

/**
 * Created by Mathew on 12/30/2014.
 */
public abstract class MobileUI extends UI {

    public MobileUI(View v) {
        super(v);
    }

    @Override
    protected int getBackgroundId() {
        return R.id.bg;
    }
}
