//package com.yimishiji.widget.dialog;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.annotation.StyleRes;
//import android.text.TextUtils;
//import android.util.DisplayMetrics;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.yimishiji.app.widget.R;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by gsd on 2017/5/3.
// * Copyright © 2017 YIMISHIJI. All rights reserved.
// */
//
//public class UpgradeDialog extends Dialog {
//
//    public UpgradeDialog(@NonNull Context context) {
//        super(context);
//    }
//
//    public UpgradeDialog(@NonNull Context context, @StyleRes int themeResId) {
//        super(context, themeResId);
//    }
//
//    protected UpgradeDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
//        super(context, cancelable, cancelListener);
//    }
//
//    public static class Builder {
//
//        private Context mContext;
//
//        public Builder(Context context) {
//            this.mContext = context;
//        }
//
//        public UpgradeDialog create() {
//            UpgradeDialog mUpdateDialog = new UpgradeDialog(mContext, R.style.Custom_Dialog);
//
//            View contentView = LayoutInflater.from(mContext).inflate(R.layout.view_app_update_window, null);
//
//            Button btnCancel = (Button) contentView.findViewById(R.id.btn_cancel);
//            Button btnUpdate = (Button) contentView.findViewById(R.id.btn_update);
//            ImageView ivUpdateImg = (ImageView) contentView.findViewById(R.id.iv_update_img);
//            TextView tvUpdateTitle = contentView.findViewById(R.id.tv_update_title);
//            TextView tvUpdateDesc = contentView.findViewById(R.id.tv_update_desc);
//
//            final boolean mustUpgrade = upgradeObj.isMustUpgrade();
//            String imgId = upgradeObj.image;
//            String title = upgradeObj.title;
//            String desc = upgradeObj.desc;
//            String btnCancelText = upgradeObj.cancelBtn;
//            String btnConfirmText = upgradeObj.confirmBtn;
//
//            Window window = mUpdateDialog.getWindow();
//
//            window.setGravity(Gravity.CENTER);
//
//            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
//
//            int windowWidth = displayMetrics.widthPixels;
//
//            if (mustUpgrade) {
//                btnCancel.setVisibility(View.GONE);
//            }
//            if (!TextUtils.isEmpty(imgId)) {
//                GlideUtils.loadImageWebp(imgId, ivUpdateImg, GlideUtils.HolderType.HOLDER_DEF);
//            }
//            if (!TextUtils.isEmpty(title)) {
//                tvUpdateTitle.setText(title);
//            }
//            if (!TextUtils.isEmpty(desc)) {
//                tvUpdateDesc.setText(desc);
//            }
//            if (!TextUtils.isEmpty(btnCancelText)) {
//                btnCancel.setText(btnCancelText);
//            }
//            if (!TextUtils.isEmpty(btnConfirmText)) {
//                btnUpdate.setText(btnConfirmText);
//            }
//            //取消
//            RxView.clicks(btnCancel)
//                    .throttleFirst(200, TimeUnit.MICROSECONDS)
//                    .subscribe(new Action1<Void>() {
//                        @Override
//                        public void call(Void aVoid) {
//                            mUpdateDialog.dismiss();
//                            cancel();
//                            if (checkCallBack != null) {
//                                checkCallBack.callBack(USER_CANCELED);
//                            }
//                        }
//                    });
//            //更新
//            RxView.clicks(btnUpdate)
//                    .throttleFirst(200, TimeUnit.MICROSECONDS)
//                    .subscribe(new Action1<Void>() {
//                        @Override
//                        public void call(Void aVoid) {
//                            if (!NetUtils.isNetworkAvailable()) {
//                                ToastUtils.show("网络不可用");
//                            } else if (NetUtils.isWifi()) {
//                                service.doDownLoadTask();
//                            } else {
//                                showNotWifiDownloadDialog();
//                            }
//                            if (!mustUpgrade) {
//                                mUpdateDialog.dismiss();
//                            }
//                        }
//                    });
//
//            mUpdateDialog.setOnKeyListener(keylistener);//按返回按钮不消失
//
//            mUpdateDialog.setCanceledOnTouchOutside(false);
//
//            mUpdateDialog.setContentView(contentView, new ViewGroup.MarginLayoutParams((int) (windowWidth * 0.8), ViewGroup.MarginLayoutParams.WRAP_CONTENT));
//            return mUpdateDialog;
//        }
//    }
//}
