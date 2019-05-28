package com.yimishiji.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yimishiji.app.widget.R;

/**
 * Created by gsd on 2016/12/15.
 */
public class CustomSwitchButton extends View implements View.OnTouchListener {

    private Bitmap mTrackOn, mTrackOff, mThumbOn, mThumbOff;

    private Drawable mThumbOnD;
    private Drawable mThumbOffD;
    private Drawable mTrackOnD;
    private Drawable mTrackOffD;
    /**
     * 按下时的x和当前的x
     */
    private float downX, nowX;

    /**
     * 记录用户是否在滑动
     */
    private boolean onSlip = false;

    /**
     * 当前的状态
     */
    private boolean nowStatus = false;

    /**
     * 监听接口
     */
    private OnChangedListener listener;

    public CustomSwitchButton(Context context) {
        super(context);
    }

    public CustomSwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomSwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public CustomSwitchButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        //载入图片资源
        Resources res = getResources();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomSwitchButton);

        int thumbOnResId = a.getResourceId(R.styleable.CustomSwitchButton_sb_thumb_on_drawable, 0);
        int thumbOffResId = a.getResourceId(R.styleable.CustomSwitchButton_sb_thumb_off_drawable, 0);
        int trackOnResId = a.getResourceId(R.styleable.CustomSwitchButton_sb_track_on_drawable, 0);
        int trackOffResId = a.getResourceId(R.styleable.CustomSwitchButton_sb_track_off_drawable, 0);

        float thumbRadius = a.getDimension(R.styleable.CustomSwitchButton_sb_thumb_radius, 20);
        float trackWidth = a.getDimension(R.styleable.CustomSwitchButton_sb_track_width, 20);

        mThumbOnD = res.getDrawable(thumbOnResId);
        mThumbOffD = res.getDrawable(thumbOffResId);
        mTrackOnD = res.getDrawable(trackOnResId);
        mTrackOffD = res.getDrawable(trackOffResId);

//        mThumbOn = BitmapFactory.decodeResource(getResources(), thumbOnResId);
//        mThumbOff = BitmapFactory.decodeResource(getResources(), thumbOffResId);
//        mTrackOn = BitmapFactory.decodeResource(getResources(), trackOnResId);
//        mTrackOff = BitmapFactory.decodeResource(getResources(), trackOffResId);

        Drawable trackOn = res.getDrawable(R.drawable.arc_seek_def_thumb);
        Drawable trackOff = res.getDrawable(R.drawable.bg_grey_track_def);
        Drawable thumb = res.getDrawable(R.drawable.bg_grey_track_def);

//        Bitmap trackOn = BitmapFactory.decodeResource(getResources(), R.drawable.arc_seek_def_thumb);
//        Bitmap trackOff = BitmapFactory.decodeResource(getResources(), R.drawable.bg_grey_track_def);
//        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.bg_grey_track_def);

        if (mThumbOnD == null) {
            mThumbOnD = thumb;
        }
        if (mThumbOffD == null) {
            mThumbOffD = thumb;
        }
        if (mTrackOnD == null) {
            mTrackOnD = trackOn;
        }
        if (mTrackOffD == null) {
            mTrackOffD = trackOff;
        }

        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Matrix matrix = new Matrix();
        Paint paint = new Paint();
        float x = 0;


        //根据nowX设置背景，开或者关状态
        if (nowX < (mTrackOn.getWidth() / 2)) {
            canvas.drawBitmap(mTrackOff, matrix, paint);//画出关闭时的背景
            //画出关闭时的滑块
//            canvas.drawBitmap(mThumbOff, x, 0, paint);
        } else {
            canvas.drawBitmap(mTrackOn, matrix, paint);//画出打开时的背景
            //画出打开时的滑块
//            canvas.drawBitmap(mThumbOn, x, 0, paint);
        }

        if (onSlip) {//是否是在滑动状态,
            if (nowX >= mTrackOn.getWidth())//是否划出指定范围,不能让滑块跑到外头,必须做这个判断
                x = mTrackOn.getWidth() - mThumbOn.getWidth() / 2;//减去滑块1/2的长度
            else
                x = nowX - mThumbOn.getWidth() / 2;
        } else {
            if (nowStatus) {//根据当前的状态设置滑块的x值
                x = mTrackOn.getWidth() - mThumbOn.getWidth();
            } else {
                x = 0;
            }
        }

        //对滑块滑动进行异常处理，不能让滑块出界
        if (x < 0) {
            x = 0;
        } else if (x > mTrackOn.getWidth() - mThumbOn.getWidth()) {
            x = mTrackOn.getWidth() - mThumbOn.getWidth();
        }

        canvas.drawBitmap(mThumbOn, x, 0, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);



    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (event.getX() > mTrackOff.getWidth() || event.getY() > mTrackOff.getHeight()) {
                    return false;
                } else {
                    onSlip = true;
                    downX = event.getX();
                    nowX = downX;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                nowX = event.getX();
                break;
            }
            case MotionEvent.ACTION_UP: {
                onSlip = false;
                if (event.getX() >= (mTrackOn.getWidth() / 2)) {
                    nowStatus = true;
                    nowX = mTrackOn.getWidth() - mThumbOn.getWidth();
                } else {
                    nowStatus = false;
                    nowX = 0;
                }

                if (listener != null) {
                    listener.OnChanged(this, nowStatus);
                }
                break;
            }
        }
        //刷新界面
        invalidate();
        return true;
    }


    /**
     * 为WiperSwitch设置一个监听，供外部调用的方法
     *
     * @param listener
     */
    public void setOnChangedListener(OnChangedListener listener) {
        this.listener = listener;
    }


    /**
     * 设置滑动开关的初始状态，供外部调用
     *
     * @param checked
     */
    public void setChecked(boolean checked) {
        if (checked) {
            nowX = mTrackOff.getWidth();
        } else {
            nowX = 0;
        }
        nowStatus = checked;
    }


    /**
     * 回调接口
     *
     * @author len
     */
    public interface OnChangedListener {
        public void OnChanged(CustomSwitchButton swipeButton, boolean checkState);
    }


}
