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
