package com.yimishiji.widget.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by gsd on 2017/7/13.
 * Copyright © 2017 YIMISHIJI. All rights reserved.
 */

public class ItemChildRecyclerView extends RecyclerView {

    private float oldX, oldY;

    public ItemChildRecyclerView(Context context) {
        super(context);
    }

    public ItemChildRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemChildRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float X = ev.getX();
                float Y = ev.getY();
                float Ys = Y - oldY;
                float Xs = X - oldX;

                if (Math.abs(Ys) >= Math.abs(Xs)) {//滑动为竖直方向
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;

            case MotionEvent.ACTION_DOWN:
                getParent().getParent().requestDisallowInterceptTouchEvent(true);
                oldY = ev.getY();
                oldX = ev.getX();
                break;

            case MotionEvent.ACTION_UP:
                getParent().getParent().requestDisallowInterceptTouchEvent(true);
                break;

            default:
                break;
        }
        return super.onTouchEvent(ev);
    }
}
