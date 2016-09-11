package com.mymiu.utils;

import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;

import com.mymiu.R;

/**
 * 忘记密码页倒计时工具类
 * */
public class FindPhoneTimeCount extends CountDownTimer {

	private SpannableString msp = null;
	private int timeNum = 60;
	private Button view;

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = (Button)view;
	}

	public FindPhoneTimeCount(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
		// TODO Auto-generated constructor stub

	}

	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		// 创建一个 SpannableString对象
		msp = new SpannableString("获取验证码");

		// 设置字体背景色
		msp.setSpan(1, 0, 5,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为

		view.setText(msp);
		view.setEnabled(true);
		view.setBackgroundResource(R.drawable.default_btn);
		setTimeNum(60);
	}

	@Override
	public void onTick(long millisUntilFinished) {
		//forgetpsdactivity.getGet_code_textview().setEnabled(false);
		int timeInt = (int) (millisUntilFinished / 1000);
		setTimeNum(timeInt);

		// 创建一个 SpannableString对象
		msp = new SpannableString("重新获取" + "(" + timeInt + "s)");
		view.setText(msp);
		view.setEnabled(false);
		view.setBackgroundResource(R.color.colorGray);
	}

	public int getTimeNum() {
		return timeNum;
	}

	public void setTimeNum(int timeNum) {
		this.timeNum = timeNum;
	}

}
