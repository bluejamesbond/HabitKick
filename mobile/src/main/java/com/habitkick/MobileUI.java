package com.habitkick;

import android.graphics.drawable.StateListDrawable;
import android.view.View;

import com.habitkick.shared.style.UI;

/**
 * Created by Mathew on 12/30/2014.
 */
public abstract class MobileUI extends UI {

    public MobileUI(View v) {
        super(v);
    }

    protected StateListDrawable createBigButtonStateList(final int appColor) {
        return createStateList(appColor, R.drawable.big_button_low_rad__background_pressed, R.drawable.big_button_low_rad__background_default,
                R.id.big_button__background_default_backgrounditem);
    }

    @Override
    protected void onThemeChange(View stub, int appColor, float hue) {
        // do nothing
    }
}
