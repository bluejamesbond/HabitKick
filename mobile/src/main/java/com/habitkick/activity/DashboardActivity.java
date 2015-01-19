package com.habitkick.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.habitkick.R;
import com.habitkick.core.MobileActivity;
import com.habitkick.shared.common.view.HoloCircularProgressBar;
import com.habitkick.shared.common.view.HueShiftImageView;

public class DashboardActivity extends MobileActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.home_activity;
    }

    @Override
    protected void onThemeChange(final int appColor, final float hue) {
        super.onThemeChange(appColor, hue);
        _runOnUiThread(new Runnable() {
            @Override
            public void run() {
                
                ((TextView) findViewById(R.id.wear_button__icon)).setTextColor(appColor);
                ((TextView) findViewById(R.id.avg_text)).setTextColor(appColor);
                ((TextView) findViewById(R.id.progress_label)).setTextColor(appColor);
                ((HoloCircularProgressBar) findViewById(R.id.progress)).setProgressColor(appColor);

                findViewById(R.id.date_button).setBackground(createBigButtonStateList(appColor));
                findViewById(R.id.recalibrate_ok_button).setBackground(createBigButtonStateList(appColor));
                findViewById(R.id.reset_ok_button).setBackground(createBigButtonStateList(appColor));
                findViewById(R.id.goal_item).setBackgroundColor(appColor);
            }
        });
    }
}
