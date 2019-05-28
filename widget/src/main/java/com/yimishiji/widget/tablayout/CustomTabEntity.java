package com.yimishiji.widget.tablayout;

import android.support.annotation.DrawableRes;

/**
 * Created by gsd on 2016/11/28.
 */
public interface CustomTabEntity {

    String getTabTitle();

    @DrawableRes
    int getTabSelectedIcon();

    @DrawableRes
    int getTabUnselectedIcon();
}
