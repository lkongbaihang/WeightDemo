package com.yimishiji.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.yimishiji.app.widget.R;

/**
 * viewpager 小点指示器
 * Created by gsd on 2016/11/24.
 */
public class DotIndicator extends LinearLayout {

    private Context mContext;
    private Paint mPaint;
    private View mMoveView;
    private int mCurrentPosition = 0;
    private float mPositionOffset;
    private int mItemCount = DRFAULT_ITEMCOUNT;
    private int mPadding = DEFAULT_PADDING;
    private int mRadius = DEFAULT_RADIUS;
    private int mMoveRadius = DEFAULT_RADIUS;
    private int mMoveInnerRadius = DEFAULT_RADIUS;
    //the distance from the left side of the previous item to the left side of the next item.
    private int mDistanceBtwItem = mRadius * 2 + mPadding;

    private static final int DRFAULT_ITEMCOUNT = 5;
    private static final int DEFAULT_RADIUS = 10;
    private static final int DEFAULT_PADDING = 10;
    private int mMoveDotInnerColor;//移动的小点的颜色
    private int mMoveDotColor;//移动的小点的颜色
    private int mNomalDotColor;//一般小点的颜色
    private boolean mMoveStoke;

    public DotIndicator(Context context) {
        this(context, null);

    }

    public DotIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAttr(attrs);
        init();
    }

    private void initAttr(AttributeSet attrs) {

        Resources res = getResources();

        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.DotIndicator);

        mMoveDotInnerColor = a.getColor(R.styleable.DotIndicator_dot_move_color_inner, Color.RED);
        mMoveDotColor = a.getColor(R.styleable.DotIndicator_dot_move_color, Color.RED);
        mNomalDotColor = a.getColor(R.styleable.DotIndicator_dot_nomal_color, Color.WHITE);
//        int moveResId = a.getResourceId(R.styleable.DotIndicator_dot_move_drawable, R.drawable.arc_seek_def_thumb);
        mMoveStoke = a.getBoolean(R.styleable.DotIndicator_dot_move_stoke, false);

    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        setWillNotDraw(false);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mNomalDotColor);
//        mPaint.setColor(Color.GRAY);

        if (mMoveStoke) {
            mMoveView = new MoveView(mContext, mMoveDotColor, mMoveDotInnerColor);
        } else {
            mMoveView = new MoveView(mContext, mMoveDotColor);
        }
        addView(mMoveView);
    }

    public void setItemCount(int count) {
        this.mItemCount = count;
        requestLayout();
    }

    /**
     * 设置点的半径
     * (默认点与选中点大小相同)
     * (如果点是有stroke的,内半径默认设为外半径的2/3)
     *
     * @param radius
     */
    public void setRadius(int radius) {
        this.mRadius = radius;
        mMoveRadius = mRadius;
        mMoveInnerRadius = (mMoveRadius * 2) / 3;
        this.mDistanceBtwItem = Math.max(mMoveRadius, mRadius) * 2 + mPadding;
        requestLayout();
    }

    /**
     * 设置点的半径
     * (自定义默认点与选中点的大小)
     * (如果点是有stroke的,内半径默认设为外半径的2/3)
     *
     * @param radius
     * @param moveRadius
     */
    public void setRadius(int radius, int moveRadius) {
        this.mRadius = radius;
        this.mMoveRadius = moveRadius;
        mMoveInnerRadius = (mMoveRadius * 2) / 3;
        this.mDistanceBtwItem = Math.max(mMoveRadius, mRadius) * 2 + mPadding;
        requestLayout();
    }

    /**
     * 设置点的半径
     * (自定义默认点与选中点的大小)
     * (如果点是有stroke的,自定义stroke半径)
     *
     * @param radius
     * @param moveRadius
     * @param moveInnerRadius
     */
    public void setRadius(int radius, int moveRadius, int moveInnerRadius) {
        this.mRadius = radius;
        this.mMoveRadius = moveRadius;
        this.mMoveInnerRadius = moveInnerRadius;
        this.mDistanceBtwItem = Math.max(mMoveRadius, mRadius) * 2 + mPadding;
        requestLayout();
    }

    public void setPadding(int padding) {
        this.mPadding = padding;
        this.mDistanceBtwItem = Math.max(mMoveRadius, mRadius) * 2 + mPadding;
        requestLayout();
    }

    public void setPositionAndOffset(int position, float offset) {
        this.mCurrentPosition = position;
        this.mPositionOffset = offset;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mPadding + (Math.max(mMoveRadius, mRadius) * 2 + mPadding) * mItemCount, 2 * Math.max(mMoveRadius, mRadius) + 2 * mPadding);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mMoveView.layout(
                (int) (mPadding + mDistanceBtwItem * (mCurrentPosition + mPositionOffset)),
                mPadding,
                (int) (mDistanceBtwItem * (1 + mCurrentPosition + mPositionOffset)),
                mPadding + Math.max(mMoveRadius, mRadius) * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mItemCount; i++) {
            canvas.drawCircle(Math.max(mMoveRadius, mRadius) + mPadding + Math.max(mMoveRadius, mRadius) * i * 2 + mPadding * i,
                    Math.max(mMoveRadius, mRadius) + mPadding, mRadius, mPaint);
        }

    }

    private class MoveView extends View {
        private Paint mPaint;
        private Paint mInterPaint;

        public MoveView(Context context, int moveViewColor) {
            super(context);
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(moveViewColor);
//            mPaint.setColor(Color.argb(255,255,176,93));
        }

        public MoveView(Context context, int moveViewColor, int moveViewInnerColor) {
            super(context);
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(moveViewColor);
//            mPaint.setColor(Color.argb(255,255,176,93));
            mInterPaint = new Paint();
            mInterPaint.setAntiAlias(true);
            mInterPaint.setColor(moveViewInnerColor);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            setMeasuredDimension(Math.max(mMoveRadius, mRadius) * 2, Math.max(mMoveRadius, mRadius) * 2);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (mInterPaint == null) {
                canvas.drawCircle(Math.max(mMoveRadius, mRadius), Math.max(mMoveRadius, mRadius), mMoveRadius, mPaint);
            } else {
                canvas.drawCircle(Math.max(mMoveRadius, mRadius), Math.max(mMoveRadius, mRadius), mMoveRadius, mPaint);
                canvas.drawCircle(Math.max(mMoveRadius, mRadius), Math.max(mMoveRadius, mRadius), mMoveInnerRadius, mInterPaint);
            }
        }
    }
}
