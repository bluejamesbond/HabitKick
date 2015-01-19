package com.habitkick.activity;

import android.view.View;
import android.widget.TextView;

import com.habitkick.R;
import com.habitkick.core.MobileActivity;
import com.habitkick.shared.common.view.HoloCircularProgressBar;
import com.habitkick.shared.common.view.HueShiftImageView;

/**
 * Created by Mathew on 1/15/2015.
 */
public class HomeActivity extends MobileActivity {

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
               // ((HueShiftImageView) findViewById(R.id.logo)).shiftHue(hue);
                ((TextView) findViewById(R.id.wear_button__icon)).setTextColor(appColor);
                ((TextView) findViewById(R.id.auto_text)).setTextColor(appColor);
                ((TextView) findViewById(R.id.progress_label)).setTextColor(appColor);
                ((HoloCircularProgressBar) findViewById(R.id.progress)).setProgressColor(appColor);

                findViewById(R.id.date_button).setBackground(createBigButtonStateList(appColor));
                findViewById(R.id.recalibrate_ok_button).setBackground(createBigButtonStateList(appColor));
                findViewById(R.id.reset_ok_button).setBackground(createBigButtonStateList(appColor));
                ((View)findViewById(R.id.goal_item)).setBackgroundColor(appColor);
            }
        });
    }
}
