package com.yimishiji.widget.photoview.gestures;

import android.view.MotionEvent;

/**
 * Created by gsd on 2017/2/21.
 */

public interface GestureDetector {

    boolean onTouchEvent(MotionEvent ev);

    boolean isScaling();

    boolean isDragging();

    void setOnGestureListener(OnGestureListener listener);

}
