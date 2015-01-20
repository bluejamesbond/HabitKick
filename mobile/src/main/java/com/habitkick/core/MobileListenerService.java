package com.habitkick.core;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.habitkick.activity.StartActivity;
import com.habitkick.shared.common.ListenerService;
import com.habitkick.shared.core.MessageId;

public class MobileListenerService extends ListenerService {

    @Override
    protected void handleMessage(MessageId id, String msg) {

        if (MobileActivity.DEBUG) {
            Toast.makeText(this, "Received message " + id, Toast.LENGTH_SHORT).show();
            Log.d("Message", "Received message " + id);
        }

        switch (id) {
            case OPEN_HOME_ACTIVITY: {
                Intent intent = new Intent(this, StartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
        }
    }
}
