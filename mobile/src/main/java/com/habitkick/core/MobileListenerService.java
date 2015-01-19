package com.habitkick.core;

import android.content.Intent;

import com.habitkick.activity.StartActivity;
import com.habitkick.shared.common.ListenerService;
import com.habitkick.shared.core.MessageConstants;

/**
 * Created by Mathew on 12/30/2014.
 */
public class MobileListenerService extends ListenerService {

    @Override
    protected void handleMessage(int id) {
        switch (id) {
            case MessageConstants.OPEN_HOME_ACTIVITY_ID: {
                Intent intent = new Intent(this, StartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent);
                break;
            }
        }
    }
}
