package com.habitkick.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.habitkick.R;
import com.habitkick.core.HabitLog;
import com.habitkick.core.MobileActivity;
import com.habitkick.shared.common.view.HoloCircularProgressBar;

import java.util.Iterator;
import java.util.List;

public class DashboardActivity extends MobileActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        updateAverage();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.dashboard_activity;
    }

    public void setGoal(int goal){
        ((TextView) findViewById(R.id.goal_value)).setText(Integer.toString(goal));
    }

    public int getGoal(){
        try {
            return Integer.parseInt(((TextView) findViewById(R.id.goal_value)).getText().toString());
        } catch (NumberFormatException e){
            return 0;
        }
    }

    public int updateAverage(){
        new AsyncTask<Void, Void, Integer>(){

            @Override
            protected Integer doInBackground(Void... params) {
                Integer total = 0;
                Integer count = 0;
                Iterator<HabitLog> habitLogList = HabitLog.findAll(HabitLog.class);

                while (habitLogList.hasNext()){
                    total += habitLogList.next().getTouches();
                    count ++;
                }

                try {
                    return total / count;
                } catch (ArithmeticException e) {
                    return 0;
                }
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                setAverage(integer);
            }
        };

        return 0;
    }

    public void setProgress(int touches, int goal){
        ((TextView) findViewById(R.id.progress_value)).setText(Integer.toString(touches));
        ((HoloCircularProgressBar) findViewById(R.id.progress)).setProgress((float) touches / (float) goal);
    }

    public void setAverage(int avg){
        ((TextView) findViewById(R.id.goal_value)).setText(Integer.toString(avg));
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
