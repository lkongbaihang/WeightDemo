package com.yimishiji.widget.custom;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by gsd on 2017/2/23.
 */

public class CustomPopupWindow extends PopupWindow {

    private Context mContext;

    public CustomPopupWindow(Context context) {
        this.mContext = context;
    }

    /**
     * 调整窗口的透明度
     *
     * @param from>=0&&from<=1.0f
     * @param to>=0&&to<=1.0f
     */
    private void dimBackground(final float from, final float to) {

        final Window window = ((Activity) mContext).getWindow();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                WindowManager.LayoutParams params = window.getAttributes();
                params.alpha = (Float) animation.getAnimatedValue();
                window.setAttributes(params);
            }
        });

        valueAnimator.start();
    }

}
