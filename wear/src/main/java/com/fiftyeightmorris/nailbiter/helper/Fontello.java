package com.fiftyeightmorris.nailbiter.helper;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Mathew on 12/27/2014.
 */
public class Fontello extends ITypefaceTextView {

    public Fontello(Context context) {
        super(context);
    }

    public Fontello(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Fontello(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public String getTypefacePath() {
        return "fonts/fontello.ttf";
    }
}
