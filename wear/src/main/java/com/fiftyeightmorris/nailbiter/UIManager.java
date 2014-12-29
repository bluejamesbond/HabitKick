package com.fiftyeightmorris.nailbiter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;
import android.os.Looper;
import android.support.wearable.view.WatchViewStub;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UIManager {

    private final boolean mBackground;
    private DisplayMetrics mMetrics;
    private Handler mHandler;
    private volatile List<Runnable> mPreInflation;
    private volatile boolean mInflated;

    public UIManager(boolean background) {
        mBackground = background;
        mInflated = false;
        mHandler = new Handler();
        mMetrics = new DisplayMetrics();
        mPreInflation = new ArrayList<>();
    }

    public void init(WatchViewStub stub) {
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mInflated = true;
                synchronized (UIManager.this) {
                    for (Runnable action : mPreInflation) {
                        action.run();
                    }
                    mPreInflation.removeAll(mPreInflation);
                    mPreInflation = null;
                }
            }
        });

        WindowManager windowManager = (WindowManager) stub.getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(mMetrics);
    }

    public int rotateHue(int color, float shift) {

        float[] hsv = new float[3];

        Color.RGBToHSV((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF, hsv);
        hsv[0] = (hsv[0] + shift) % 360.0f;

        return Color.HSVToColor((color >> 24) & 0xFF, hsv);
    }

    public final void runOnUiThread(Runnable action) {
        if (mInflated) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                action.run();
            } else {
                mHandler.post(action);
            }
        } else {
            synchronized (this) {
                if (mPreInflation == null) {
                    runOnUiThread(action);
                } else {
                    mPreInflation.add(action);
                }
            }
        }
    }

    public void setHue(final WatchViewStub stub, final float hue) {

        final int themeColor = rotateHue(stub.getResources().getColor(R.color.universal__appcolor), hue);
        final int bgStartColor = rotateHue(stub.getResources().getColor(R.color.app__background_startcolor), hue);
        final int bgCenterColor = rotateHue(stub.getResources().getColor(R.color.app__background_centercolor), hue);
        final int bgEndColor = rotateHue(stub.getResources().getColor(R.color.app__background_endcolor), hue);

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

                layerDrawable = (LayerDrawable) stub.getResources().getDrawable(R.drawable.big_button__background_default);
                gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.big_button__background_default_backgrounditem);
                gradientDrawable.setColor(themeColor);
                stateListDrawable.addState(new int[]{}, layerDrawable);

                layerDrawable = (LayerDrawable) stub.getResources().getDrawable(R.drawable.big_button__background_pressed);
                gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.big_button__background_pressed_backgrounditem);
                gradientDrawable.setColor(themeColor);
                stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, layerDrawable);

                stub.findViewById(R.id.menu_button).setBackground(stateListDrawable);

                // ---
                layerDrawable = (LayerDrawable) stub.findViewById(R.id.goal_container).getBackground();
                gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.big_button__background_default_backgrounditem);
                gradientDrawable.setColor(themeColor);

                // ---
                stub.findViewById(R.id.bg).setBackground(bgDrawable);

                // ---
                ((TextView) stub.findViewById(R.id.today_label)).setTextColor(themeColor);
                ((TextView) stub.findViewById(R.id.detail_value)).setTextColor(themeColor);
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
