package com.yimishiji.widget.custom;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.yimishiji.app.widget.R;
import com.yimishiji.widget.passwordview.OnPasswordInputFinish;
import com.yimishiji.widget.passwordview.PasswordView;

/**
 * Created by gsd on 2017/3/1.
 */

public class PasswordInputDialog extends Dialog {

    public PasswordInputDialog(Context context) {
        super(context);
    }

    public PasswordInputDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PasswordInputDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder {

        private Context mContext;
        private OnInputFinishListener mOnInputFinishListener;
        private OnForgetClickListener mOnForgetClickListener;
        private OnCancelListener mOnCancelListener;

        public Builder(Context context) {
            this.mContext = context;
        }

        public PasswordInputDialog create() {

            final PasswordInputDialog dialog = new PasswordInputDialog(mContext, R.style.InputPassword_Dialog);

            LayoutInflater layoutInflater = dialog.getLayoutInflater();
            //获取布局
            View layout = layoutInflater.inflate(R.layout.view_input_password_dialog, null);

            final PasswordView passwordView = (PasswordView) layout.findViewById(R.id.password_view);

            passwordView.setOnFinishInput(new OnPasswordInputFinish() {
                @Override
                public void inputFinish() {
                    mOnInputFinishListener.onInputFinished(passwordView.getStrPassword());
                    dialog.dismiss();
                }
            });

            passwordView.getCancelImageView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });


            passwordView.getForgetTextView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnForgetClickListener.onForgetClick();
                }
            });

            dialog.setOnCancelListener(mOnCancelListener);

            dialog.setCanceledOnTouchOutside(false);

            Window window = dialog.getWindow();

            window.setGravity(Gravity.BOTTOM);

            DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();

            int windowWidth = displayMetrics.widthPixels;

            dialog.addContentView(layout, new ViewGroup.LayoutParams(windowWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            return dialog;
        }

        /**
         * 设置密码结束监听
         *
         * @param listener
         * @return
         */
        public Builder setOnInputFinishListener(OnInputFinishListener listener) {
            this.mOnInputFinishListener = listener;
            return this;
        }

        /**
         * 设置忘记密码点击监听
         *
         * @param listener
         * @return
         */
        public Builder setOnForgetClickListener(OnForgetClickListener listener) {
            this.mOnForgetClickListener = listener;
            return this;
        }

        /**
         * 设置dialog取消监听
         *
         * @param listener
         * @return
         */
        public Builder setOnCancelListener(OnCancelListener listener) {
            this.mOnCancelListener = listener;
            return this;
        }
    }

    public interface OnInputFinishListener {
        void onInputFinished(String password);
    }

    public interface OnForgetClickListener {
        void onForgetClick();
    }

}
