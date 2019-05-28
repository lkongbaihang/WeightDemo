package com.yimishiji.widget.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ViewFlipper;

import java.util.List;

/**
 * Created by gsd on 2017/7/26.
 * Copyright © 2017 YIMISHIJI. All rights reserved.
 */

public class CustomViewFilter extends ViewFlipper {
    public CustomViewFilter(Context context) {
        super(context);
    }

    public CustomViewFilter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setViews(List<View> views) {

        if (views == null || views.size() == 0) return;
        removeAllViews();
        for (View view : views) {
            addView(view);
        }
        if (views.size() > 1) startFlipping();
    }
    public interface OnFilterListener {
//        void onShowP
    }


}
