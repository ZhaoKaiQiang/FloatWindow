package com.qust.floatwindow;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

/**
 * 悬浮窗后台服务
 * 
 * @author zhaokaiqiang
 * 
 */

public class FloatWindowService extends Service {

	public static final String LAYOUT_RES_ID = "layoutResId";
	public static final String ROOT_LAYOUT_ID = "rootLayoutId";

	// 用于在线程中创建/移除/更新悬浮窗
	private Handler handler = new Handler();
	private Context context;
	private Timer timer;
	// 小窗口布局资源id
	private int layoutResId;
	// 布局根布局id
	private int rootLayoutId;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		context = this;
		layoutResId = intent.getIntExtra(LAYOUT_RES_ID, 0);
		rootLayoutId = intent.getIntExtra(ROOT_LAYOUT_ID, 0);

		if (layoutResId == 0 || rootLayoutId == 0) {
			throw new IllegalArgumentException(
					"layoutResId or rootLayoutId is illegal");
		}

		if (timer == null) {
			timer = new Timer();
			// 每500毫秒就执行一次刷新任务
			timer.scheduleAtFixedRate(new RefreshTask(), 0, 500);
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Service被终止的同时也停止定时器继续运行
		timer.cancel();
		timer = null;
	}

	private class RefreshTask extends TimerTask {

		@Override
		public void run() {
			// 当前界面没有悬浮窗显示，则创建悬浮
			if (!FloatWindowManager.getInstance(context).isWindowShowing()) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						FloatWindowManager.getInstance(context)
								.createSmallWindow(context, layoutResId,
										rootLayoutId);
					}
				});
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
