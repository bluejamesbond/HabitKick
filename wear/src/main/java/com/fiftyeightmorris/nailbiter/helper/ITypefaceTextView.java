package com.fiftyeightmorris.nailbiter.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Mathew on 12/27/2014.
 */
public abstract  class ITypefaceTextView extends TextView {
    public ITypefaceTextView(Context context) {
        super(context);
        setTypeface(Typeface.createFromAsset(context.getAssets(), getTypefacePath()));
    }

    public ITypefaceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(Typeface.createFromAsset(context.getAssets(), getTypefacePath()));
    }

    public ITypefaceTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(Typeface.createFromAsset(context.getAssets(), getTypefacePath()));
    }

    public abstract String getTypefacePath();
}
