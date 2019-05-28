package com.yimishiji.widget.loading.factory;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.yimishiji.app.widget.R;

/**
 * Created by gsd on 2017/6/7.
 * Copyright © 2017 YIMISHIJI. All rights reserved.
 */

public class CustomDialogFactory implements DialogFactory {

    @Override
    public Dialog onCreateDialog(Context context) {
        Dialog dialog = new Dialog(context, R.style.loading_dialog);
        dialog.setContentView(R.layout.loading_dialog_view);
        return dialog;
    }

    @Override
    public void setMessage(Dialog dialog, CharSequence message) {
        TextView tv = (TextView) dialog.findViewById(R.id.loading_text);
        if (tv != null) {
            tv.setText(message);
        }
    }

    @Override
    public int getAnimateStyleId() {
        return 0;
    }
}
