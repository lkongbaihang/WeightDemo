package com.yimishiji.widget.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.yimishiji.app.widget.R;

/**
 * Created by gsd on 2018/8/7.
 * Copyright © 2018 YIMISHIJI. All rights reserved.
 */

public class CustomCountEditView extends FrameLayout {

    private FrameLayout mFlCountDown;
    private FrameLayout mFlCountUp;
    private EditText mEtCount;

    private boolean mCountTextBold;
    private float mCountTextSize;
    private int mCountTextColor;
    private int mCountInitValue;
    private int mCountMinValue;

    public CustomCountEditView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public CustomCountEditView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomCountEditView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomCountEditView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.custom_count_edit, this, true);

        mFlCountDown = inflate.findViewById(R.id.fl_count_down);
        mFlCountUp = inflate.findViewById(R.id.fl_count_up);
        mEtCount = inflate.findViewById(R.id.et_count);

        float defaultValue = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomCountEditView);

        mCountInitValue = a.getInteger(R.styleable.CustomCountEditView_count_init_value, 1);
        mCountMinValue = a.getInteger(R.styleable.CustomCountEditView_count_min_value, 0);
        mCountTextBold = a.getBoolean(R.styleable.CustomCountEditView_count_text_bold, false);
        mCountTextSize = a.getDimension(R.styleable.CustomCountEditView_count_text_size, defaultValue);
        mCountTextColor = a.getColor(R.styleable.CustomCountEditView_count_text_color, Color.BLACK);

        mEtCount.setText(String.valueOf(mCountInitValue));
        mEtCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, mCountTextSize);
        mEtCount.setTextColor(mCountTextColor);
        if (mCountTextBold) {
            mEtCount.getPaint().setFakeBoldText(true);//加粗
        }

        mEtCount.setFocusable(false);
        mEtCount.setFocusableInTouchMode(false);

        a.recycle();

        mFlCountDown.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentCount = getCurrentCount();
                if (currentCount <= mCountMinValue) {
                    return;
                }
                mEtCount.setText(String.valueOf(currentCount - 1));
                mEtCount.clearFocus();
                mEtCount.setCursorVisible(false);
            }
        });

        mFlCountUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentCount = getCurrentCount();
                mEtCount.setText(String.valueOf(currentCount + 1));
                mEtCount.clearFocus();
                mEtCount.setCursorVisible(false);
            }
        });
    }

    private int toInt(String count) {
        if (count == null) {
            return 0;
        }
        try {
            return Integer.parseInt(count);
        } catch (Exception e) {
            return 0;
        }
    }

    public int getCurrentCount() {
        String countStr = mEtCount.getText().toString().trim();
        return toInt(countStr);
    }
}
