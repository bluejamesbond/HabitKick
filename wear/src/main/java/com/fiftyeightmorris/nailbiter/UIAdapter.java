package com.fiftyeightmorris.nailbiter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.wearable.view.WatchViewStub;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Mathew on 12/28/2014.
 */
public class UIAdapter {

    public int rotateHue(int color, float shift) {

        float[] hsbVals = new float[3];

        Color.RGBToHSV((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF, hsbVals);
        hsbVals[0] = (hsbVals[0] + shift) % 360.0f;

        return Color.HSVToColor((color >> 24) & 0xFF, hsbVals);
    }

    public void setHue(WatchViewStub stub, float hue) {

        DisplayMetrics metrics;
        WindowManager windowManager;

        int themeColor;
        int bgStartColor;
        int bgCenterColor;
        int bgEndColor;

        float bgCenterX;
        float bgCenterY;
        float bgGradientRadius;

        LayerDrawable layerDrawable;
        GradientDrawable gradientDrawable;

        metrics = new DisplayMetrics();
        windowManager = (WindowManager) stub.getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);

        themeColor = rotateHue(stub.getResources().getColor(R.color.theme_color), hue);
        bgStartColor = rotateHue(stub.getResources().getColor(R.color.bg_gradient_startColor), hue);
        bgCenterColor = rotateHue(stub.getResources().getColor(R.color.bg_gradient_centerColor), hue);
        bgEndColor = rotateHue(stub.getResources().getColor(R.color.bg_gradient_endColor), hue);

        layerDrawable = (LayerDrawable) stub.findViewById(R.id.goal_container).getBackground();
        gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.rnd_button_theme_background);
        gradientDrawable.setColor(themeColor);

        layerDrawable = (LayerDrawable) stub.findViewById(R.id.menu_button).getBackground();
        gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.rnd_button_theme_background);
        gradientDrawable.setColor(themeColor);

        bgCenterX = stub.getResources().getFraction(R.fraction.bg_gradient_centerX, 1, 1);
        bgCenterY = stub.getResources().getFraction(R.fraction.bg_gradient_centerY, 1, 1);
        bgGradientRadius = stub.getResources().getFraction(R.fraction.bg_gradient_gradientRadius, 1, 1) * metrics.widthPixels;

        gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{bgStartColor, bgCenterColor, bgEndColor});
        gradientDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        gradientDrawable.setGradientRadius(bgGradientRadius);
        gradientDrawable.setGradientCenter(bgCenterX, bgCenterY);
        (stub.findViewById(R.id.bg)).setBackground(gradientDrawable);

        ((TextView) stub.findViewById(R.id.today_label)).setTextColor(themeColor);
        ((TextView) stub.findViewById(R.id.detail_value)).setTextColor(themeColor);
    }

}
