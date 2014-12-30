/*
 * Copyright (c) 2014. 58 Morris LLC All rights reserved
 */

package com.habitkick;

import java.util.EventListener;

/**
 * Created by dmitriyblok on 10/25/14.
 */
public interface IMonitorEventListener extends EventListener {
    public void onMonitorAlert();
}
