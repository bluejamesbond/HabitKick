package com.habitkick.core;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;

import com.habitkick.R;
import com.habitkick.shared.common.Utils;
import com.habitkick.shared.core.HabitKickActivity;

public abstract class WatchActivity extends HabitKickActivity {

    protected StateListDrawable createBigButtonStateList(final int appColor) {
        return createStateList(appColor, R.drawable.big_button__background_pressed, R.drawable.big_button__background_default, R.id.big_button__background_default_backgrounditem);
    }

    @Override
    protected void onThemeChange(int appColor, float hue) {

        final View stub = getRootView();

        int bgStartColor = Utils.shiftHue(stub.getResources().getColor(com.habitkick.shared.R.color.app__background_startcolor), hue);
        int bgCenterColor = Utils.shiftHue(stub.getResources().getColor(com.habitkick.shared.R.color.app__background_centercolor), hue);
        int bgEndColor = Utils.shiftHue(stub.getResources().getColor(com.habitkick.shared.R.color.app__background_endcolor), hue);

        final Drawable bgDrawable;

        if (GRADIENT_BACKGROUND_ENABLED) {
            float bgCenterX = stub.getResources().getFraction(com.habitkick.shared.R.fraction.app__background_centerx, 1, 1);
            float bgCenterY = stub.getResources().getFraction(com.habitkick.shared.R.fraction.app__background_centery, 1, 1);
            float bgGradientRadius = stub.getResources().getFraction(com.habitkick.shared.R.fraction.app__background_radius, 1, 1) * mMetrics.widthPixels;

            GradientDrawable bgGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{bgStartColor, bgCenterColor, bgEndColor});
            bgGradientDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
            bgGradientDrawable.setGradientRadius(bgGradientRadius);
            bgGradientDrawable.setGradientCenter(bgCenterX, bgCenterY);
            bgDrawable = bgGradientDrawable;
        } else {
            bgDrawable = new ColorDrawable(stub.getResources().getColor(com.habitkick.shared.R.color.dark_grey));
        }

        _runOnUiThread(new Runnable() {
            @Override
            public void run() {
                stub.findViewById(R.id.bg).setBackground(bgDrawable);
            }
        });
    }

    @Override
    protected View getRootView() {
        return findViewById(R.id.watch_view_stub);
    }
}
