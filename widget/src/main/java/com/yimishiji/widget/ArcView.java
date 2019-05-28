package com.yimishiji.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.yimishiji.app.widget.R;

/**
 * Created by gsd on 2016/12/12.
 */
public class ArcView extends View {


    /**
     * 背景颜色
     */
    private int arcBackgroundColor = Color.WHITE;
    /**
     * 弧形的半径
     */
    private float arcViewRadius = 0;

    Paint mPaint;
    private int widthSize;
    private int heightSize;

    private RectF mRectF = new RectF();
    private float mArcStartAngle;
    private float mArcSweepAngle;


    public ArcView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public ArcView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }


    public void init(Context context, AttributeSet attrs, int defStyleAttr) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ArcView);

        arcBackgroundColor = a.getColor(R.styleable.ArcView_arcBackgroundColor, arcBackgroundColor);
        arcViewRadius = a.getDimension(R.styleable.ArcView_arcBackgroundRadius, arcViewRadius);

        a.recycle();

        mPaint = new Paint();

        mPaint.setColor(arcBackgroundColor);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {


        canvas.drawArc(mRectF, mArcStartAngle, mArcSweepAngle, false, mPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        widthSize = getSize(getSuggestedMinimumWidth(), widthMeasureSpec);

        if (arcViewRadius <= 0) {
            arcViewRadius = widthSize / 2;
        }
        float angDegrees = (float) Math.toDegrees(Math.asin(((widthSize) / 2) / arcViewRadius));
        mArcSweepAngle = angDegrees * 2;
        mArcStartAngle = 90 - angDegrees;

        int x = widthSize / 2;

        int minimumHeight = (int) (arcViewRadius - Math.sqrt(arcViewRadius * arcViewRadius - x * x));

//        setMinimumHeight(minimumHeight);

        heightSize = minimumHeight + 10;
//        heightSize = getSize(getSuggestedMinimumHeight(), heightMeasureSpec);

        mRectF.set(widthSize / 2 - arcViewRadius, heightSize - arcViewRadius * 2 - 10, widthSize / 2 + arcViewRadius, heightSize - 10);

        setMeasuredDimension(widthSize, heightSize);
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
}

