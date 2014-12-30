package com.habitkick;

import android.content.Intent;

import com.habitkick.shared.ListenerService;

/**
 * Created by Mathew on 12/30/2014.
 */
public class MobileListenerService extends ListenerService {

    @Override
    protected void handleMessage(int id) {
        switch (id) {
            case ListenerService.OPEN_HOME_ACTIVITY: {
                this.startActivity(new Intent(this, HomeActivity.class));
                break;
            }
        }
    }
}
