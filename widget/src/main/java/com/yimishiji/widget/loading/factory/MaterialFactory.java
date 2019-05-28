package com.yimishiji.widget.loading.factory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yimishiji.app.widget.R;

/**
 * Created by gsd on 2017/6/7.
 * Copyright © 2017 YIMISHIJI. All rights reserved.
 */

public class MaterialFactory implements LoadingFactory {

    @Override
    public View onCreateView(ViewGroup parent) {
        View loadingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_progressbar_vertical_material, parent, false);
        return loadingView;
    }
}
