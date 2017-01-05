package com.bityan.androidskills;

import android.view.MotionEvent;

public class MyUitls {

	public static String getTouchType(int action) {
		String actionName = "Unknow:id=" + action;
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			actionName = "ACTION_DOWN";
			break;
		case MotionEvent.ACTION_MOVE:
			actionName = "ACTION_MOVE";
			break;
		case MotionEvent.ACTION_UP:
			actionName = "ACTION_UP";
			break;
		case MotionEvent.ACTION_CANCEL:
			actionName = "ACTION_CANCEL";
			break;
		case MotionEvent.ACTION_OUTSIDE:
			actionName = "ACTION_OUTSIDE";
			break;
		}
		return actionName;
	}
}
