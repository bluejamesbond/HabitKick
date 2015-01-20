package com.habitkick.core;

import android.content.Context;

import com.habitkick.shared.common.Global;
import com.habitkick.shared.common.Utils;
import com.orm.SugarRecord;

import java.util.Date;
import java.util.List;

/**
 * Created by Mathew on 1/19/2015.
 */
public class MonitorLog extends SugarRecord {

    private int alerts;
    private int goal;
    private Date date;

    public MonitorLog(int alerts, int goal) {
        super();
        this.alerts = alerts;
        this.goal = goal;
        this.date = new Date();
    }

    public static MonitorLog getRecent(Context context) {
        List<MonitorLog> entries = MonitorLog.find(MonitorLog.class, null, null, null, "date DESC", "1");

        if (entries.size() == 0 || !Utils.isSameDay(entries.get(0).date, new Date())) {
            MonitorLog monitorLog = new MonitorLog(1, Utils.getStore(context, Global.GOAL_COUNT_STORE_KEY, -1));
            monitorLog.save();
            return monitorLog;
        } else {
            return entries.get(0);
        }
    }

    public Date getDate() {
        return date;
    }

    public void incrementAlerts() {
        alerts++;
    }

    public int getAlerts() {
        return alerts;
    }

    public void setAlerts(int alerts) {
        this.alerts = alerts;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }
}
