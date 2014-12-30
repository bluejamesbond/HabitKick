package com.habitkick.helper;

import android.graphics.Color;

public class Utils {

    @SuppressWarnings("unused")
    public static int shiftHue(int color, float shift) {

        float[] hsv = new float[3];

        Color.RGBToHSV((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF, hsv);
        hsv[0] = (hsv[0] + shift) % 360f;

        return Color.HSVToColor((color >> 24) & 0xFF, hsv);
    }

    @SuppressWarnings("unused")
    public static int contrast(int color, double contrast) {

        contrast = Math.pow((100 + contrast) / 100, 2);

        int A = (color >> 24) & 0xFF;
        int R = (color >> 16) & 0xFF;
        int G = (color >> 8) & 0xFF;
        int B = color & 0xFF;

        R = (int) (((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
        if (R < 0) {
            R = 0;
        } else if (R > 255) {
            R = 255;
        }

        G = (int) (((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
        if (G < 0) {
            G = 0;
        } else if (G > 255) {
            G = 255;
        }

        B = (int) (((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
        if (B < 0) {
            B = 0;
        } else if (B > 255) {
            B = 255;
        }

        return Color.argb(A, R, G, B);
    }

}
