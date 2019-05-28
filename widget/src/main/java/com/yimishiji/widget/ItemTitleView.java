package com.yimishiji.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yimishiji.app.widget.R;

/**
 * 自定义标题布局
 */
public class ItemTitleView extends LinearLayout {


    private TextView tvTitle;
    private View inflate;
    private View lineLeft;
    private View lineRight;
    private Context mContext;
    private CircleView dotLeft;
    private CircleView dotRight;
    private float mTitleSize;
    private boolean mIsTitleBold;
    private boolean mHasLine;
    private String mTitleText;

    public ItemTitleView(Context context) {
        super(context);
        mContext = context;
        initView(context);
    }

    public ItemTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        initView(context);

        initAttrs(attrs);
    }


    public ItemTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(context);
        initAttrs(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ItemTitleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initView(context);
        initAttrs(attrs);
    }

    public void setTitle(CharSequence title) {
        tvTitle.setText(title);
    }

    /**
     * @param unit TypedValue.COMPLEX_UNIT_SP等
     * @param size
     */
    public void setTitleSize(int unit, float size) {
        tvTitle.setTextSize(unit, size);
    }

    public void setTitleBold() {
//        tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
        tvTitle.getPaint().setFakeBoldText(true);//加粗
    }

    private void initView(Context context) {
        inflate = LayoutInflater.from(context).inflate(R.layout.item_title_view, this, true);

        tvTitle = (TextView) inflate.findViewById(R.id.text_view_title);
        lineLeft = inflate.findViewById(R.id.line_left);
        lineRight = inflate.findViewById(R.id.line_right);
        dotLeft = (CircleView) inflate.findViewById(R.id.dot_left);
        dotRight = (CircleView) inflate.findViewById(R.id.dot_right);
    }

    private void initAttrs(AttributeSet attrs) {

        float defaultValue = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics());

        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.ItemTitleView);

        mTitleText = a.getString(R.styleable.ItemTitleView_titleText);
        mHasLine = a.getBoolean(R.styleable.ItemTitleView_hasLine, true);
        mIsTitleBold = a.getBoolean(R.styleable.ItemTitleView_isTitleBold, false);
        mTitleSize = a.getDimension(R.styleable.ItemTitleView_titleSize, defaultValue);
//        int titleSize = a.getDimensionPixelSize(R.styleable.CutLineView_titleSize, 16);
        int titleColor = a.getColor(R.styleable.ItemTitleView_titleColor, Color.BLACK);

        int lineColor = a.getColor(R.styleable.ItemTitleView_lineColor, Color.BLACK);

        lineLeft.setBackgroundColor(lineColor);
        lineRight.setBackgroundColor(lineColor);
        dotLeft.setColor(lineColor);
        dotRight.setColor(lineColor);

        if (mIsTitleBold) {
//            tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
            tvTitle.getPaint().setFakeBoldText(true);//加粗
        }

        tvTitle.setText(mTitleText);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSize);

//        tvTitle.setTextSize(titleSize);
        tvTitle.setTextColor(titleColor);
        tvTitle.setGravity(Gravity.CENTER);

//        lineLeft.setBackgroundColor(titleColor);
//        lineRight.setBackgroundColor(titleColor);
//        dotLeft.setBackgroundColor(titleColor);
//        dotRight.setBackgroundColor(titleColor);


        if (mHasLine) {
            lineLeft.setVisibility(View.VISIBLE);
            lineRight.setVisibility(View.VISIBLE);
        } else {
            lineLeft.setVisibility(View.INVISIBLE);
            lineRight.setVisibility(View.INVISIBLE);
        }
//        tvTitle.setTextSize(Float.parseFloat(titleSize.substring(0,titleSize.length() -1)));
//        tvTitle.setTextSize(Float.parseFloat(titleSize));
//        tvTitle.setTextColor(Integer.parseInt(titleColor));

        a.recycle();
    }
}
