package com.yimishiji.widget.custom;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 自适应高度viewpager 高度取子view最高的
 * Created by gsd on 2017/8/28.
 * Copyright © 2017 YIMISHIJI. All rights reserved.
 */

public class WrapHeightViewPager extends ViewPager {

    //能否左右滑动
    private boolean isPagingEnabled = true;

    public WrapHeightViewPager(Context context) {
        super(context);
    }

    public WrapHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //高度自适应,viewpager高度为子view中的最大高度
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childSize = getChildCount();
        int maxHeight = 0;
        for (int i = 0; i < childSize; i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            if (child.getMeasuredHeight() > maxHeight) {
                maxHeight = child.getMeasuredHeight();
            }
        }

        if (maxHeight > 0) {
            setMeasuredDimension(getMeasuredWidth(), maxHeight);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            return this.isPagingEnabled && super.onTouchEvent(event);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return this.isPagingEnabled && false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        try {
            return this.isPagingEnabled && super.onInterceptTouchEvent(event);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return this.isPagingEnabled && false;
    }

    /**
     * 设置viewpager能否滑动
     *
     * @param b
     */
    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }
}
