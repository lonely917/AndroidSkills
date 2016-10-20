package com.bityan.androidskills;

import android.app.Application;
import android.app.ApplicationErrorReport.CrashInfo;

/**
 * 全局application类，需要在AndroidManifest文件注册
 * @author yan
 *
 */
public class MyApplication extends Application {

	@Override
	public void onCreate() {
		
		super.onCreate();
		AppUncaughtExceptionHandler handler = AppUncaughtExceptionHandler.getInstance();
		handler.initCrashHandler(getApplicationContext());
		Thread.setDefaultUncaughtExceptionHandler(handler);
	}
}
