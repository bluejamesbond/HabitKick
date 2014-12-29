package com.fiftyeightmorris.nailbiter.helper;

import android.content.Context;
import android.os.Handler;
import android.support.wearable.view.WatchViewStub;
import android.util.DisplayMetrics;
import android.os.Looper;
import android.view.WindowManager;

import com.fiftyeightmorris.nailbiter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mathew on 12/29/2014.
 */
public class UI {

    protected static DisplayMetrics mMetrics = null;
    private final Handler mHandler;
    private volatile List<Runnable> mPreInflation;
    private volatile boolean mInflated;

    public UI(){
        mInflated = false;
        mHandler = new Handler();
        mPreInflation = new ArrayList<>();
    }


    public void create(final WatchViewStub stub) {

        if(mMetrics == null){
            mMetrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) stub.getContext().getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(mMetrics);
        }

        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
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
