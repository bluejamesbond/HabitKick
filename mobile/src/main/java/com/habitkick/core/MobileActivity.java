package com.habitkick.core;

import android.graphics.drawable.StateListDrawable;

import com.habitkick.R;
import com.habitkick.shared.core.HabitKickActivity;

public abstract  class MobileActivity extends HabitKickActivity {
    protected StateListDrawable createBigButtonStateList(final int appColor) {
        return createStateList(appColor, R.drawable.big_button_low_rad__background_pressed, R.drawable.big_button_low_rad__background_default,
                R.id.big_button__background_default_backgrounditem);
    }

    @Override
    protected void onThemeChange(int appColor, float hue) {
        // none for now
    }
}
