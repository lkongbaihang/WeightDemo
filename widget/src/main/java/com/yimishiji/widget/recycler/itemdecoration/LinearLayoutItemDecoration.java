package com.yimishiji.widget.recycler.itemdecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by gsd on 2016/12/27.
 */
public class LinearLayoutItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpace;
    private int mOrientation;
    private boolean mIncludeEdge;

    /**
     * @param orientation LinearLayout.HORIZONTAL
     * @param space
     */
    public LinearLayoutItemDecoration(int orientation, int space, boolean includeEdge) {
        this.mSpace = space;
        this.mOrientation = orientation;
        this.mIncludeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (mIncludeEdge) {
            outRect.right = mSpace;
            outRect.bottom = mSpace;

            if (mOrientation == LinearLayout.VERTICAL) {
                outRect.left = mSpace;
                if (parent.getChildLayoutPosition(view) == 0) {
                    outRect.top = mSpace;
                } else {
                    outRect.top = 0;
                }
            }

            if (mOrientation == LinearLayout.HORIZONTAL) {
                outRect.top = mSpace;
                if (parent.getChildLayoutPosition(view) == 0) {
                    outRect.left = mSpace;
                } else {
                    outRect.left = 0;
                }
            }
        } else {
            if (mOrientation == LinearLayout.HORIZONTAL) {
                outRect.top = 0;
                outRect.bottom = 0;
                if (parent.getChildLayoutPosition(view) == 0) {
                    outRect.left = 0;
                } else {
                    outRect.left = mSpace;
                }
            }

            if (mOrientation == LinearLayout.VERTICAL) {
                outRect.right = 0;
                outRect.left = 0;
                if (parent.getChildLayoutPosition(view) == 0) {
                    outRect.top = 0;
                } else {
                    outRect.top = mSpace;
                }
            }
        }
    }
}
