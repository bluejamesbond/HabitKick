package com.habitkick.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;

import com.habitkick.R;
import com.habitkick.WatchUI;
import com.habitkick.menu.MenuActivity;
import com.habitkick.shared.core.SocketActivity;

public class HomeUI extends WatchUI {

    public HomeUI(WatchViewStub stub) {
        super(stub);
    }

    @Override
    public void onDestroy(Activity activity, View stub) {
    }

    @Override
    protected void onThemeChange(final View stub, final int appColor, final float hue) {
        super.onThemeChange(stub, appColor, hue);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // ---
                stub.findViewById(R.id.menu_button).setBackground(createBigButtonStateList(appColor));

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

    protected void onCreate(final SocketActivity activity, final View stub) {

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
