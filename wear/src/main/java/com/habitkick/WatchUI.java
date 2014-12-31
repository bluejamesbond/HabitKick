package com.habitkick;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.wearable.view.WatchViewStub;
import android.view.View;

import com.habitkick.shared.UI;
import com.habitkick.shared.Utils;

/**
 * Created by Mathew on 12/30/2014.
 */
public abstract class WatchUI extends UI {

    public WatchUI(WatchViewStub stub) {
        super(stub);
    }

    protected StateListDrawable createBigButtonStateList(final int appColor) {
        return createStateList(appColor, R.drawable.big_button__background_pressed, R.drawable.big_button__background_default, R.id.big_button__background_default_backgrounditem);
    }

    @Override
    protected void onThemeChange(View stub, int appColor, float hue) {
        int bgStartColor = Utils.shiftHue(mRoot.getResources().getColor(com.habitkick.shared.R.color.app__background_startcolor), hue);
        int bgCenterColor = Utils.shiftHue(mRoot.getResources().getColor(com.habitkick.shared.R.color.app__background_centercolor), hue);
        int bgEndColor = Utils.shiftHue(mRoot.getResources().getColor(com.habitkick.shared.R.color.app__background_endcolor), hue);

        final Drawable bgDrawable;

        if (mBackground) {
            float bgCenterX = mRoot.getResources().getFraction(com.habitkick.shared.R.fraction.app__background_centerx, 1, 1);
            float bgCenterY = mRoot.getResources().getFraction(com.habitkick.shared.R.fraction.app__background_centery, 1, 1);
            float bgGradientRadius = mRoot.getResources().getFraction(com.habitkick.shared.R.fraction.app__background_radius, 1, 1) * mMetrics.widthPixels;

            GradientDrawable bgGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{bgStartColor, bgCenterColor, bgEndColor});
            bgGradientDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
            bgGradientDrawable.setGradientRadius(bgGradientRadius);
            bgGradientDrawable.setGradientCenter(bgCenterX, bgCenterY);
            bgDrawable = bgGradientDrawable;
        } else {
            bgDrawable = new ColorDrawable(mRoot.getResources().getColor(com.habitkick.shared.R.color.dark_grey));
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRoot.findViewById(R.id.bg).setBackground(bgDrawable);
            }
        });
    }
}
