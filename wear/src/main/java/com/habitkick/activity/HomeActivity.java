package com.habitkick.activity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.habitkick.R;
import com.habitkick.core.WatchActivity;

public class HomeActivity extends WatchActivity {


    @Override
    protected void onCreate(Bundle bundle) {

        final Intent menuIntent = new Intent(this, MenuActivity.class);

        _runOnUiThread(new Runnable() {

            @Override
            public void run() {
                getRootView().findViewById(R.id.lasttime_button).setSelected(true);
                getRootView().findViewById(R.id.menu_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(menuIntent);
                    }
                });
            }
        });
    }

    @Override
    protected void onThemeChange(final int appColor, final float hue) {
        super.onThemeChange(appColor, hue);
        _runOnUiThread(new Runnable() {
            @Override
            public void run() {

                View stub = getRootView();
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

    @Override
    protected int getContentViewId() {
        return R.layout.home_activity;
    }
}
