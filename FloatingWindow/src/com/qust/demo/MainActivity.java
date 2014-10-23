package com.qust.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.qust.floatingwindow.R;
import com.qust.floatwindow.FloatWindowManager;
import com.qust.floatwindow.FloatWindowService;
import com.qust.floatwindow.FloatWindowSmallView.OnClickListener;

/**
 * 示例
 * 
 * @ClassName: com.qust.demo.MainActivity
 * @Description:
 * @author zhaokaiqiang
 * @date 2014-10-23 下午11:30:13
 * 
 */
public class MainActivity extends Activity {

	private FloatWindowManager floatWindowManager;

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context = this;
		floatWindowManager = FloatWindowManager.getInstance(context);

	}

	/**
	 * 显示小窗口
	 * 
	 * @param view
	 */
	public void show(View view) {
		// 需要传递小悬浮窗布局，以及根布局的id，启动后台服务
		Intent intent = new Intent(context, FloatWindowService.class);
		intent.putExtra(FloatWindowService.LAYOUT_RES_ID,
				R.layout.float_window_small);
		intent.putExtra(FloatWindowService.ROOT_LAYOUT_ID,
				R.id.small_window_layout);
		startService(intent);
	}

	/**
	 * 显示二级悬浮窗
	 * 
	 * @param view
	 */
	public void showBig(View view) {

		// 设置小悬浮窗的单击事件
		floatWindowManager.setOnClickListener(new OnClickListener() {

			@Override
			public void click() {
				floatWindowManager.createBigWindow(context);
			}
		});

	}

	/**
	 * 移除所有的悬浮窗
	 * 
	 * @param view
	 */
	public void remove(View view) {
		floatWindowManager.removeAll();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		// 返回键移除二级悬浮窗
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			floatWindowManager.removeBigWindow();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

}
