package com.bityan.androidskills;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.Looper;
import android.util.Log;

public class AppUncaughtExceptionHandler implements UncaughtExceptionHandler{

	Context mContext;
	static AppUncaughtExceptionHandler handler = null;
	
	public static AppUncaughtExceptionHandler getInstance()
	{
		if(handler == null)
		{
			handler = new AppUncaughtExceptionHandler();
		}
			
		return handler;
	}
	
	//初始化操作
	public void initCrashHandler(Context context)
	{
		mContext = context;
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		
//		//记录错误信息，上传报告
//		AlertDialog.Builder builder = new Builder(mContext);
//		builder.setTitle(thread.toString()+":"+ex.toString());
//		builder.create().show();

//		// 退出程序
//		android.os.Process.killProcess(android.os.Process.myPid());
//		System.exit(1);
	}

}
