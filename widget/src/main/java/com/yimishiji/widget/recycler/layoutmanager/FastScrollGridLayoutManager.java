package com.yimishiji.widget.recycler.layoutmanager;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

/**
 * Created by gsd on 2017/3/29.
 */

public class FastScrollGridLayoutManager extends GridLayoutManager {


    public FastScrollGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {

        LinearSmoothScroller smoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
            @Nullable
            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return FastScrollGridLayoutManager.this.computeScrollVectorForPosition(targetPosition);
            }

            /**
             * 该方法控制速度。
             * if returned value is 2 ms, it means scrolling 1000 pixels with LinearInterpolation should take 2 seconds.
             * @param displayMetrics
             * @return
             */
            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {

                return super.calculateSpeedPerPixel(displayMetrics);
            }

            /**
             * 该方法计算滑动所需时间。在此处间接控制速度。
             * Calculates the time it should take to scroll the given distance (in pixels)
             * @param dx
             * @return
             */
            @Override
            protected int calculateTimeForScrolling(int dx) {
                //间接计算时提高速度，也可以直接在calculateSpeedPerPixel提高
                if (dx > 1000) {
                    dx = 1000;
                }
                int time = super.calculateTimeForScrolling(dx);
//                System.out.println("滑动用时" + time);//打印时间看下
                return time;
            }
        };

        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
//        super.smoothScrollToPosition(recyclerView, state, position);
    }
}
