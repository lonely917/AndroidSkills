package com.bityan.androidskills;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * 这里实现UncaughtExceptionHandler接口，并set默认未处理异常回调，会覆盖application中设置的handler
 * 结合AppUncaughtExceptionHandler和MyApplication学习
 * @author yan
 *
 */
public class TestCrashHandlerActivity extends Activity implements UncaughtExceptionHandler{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//屏蔽此句则交由application中设定的handler处理，否则调用此Activity中的回调处理
//		Thread.setDefaultUncaughtExceptionHandler(this);
		
		Log.i("ywb", "onCreate:"+Thread.currentThread());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_crash_handler);
		
//		//主线程空指针异常测试
//		TextView tv = null;
//		tv.findViewById(R.id.modeTestTextView);
		
		//子线程异常测试
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				int a[] = new int[2];
				a[3] = 1;//异常
			}
		}).start();
	}

	
	@Override
	public void uncaughtException(final Thread thread, final Throwable ex) {
		
		Log.e("ywb", Thread.currentThread().toString());
		Log.e("ywb", this+":"+thread.getId()+":"+thread.toString()+":"+ex.toString());
		
		// 退出程序，这种方式退出，系统可能会试图重启此应用.此应用.
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(1);
	}
}

/**
 * 
主线程空指针异常
10-21 14:34:47.259 18884 18884 I ywb     : onCreate:Thread[main,5,main]
10-21 14:34:47.269 18884 18884 E ywb     : com.bityan.androidskills.TestCrashHandlerActivity@c90dba4
:1:Thread[main,5,main]:java.lang.RuntimeException: Unable to start activity ComponentInfo{com.bityan.
androidskills/com.bityan.androidskills.TestCrashHandlerActivity}: java.lang.NullPointerException: At
tempt to invoke virtual method 'android.view.View android.widget.TextView.findViewById(int)' on a nu
ll object reference
10-21 14:34:47.449 18917 18917 I ywb     : onCreate:Thread[main,5,main]
10-21 14:34:47.469 18917 18917 E ywb     : com.bityan.androidskills.TestCrashHandlerActivity@d2b6b75
:1:Thread[main,5,main]:java.lang.RuntimeException: Unable to start activity ComponentInfo{com.bityan.
androidskills/com.bityan.androidskills.TestCrashHandlerActivity}: java.lang.NullPointerException: At
tempt to invoke virtual method 'android.view.View android.widget.TextView.findViewById(int)' on a nu
ll object reference
10-21 14:34:47.619 18930 18930 I ywb     : onCreate:Thread[main,5,main]
10-21 14:34:47.639 18930 18930 E ywb     : com.bityan.androidskills.TestCrashHandlerActivity@d2b6b75
:1:Thread[main,5,main]:java.lang.RuntimeException: Unable to start activity ComponentInfo{com.bityan.
androidskills/com.bityan.androidskills.TestCrashHandlerActivity}: java.lang.NullPointerException: At
tempt to invoke virtual method 'android.view.View android.widget.TextView.findViewById(int)' on a nu
ll object reference

子线程数组越界异常
an.androidskills.TestCrashHandlerActivity}: java.lang.NullPointerException
I/ywb     (15637): application thread:Thread[main,5,main]-id:1
I/ywb     (15637): onCreate:Thread[main,5,main]
E/ywb     (15637): Thread[Thread-1783,5,main]
E/ywb     (15637): com.bityan.androidskills.TestCrashHandlerActivity@431188d0:1783:Thread[Thread-178
3,5,main]:java.lang.ArrayIndexOutOfBoundsException: length=2; index=3
I/ywb     (15705): application thread:Thread[main,5,main]-id:1
I/ywb     (15705): onCreate:Thread[main,5,main]
E/ywb     (15705): Thread[Thread-1787,5,main]
E/ywb     (15705): com.bityan.androidskills.TestCrashHandlerActivity@430f03a8:1787:Thread[Thread-178
7,5,main]:java.lang.ArrayIndexOutOfBoundsException: length=2; index=3
I/ywb     (15725): application thread:Thread[main,5,main]-id:1
I/ywb     (15725): onCreate:Thread[main,5,main]
E/ywb     (15725): Thread[Thread-1790,5,main]
E/ywb     (15725): com.bityan.androidskills.TestCrashHandlerActivity@430f12e8:1790:Thread[Thread-179
0,5,main]:java.lang.ArrayIndexOutOfBoundsException: length=2; index=3

reference:
http://www.cnblogs.com/jycboy/p/5754396.html
https://www.intertech.com/Blog/android-handling-the-unexpected/
 */