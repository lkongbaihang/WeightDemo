package com.yimishiji.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yimishiji.app.widget.R;

import java.text.DecimalFormat;

/**
 * Created by gsd on 2017/4/10.
 */

public class NumberEditDialog extends Dialog {

    public NumberEditDialog(Context context) {
        super(context);
    }

    public NumberEditDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected NumberEditDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder {

        private DialogInterface.OnClickListener positiveButtonClickListener, negativeButtonClickListener;

        private int inputType = 0;
        private Context mContext;
        private String editCount = "1";
        private String title;
        private boolean canceledOnTouchOutside = true;
        private String maxInputValue;

        public Builder(Context context) {
            this.mContext = context;
        }

        public NumberEditDialog create() {

            final NumberEditDialog dialog = new NumberEditDialog(mContext, R.style.Custom_Dialog);

            LayoutInflater inflater = dialog.getLayoutInflater();

            View contentView = inflater.inflate(R.layout.number_edit_dialog, null);

            RelativeLayout rlDelete = (RelativeLayout) contentView.findViewById(R.id.rl_delete);
            RelativeLayout rlAdd = (RelativeLayout) contentView.findViewById(R.id.rl_add);
            TextView tvTitle = (TextView) contentView.findViewById(R.id.tv_title);
            final EditText etCount = (EditText) contentView.findViewById(R.id.et_count);
            Button btnPositive = (Button) contentView.findViewById(R.id.btn_positive);
            Button btnNagative = (Button) contentView.findViewById(R.id.btn_nagative);

            if (inputType == 0) {
                etCount.setInputType(InputType.TYPE_CLASS_NUMBER);
            } else if (inputType == 1) {
                etCount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            }

            if (!TextUtils.isEmpty(title)) {
                tvTitle.setText(title);
            }

            etCount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    editCount = s.toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            etCount.setText(editCount + "");
            etCount.setSelection(editCount.length());

            rlDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (inputType == 0) {
                        Integer integer = Integer.valueOf(etCount.getText().toString());
                        if (integer - 1 < 0) {
                            etCount.setText("0");
                        } else {
                            etCount.setText((integer - 1) + "");
                        }
                    } else if (inputType == 1) {
                        Double doubleValue = Double.valueOf(etCount.getText().toString());
                        if (doubleValue - 1 < 0) {
                            etCount.setText(new DecimalFormat("#0.00").format(0));
                        } else {
                            etCount.setText(new DecimalFormat("#0.00").format(doubleValue - 1));
                        }
                    }
                    etCount.setSelection(etCount.length());
                }
            });

            rlAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (inputType == 0) {
                        if (!TextUtils.isEmpty(maxInputValue)) {
                            //Todo
                            if (Integer.valueOf(etCount.getText().toString()) + 1 <= Integer.valueOf(maxInputValue)) {
                                etCount.setText((Integer.valueOf(etCount.getText().toString()) + 1) + "");
                            }
                        }
//                        etCount.setText((Integer.valueOf(etCount.getText().toString()) + 1) + "");
                    } else if (inputType == 1) {
                        if (Double.valueOf(etCount.getText().toString()) + 1 <= Double.valueOf(maxInputValue)) {
                            etCount.setText(new DecimalFormat("#0.00").format((Double.valueOf(etCount.getText().toString()) + 1)));
                        }
//                        etCount.setText(new DecimalFormat("#0.00").format((Double.valueOf(etCount.getText().toString()) + 1)));
                    }
                    etCount.setSelection(etCount.length());
                }
            });

            if (positiveButtonClickListener != null) {
                btnPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                    }
                });
            }

            if (negativeButtonClickListener != null) {
                btnNagative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                    }
                });
            }

            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);

            Window window = dialog.getWindow();

            window.setGravity(Gravity.CENTER);

            DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();

            int windowWidth = displayMetrics.widthPixels;

            LinearLayout llContent = (LinearLayout) contentView.findViewById(R.id.ll_content);
            LinearLayout llButtons = (LinearLayout) contentView.findViewById(R.id.ll_buttons);

            int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

            llContent.measure(width, height);
            llButtons.measure(width, height);

            int contentHeight = llContent.getMeasuredHeight();
            int buttonsHeight = llButtons.getMeasuredHeight();

            dialog.setContentView(contentView, new ViewGroup.MarginLayoutParams((int) (windowWidth * 0.8), contentHeight + buttonsHeight));

            return dialog;
        }

        /**
         * 获取编辑框数字
         *
         * @return
         */
        public String getCount() {
            return editCount;
        }

        /**
         * 设置编辑框数字
         *
         * @param initCount
         */
        public Builder setCount(String initCount) {
            this.editCount = initCount;
            return this;
        }

        /**
         * 设置确定按钮监听
         *
         * @param positiveButtonClickListener
         */
        public Builder setPositiveButtonClickListener(OnClickListener positiveButtonClickListener) {
            this.positiveButtonClickListener = positiveButtonClickListener;
            return this;
        }

        /**
         * 设置取消按钮监听
         *
         * @param negativeButtonClickListener
         */
        public Builder setNegativeButtonClickListener(OnClickListener negativeButtonClickListener) {
            this.negativeButtonClickListener = negativeButtonClickListener;
            return this;
        }

        /**
         * 设置触摸是否可以取消(默认true)
         *
         * @param canceledOnTouchOutside
         */
        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        /**
         * 设置标题
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * @param inputType InputType.TYPE_CLASS_NUMBER
         *                  InputType.TYPE_NUMBER_FLAG_DECIMAL
         */
        public Builder setInputType(int inputType) {
            this.inputType = inputType;
            return this;
        }

        /**
         * 设置输入最大值
         *
         * @param maxInputValue
         * @return
         */
        public Builder setMaxInputValue(String maxInputValue) {
            this.maxInputValue = maxInputValue;
            return this;
        }
    }

}
