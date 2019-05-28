package com.yimishiji.widget.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * 自定义scrollview设置滚动监听
 */
public class CustomScrollView extends ScrollView {

    /**
     * 记录手指离开屏幕继续滑动的Y的距离
     */
    private int lastScrollY;

    private OnScrollListener mOnScrollListener;

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(l, t, oldl, oldt);
        }
    }

    /**
     * 是否到顶部
     *
     * @return
     */
    public boolean isAtTop() {
        return getScrollY() <= 0;
    }

    /**
     * 是否在底部
     *
     * @return
     */
    public boolean isAtBottom() {
        return getScrollY() == getChildAt(getChildCount() - 1).getBottom() + getPaddingBottom() - getHeight();
    }

    /**
     * 判断呢条目是否在可见范围内
     *
     * @param child
     * @return
     */
    public boolean isChildVisible(View child) {
        if (child == null) {
            return false;
        }
        Rect scrollBounds = new Rect();
        getHitRect(scrollBounds);
        return child.getLocalVisibleRect(scrollBounds);
    }

    //    private Handler mHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
////            super.handleMessage(msg);
//            int scrollY = CustomScrollView.this.getScrollY();
//
//            if (lastScrollY != scrollY) {
//                lastScrollY = scrollY;
//                mHandler.sendMessageDelayed(mHandler.obtainMessage(), 5);
//            }
//
//            if (mOnScrollListener != null) {
//                mOnScrollListener.onScroll(scrollY);
//            }
//        }
//    };
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//
//        if (mOnScrollListener != null) {
//            mOnScrollListener.onScroll(lastScrollY = this.getScrollY());
//        }
//
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_UP:
//                mHandler.sendMessageDelayed(mHandler.obtainMessage(), 5);
//                break;
//        }
//
//        return super.onTouchEvent(ev);
//    }

    /**
     * 设置接口回调
     *
     * @param onScrollListener
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    /**
     * 接口回调
     */
    public interface OnScrollListener {
        void onScroll(int x, int y, int oldX, int oldY);
    }
}
