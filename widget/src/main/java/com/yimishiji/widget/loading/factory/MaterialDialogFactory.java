package com.yimishiji.widget.loading.factory;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;

import com.yimishiji.app.widget.R;

/**
 * Created by gsd on 2017/6/7.
 * Copyright © 2017 YIMISHIJI. All rights reserved.
 */

public class MaterialDialogFactory implements DialogFactory<ProgressDialog> {

    @Override
    public ProgressDialog onCreateDialog(Context context) {
        ProgressDialog dialog;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog = new ProgressDialog(context, R.style.Custom_Dialog);
        } else {
            dialog = new ProgressDialog(context);
            dialog.setProgressStyle(android.support.v7.appcompat.R.style.Widget_AppCompat_ProgressBar);
        }
        dialog.setMessage("正在加载");
        dialog.setCancelable(false);
        return dialog;
    }

    @Override
    public void setMessage(ProgressDialog dialog, CharSequence message) {
        dialog.setMessage(message);
    }

    @Override
    public int getAnimateStyleId() {
        return 0;
    }
}
