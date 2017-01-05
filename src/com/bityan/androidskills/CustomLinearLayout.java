package com.bityan.androidskills;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class CustomLinearLayout extends LinearLayout {


	
	public CustomLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		Log.i("ywb",
				this.toString() + ":dispatchTouchEvent-"
						+ MyUitls.getTouchType(event.getAction()));
//		return super.dispatchTouchEvent(event);
		
		//正常dispatch，但是确定返回true，可以保证布局在action_down后仍然能够接受后续事件，但其parent的onTouchEvent就不会执行了
		//在该函数的onTouchEvent中最后返回true也可已使得必定接收actionDown，因而确保接受后续事件
		super.dispatchTouchEvent(event);
		return true;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.i("ywb",
				this.toString() + ":onTouchEvent-"
						+ MyUitls.getTouchType(event.getAction()));
		return super.onTouchEvent(event);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		Log.i("ywb",
				this.toString() + ":onInterceptTouchEvent-"
						+ MyUitls.getTouchType(event.getAction()));
		return super.onInterceptTouchEvent(event);
	}
}
