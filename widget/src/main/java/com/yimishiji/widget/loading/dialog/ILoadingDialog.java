package com.yimishiji.widget.loading.dialog;

import android.app.Dialog;

import com.yimishiji.widget.loading.ILoading;

/**
 * Created by gsd on 2017/6/7.
 * Copyright © 2017 YIMISHIJI. All rights reserved.
 */

public interface ILoadingDialog extends ILoading {

    Dialog create();

    ILoadingDialog setCancelable(boolean flag);

    /**
     * 设置Message
     *
     * @param message
     * @return
     */
    ILoadingDialog setMessage(CharSequence message);
}
