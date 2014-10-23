package com.qust.demo;

import android.content.Context;
import android.view.WindowManager;

/**
 * 屏幕帮助类
 * 
 * @author zhaokaiqiang
 * 
 */
public class ScreenUtils {

	/**
	 * 获取屏幕宽度
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getScreenWidth(Context context) {
		return ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
				.getWidth();
	}

	/**
	 * 获取屏幕宽度
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getScreenHeight(Context context) {
		return ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
				.getHeight();
	}

}
