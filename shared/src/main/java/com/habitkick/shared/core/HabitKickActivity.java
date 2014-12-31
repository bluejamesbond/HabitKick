package com.habitkick.shared.core;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.habitkick.shared.R;
import com.habitkick.shared.common.SocketActivity;
import com.habitkick.shared.common.Utils;

import java.util.ArrayList;
import java.util.List;

public abstract class HabitKickActivity extends SocketActivity {

    protected final static boolean GRADIENT_BACKGROUND_ENABLED = true;
    protected static DisplayMetrics mMetrics = null;
    protected static float mHue = 0;

    private View mRoot;
    private volatile List<Runnable> mPreInflation;
    private volatile boolean mInflated = false;

    protected abstract void onThemeChange(final int appColor, final float hue);

    public final void onDestroy() {
        super.onDestroy();
        mRoot = null;
        mPreInflation.removeAll(mPreInflation);
        mPreInflation = null;
    }

    protected View getRootView() {
        return findViewById(android.R.id.content);
    }

    public final void setTheme(float theme) {
        mHue = theme;
        onThemeChange(Utils.shiftHue(getRootView().getResources().getColor(R.color.universal__appcolor), theme), theme);
    }

    protected StateListDrawable createStateList(final int appColor, final int pressed, final int def, final int presid, final int defid) {

        StateListDrawable stateListDrawable;
        LayerDrawable layerDrawable;
        GradientDrawable gradientDrawable;

        // ---
        stateListDrawable = new StateListDrawable();

        layerDrawable = (LayerDrawable) getResources().getDrawable(pressed);
        gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(presid);
        gradientDrawable.setColor(appColor);
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_selected}, layerDrawable);

        layerDrawable = (LayerDrawable) getResources().getDrawable(def);
        gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(defid);
        gradientDrawable.setColor(appColor);
        stateListDrawable.addState(new int[]{}, layerDrawable);

        return stateListDrawable;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        if (mMetrics == null) {
            mMetrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(mMetrics);
        }

        mPreInflation = new ArrayList<>();
        mRoot = getRootView();
        mInflated = !(mRoot instanceof WatchViewStub);

        if (mRoot instanceof WatchViewStub) {
            ((WatchViewStub) mRoot).setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
                @Override
                public void onLayoutInflated(WatchViewStub stub) {
                    mInflated = true;
                    synchronized (HabitKickActivity.this) {
                        for (Runnable action : mPreInflation) {
                            action.run();
                        }
                        mPreInflation.removeAll(mPreInflation);
                        mPreInflation = null;
                    }
                }
            });
        }

        setTheme(Theme.RANDOM);
    }

    public void _runOnUiThread(Runnable action) {
        if (mInflated) {
            runOnUiThread(action);
        } else {
            synchronized (this) {
                if (mPreInflation == null) {
                    _runOnUiThread(action);
                } else {
                    mPreInflation.add(action);
                }
            }
        }
    }
}
