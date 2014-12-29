package com.fiftyeightmorris.nailbiter.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;

import com.fiftyeightmorris.nailbiter.IUserInterface;
import com.fiftyeightmorris.nailbiter.R;
import com.fiftyeightmorris.nailbiter.menu.MenuActivity;

public class HomeUI extends IUserInterface {

    public HomeUI(WatchViewStub stub) {
        super(stub);
    }

    @Override
    protected void onThemeChange(final WatchViewStub stub, final int appColor, final float hue) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // ---
                stub.findViewById(R.id.menu_button).setBackground(getBigButtonStateList(stub, appColor));

                // ---
                LayerDrawable layerDrawable = (LayerDrawable) stub.findViewById(R.id.goal_container).getBackground();
                GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.big_button__background_default_backgrounditem);
                gradientDrawable.setColor(appColor);

                // ---
                ((TextView) stub.findViewById(R.id.today_label)).setTextColor(appColor);
                ((TextView) stub.findViewById(R.id.detail_value)).setTextColor(appColor);
            }
        });
    }

    protected void onCreate(final WatchViewStub stub) {

        final Context context = stub.getContext();
        final Intent menuIntent = new Intent(context, MenuActivity.class);

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                stub.findViewById(R.id.lasttime_button).setSelected(true);
                stub.findViewById(R.id.menu_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(menuIntent);
                    }
                });
            }
        });
    }
}
