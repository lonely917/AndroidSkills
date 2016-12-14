package com.bityan.androidskills;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class TestService extends Service {

	public SimpleBinder binder;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return binder;
	}

	@SuppressLint("NewApi")
	@Override
	public void onCreate() {
		Log.i("ywb", "create service");
		super.onCreate();
		
		//设置前台服务，配合使用pendingIntent和Notification
		//新api鼓励使用builder去构造
		 Notification.Builder builder = new Notification.Builder(getApplicationContext())
         .setContentTitle("notification for foregroundservice")
         .setContentText("content")
         .setSmallIcon(R.drawable.ic_launcher)
         .setLargeIcon(null)
         .setTicker("ticker");// ticker text no longer show after L

		 //pendingIntent  this will start a new activity
		Intent notificationIntent = new Intent(this, IndexActivity.class);//这里intent的行为决定其flag必定是FLAG_ACTIVITY_NEW_TASK 
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		
		builder.setWhen(System.currentTimeMillis())
		.setShowWhen(false) //控制通知最右侧的时间标签是否显示，默认显示
        .setContentIntent(pendingIntent);
		
		//创建通知
		Notification notification = builder.build();
		
		//开启前台状态
		startForeground(123, notification);
		
		//stopForeground(true)//可以关闭前台状态，前后台状态只是使得service的存活能力更强
		
		//TimerTask 
		
		
		binder = new SimpleBinder();
		
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		Log.i("ywb", "start service");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		Log.i("ywb", "service destroy");
		super.onDestroy();
	}
	
	
	public class SimpleBinder extends Binder
	{
		public TestService getService()
		{
			return TestService.this;
		}
		
		public String saySomething()
		{
			return "blablabla....";
		}
		
	}
}
