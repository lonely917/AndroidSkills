package com.bityan.androidskills;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class TestLaunchModeActivity extends Activity {

	TextView standardTextView;
	TextView singleTopTextView;
	TextView singleTaskTextView;
	TextView singleInstanceTextView;
	Context mContext = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_launch_mode);
		initView();
		setListeners();
		Toast.makeText(this, toString()+"oncreate", Toast.LENGTH_SHORT).show();
	}
	
	private void setListeners() {
		
		standardTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(mContext, TestLaunchModeActivity.class);
				startActivity(intent);
			}
		});
		
		singleTopTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, SingleTopActivity.class);
				startActivity(intent);
			}
		});
		
		singleTaskTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, SingleTaskActivity.class);
				startActivity(intent);
			}
		});
		
		singleInstanceTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, SingleInstanceActivity.class);
				startActivity(intent);
			}
		});
	}

	private void initView() {
		
		standardTextView = (TextView) findViewById(R.id.modeTestTextView);
		singleTopTextView = (TextView) findViewById(R.id.singleTopTextView);
		singleTaskTextView = (TextView) findViewById(R.id.singleTaskTextView);
		singleInstanceTextView = (TextView) findViewById(R.id.singleInstanceTextVeiw);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		
		Toast.makeText(this, toString()+"onNewIntent", Toast.LENGTH_SHORT).show();
		super.onNewIntent(intent);
	}
	
	@Override
	protected void onDestroy() {
		Toast.makeText(this, toString()+"destroy", Toast.LENGTH_SHORT).show();
		super.onDestroy();
	}
}

/**
 * 
 * 这个示例提供四种模式的互相调用途径，通过toast提示create和destroy移机onNewIntent，展示Stack和Activity的变化细节。
 * 示例能够体现的只是程序内部的调用，不涉及跨应用程序的调用，两者一些场景会有细微差别，但是模式的含义是具有显示的意义的。
 * 
 * 对于standar模式5.0之前程序内外调用无差别，5.0后程序间调用会建立新的stack。
 * 对于singleTask模式，程序内外调用形式差不多，程序内要制定taskAffinity属性才能加载newTask中，所有行为和名字一致，只有唯一的task含有此Activity。
 * 不加taskAffinity这个字段，singleTask会对当前栈操作，而不会触发新的栈(即使之前不存在此Activity实例)，这点官方文档未明确。
 * 随着版本改进行为可能变化，控制也会更简单，技术发展的趋势如此。
 */
