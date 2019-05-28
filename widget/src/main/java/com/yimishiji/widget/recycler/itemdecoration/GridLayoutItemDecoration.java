package com.yimishiji.widget.recycler.itemdecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 *
 * Created by gsd on 2016/12/27.
 */
public class GridLayoutItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpanCount;
    private int mSpace;
    private boolean mIncludeEdge;
    private boolean mHorizontalShow = true;
    private boolean mVerticalShow = true;

    /**
     * @param spanCount
     * @param space
     * @param includeEdge
     */
    public GridLayoutItemDecoration(int spanCount, int space, boolean includeEdge) {
        this.mSpace = space;
        this.mSpanCount = spanCount;
        this.mIncludeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildLayoutPosition(view);

        int column = position % mSpanCount;

        if (mIncludeEdge) {

            if (mVerticalShow) {
                outRect.left = mSpace - column * mSpace / mSpanCount;
                outRect.right = (column + 1) * mSpace / mSpanCount;
            }

            if (mHorizontalShow) {
                if (position < mSpanCount) {
                    outRect.top = mSpace;
                }
                outRect.bottom = mSpace;
            }
        } else {
            if (mVerticalShow) {
                outRect.left = column * mSpace / mSpanCount;
                outRect.right = mSpace - (column + 1) * mSpace / mSpanCount;
            }
            if (mHorizontalShow) {
                if (position >= mSpanCount) {
                    outRect.top = mSpace;
                }
            }
        }
    }

    public void setHorizontalShow(boolean horizontalShow) {
        this.mHorizontalShow = horizontalShow;
    }

    public void setVerticalShow(boolean verticalShow) {
        this.mVerticalShow = verticalShow;
    }

}
