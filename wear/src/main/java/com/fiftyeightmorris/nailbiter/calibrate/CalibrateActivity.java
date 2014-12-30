package com.fiftyeightmorris.nailbiter.calibrate;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;

import com.fiftyeightmorris.nailbiter.IUserInterface;
import com.fiftyeightmorris.nailbiter.R;
import com.fiftyeightmorris.nailbiter.menu.MenuUI;


public class CalibrateActivity extends Activity {

    private CalibrateUI ui;

    @Override
    public void onAttachedToWindow() {
        getWindow().setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.calibrate_activity);

        // get context
        WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);

        // ui communication
        ui = new CalibrateUI(stub);
        ui.create(this, stub);
        ui.setTheme(stub, IUserInterface.Theme.RANDOM);
    }
}
