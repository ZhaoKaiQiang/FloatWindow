package com.qust.floatwindow;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qust.demo.ScreenUtils;
import com.qust.floatingwindow.R;

/**
 * 小悬浮窗,用于初始显示
 * 
 * @author zhaokaiqiang
 * 
 */
public class FloatWindowSmallView extends LinearLayout {

	// 小悬浮窗的宽
	public int viewWidth;
	// 小悬浮窗的高
	public int viewHeight;
	// 系统状态栏的高度
	private static int statusBarHeight;
	// 用于更新小悬浮窗的位置
	private WindowManager windowManager;
	// 小悬浮窗的布局参数
	public WindowManager.LayoutParams smallWindowParams;
	// 记录当前手指位置在屏幕上的横坐标
	private float xInScreen;
	// 记录当前手指位置在屏幕上的纵坐标
	private float yInScreen;
	// 记录手指按下时在屏幕上的横坐标,用来判断单击事件
	private float xDownInScreen;
	// 记录手指按下时在屏幕上的纵坐标,用来判断单击事件
	private float yDownInScreen;
	// 记录手指按下时在小悬浮窗的View上的横坐标
	private float xInView;
	// 记录手指按下时在小悬浮窗的View上的纵坐标
	private float yInView;
	// 单击接口
	private OnClickListener listener;

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            上下文对象
	 * @param layoutResId
	 *            布局资源id
	 * @param rootLayoutId
	 *            根布局id
	 */
	public FloatWindowSmallView(Context context, int layoutResId,
			int rootLayoutId) {
		super(context);
		windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		LayoutInflater.from(context).inflate(layoutResId, this);
		View view = findViewById(rootLayoutId);
		viewWidth = view.getLayoutParams().width;
		viewHeight = view.getLayoutParams().height;

		statusBarHeight = getStatusBarHeight();

		TextView percentView = (TextView) findViewById(R.id.percent);
		percentView.setText("悬浮窗");

		smallWindowParams = new WindowManager.LayoutParams();
		// 设置显示类型为phone
		smallWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
		// 显示图片格式
		smallWindowParams.format = PixelFormat.RGBA_8888;
		// 设置交互模式
		smallWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		// 设置对齐方式为左上
		smallWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
		smallWindowParams.width = viewWidth;
		smallWindowParams.height = viewHeight;
		smallWindowParams.x = ScreenUtils.getScreenWidth(context);
		smallWindowParams.y = ScreenUtils.getScreenHeight(context) / 2;

	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		// 手指按下时记录必要的数据,纵坐标的值都减去状态栏的高度
		case MotionEvent.ACTION_DOWN:
			// 获取相对与小悬浮窗的坐标
			xInView = event.getX();
			yInView = event.getY();
			// 按下时的坐标位置，只记录一次
			xDownInScreen = event.getRawX();
			yDownInScreen = event.getRawY() - statusBarHeight;
			break;
		case MotionEvent.ACTION_MOVE:
			// 时时的更新当前手指在屏幕上的位置
			xInScreen = event.getRawX();
			yInScreen = event.getRawY() - statusBarHeight;
			// 手指移动的时候更新小悬浮窗的位置
			updateViewPosition();
			break;
		case MotionEvent.ACTION_UP:
			// 如果手指离开屏幕时，按下坐标与当前坐标相等，则视为触发了单击事件
			if (xDownInScreen == event.getRawX()
					&& yDownInScreen == (event.getRawY() - getStatusBarHeight())) {

				if (listener != null) {
					listener.click();
				}

			}
			break;
		}
		return true;
	}

	/**
	 * 设置单击事件的回调接口
	 */
	public void setOnClickListener(OnClickListener listener) {
		this.listener = listener;
	}

	/**
	 * 更新小悬浮窗在屏幕中的位置
	 */
	private void updateViewPosition() {
		smallWindowParams.x = (int) (xInScreen - xInView);
		smallWindowParams.y = (int) (yInScreen - yInView);
		windowManager.updateViewLayout(this, smallWindowParams);
	}

	/**
	 * 获取状态栏的高度
	 * 
	 * @return
	 */
	private int getStatusBarHeight() {

		try {
			Class<?> c = Class.forName("com.android.internal.R$dimen");
			Object o = c.newInstance();
			Field field = c.getField("status_bar_height");
			int x = (Integer) field.get(o);
			return getResources().getDimensionPixelSize(x);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 单击接口
	 * 
	 * @author zhaokaiqiang
	 * 
	 */
	public interface OnClickListener {

		public void click();

	}

}
