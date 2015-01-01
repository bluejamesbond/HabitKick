package com.habitkick.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.habitkick.R;
import com.habitkick.core.MobileActivity;
import com.habitkick.shared.common.view.HueShiftImageView;
import com.habitkick.shared.core.MessageConstants;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends MobileActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        sendMessage("App opened");

        _runOnUiThread(new Runnable() {
            @Override
            public void run() {

                findViewById(R.id.calibrate_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendMessage(MessageConstants.OPEN_CALIBRATION_MSG);
                        sendMessage(MessageConstants.START_CALIBRATION_SERVICE_MSG);
                    }
                });

//                findViewById(R.id.calibrate_next_button).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        sendMessage(MessageConstants.NEXT_CALIBRATION_POSITION_MSG);
//                        setNextPositionEnabled(HomeActivity.this, false);
//                    }
//                });
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.home_activity;
    }

    @Override
    protected void onMessageReceived(int id, String message) {
        switch (id) {
            case MessageConstants.STORED_CALIBRATION_POSITION_ID: {
                setNextPositionEnabled(this, true);
            }
        }
    }


    @Override
    protected void onThemeChange(final int appColor, final float hue) {
        super.onThemeChange(appColor, hue);
        _runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView)findViewById(R.id.tagline)).setTextColor(appColor);
                ((HueShiftImageView)findViewById(R.id.logo)).shiftHue(hue);
                findViewById(R.id.calibrate_button).setBackground(createBigButtonStateList(appColor));
            }
        });
    }

    public void setNextPositionEnabled(final Activity activity, final boolean enable) {
        //    activity.findViewById(R.id.calibrate_next_button).setEnabled(enable)
    }
}
