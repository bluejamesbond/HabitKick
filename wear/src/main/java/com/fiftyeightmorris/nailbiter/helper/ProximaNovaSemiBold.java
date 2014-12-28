package com.fiftyeightmorris.nailbiter.helper;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Mathew on 12/27/2014.
 */
public class ProximaNovaSemiBold extends ITypefaceTextView {

    public ProximaNovaSemiBold(Context context) {
        super(context);
    }

    public ProximaNovaSemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProximaNovaSemiBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public String getTypefacePath() {
        return "fonts/proxima-nova-semibold.otf";
    }
}
