package com.bityan.androidskills;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class TestHandlerActivity extends Activity {

	Handler threadHandler;//在子线程中使用
	Thread childThread;//子线程
	int limit = 10;
	
	//不指定looper，默认选择当前线程的looper，必须在Looper.prepare()方法后才可以执行，这里系统框架在其他地方调用过了
	Handler mainThreadHandler = new Handler(){
		
		
		
		@Override
		public void handleMessage(Message msg) {
			Log.i("ywb", Thread.currentThread().getName() +" get message from thread "+msg.obj);
//			msg.recycle();//这里recycle是配合线程池使用，参考下面的obtain源码细节
			
			limit--;
			//向子线程发送消息,obtain高效使用Message
			Message m = Message.obtain();
//			Message m = new Message();
			m.obj = Thread.currentThread().getName().toString();
			threadHandler.sendMessage(m);
//			threadHandler.sendEmptyMessage(0);
			
			if(limit==0)
				threadHandler.getLooper().quit();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_handler);
		
		childThread =  new Thread("child"){
			public void run() {
				
				//首先调用prepare才可以去初始化handler
				Looper.prepare();
				
				//尝试线程toast，和主UI不同的viewRoot，activity退出后依然存在
//				Toast.makeText(getApplicationContext(), "toast from thread 1", Toast.LENGTH_SHORT).show();
				
				threadHandler = new Handler(Looper.myLooper()){
					@Override
					public void handleMessage(Message msg) {
						
						Log.i("ywb", Thread.currentThread().getName() +" get message from thread "+msg.obj);
//						msg.recycle();//并没有找到合理的使用地方，系统会自己调用recycleUnchecked方法
						
						//向主线程发送消息,obtain高效使用Message
						Message m = Message.obtain();
//						Message m = new Message();
						m.obj = Thread.currentThread().getName().toString();
						mainThreadHandler.sendMessage(m);
//						mainThreadHandler.sendEmptyMessage(0);
						
					}
				};
				
//				//向主线程发送消息
				Message m = Message.obtain();
//				Message m = new Message();
				m.obj = Thread.currentThread().getName().toString();
				mainThreadHandler.sendMessage(m);
//				mainThreadHandler.sendEmptyMessage(0);
				
				//进入消息循环
				Looper.loop();
				
				//loop被quit的时候进入这里
				Log.i("ywb", "childthread ends");
			};
		};
		
		//启动子线程
		childThread.start();
		
	}
	
	@Override
	protected void onDestroy() {
		
		//结束线程，通过handler获取线程looper进而quit结束消息循环，线程结束
		mainThreadHandler.removeCallbacksAndMessages(null);
		super.onDestroy();
	}
	
}

/**
 * obtain和recycle巨坑...不深入理解滥用所谓的高效方法会出莫名其妙的问题
 * */
