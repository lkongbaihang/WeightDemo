package com.yimishiji.widget.loading;

import com.yimishiji.widget.loading.factory.DialogFactory;
import com.yimishiji.widget.loading.factory.LoadingFactory;
import com.yimishiji.widget.loading.factory.MaterialDialogFactory;
import com.yimishiji.widget.loading.factory.MaterialFactory;

/**
 * Created by gsd on 2017/6/7.
 * Copyright © 2017 YIMISHIJI. All rights reserved.
 */

public class LoadingConfig {
    private final static LoadingFactory DEFAULT_LOADING_FACTORY = new MaterialFactory();
    private final static DialogFactory DEFAULT_DIALOG_FACTORY = new MaterialDialogFactory();

    private static LoadingFactory mLoadingFactory = DEFAULT_LOADING_FACTORY;
    private static DialogFactory mDialogFactory = DEFAULT_DIALOG_FACTORY;

    /**
     * 全局配置
     * <p>在程序入口调用</p>
     *
     * @param loadingFactory
     * @param dialogFactory
     */
    public static void setFactory(LoadingFactory loadingFactory, DialogFactory dialogFactory) {
        if (loadingFactory != null) {
            mLoadingFactory = loadingFactory;
        }
        if (dialogFactory != null) {
            mDialogFactory = dialogFactory;
        }
    }


    public static void defaultFactory() {
        setFactory(new MaterialFactory(), new MaterialDialogFactory());
    }


    public static LoadingFactory getLoadingFactory() {
        return mLoadingFactory;
    }

    public static DialogFactory getDialogFactory() {
        return mDialogFactory;
    }
}
