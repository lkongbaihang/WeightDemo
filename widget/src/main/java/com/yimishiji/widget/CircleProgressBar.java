package com.yimishiji.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.yimishiji.app.widget.R;

/**
 * Created by gsd on 2018/3/19.
 * Copyright © 2018 YIMISHIJI. All rights reserved.
 */

public class CircleProgressBar extends View {

    /**
     * 进度颜色
     */
    private int progressColor = Color.BLACK;
    /**
     * 进度背景色
     */
    private int bgColor = Color.TRANSPARENT;
    /**
     * 圆圈宽度
     */
    private float circleWidth = 2;
    /**
     * 进度条类型。
     */
    private ProgressType mProgressType = ProgressType.COUNT;
    /**
     * View的显示区域。
     */
    final Rect bounds = new Rect();
    /**
     * 进度条的矩形区域。
     */
    private RectF mArcRect = new RectF();
    /**
     * 画笔
     */
    private Paint mPaint = new Paint();
    /**
     * 进度。
     */
    private int mProgress = 0;
    /**
     * 进度倒计时时间。
     */
    private long timeMillis = 800;
    /**
     * 进度条通知。
     */
    private OnProgressChangeListener mCountdownProgressListener;

    public CircleProgressBar(Context context) {
        super(context);
        init(context, null);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        progressColor = typedArray.getColor(R.styleable.CircleProgressBar_circle_progress_color, progressColor);
        bgColor = typedArray.getColor(R.styleable.CircleProgressBar_circle_bg_color, bgColor);
        circleWidth = typedArray.getDimension(R.styleable.CircleProgressBar_circle_width, circleWidth);

        typedArray.recycle();
    }

    /**
     * 设置进度条颜色
     *
     * @param progressColor
     */
    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
        invalidate();
    }

    /**
     * 设置进度条背景色
     *
     * @param bgColor
     */
    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
        invalidate();
    }

    /**
     * 设置进度圆圈宽度
     *
     * @param circleWidth
     */
    public void setCircleWidth(int circleWidth) {
        this.circleWidth = circleWidth;
        invalidate();
    }

    /**
     * 设置倒计时总时间。
     *
     * @param timeMillis 毫秒。
     */
    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
        invalidate();
    }

    /**
     * 拿到进度条计时时间。
     *
     * @return 毫秒。
     */
    public long getTimeMillis() {
        return this.timeMillis;
    }

    /**
     * 设置进度。
     *
     * @param progress 进度。
     */
    public void setProgress(int progress) {
        this.mProgress = validateProgress(progress);
        invalidate();
    }

    /**
     * 验证进度。
     *
     * @param progress 你要验证的进度值。
     * @return 返回真正的进度值。
     */
    private int validateProgress(int progress) {
        if (progress > 100)
            progress = 100;
        else if (progress < 0)
            progress = 0;
        return progress;
    }

    /**
     * 拿到此时的进度。
     *
     * @return 进度值，最大100，最小0。
     */
    public int getProgress() {
        return mProgress;
    }

    /**
     * 设置进度监听。
     *
     * @param mCountdownProgressListener 监听器。
     */
    public void setOnProgressChangeListener(OnProgressChangeListener mCountdownProgressListener) {
        this.mCountdownProgressListener = mCountdownProgressListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        getDrawingRect(bounds);
        //画进度条
        mPaint.setAntiAlias(true);
        mPaint.setColor(progressColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(circleWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        int deleteWidth = ((int) circleWidth - 1);
        mArcRect.set(bounds.left + deleteWidth, bounds.top + deleteWidth, bounds.right - deleteWidth, bounds.bottom - deleteWidth);

        canvas.drawArc(mArcRect, -90, 360 * mProgress / 100, false, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int size = (width > height ? width : height);
        setMeasuredDimension(size, size);
    }

    /**
     * 开始。
     */
    public void start() {
        stop();
        post(progressChangeTask);
    }

    /**
     * 重新开始。
     */
    public void reStart() {
        resetProgress();
        start();
    }

    /**
     * 停止。
     */
    public void stop() {
        removeCallbacks(progressChangeTask);
    }

    /**
     * 重置进度。
     */
    private void resetProgress() {
        switch (mProgressType) {
            case COUNT:
                mProgress = 0;
                break;
            case COUNT_BACK:
                mProgress = 100;
                break;
        }
    }

    /**
     * 进度更新task。
     */
    private Runnable progressChangeTask = new Runnable() {
        @Override
        public void run() {
            removeCallbacks(this);
            switch (mProgressType) {
                case COUNT:
                    mProgress += 1;
                    break;
                case COUNT_BACK:
                    mProgress -= 1;
                    break;
            }
            if (mProgress >= 0 && mProgress <= 100) {
                if (mCountdownProgressListener != null)
                    mCountdownProgressListener.onProgress(mProgress);
                invalidate();
                postDelayed(progressChangeTask, timeMillis / 100);
            } else
                mProgress = validateProgress(mProgress);
        }
    };

    /**
     * 进度条类型。
     */
    public enum ProgressType {
        /**
         * 顺数进度条，从0-100；
         */
        COUNT,

        /**
         * 倒数进度条，从100-0；
         */
        COUNT_BACK
    }

    /**
     * 进度监听。
     */
    public interface OnProgressChangeListener {

        /**
         * 进度通知。
         *
         * @param progress 进度值。
         */
        void onProgress(int progress);
    }
}
