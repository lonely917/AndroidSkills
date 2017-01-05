package com.bityan.androidskills;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

public class CustomButton extends Button {



	public CustomButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		Log.i("ywb",
				this.toString() + ":dispatchTouchEvent-"
						+ MyUitls.getTouchType(event.getAction()));
		return super.dispatchTouchEvent(event);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.i("ywb",
				this.toString() + ":onTouchEvent-"
						+ MyUitls.getTouchType(event.getAction()));
		return super.onTouchEvent(event);
	}
}
