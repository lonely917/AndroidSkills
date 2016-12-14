package com.bityan.androidskills;

import java.util.Date;

import android.util.Log;

public class TestThread extends Thread {

	private Boolean state = true;
	
	public TestThread() {
		// TODO Auto-generated constructor stub
	}
	
	public TestThread(String name)
	{
		setName(name);
	}
	
	@Override
	public void run() {
		
		while(state)
		{
			
			try {
				Log.i("ywb", getName()+":"+new Date().toString());
				Thread.sleep(5*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void stopThread()
	{
		state = false;
		Log.i("ywb", "stop thread "+getName()+":"+new Date().toString());
	}
}
