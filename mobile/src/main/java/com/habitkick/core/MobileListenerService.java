package com.habitkick.core;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.habitkick.activity.StartActivity;
import com.habitkick.shared.common.Global;
import com.habitkick.shared.common.ListenerService;
import com.habitkick.shared.core.MessageId;

public class MobileListenerService extends ListenerService {

    @Override
    protected void handleMessage(MessageId id, String msg) {

        switch (id) {
            case OPEN_HOME_ACTIVITY: {
                Intent intent = new Intent(this, StartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
            case MONITOR_ALERT: {
                MonitorLog monitorLog = MonitorLog.getRecent(this);
                monitorLog.incrementAlerts();
                monitorLog.save();
            }
        }
    }
}
