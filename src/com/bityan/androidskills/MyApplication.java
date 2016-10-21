package com.bityan.androidskills;

import android.app.Application;
import android.app.ApplicationErrorReport.CrashInfo;
import android.util.Log;

/**
 * 全局application类，需要在AndroidManifest文件注册
 * @author yan
 *
 */
public class MyApplication extends Application {

	@Override
	public void onCreate() {
		
		super.onCreate();
		Log.i("ywb", "application thread:"+Thread.currentThread().toString()+"-id:"+Thread.currentThread().getId());
		AppUncaughtExceptionHandler handler = AppUncaughtExceptionHandler.getInstance();
		handler.initCrashHandler(getApplicationContext());
	}
}
