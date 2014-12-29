package com.fiftyeightmorris.nailbiter.menu;

import android.content.Context;
import android.content.Intent;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;

import com.fiftyeightmorris.nailbiter.IUserInterface;
import com.fiftyeightmorris.nailbiter.R;
import com.fiftyeightmorris.nailbiter.home.HomeActivity;

public class MenuUI extends IUserInterface {
    public MenuUI(WatchViewStub stub) {
        super(stub);
    }

    @Override
    protected void onThemeChange(final WatchViewStub stub, final int appColor, float hue) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // ---
                stub.findViewById(R.id.home_button).setBackground(getBigButtonStateList(stub, appColor));

                // ---
                ((TextView) stub.findViewById(R.id.calibrate_button_icon)).setTextColor(appColor);
                ((TextView) stub.findViewById(R.id.mobile_button_icon)).setTextColor(appColor);
            }
        });
    }

    @Override
    protected void onCreate(final WatchViewStub stub) {

        final Context context = stub.getContext();
        final Intent homeIntent = new Intent(context, HomeActivity.class);

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                stub.findViewById(R.id.home_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        stub.getApp
                    }
                });
            }
        });
    }
}
