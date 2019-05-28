package com.yimishiji.widget.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by gsd on 2017/7/13.
 * Copyright © 2017 YIMISHIJI. All rights reserved.
 */

public class DetailScrollView extends ScrollView {

    private float oldX, oldY;

    public void setNeedIntercept(boolean needIntercept) {
        this.needIntercept = needIntercept;
    }

    private boolean needIntercept = true;

    public DetailScrollView(Context context) {
        super(context);
    }

    public DetailScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DetailScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//                float X = ev.getX();
//                float Y = ev.getY();
//                float Ys = Y - oldY;
//                float Xs = X - oldX;
//
//                if (needIntercept) {
//                    if (Math.abs(Ys) <= Math.abs(Xs)) {//滑动为水平方向
//                        return super.onInterceptTouchEvent(ev);
//                    }
//                    return true;
//                } else {
//                    if (Math.abs(Ys) <= Math.abs(Xs)) {//滑动为水平方向
//                        return super.onInterceptTouchEvent(ev);
//                    }
//                    return false;
//                }
//            case MotionEvent.ACTION_DOWN:
//                oldY = ev.getY();
//                oldX = ev.getX();
//                break;
//
//            case MotionEvent.ACTION_UP:
//                break;
//
//            default:
//                break;
//        }
//        return super.onInterceptTouchEvent(ev);
//    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollListener != null)
            mScrollListener.onScroll(l, t, oldl, oldt);
    }

    private OnScrollListener mScrollListener;

    public void setScrollListener(OnScrollListener scrollListener) {
        mScrollListener = scrollListener;
    }

    public interface OnScrollListener {
        void onScroll(int l, int t, int oldl, int oldt);
    }
}
