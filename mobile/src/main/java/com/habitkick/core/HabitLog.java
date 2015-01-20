package com.habitkick.core;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by Mathew on 1/19/2015.
 */
public class HabitLog extends SugarRecord {

    private int touches;
    private int goal;
    private Date date;

    public HabitLog(int touches, int goal) {
        super();
        this.touches = touches;
        this.goal = goal;
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTouches() {
        return touches;
    }

    public void setTouches(int touches) {
        this.touches = touches;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }
}
