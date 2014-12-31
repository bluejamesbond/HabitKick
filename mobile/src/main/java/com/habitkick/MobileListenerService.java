package com.habitkick;

import android.content.Intent;

import com.habitkick.home.HomeActivity;
import com.habitkick.shared.core.ListenerService;

/**
 * Created by Mathew on 12/30/2014.
 */
public class MobileListenerService extends ListenerService {

    @Override
    protected void handleMessage(int id) {
        switch (id) {
            case ListenerService.OPEN_HOME_ACTIVITY_ID: {
                Intent intent = new Intent(this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent);
                break;
            }
        }
    }
}
