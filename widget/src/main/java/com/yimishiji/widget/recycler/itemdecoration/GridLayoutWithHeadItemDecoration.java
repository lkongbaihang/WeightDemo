package com.yimishiji.widget.recycler.itemdecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 有头部的分割线
 * Created by gsd on 2016/12/27.
 */
public class GridLayoutWithHeadItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpanCount;
    private int mSpace;
    private boolean mIncludeEdge;
    private int mHeadCount;

    public GridLayoutWithHeadItemDecoration(int spanCount, int space, int headCount, boolean includeEdge) {
        this.mSpace = space;
        this.mSpanCount = spanCount;
        this.mIncludeEdge = includeEdge;
        this.mHeadCount = headCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildLayoutPosition(view);

        if (position >= mHeadCount) {
            int column = (position - mHeadCount) % mSpanCount;

            if (mIncludeEdge) {
                outRect.left = mSpace - column * mSpace / mSpanCount;
                outRect.right = (column + 1) * mSpace / mSpanCount;

                if ((position - mHeadCount) < mSpanCount) {
                    outRect.top = mSpace;
                }
                outRect.bottom = mSpace;
            } else {
                outRect.left = column * mSpace / mSpanCount;
                outRect.right = mSpace - (column + 1) * mSpace / mSpanCount;
                if ((position - mHeadCount) >= mSpanCount) {
                    outRect.top = mSpace;
                }
            }
        }

    }
}
