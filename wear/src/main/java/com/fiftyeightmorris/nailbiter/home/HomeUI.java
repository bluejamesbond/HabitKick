package com.fiftyeightmorris.nailbiter.home;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

import com.fiftyeightmorris.nailbiter.R;
import com.fiftyeightmorris.nailbiter.helper.UI;
import com.fiftyeightmorris.nailbiter.helper.Utils;

public class HomeUI extends UI {

    public static final boolean BACKGROUND_ENABLED = true;
    private static final HomeUI homeUI = new HomeUI(BACKGROUND_ENABLED);

    private final boolean mBackground;

    private HomeUI(boolean background) {
        mBackground = background;
    }

    public static HomeUI getInstance() {
        return homeUI;
    }

    public void create(final WatchViewStub stub) {
        super.create(stub);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                stub.findViewById(R.id.lasttime_button).setSelected(true);
            }
        });
    }

    public void setHue(final WatchViewStub stub, final float hue) {

        final int appColor = Utils.shiftHue(stub.getResources().getColor(R.color.universal__appcolor), hue);
        final int bgStartColor = Utils.shiftHue(stub.getResources().getColor(R.color.app__background_startcolor), hue);
        final int bgCenterColor = Utils.shiftHue(stub.getResources().getColor(R.color.app__background_centercolor), hue);
        final int bgEndColor = Utils.shiftHue(stub.getResources().getColor(R.color.app__background_endcolor), hue);

        final Drawable bgDrawable;

        if (mBackground) {

            final float bgCenterX = stub.getResources().getFraction(R.fraction.app__background_centerx, 1, 1);
            final float bgCenterY = stub.getResources().getFraction(R.fraction.app__background_centery, 1, 1);
            final float bgGradientRadius = stub.getResources().getFraction(R.fraction.app__background_radius, 1, 1) * mMetrics.widthPixels;

            GradientDrawable bgGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{bgStartColor, bgCenterColor, bgEndColor});
            bgGradientDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
            bgGradientDrawable.setGradientRadius(bgGradientRadius);
            bgGradientDrawable.setGradientCenter(bgCenterX, bgCenterY);
            bgDrawable = bgGradientDrawable;
        } else {
            bgDrawable = new ColorDrawable(stub.getResources().getColor(R.color.dark_grey));
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                StateListDrawable stateListDrawable;
                LayerDrawable layerDrawable;
                GradientDrawable gradientDrawable;

                // ---
                stateListDrawable = new StateListDrawable();

                layerDrawable = (LayerDrawable) stub.getResources().getDrawable(R.drawable.big_button__background_pressed);
                gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.big_button__background_pressed_backgrounditem);
                gradientDrawable.setColor(appColor);
                stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, layerDrawable);

                layerDrawable = (LayerDrawable) stub.getResources().getDrawable(R.drawable.big_button__background_default);
                gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.big_button__background_default_backgrounditem);
                gradientDrawable.setColor(appColor);
                stateListDrawable.addState(new int[]{}, layerDrawable);

                stub.findViewById(R.id.menu_button).setBackground(stateListDrawable);

                // ---
                layerDrawable = (LayerDrawable) stub.findViewById(R.id.goal_container).getBackground();
                gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.big_button__background_default_backgrounditem);
                gradientDrawable.setColor(appColor);

                // ---
                stub.findViewById(R.id.bg).setBackground(bgDrawable);

                // ---
                ((TextView) stub.findViewById(R.id.today_label)).setTextColor(appColor);
                ((TextView) stub.findViewById(R.id.detail_value)).setTextColor(appColor);
            }
        });
    }

    @SuppressWarnings("unused")
    public static class Theme {
        public static final float RED = 160f;
        public static final float YELLOW = 230f;
        public static final float ORANGE = 210f;
        public static final float GREEN = 270f;
        public static final float CYAN = 0f;
        public static final float BLUE = 12f;
        public static final float PURPLE = 100f;
        public static final float PINK = 120f;
        public static final float RANDOM = (float) (Math.random() * 360f);
    }

}
