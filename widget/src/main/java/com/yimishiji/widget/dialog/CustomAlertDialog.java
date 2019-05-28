package com.yimishiji.widget.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yimishiji.app.widget.R;

/**
 * 定制提示框
 * Created by gsd on 2017/3/1.
 */
public class CustomAlertDialog extends Dialog {

    public CustomAlertDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public CustomAlertDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private boolean bold;
        private String message;
        private int msgGravity = Gravity.CENTER;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        @IdRes
        private int positiveButtonBgResId;
        @IdRes
        private int negativeButtonBgResId;
        @IdRes
        private int positiveTextColorId;
        @IdRes
        private int negativeTextColorId;
        @IdRes
        private int animId;

        private DialogInterface.OnClickListener positiveButtonClickListener,
                negativeButtonClickListener;
        private boolean isCloseShow = false;
        private boolean canceledOnTouchOutside = true;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置对话框的文本
         *
         * @param message
         * @return
         */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * 通过资源文件设置对话框的文本
         *
         * @param message
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * 设置内容gravity
         *
         * @param msgGravity
         */
        public Builder setMsgGravity(int msgGravity) {
            this.msgGravity = msgGravity;
            return this;
        }

        /**
         * 通过资源文件设置对话框的标题
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * 设置对话框的标题
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * 设置对话框的标题
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title, boolean bold) {
            this.title = title;
            this.bold = bold;
            return this;
        }


        /**
         * 设置关闭按钮是否显示(默认false)
         *
         * @param closeShow
         * @return
         */
        public Builder setCloseShow(boolean closeShow) {
            this.isCloseShow = closeShow;
            return this;
        }

        /**
         * 设置触摸是否可以取消(默认true)
         *
         * @param cancelable
         * @return
         */
        public Builder setCanceledOnTouchOutside(boolean cancelable) {
            this.canceledOnTouchOutside = cancelable;
            return this;
        }

        /**
         * Set a custom content view for the Dialog. If a message is set, the
         * contentView is not added to the Dialog...
         *
         * @param v
         * @return
         */
        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * 设置确定按钮的背景资源id
         *
         * @param positiveButtonBgResId
         * @return
         */
        public Builder setPositiveButtonBgResId(@IdRes int positiveButtonBgResId) {
            this.positiveButtonBgResId = positiveButtonBgResId;
            return this;
        }

        /**
         * 设置确定按钮的标题和监听器
         *
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(String positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * 设置确定按钮的标题和监听器
         *
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(String positiveButtonText, @IdRes int positiveButtonBgResId, @IdRes int positiveTextColor, DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonBgResId = positiveButtonBgResId;
            this.positiveTextColorId = positiveTextColor;
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * 设置取消按钮的背景资源id
         *
         * @param negativeButtonBgResId
         * @return
         */
        public Builder setNegativeButtonBgResId(@IdRes int negativeButtonBgResId) {
            this.negativeButtonBgResId = negativeButtonBgResId;
            return this;
        }

        /**
         * 设置取消按钮的标题和监听器
         *
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(String negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * 设置确定按钮的标题和监听器
         *
         * @param negativeButtonText
         * @param negativeButtonBgResId
         * @param negativeTextColor
         * @param listener
         * @return
         */
        public Builder setNegativeButton(String negativeButtonText, @IdRes int negativeButtonBgResId, @IdRes int negativeTextColor, DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonBgResId = negativeButtonBgResId;
            this.negativeTextColorId = negativeTextColor;
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * 设置进/出动画
         *
         * @param animRes
         * @return
         */
        public Builder setAnimRes(@IdRes int animRes) {
            this.animId = animRes;
            return this;
        }

        /**
         * 创建定制的对话框
         */
        @SuppressLint("ResourceType")
        public CustomAlertDialog create() {

            // 创建指定样式的对话框
            final CustomAlertDialog dialog = new CustomAlertDialog(context, R.style.Custom_Dialog);

            LayoutInflater layoutInflater = dialog.getLayoutInflater();
            // 获取对话框的布局
            View layout = layoutInflater.inflate(R.layout.custom_dialog, null);
            // 为对话框添加布局和设置大小
//            dialog.addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            // 设置对话框的标题
            TextView tvTitle = layout.findViewById(R.id.tv_title);
            if (TextUtils.isEmpty(title)) {
                tvTitle.setVisibility(View.GONE);
            } else {
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText(title);
                if (bold) {//是否加粗
                    TextPaint tp = tvTitle.getPaint();
                    tp.setFakeBoldText(bold);
                }
            }
            //设置关闭按钮是否显示
            RelativeLayout rlClose = (RelativeLayout) layout.findViewById(R.id.rl_close);
            if (isCloseShow) {
                rlClose.setVisibility(View.VISIBLE);
                rlClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            } else {
                rlClose.setVisibility(View.GONE);
            }
            // 设置确定按钮
            Button btnPositive = (Button) layout.findViewById(R.id.btn_positive);
            if (positiveButtonText != null) {
                // 如果有文本设置按钮的文本
                btnPositive.setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    // 设置按钮的监听器
                    btnPositive.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
                if (positiveButtonBgResId > 0) {
                    btnPositive.setBackgroundResource(positiveButtonBgResId);
                }
                if (positiveTextColorId > 0) {
                    btnPositive.setTextColor(ContextCompat.getColor(context, positiveTextColorId));
                }
            } else {
                // 如果没有文本就设置按钮不可见
                btnPositive.setVisibility(View.GONE);
            }
            // 设置取消按钮
            Button btnNegative = (Button) layout.findViewById(R.id.btn_nagative);
            if (negativeButtonText != null) {
                btnNegative.setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    //设置按钮监听器
                    btnNegative.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }
                if (negativeButtonBgResId > 0) {
                    btnNegative.setBackgroundResource(negativeButtonBgResId);
                }
                if (negativeTextColorId > 0) {
                    btnNegative.setTextColor(ContextCompat.getColor(context, negativeTextColorId));
                }
            } else {
                // 如果没有文本就设置按钮不可见
                btnNegative.setVisibility(View.GONE);
            }
            // 显示文本内容
            TextView tvMsg = (TextView) layout.findViewById(R.id.tv_message);
            if (message != null) {
                tvMsg.setGravity(msgGravity);
                tvMsg.setText(message);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((RelativeLayout) layout.findViewById(R.id.rl_content))
                        .removeAllViews();
                ((RelativeLayout) layout.findViewById(R.id.rl_content)).addView(
                        contentView, new LayoutParams(
                                LayoutParams.WRAP_CONTENT,
                                LayoutParams.WRAP_CONTENT));
            } else {
                tvMsg.setVisibility(View.GONE);
            }
            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
//            dialog.setContentView(layout);

            Window window = dialog.getWindow();

            window.setGravity(Gravity.CENTER);
            if (animId > 0) {//= R.style.CustomDialogAnimation
                window.setWindowAnimations(animId);
            }

            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

            int windowWidth = displayMetrics.widthPixels;

            dialog.setContentView(layout, new ViewGroup.MarginLayoutParams((int) (windowWidth * 0.9), ViewGroup.LayoutParams.WRAP_CONTENT));

            return dialog;
        }
    }

}
