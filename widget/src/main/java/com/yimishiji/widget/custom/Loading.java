package com.yimishiji.widget.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.yimishiji.app.widget.R;

/**
 * Created by gsd on 2017/8/4.
 * Copyright © 2017 YIMISHIJI. All rights reserved.
 */

public class Loading extends Dialog {

    public Loading(@NonNull Context context) {
        super(context, R.style.loading_dialog);
        //加载布局
//        setContentView(R.layout.loading_dialog_view);
        setContentView(R.layout.loading_view);

        ImageView ivLoading = findViewById(R.id.iv_loading);

        AnimationDrawable animationDrawable = new AnimationDrawable();
        Drawable drawable1 = context.getResources().getDrawable(R.drawable.loading_frame_1);
        Drawable drawable2 = context.getResources().getDrawable(R.drawable.loading_frame_2);
        Drawable drawable3 = context.getResources().getDrawable(R.drawable.loading_frame_3);
        Drawable drawable4 = context.getResources().getDrawable(R.drawable.loading_frame_4);
        Drawable drawable5 = context.getResources().getDrawable(R.drawable.loading_frame_5);

        animationDrawable.addFrame(drawable1, 500);
        animationDrawable.addFrame(drawable2, 500);
        animationDrawable.addFrame(drawable3, 500);
        animationDrawable.addFrame(drawable4, 500);
        animationDrawable.addFrame(drawable5, 500);

        animationDrawable.setOneShot(false);

        ivLoading.setImageDrawable(animationDrawable);
        animationDrawable.start();

        //设置Dialog参数
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

//    @Override
//    public void onBackPressed() {
//        //回调
//        cancle();
//        //关闭Loading
//        dismiss();
//    }

    private void cancle() {

    }
}
