package com.fiftyeightmorris.nailbiter.helper;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Mathew on 12/27/2014.
 */
public class ProximaNovaBold extends ITypefaceTextView {

    public ProximaNovaBold(Context context) {
        super(context);
    }

    public ProximaNovaBold(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProximaNovaBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public String getTypefacePath() {
        return "fonts/proxima-nova-bold.otf";
    }
}
