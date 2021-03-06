package com.xsm.widgets.timepicker;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xsm.widgets.R;
import com.xsm.widgets.timepicker.configure.PickerOptions;

import java.text.ParseException;
import java.util.Date;

/**
 * Author: 夏胜明
 * Date: 2018/4/14 0014
 * Email: xiasem@163.com
 * Description:
 */

public class TimePickerView extends BasePickerView implements View.OnClickListener {
    private WheelTime wheelTime;
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";

    public TimePickerView(PickerOptions pickerOptions) {
        super(pickerOptions.context);
        mPickerOptions = pickerOptions;
        initView(mPickerOptions.context);
    }

    private void initView(Context context) {
        initViews();
        initAnim();
        LayoutInflater.from(context).inflate(R.layout.pickerview_time, contentContainer);
        //顶部标题
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        RelativeLayout rv_top_bar = (RelativeLayout) findViewById(R.id.rv_topbar);

        //确定和取消按钮
        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSubmit.setTag(TAG_SUBMIT);
        btnCancel.setTag(TAG_CANCEL);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        //设置文字
        btnSubmit.setText(TextUtils.isEmpty(mPickerOptions.textContentConfirm) ? context.getResources().getString(R.string.pickerview_submit) : mPickerOptions.textContentConfirm);
        btnCancel.setText(TextUtils.isEmpty(mPickerOptions.textContentCancel) ? context.getResources().getString(R.string.pickerview_cancel) : mPickerOptions.textContentCancel);
        tvTitle.setText(TextUtils.isEmpty(mPickerOptions.textContentTitle) ? "" : mPickerOptions.textContentTitle);//默认为空

        //设置color
        btnSubmit.setTextColor(mPickerOptions.textColorConfirm);
        btnCancel.setTextColor(mPickerOptions.textColorCancel);
        tvTitle.setTextColor(mPickerOptions.textColorTitle);
        rv_top_bar.setBackgroundColor(mPickerOptions.bgColorTitle);

        //设置文字大小
        btnSubmit.setTextSize(mPickerOptions.textSizeSubmitCancel);
        btnCancel.setTextSize(mPickerOptions.textSizeSubmitCancel);
        tvTitle.setTextSize(mPickerOptions.textSizeTitle);
        // 时间转轮 自定义控件
        LinearLayout timePickerView = (LinearLayout) findViewById(R.id.timepicker);
        timePickerView.setBackgroundColor(mPickerOptions.bgColorWheel);

        initWheelTime(timePickerView);
    }

    private void initWheelTime(LinearLayout timePickerView) {
        wheelTime = new WheelTime(timePickerView);
        wheelTime.setPicker(mPickerOptions.customerDate, mPickerOptions.customerNum);
    }

    @Override
    public void onClick(View view) {
        String tag = (String) view.getTag();
        if (tag.equals(TAG_SUBMIT)) {
            returnData();
        }
        dismiss();
    }

    public void returnData() {
        if (mPickerOptions.timeSelectListener != null) {
            try {
                String time = wheelTime.getTime();
                Date date = WheelTime.dateFormat.parse(time);
                mPickerOptions.timeSelectListener.onTimeSelect(date, clickView);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
