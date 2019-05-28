package com.yimishiji.widget.swipemenulistview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by gsd on 2016/12/5.
 */
public class SwipeMenuItem {

    private int id;
    private Context mContext;
    private String title;
    private Drawable icon;
    private Drawable background;
    private int titleColor = Color.BLACK;
    private int titleSize = 14;
    private int width;
    private View itemView;

    private int unit = TypedValue.COMPLEX_UNIT_SP;
    private boolean isHorizontal = false;


    public SwipeMenuItem(Context context) {
        mContext = context;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public int getTitleSize() {
        return titleSize;
    }

    /**
     * @param uint      TypedValue.sp dp px
     * @param titleSize
     */
    public void setTitleSize(int uint, int titleSize) {
        this.unit = uint;
        this.titleSize = titleSize;
    }

    public int getTitleUnit() {
        return unit;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitle(int resId) {
        setTitle(mContext.getString(resId));
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public void setIcon(int resId) {
        this.icon = mContext.getResources().getDrawable(resId);
    }

    public Drawable getBackground() {
        return background;
    }

    public void setBackground(Drawable background) {
        this.background = background;
    }

    public void setBackground(int resId) {
        this.background = mContext.getResources().getDrawable(resId);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setItemView(View view) {
        this.itemView = view;
    }

    public View getItemView() {
        return itemView;
    }

    public void setOrientation(boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
    }
    public boolean getOrientation() {
        return isHorizontal;
    }

}
