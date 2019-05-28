package com.yimishiji.widget.loading.factory;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.StyleRes;

/**
 * Created by gsd on 2017/6/7.
 * Copyright © 2017 YIMISHIJI. All rights reserved.
 */

public interface DialogFactory<D extends Dialog> {
    /**
     * 创建dialog
     *
     * @param context
     * @return
     */
    D onCreateDialog(Context context);

    /**
     * 设置提示消息
     *
     * @param dialog
     * @param message
     */
    void setMessage(D dialog, CharSequence message);

    /**
     * 进入退出的动画id
     *
     * @return
     */
    @StyleRes
    int getAnimateStyleId();
}
