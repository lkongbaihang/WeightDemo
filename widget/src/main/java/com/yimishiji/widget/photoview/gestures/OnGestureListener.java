package com.yimishiji.widget.photoview.gestures;

/**
 * Created by gsd on 2017/2/21.
 */

public interface OnGestureListener {

    void onDrag(float dx, float dy);

    void onFling(float startX, float startY, float velocityX,
                 float velocityY);

    void onScale(float scaleFactor, float focusX, float focusY);

}
