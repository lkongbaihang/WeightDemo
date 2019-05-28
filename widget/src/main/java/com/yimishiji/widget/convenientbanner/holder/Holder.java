package com.yimishiji.widget.convenientbanner.holder;

import android.content.Context;
import android.view.View;

/**
 * Created by gsd on 2017/7/12.
 * Copyright © 2017 YIMISHIJI. All rights reserved.
 */

public interface Holder<T> {
    View createView(Context context);

    void UpdateUI(Context context, int position, T data);
}
