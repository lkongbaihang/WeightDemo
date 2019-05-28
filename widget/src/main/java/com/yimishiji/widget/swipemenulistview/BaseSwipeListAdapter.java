package com.yimishiji.widget.swipemenulistview;

import android.widget.BaseAdapter;

/**
 * Created by gsd on 2016/12/5.
 */
public abstract  class BaseSwipeListAdapter extends BaseAdapter {

    public boolean getSwipEnableByPosition(int position){
        return true;
    }

}
