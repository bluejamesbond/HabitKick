package com.habitkick.shared.style;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;
import android.os.Looper;
import android.support.wearable.view.WatchViewStub;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.habitkick.shared.R;
import com.habitkick.shared.Utils;
import com.habitkick.shared.core.SocketActivity;

import java.util.ArrayList;
import java.util.List;

public abstract class UI {

    protected static DisplayMetrics mMetrics = null;
    protected static float mHue;
    protected final boolean mBackground;
    private final Handler mHandler;
    protected View mRoot;
    private volatile List<Runnable> mPreInflation;
    private volatile boolean mInflated;

    public UI(View view) {
        mHue = 0;
        mRoot = view;
        mInflated = !(mRoot instanceof WatchViewStub);
        mHandler = new Handler();
        mPreInflation = new ArrayList<>();
        mBackground = view.getResources().getBoolean(R.bool.app_background__enabled);
    }

    public void destroy(Activity activity) {
        onDestroy(activity, mRoot);
        mRoot = null;
    }

    protected View getRoot() {
        return mRoot;
    }


    public final void setTheme(float theme) {

        mHue = theme;

        int appColor = Utils.shiftHue(mRoot.getResources().getColor(R.color.universal__appcolor), theme);

        onThemeChange(mRoot, appColor, theme);
    }

    protected StateListDrawable createStateList(final int appColor, final int pressed, final int def, final int backgroundId) {

        StateListDrawable stateListDrawable;
        LayerDrawable layerDrawable;
        GradientDrawable gradientDrawable;

        // ---
        stateListDrawable = new StateListDrawable();

        layerDrawable = (LayerDrawable) mRoot.getResources().getDrawable(pressed);
        gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(backgroundId);
        gradientDrawable.setColor(appColor);
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_selected}, layerDrawable);

        layerDrawable = (LayerDrawable) mRoot.getResources().getDrawable(def);
        gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(backgroundId);
        gradientDrawable.setColor(appColor);
        stateListDrawable.addState(new int[]{}, layerDrawable);

        return stateListDrawable;
    }

    public abstract void onDestroy(final Activity activity, final View stub);

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
