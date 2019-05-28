package com.yimishiji.widget.custom;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yimishiji.app.widget.R;

/**
 * Created by gsd on 2017/2/8.
 */
public class LoadingDialog {

    private Dialog mLoadingDialog;

    public LoadingDialog(Context context, String msg) {

        View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog_view, null);

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_view);
        TextView loadingText = (TextView) view.findViewById(R.id.loading_text);

        if (TextUtils.isEmpty(msg)) {
            loadingText.setVisibility(View.GONE);
        } else {
            loadingText.setVisibility(View.VISIBLE);
            loadingText.setText(msg);
        }

        mLoadingDialog = new Dialog(context, R.style.loading_dialog);

        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setContentView(layout, new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void show() {
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    public void close() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }
}
