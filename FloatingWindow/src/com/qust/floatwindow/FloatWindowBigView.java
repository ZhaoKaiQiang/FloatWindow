package com.qust.floatwindow;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qust.demo.ScreenUtils;
import com.qust.floatingwindow.R;

public class FloatWindowBigView extends LinearLayout {

	// 记录大悬浮窗的宽
	public int viewWidth;
	// 记录大悬浮窗的高
	public int viewHeight;

	public WindowManager.LayoutParams bigWindowParams;

	private Context context;

	public FloatWindowBigView(Context context) {
		super(context);
		this.context = context;

		LayoutInflater.from(context).inflate(R.layout.float_window_big, this);

		View view = findViewById(R.id.big_window_layout);
		viewWidth = view.getLayoutParams().width;
		viewHeight = view.getLayoutParams().height;

		bigWindowParams = new WindowManager.LayoutParams();
		// 设置显示的位置，默认的是屏幕中心
		bigWindowParams.x = ScreenUtils.getScreenWidth(context) / 2 - viewWidth
				/ 2;
		bigWindowParams.y = ScreenUtils.getScreenHeight(context) / 2
				- viewHeight / 2;
		bigWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
		bigWindowParams.format = PixelFormat.RGBA_8888;

		// 设置交互模式
		bigWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

		bigWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
		bigWindowParams.width = viewWidth;
		bigWindowParams.height = viewHeight;

		initView();

	}

	private void initView() {
		TextView tv_back = (TextView) findViewById(R.id.tv_back);
		tv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FloatWindowManager.getInstance(context).removeBigWindow();
			}
		});
	}

}
