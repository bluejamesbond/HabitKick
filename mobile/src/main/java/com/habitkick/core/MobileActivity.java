package com.habitkick.core;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.habitkick.R;
import com.habitkick.shared.core.HabitKickActivity;

public abstract class MobileActivity extends HabitKickActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    protected void startActivity(Class act) {
        startActivity(new Intent(this, act));
        finish();
    }

    @Override
    protected void onThemeChange(int appColor, float hue) {
        // none for now
    }

    @Override
    public View getBackgroundView() {
        return findViewById(R.id.bg);
    }
}
