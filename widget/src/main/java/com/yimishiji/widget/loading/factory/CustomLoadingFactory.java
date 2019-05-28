package com.yimishiji.widget.loading.factory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yimishiji.app.widget.R;

/**
 * Created by gsd on 2017/6/7.
 * Copyright © 2017 YIMISHIJI. All rights reserved.
 */

public class CustomLoadingFactory implements LoadingFactory {
    @Override
    public View onCreateView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_dialog_view, parent, false);
    }
}
