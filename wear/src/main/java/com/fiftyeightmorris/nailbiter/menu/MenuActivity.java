package com.fiftyeightmorris.nailbiter.menu;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;

import com.fiftyeightmorris.nailbiter.IUserInterface;
import com.fiftyeightmorris.nailbiter.R;


public class MenuActivity extends Activity {

    private MenuUI ui;

    @Override
    public void onAttachedToWindow() {
        getWindow().setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.menu_activity);

        // get context
        WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);

        // ui communication
        ui = new MenuUI(stub);
        ui.create(stub);
        ui.setTheme(stub, IUserInterface.Theme.RANDOM);
    }
}
