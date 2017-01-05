package com.bityan.androidskills;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class ViewEventTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_event_test);
	}

	@Override
	public void onUserInteraction() {

		Log.i("ywb", this.toString() + ":onUserInteraction");
		super.onUserInteraction();
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
	
//	public void onClick(View v)
//	{
//		Log.i("ywb", v.toString()+"onClick");
//	}
}

/**
 * 关于view的touch事件传递的概述
 * 事件类型：action_down move ...move up，这是一系列的动作
 * viewGroup是view的子类，有额外的事件处理函数
 * 主流相关处理：dispatchTouchEvent，事件分发， onTouchEvent，事件处理，一半还会涉及到外部设定的onTouchListener
 * viewGroup会有InterceptTouch的函数可用于拦截事件向下传递
 * Activity也有dispatchTouchEvent，onTouchEvent这些，还有onUserInterAction函数可供自定义行为。
 * Activity也有对应的窗体-window-decorView-最终是ViewGroup的实现类FrameLayout，id为content，这也是setContentView的由来。(更准确地说decorview是窗体的界面，
 * 对应phoneWindow的installDecorView的方法，会根据主题选择不同的模版，模版里有id为content的frameLayout用于加载用户界面，模版是个linearlayout可能还有其他元素，比如标题栏，图片等)
 * 事件由最外侧Activity的dispatchTouchEvent到内部layout布局再到具体的子view传递，寻找一个targetView去消费事件，如果传到底部子view或者
 * 最后一个ViewGroup(没有子view的话，viewGroup自身会调用super.onTouchEvent作为向下传递的终结)，仍然没有被消费，会向上传递返回，逆着传下的路径，
 * 向下传递的过程实际是Dispatch的一部分，dispatch返回发现没有消费事件，会去调用父布局的onTouchEvent(对于view来说，没有设置touchListener才
 * 会调用这个方法)，直到找到一个targetView消费事件，上述是一个down事件的过程，也是touch的起点，如果dispatch返回false，该view不会再接收到后续事件，
 * 这也就说明down事件的重要性，不接受down，后续操作很可能收不到。后续事件会从上向下传递，到targetView后不再向后传递。OnIntercept会拦截事件，默认返回false，
 * 不拦截。如果拦截，则会调用其onTouchEvent方法，后续分析类似寻找targetView，显然targetView是自己或者传递层的上端view，这个拦截只会进入一次，拦截返回false
 * 的话，如果targetView在低端，以后事件还会进入此函数，在上层则不会再进入此函数。
 * 
 */
