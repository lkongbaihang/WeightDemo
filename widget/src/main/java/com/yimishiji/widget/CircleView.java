package com.yimishiji.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.yimishiji.app.widget.R;

/**
 * Created by gsd on 2016/10/9.
 */
public class CircleView extends View {

    private Context mContext;
    private float m_cx;
    private float m_cy;
    private float m_radius;
    private Paint paint;

    public CircleView(Context context) {
        super(context);
        mContext = context;
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initAttrs(attrs);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttrs(attrs);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initAttrs(attrs);
    }

    public CircleView(Context context, float cx, float cy, float radius) {
        super(context);
        m_cx = cx;
        m_cy = cy;
        m_radius = radius;

        paint = new Paint();

        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        // 设置样式-填充
        paint.setStyle(Paint.Style.FILL);
    }

    public void setColor(int color) {
        // 设置颜色
        paint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //  Auto-generated method stub
        super.onDraw(canvas);

        // draw circle
        canvas.drawCircle(m_cx, m_cy, m_radius, paint);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.CircleView);

        int dotColor = a.getColor(R.styleable.CircleView_dotColor, Color.BLACK);
        int width = a.getLayoutDimension(R.styleable.CircleView_android_layout_width, -1);
        int height = a.getLayoutDimension(R.styleable.CircleView_android_layout_height, -2);

        a.recycle();

        float x = getX();
        float y = getY();

        m_cx = x + width / 2;
        m_cy = y + height / 2;
        m_radius = Math.min(width, height) / 2;

        paint = new Paint();

        paint.setColor(dotColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
    }

}
