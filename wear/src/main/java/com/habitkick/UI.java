package com.habitkick;

import android.app.Activity;
import android.content.Context;
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

import com.habitkick.helper.Utils;

import java.util.ArrayList;
import java.util.List;

public abstract class UI {

    protected static DisplayMetrics mMetrics = null;
    protected static float mHue;
    private final Handler mHandler;
    private final boolean mBackground;
    private volatile List<Runnable> mPreInflation;
    private volatile boolean mInflated;
    private WatchViewStub mStub;

    public UI(WatchViewStub stub) {
        mHue = 0;
        mStub = stub;
        mInflated = false;
        mHandler = new Handler();
        mPreInflation = new ArrayList<>();
        mBackground = stub.getResources().getBoolean(R.bool.app_background__enabled);
    }

    public final void destroy(Activity activity) {
        onDestroy(activity, mStub);
        mStub = null;
    }

    public abstract void onDestroy(final Activity activity, final WatchViewStub stub);

    public final void setTheme(float theme) {

        mHue = theme;

        int appColor = Utils.shiftHue(mStub.getResources().getColor(R.color.universal__appcolor), theme);
        int bgStartColor = Utils.shiftHue(mStub.getResources().getColor(R.color.app__background_startcolor), theme);
        int bgCenterColor = Utils.shiftHue(mStub.getResources().getColor(R.color.app__background_centercolor), theme);
        int bgEndColor = Utils.shiftHue(mStub.getResources().getColor(R.color.app__background_endcolor), theme);

        final Drawable bgDrawable;

        if (mBackground) {
            float bgCenterX = mStub.getResources().getFraction(R.fraction.app__background_centerx, 1, 1);
            float bgCenterY = mStub.getResources().getFraction(R.fraction.app__background_centery, 1, 1);
            float bgGradientRadius = mStub.getResources().getFraction(R.fraction.app__background_radius, 1, 1) * mMetrics.widthPixels;

            GradientDrawable bgGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{bgStartColor, bgCenterColor, bgEndColor});
            bgGradientDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
            bgGradientDrawable.setGradientRadius(bgGradientRadius);
            bgGradientDrawable.setGradientCenter(bgCenterX, bgCenterY);
            bgDrawable = bgGradientDrawable;
        } else {
            bgDrawable = new ColorDrawable(mStub.getResources().getColor(R.color.dark_grey));
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mStub.findViewById(R.id.bg).setBackground(bgDrawable);
            }
        });

        onThemeChange(mStub, appColor, theme);
    }

    protected StateListDrawable createBigButtonStateList(final int appColor) {

        StateListDrawable stateListDrawable;
        LayerDrawable layerDrawable;
        GradientDrawable gradientDrawable;

        // ---
        stateListDrawable = new StateListDrawable();

        layerDrawable = (LayerDrawable) mStub.getResources().getDrawable(R.drawable.big_button__background_pressed);
        gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.big_button__background_pressed_backgrounditem);
        gradientDrawable.setColor(appColor);
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, layerDrawable);

        layerDrawable = (LayerDrawable) mStub.getResources().getDrawable(R.drawable.big_button__background_default);
        gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.big_button__background_default_backgrounditem);
        gradientDrawable.setColor(appColor);
        stateListDrawable.addState(new int[]{}, layerDrawable);

        return stateListDrawable;
    }

    protected abstract void onThemeChange(final WatchViewStub stub, final int appColor, final float hue);

    protected abstract void onCreate(final Activity activity, final WatchViewStub stub);

    public final void create(final Activity activity) {

        if (mMetrics == null) {
            mMetrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) mStub.getContext().getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(mMetrics);
        }

        mStub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mInflated = true;
                synchronized (UI.this) {
                    for (Runnable action : mPreInflation) {
                        action.run();
                    }
                    mPreInflation.removeAll(mPreInflation);
                    mPreInflation = null;
                }
            }
        });

        onCreate(activity, mStub);
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
