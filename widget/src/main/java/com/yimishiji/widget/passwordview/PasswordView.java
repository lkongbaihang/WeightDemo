package com.yimishiji.widget.passwordview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yimishiji.app.widget.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gsd on 2016/10/19.
 */
public class PasswordView extends RelativeLayout {

    private Context context;

    private String strPassword;     //输入的密码
    private TextView[] tvList;      //用数组保存6个TextView，为什么用数组？
    //因为就6个输入框不会变了，用数组内存申请固定空间，比List省空间（自己认为）
    private GridView gridView;    //用GrideView布局键盘，其实并不是真正的键盘，只是模拟键盘的功能
    private ArrayList<Map<String, String>> valueList;    //有人可能有疑问，为何这里不用数组了？
    //因为要用Adapter中适配，用数组不能往adapter中填充

    private ImageView imgCancel;
    private TextView tvForget;
    private TextView tvInputNotice;
    private int currentIndex = -1;    //用于记录当前输入密码格位置

    public PasswordView(Context context) {
        this(context, null);
    }

    public PasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View view = View.inflate(context, R.layout.view_password_input, null);

        valueList = new ArrayList<Map<String, String>>();
        tvList = new TextView[6];

        imgCancel = (ImageView) view.findViewById(R.id.img_cancel);
//        imgCancel.setOnClickListener(this);

        tvForget = (TextView) view.findViewById(R.id.tv_forgetPwd);
        tvInputNotice = view.findViewById(R.id.tv_input_notice);
//        tvForget.setOnClickListener(this);

        tvList[0] = (TextView) view.findViewById(R.id.tv_pass1);
        tvList[1] = (TextView) view.findViewById(R.id.tv_pass2);
        tvList[2] = (TextView) view.findViewById(R.id.tv_pass3);
        tvList[3] = (TextView) view.findViewById(R.id.tv_pass4);
        tvList[4] = (TextView) view.findViewById(R.id.tv_pass5);
        tvList[5] = (TextView) view.findViewById(R.id.tv_pass6);

        gridView = (GridView) view.findViewById(R.id.gv_keybord);

        gridView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return MotionEvent.ACTION_MOVE == event.getAction() ? true : false;
            }

        });

        setView();

        addView(view);      //必须要，不然不显示控件
    }

//    @Override
//    public void onClick(View v) {
//
//        int i = v.getId();
//        if (i == R.id.img_cancel) {
//            Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show();
//
//        } else if (i == R.id.tv_forgetPwd) {
//            Toast.makeText(context, "Forget", Toast.LENGTH_SHORT).show();
//
//        }
//    }

    private void setView() {
        /* 初始化按钮上应该显示的数字 */
        for (int i = 1; i < 13; i++) {
            Map<String, String> map = new HashMap<>();
            if (i < 10) {
                map.put("name", String.valueOf(i));
            } else if (i == 10) {
                map.put("name", "");
            } else if (i == 12) {
//                map.put("name", "<<-");
                map.put("name", "");
            } else if (i == 11) {
                map.put("name", String.valueOf(0));
            }
            valueList.add(map);
        }

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 11 && position != 9) {    //点击0~9按钮
                    if (currentIndex >= -1 && currentIndex < 5) {      //判断输入位置————要小心数组越界
                        tvList[++currentIndex].setText(valueList.get(position).get("name"));
                    }
                } else {
                    if (position == 11) {      //点击退格键
                        if (currentIndex - 1 >= -1) {      //判断是否删除完毕————要小心数组越界
                            tvList[currentIndex--].setText("");
                        }
                    }
                }
            }
        });
    }

    //设置监听方法，在第6位输入完成后触发
    public void setOnFinishInput(final OnPasswordInputFinish pass) {
        tvList[5].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    strPassword = "";     //每次触发都要先将strPassword置空，再重新获取，避免由于输入删除再输入造成混乱
                    for (int i = 0; i < 6; i++) {
                        strPassword += tvList[i].getText().toString().trim();
                    }
                    pass.inputFinish();    //接口中要实现的方法，完成密码输入完成后的响应逻辑
                }
            }
        });
    }

    /**
     * 全部清空输入框内容
     */
    public void clearInput() {
        for (int i = 0; i < tvList.length; i++) {
            TextView textView = tvList[i];
            textView.setText("");
        }
        currentIndex = -1;
    }

    /**
     * 获取输入的密码
     *
     * @return
     */
    public String getStrPassword() {
        return strPassword;
    }

    /**
     * 获取取消支付的按钮控件,便于定制
     *
     * @return
     */
    public ImageView getCancelImageView() {
        return imgCancel;
    }

    /**
     * 获取忘记密码的按钮控件,便于定制
     *
     * @return
     */
    public TextView getForgetTextView() {
        return tvForget;
    }

    /**
     * 获取错误提示的按钮控件,便于定制
     *
     * @return
     */
    public TextView getInputNoticeTextView() {
        return tvInputNotice;
    }

    //GrideView的适配器
    BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return valueList.size();
        }

        @Override
        public Object getItem(int position) {
            return valueList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                if (position == 11) {
                    convertView = View.inflate(context, R.layout.item_gride_key_back, null);
                    float density = context.getResources().getDisplayMetrics().density;

                    convertView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (54 * density + 0.5f)));

                    viewHolder.llKeyBack = (LinearLayout) convertView.findViewById(R.id.ll_key_back);
                } else {
                    convertView = View.inflate(context, R.layout.item_gride_key, null);
                    viewHolder.llKey = (LinearLayout) convertView.findViewById(R.id.ll_key);
                    viewHolder.btnKey = (TextView) convertView.findViewById(R.id.tv_key);
                    viewHolder.btnKeyWord = (TextView) convertView.findViewById(R.id.tv_key_word);
                }

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (position != 11) {
                viewHolder.btnKey.setText(valueList.get(position).get("name"));
                viewHolder.llKey.setBackgroundResource(R.drawable.selector_password_key_number);
            } else {
                viewHolder.llKeyBack.setBackgroundResource(R.drawable.selector_password_key_other);
            }

            if (position == 1) {
                viewHolder.btnKeyWord.setText("ABC");
            } else if (position == 2) {
                viewHolder.btnKeyWord.setText("DEF");
            } else if (position == 3) {
                viewHolder.btnKeyWord.setText("GHI");
            } else if (position == 4) {
                viewHolder.btnKeyWord.setText("JKL");
            } else if (position == 5) {
                viewHolder.btnKeyWord.setText("MNO");
            } else if (position == 6) {
                viewHolder.btnKeyWord.setText("PQRS");
            } else if (position == 7) {
                viewHolder.btnKeyWord.setText("TUV");
            } else if (position == 8) {
                viewHolder.btnKeyWord.setText("WXYZ");
            } else if (position == 9) {
                viewHolder.llKey.setBackgroundResource(R.drawable.selector_password_key_other);
                viewHolder.llKey.setEnabled(false);
            }

//            if (position == 11) {
//                viewHolder.llKeyBack.setBackgroundResource(R.drawable.selector_password_key_other);
//            }

            return convertView;
        }
    };

    /**
     * 存放控件
     */
    public final class ViewHolder {
        public TextView btnKey;
        public TextView btnKeyWord;
        public LinearLayout llKey;
        public LinearLayout llKeyBack;
    }
}
