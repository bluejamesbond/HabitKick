package com.habitkick.shared;

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
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public abstract class UI {

    public static final int NO_BACKGROUND = -1;
    protected static DisplayMetrics mMetrics = null;
    protected static float mHue;
    private final Handler mHandler;
    private final boolean mBackground;
    private volatile List<Runnable> mPreInflation;
    private volatile boolean mInflated;
    private View mRoot;

    public UI(View view) {
        mHue = 0;
        mRoot = view;
        mInflated = mRoot == null || !(mRoot instanceof WatchViewStub);
        mHandler = new Handler();
        mPreInflation = new ArrayList<>();
        mBackground = view.getResources().getBoolean(R.bool.app_background__enabled);
    }

    public final void destroy(Activity activity) {
        onDestroy(activity, mRoot);
        mRoot = null;
    }

    public abstract void onDestroy(final Activity activity, final View stub);

    public final void setTheme(float theme) {

        mHue = theme;

        int appColor = Utils.shiftHue(mRoot.getResources().getColor(R.color.universal__appcolor), theme);
        int bgStartColor = Utils.shiftHue(mRoot.getResources().getColor(R.color.app__background_startcolor), theme);
        int bgCenterColor = Utils.shiftHue(mRoot.getResources().getColor(R.color.app__background_centercolor), theme);
        int bgEndColor = Utils.shiftHue(mRoot.getResources().getColor(R.color.app__background_endcolor), theme);

        final Drawable bgDrawable;
        final int bgId = getBackgroundId();

        if (mBackground && getBackgroundId() != NO_BACKGROUND) {
            float bgCenterX = mRoot.getResources().getFraction(R.fraction.app__background_centerx, 1, 1);
            float bgCenterY = mRoot.getResources().getFraction(R.fraction.app__background_centery, 1, 1);
            float bgGradientRadius = mRoot.getResources().getFraction(R.fraction.app__background_radius, 1, 1) * mMetrics.widthPixels;

            GradientDrawable bgGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{bgStartColor, bgCenterColor, bgEndColor});
            bgGradientDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
            bgGradientDrawable.setGradientRadius(bgGradientRadius);
            bgGradientDrawable.setGradientCenter(bgCenterX, bgCenterY);
            bgDrawable = bgGradientDrawable;
        } else {
            bgDrawable = new ColorDrawable(mRoot.getResources().getColor(R.color.dark_grey));
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRoot.findViewById(bgId).setBackground(bgDrawable);
            }
        });

        onThemeChange(mRoot, appColor, theme);
    }

    protected abstract int getBackgroundId();

    protected StateListDrawable createBigButtonStateList(final int appColor) {

        StateListDrawable stateListDrawable;
        LayerDrawable layerDrawable;
        GradientDrawable gradientDrawable;

        // ---
        stateListDrawable = new StateListDrawable();

        layerDrawable = (LayerDrawable) mRoot.getResources().getDrawable(R.drawable.big_button__background_pressed);
        gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.big_button__background_pressed_backgrounditem);
        gradientDrawable.setColor(appColor);
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, layerDrawable);

        layerDrawable = (LayerDrawable) mRoot.getResources().getDrawable(R.drawable.big_button__background_default);
        gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.big_button__background_default_backgrounditem);
        gradientDrawable.setColor(appColor);
        stateListDrawable.addState(new int[]{}, layerDrawable);

        return stateListDrawable;
    }

    protected abstract void onThemeChange(final View stub, final int appColor, final float hue);

    protected abstract void onCreate(final SocketActivity activity, final View stub);

    public final void create(final SocketActivity activity) {

        if (mMetrics == null) {
            mMetrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) mRoot.getContext().getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(mMetrics);
        }

        if (mRoot instanceof WatchViewStub) {
            ((WatchViewStub) mRoot).setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
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
        }

        onCreate(activity, mRoot);
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
}
