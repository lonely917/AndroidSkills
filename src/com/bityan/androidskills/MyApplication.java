package com.bityan.androidskills;

import java.util.Date;

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
		
		
		//尝试启动一个thread
		Thread thread = new TestThread("thread in application");
//		thread.start();
	}
	
	@Override
	public void onLowMemory() {
		Log.i("ywb", "onLowMemory");
		super.onLowMemory();
	}
	
	//下面这个方法只是用作模拟器，实际设备永远不会触发，不要被名字迷惑
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		Log.i("ywb", "onTerminate");
		super.onTerminate();
	}
}

/**
 * application和Activity中可以使用thread，这个thread在程序生命周期都是有效的.
 * 多次启动一个Activity可能多次触发新线程开启，对应处理方法
 * #1可以通过Activity的生命周期去控制线程启动结束，重点是结束，不主动结束，则Activity退出thread依然存在；
 * #2application中开启thread，避免Activity多次开启导致多次调用thread开启。
 * 当然程序设计方法有很多解决办法，比如Activity中设置thread的控制变量，提供具体操控，这里可能使用静态变量和单例模式思想，
 * 或者提供对外接口控制线程，达到暴露给外部进行控制的目的。
 * 
 * service和thread不是互斥的两个，service中的耗时操作也要开启thread去处理。
 * 默认thread依附于某一Activity，即在某一Activity开启或者控制，其他Activity不好控制(并非不可实现)
 * service可以从Activity进行开启，甚至可以由其他程序开启。
 */
