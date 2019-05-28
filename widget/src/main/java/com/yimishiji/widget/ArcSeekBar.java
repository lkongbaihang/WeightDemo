package com.yimishiji.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yimishiji.app.widget.R;

/**
 * 弧形seekbar
 * <p/>
 * Created by gsd on 2016/12/9.
 */
public class ArcSeekBar extends View {

    private RectF mArcRectF = new RectF();
    /**
     * 弧线背景线的画笔
     */
    private Paint mArcPaint;
    /**
     * 进度条的画笔
     */
    private Paint mProgressPaint;
    /**
     * 手指拖动的点
     */
    private Drawable mThumb;
    /**
     * 进度条的宽度
     */
    private float mProgressWidth = 4;
    /**
     * 进度条的颜色
     */
    private int mProgressColor;
    /**
     * 弧线背景线的宽度
     */
    private float mArcWidth = 4;
    /**
     * 弧线背景线的颜色
     */
    private int mArcColor;
    /**
     * 手指拖动点的大小
     */
    private float mThumbRadius = 6;

    /**
     * 最大进度
     */
    private int mMaxProgress = 100;
    /**
     * 进度值
     */
    private int mProgress = 0;
    /**
     * 边线是否圆角
     */
    private boolean mRoundEdges = true;
    /**
     * 是否顺时针
     * 默认false 逆时针
     */
    private boolean mIsClockWise = false;
    /**
     * 是否可拖动
     */
    private boolean mEnabled = true;
    /**
     * 开始角度
     */
    private float mArcStartAngle;
    /**
     * 弧线扫过角度
     */
    private float mArcSweepAngle;
    /**
     * 进度条扫过角度
     */
    private float mProgressSweep;
    /**
     * 弧线的弧度半径
     */
    private float mArcRadius = 0;
    /**
     * 弧线旋转角度
     */
    private float mRotation = 0;


    private float mTranslateX;
    private float mTranslateY;
    private float mThumbXPos;
    private float mThumbYPos;
    private double mTouchAngle;
    private boolean mTouchInside = false;

    //    private float mTouchIgnoreRadius;
    private float mTouchEnableMaxRadius;
    private float mTouchEnableMinRadius;

    private static int INVALID_PROGRESS_VALUE = -1;

    private OnSeekChangeListener mOnSeekArcChangeListener;
    private float lineWidh;
    private int widthSize;
    private int heightSize;

    public ArcSeekBar(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ArcSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ArcSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public ArcSeekBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }


    public void init(Context context, AttributeSet attrs, int defStyleAttr) {

        Resources res = getResources();

        float density = context.getResources().getDisplayMetrics().density;

        int arcColor = Color.GRAY;
        int progressColor = Color.GREEN;

        int thumbHalfHeight = 0;
        int thumbHalfWidth = 0;

        mThumb = res.getDrawable(R.drawable.arc_seek_def_thumb);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ArcSeekBar, defStyleAttr, 0);

            int thumbResId = a.getResourceId(R.styleable.ArcSeekBar_thumb, 0);
            Drawable thumb = res.getDrawable(thumbResId);
            if (thumb != null) {
                mThumb = thumb;
            }
            mThumbRadius = a.getDimension(R.styleable.ArcSeekBar_thumbRadius, mThumbRadius);

            if (mThumbRadius <= 0) {
                thumbHalfHeight = mThumb.getIntrinsicHeight() / 2;
                thumbHalfWidth = mThumb.getIntrinsicWidth() / 2;
            } else {
                thumbHalfHeight = (int) mThumbRadius;
                thumbHalfWidth = (int) mThumbRadius;
            }

//            mThumb.setBounds(-thumbHalfWidth, -thumbHalfHeight, thumbHalfWidth, thumbHalfHeight);
            mThumb.setBounds(0, 0, thumbHalfWidth, thumbHalfHeight);

            mMaxProgress = a.getInteger(R.styleable.ArcSeekBar_maxProgress, mMaxProgress);

            mProgress = a.getInteger(R.styleable.ArcSeekBar_progress, mProgress);

            mProgressWidth = a.getDimension(R.styleable.ArcSeekBar_progressWidth, mProgressWidth);

            mProgressColor = a.getColor(R.styleable.ArcSeekBar_progressColor, progressColor);

            mArcWidth = a.getDimension(R.styleable.ArcSeekBar_arcWidth, mArcWidth);

            mArcColor = a.getColor(R.styleable.ArcSeekBar_arcColor, arcColor);

            mRoundEdges = a.getBoolean(R.styleable.ArcSeekBar_roundEdges, mRoundEdges);

            mIsClockWise = a.getBoolean(R.styleable.ArcSeekBar_clockwise, mIsClockWise);

            mEnabled = a.getBoolean(R.styleable.ArcSeekBar_enabled, mEnabled);

            mArcRadius = a.getDimension(R.styleable.ArcSeekBar_arcRadius, mArcRadius);

            mRotation = a.getFloat(R.styleable.ArcSeekBar_rotation, mRotation);

            a.recycle();
        }

        lineWidh = Math.max(Math.max(mArcWidth, mProgressWidth), mThumbRadius);

        mProgress = Math.max(0, mProgress);
        mProgress = Math.min(mProgress, mMaxProgress);

        mProgressSweep = (float) mProgress / mMaxProgress * mArcSweepAngle;

        mArcPaint = new Paint();
        mArcPaint.setColor(mArcColor);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(mArcWidth);

        mProgressPaint = new Paint();
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(mProgressWidth);

        if (mRoundEdges) {
            mArcPaint.setStrokeCap(Paint.Cap.ROUND);
            mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!mIsClockWise) {
            canvas.scale(-1, 1, mArcRectF.centerX(), mArcRectF.centerY());
        }

        final float startAngle = mArcStartAngle + mRotation;


        canvas.drawArc(mArcRectF, startAngle, mArcSweepAngle, false, mArcPaint);
        canvas.drawArc(mArcRectF, startAngle, mProgressSweep, false, mProgressPaint);

        if (mEnabled) {
            // Draw the thumb nail
            float defX = mThumbXPos;
//            float defX = mTranslateX + (mThumbXPos - mThumbRadius / 2);
//            float defY = mTranslateY - (mArcRadius - (mThumbYPos - mThumbRadius)) - mArcWidth / 2 * Math.sin();
//            float defY = mTranslateY - mThumbYPos;
            float defY = mThumbYPos;

//            canvas.translate(mTranslateX - mThumbXPos, mTranslateY - mArcRadius + mThumbYPos);
            canvas.translate(defX, defY);

            mThumb.draw(canvas);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        widthSize = getSize(getSuggestedMinimumWidth(), widthMeasureSpec);
//        heightSize = getSize(getSuggestedMinimumHeight(), heightMeasureSpec);

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int paddingTop = getPaddingTop();

        if (mArcRadius <= 0) {
            mArcRadius = widthSize / 2;
        }

        mArcRadius = Math.max((widthSize) / 2, mArcRadius);

        int x = (widthSize - paddingLeft - paddingRight) / 2;

        int minimumHeight = (int) (mArcRadius - Math.sqrt(mArcRadius * mArcRadius - x * x));

        heightSize = (int) (minimumHeight + paddingBottom + paddingTop + lineWidh * 2);

        mArcRectF.set((widthSize + paddingLeft) / 2 - mArcRadius, (heightSize - paddingBottom) - lineWidh - mArcRadius * 2, (widthSize + paddingLeft) / 2 + mArcRadius, (heightSize - paddingBottom) - lineWidh);

        float angDegrees = (float) Math.toDegrees(Math.asin(((widthSize - lineWidh - mThumbRadius) / 2) / mArcRadius));

        mArcStartAngle = 90 - angDegrees;
        mArcSweepAngle = angDegrees * 2;

        mProgressSweep = (float) mProgress / mMaxProgress * mArcSweepAngle;

//        mTranslateX = mArcRectF.centerX();
        mTranslateX = widthSize * 0.5f;
        mTranslateY = heightSize;
//        mTranslateY = widthSize * 0.5f;

        float thumbStart = mProgressSweep + mArcStartAngle + mRotation;

        float thumbY = (float) ((mArcRadius - lineWidh) * Math.sin(Math.toRadians(thumbStart)));
        float thumbX = (float) ((mArcRadius - lineWidh) * Math.cos(Math.toRadians(thumbStart)));

        mThumbXPos = widthSize / 2 + thumbX - mThumbRadius / 2;
        mThumbYPos = heightSize - paddingBottom - (mArcRadius - (thumbY - mThumbRadius / 2));
//        mThumbYPos = (float) (thumbY - mThumbRadius / 2 - (bottomWidth / 2) * Math.sin(Math.toRadians(thumbStart)));
//        mThumbYPos = (float) (thumbY - mThumbRadius);

//        mThumbYPos = (float) (thumbY - mThumbRadius / 2);
//        mThumbXPos = (float) (thumbX - mThumbRadius / 2);


//        mThumbYPos = (float) ((mArcRadius - (thumbY - mThumbRadius)) - mArcWidth / 2);
//        mThumbYPos = (float) ((mArcRadius - mArcWidth / 2) * Math.sin(Math.toRadians(thumbStart)));
//        mThumbXPos = (float) ((mArcRadius - mArcWidth / 2) * Math.cos(Math.toRadians(thumbStart)));

        setTouchInSide(mTouchInside);
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (mThumb != null && mThumb.isStateful()) {
            int[] state = getDrawableState();
            mThumb.setState(state);
        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mEnabled) {
            this.getParent().requestDisallowInterceptTouchEvent(true);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    onStartTrackingTouch();
                    updateOnTouch(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    updateOnTouch(event);
                    break;
                case MotionEvent.ACTION_UP:
                    onStopTrackingTouch();
                    setPressed(false);
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    onStopTrackingTouch();
                    setPressed(false);
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
            return true;
        }
        return false;
    }

    private void onStartTrackingTouch() {
        if (mOnSeekArcChangeListener != null) {
            mOnSeekArcChangeListener.onStartTrackingTouch(this);
        }
    }

    private void onStopTrackingTouch() {
        if (mOnSeekArcChangeListener != null) {
            mOnSeekArcChangeListener.onStopTrackingTouch(this);
        }
    }

    private void updateOnTouch(MotionEvent event) {
        boolean ignoreTouch = ignoreTouch(event.getX(), event.getY());

        if (ignoreTouch) {
            return;
        }
        setPressed(true);
        mTouchAngle = getTouchDegrees(event.getX(), event.getY());
        int progress = getProgressForAngle(mTouchAngle);

        onProgressRefresh(progress, true);
    }

    private void onProgressRefresh(int progress, boolean fromUser) {
        updateProgress(progress, fromUser);
    }

    private void updateProgress(int progress, boolean fromUser) {

        if (progress == INVALID_PROGRESS_VALUE) {
            return;
        }

        progress = Math.min(mMaxProgress, progress);
        progress = Math.max(0, progress);
//        progress = (progress > mMaxProgress) ? mMaxProgress : progress;
//        progress = (progress < 0) ? 0 : progress;
        mProgress = progress;

        if (mOnSeekArcChangeListener != null) {
            mOnSeekArcChangeListener.onProgressChanged(this, mProgress, fromUser);
        }

        mProgressSweep = (float) mProgress / mMaxProgress * mArcSweepAngle;

        updateThumbPosition();

        invalidate();
    }

    /**
     * 更新触点的位置
     */
    private void updateThumbPosition() {
//        int thumbAngle = (int) (mArcStartAngle + mProgressSweep + mRotation);
//        mThumbYPos = (int) (mArcRadius * Math.cos(Math.toRadians(thumbAngle)));
//        mThumbXPos = (int) (mArcRadius * Math.sin(Math.toRadians(thumbAngle)));
        float thumbStart = mProgressSweep + mArcStartAngle + mRotation;

        float thumbY = (float) ((mArcRadius - lineWidh) * Math.sin(Math.toRadians(thumbStart)));
        float thumbX = (float) ((mArcRadius - lineWidh) * Math.cos(Math.toRadians(thumbStart)));

        mThumbXPos = widthSize / 2 + thumbX - mThumbRadius / 2;
        mThumbYPos = heightSize - getPaddingBottom() - (mArcRadius - (thumbY - mThumbRadius / 2));
    }

    private int getProgressForAngle(double angle) {
//        int touchProgress = (int) Math.round(valuePerDegree() * angle);

        int touchProgress = (int) ((angle / mArcSweepAngle) * mMaxProgress);

        touchProgress = (touchProgress < 0) ? INVALID_PROGRESS_VALUE : touchProgress;
        touchProgress = (touchProgress > mMaxProgress) ? INVALID_PROGRESS_VALUE : touchProgress;

        return touchProgress;
    }

    private float valuePerDegree() {
        return (float) mMaxProgress / mArcSweepAngle;
    }

    private boolean ignoreTouch(float xPos, float yPos) {
        boolean ignore = true;
        float x = Math.abs(xPos - mTranslateX);
//        float y = yPos - mTranslateY;
        float y = yPos + (mArcRadius - heightSize);

        float touchRadius = (float) Math.sqrt(((x * x) + (y * y)));
        if (touchRadius < mTouchEnableMaxRadius && touchRadius > mTouchEnableMinRadius) {
            ignore = false;
        }
        return ignore;
    }

    public void setTouchInSide(boolean isEnabled) {
//        int thumbHalfheight = mThumb.getIntrinsicHeight() / 2;
//        int thumbHalfWidth = mThumb.getIntrinsicWidth() / 2;
        mTouchInside = isEnabled;
//        if (mTouchInside) {
//            mTouchIgnoreRadius = (float) mArcRadius / 4;
//        } else {
        // Don't use the exact radius makes interaction too tricky
//            mTouchIgnoreRadius = mArcRadius - Math.min(thumbHalfWidth, thumbHalfheight);
        mTouchEnableMinRadius = mArcRadius - mThumbRadius * 2;
        mTouchEnableMaxRadius = mArcRadius + mThumbRadius;
//        }
    }

    private double getTouchDegrees(float xPos, float yPos) {
        float x = xPos - mTranslateX;
//        float y = yPos - mTranslateY;
        float y = yPos + mArcRadius - mTranslateY;
        //invert the x-coord if we are rotating anti-clockwise
        x = (mIsClockWise) ? x : -x;
        // convert to arc Angle
//        double angle = Math.toDegrees(Math.atan2(y, x) + (Math.PI / 2)
//                - Math.toRadians(mRotation));

//        double angle = Math.toDegrees(Math.atan2(y, x)) + mRotation;

        double angle = (Math.toDegrees(Math.acos(x / mArcRadius)) + mRotation) - mArcStartAngle;

//        if (angle < 0) {
//            angle = 360 + angle;
//        }
//        angle -= mArcStartAngle;
        return angle;
    }

    private int getSize(int defSize, int measureSpec) {
        int mySize = defSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                mySize = defSize;
                break;
            case MeasureSpec.AT_MOST:
                mySize = size;
                break;
            case MeasureSpec.EXACTLY:
                mySize = size;
                break;
        }
        return mySize;
    }

    public int getProgress() {
        return mProgress;
    }

    /**
     * 设置进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        updateProgress(progress, false);
    }

    public void setOnSeekArcChangeListener(OnSeekChangeListener onSeekArcChangeListener) {
        this.mOnSeekArcChangeListener = onSeekArcChangeListener;
    }

    public interface OnSeekChangeListener {
        /**
         * 进度改变监听
         *
         * @param arcSeekBar
         * @param progress   进度
         * @param fromUser   是否是用户操作改变
         */
        void onProgressChanged(ArcSeekBar arcSeekBar, int progress, boolean fromUser);

        /**
         * 开始滑动
         *
         * @param arcSeekBar
         */
        void onStartTrackingTouch(ArcSeekBar arcSeekBar);

        /**
         * 停止滑动
         *
         * @param arcSeekBar
         */
        void onStopTrackingTouch(ArcSeekBar arcSeekBar);
    }

}
