package com.bityan.androidskills;

import com.bityan.androidskills.TestService.SimpleBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ThreadAndServiceActivity extends Activity implements OnClickListener{


	TestThread th1;
	TestThread th2;
	Intent intentForService;
	ServiceConnection sc;
	Boolean isBind = false;
    TextView startServiceTextView, stopServiceTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thread_and_service);
		
		//结合xml看四个textview的响应设置，使用了不同的方式，xml中指定onclick对应方法的话必须主动设置textview的clickable为true
		findViewById(R.id.textView0).setOnClickListener(this);
		findViewById(R.id.textView1).setOnClickListener(this);
		
		//开启一个测试线程
		th1 = new TestThread(this.toString());
		th1.start();
		
		//准备开启一个服务
		intentForService = new Intent(this, TestService.class);
//		intentForService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//配合manifest 文件中service的process属性，会启动一个新的进程，application会再次onCreate
//		startService(intentForService);
		
		//sc 用于bind
		sc = new ServiceConnection() {
			
			@Override
			public void onServiceDisconnected(ComponentName name) {
				Log.i("ywb", "service disconnected");
			}
			
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				Log.i("ywb", "service connected");		
				
				SimpleBinder sb = (SimpleBinder)service;
				Log.i("ywb", "binder call :" + sb.saySomething());	
			}
		};
		
		
	}
	
	@Override
	protected void onDestroy() {
		
		//Activity退出的时候停止线程th1
		th1.stopThread();
		
		//停止服务
//		stopService(intentForService);
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.textView0:
			startService(intentForService);
			break;
		case R.id.textView1:
			stopService(intentForService);
			break;
		case R.id.textView2:
			bindService(intentForService, sc, BIND_AUTO_CREATE);
			isBind = true;
			break;
		case R.id.textView3:
			if(isBind)
			{
				unbindService(sc); //多次调用 unbindService 来释放相同的连接会抛出异常，如果被调用了stopservice再次unbind也会出问题
				isBind = false;
			}
			break;
		default:
			break;
		}
	}
}

/**
 * 线程开启后，如果没有关闭或者主动结束，则会在Activity结束后继续执行。
 * 不加判别的使用会使得下次Activity调用的时候又启动一次。
 * 
 * 对于一个耗时操作应该开启另外一个线程去处理而不应该在UI线程去做，那么无论Activity还是Service中都应该用一个线程
 * 去做background的工作，如果activity开启的线程工作域超出活动范围，那么在activity结束后就没有对象引用了，线程也就不可控了，
 * 当然这里可以有些方式达到目的，比如静态变量或者单例模式等等，但可能涉及内存泄漏，而service组建则可以承担这一任务，本身是一个单例模式，
 * 可以被其他activity多次start，也就是进行控制，也可以独立于程序被其他程序调用，因此其中的thread也就不存在超出工作空间的问题，
 * 对于thread要注意其生存周期与所属对象的生存周期匹配(这更多是一个设计原则而非必要项)，问题不在于何时使用service何时开启thread，
 * 二者不在一个维度，只是这个thread应该放在那里的问题。
 * 
 * 在另外一个进程中启动service会使得对应的application再次onCreate
 */
