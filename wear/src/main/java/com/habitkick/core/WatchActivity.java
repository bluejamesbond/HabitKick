package com.habitkick.core;

import android.view.View;

import com.habitkick.R;
import com.habitkick.shared.core.HabitKickActivity;

public abstract class WatchActivity extends HabitKickActivity {

    @Override
    protected void onThemeChange(int appColor, float hue) {
        // ignore
    }

    @Override
    public View getBackgroundView() {
        return getRootView().findViewById(R.id.bg);
    }

    @Override
    protected View getRootView() {
        return findViewById(R.id.watch_view_stub);
    }
}
