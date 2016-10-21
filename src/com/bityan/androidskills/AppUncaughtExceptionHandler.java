package com.bityan.androidskills;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.Looper;
import android.util.Log;

public class AppUncaughtExceptionHandler implements UncaughtExceptionHandler{

	Context mContext;
	Thread.UncaughtExceptionHandler defaultExceptionHandler = null;
	
	//单例模式
	static AppUncaughtExceptionHandler handler = null;
	
	public static AppUncaughtExceptionHandler getInstance()
	{
		if(handler == null)
		{
			handler = new AppUncaughtExceptionHandler();
		}
			
		return handler;
	}
	
	//初始化操作，获取单例后调用此方法初始化
	public void initCrashHandler(Context context)
	{
		mContext = context;
		defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	
	@Override
	public void uncaughtException(final Thread thread, final Throwable ex) {
		
		Log.e("ywb", Thread.currentThread().toString());
		Log.e("ywb", this.toString()+":"+thread.getId()+":"+thread.toString()+":"+ex.toString());
		
		//记录错误信息，上传报告
		//......
//		//提示，此处不能够尝试UI操作，进入到这里的时候，相关问题线程已经die掉
//		new Thread()
//		{
//			public void run() {
//				
//				Looper.prepare();
//				AlertDialog.Builder builder = new Builder(mContext);
//				builder.setTitle("AppUncaughtException!")
//						.setMessage(thread.toString()+":"+ex.toString());
//				builder.create().show();
//				Looper.prepare();
//			};
//		}.start();

		
		//后续处理，交由系统处理或者主动退出，前文Thread.getDefaultUncaughtExceptionHandler()确定这里进入if分支
		if(defaultExceptionHandler!=null)
		{
			//系统最初默认的处理，这个是在那里添加上的，源码里没有找到？，if的这个分支处理只对应一次异常信息，不会再次触发异常
			defaultExceptionHandler.uncaughtException(thread, ex);
		}
		
		else
		{
			// 退出程序，执行这部分代码会导致错误信息多打印两边？又多进入两次异常处理？ 这里退出后程序试图重启了两次
			//init的时候提前获取了default的handler，不会再进入这个分支
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}

	}

}
