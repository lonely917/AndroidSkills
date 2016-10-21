package com.bityan.androidskills;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class IndexActivity extends Activity {
	
	private String clsString[] = {"InputMethodTestActivity","ListWithEditTextActivity",
							"ViewEventTestActivity",
							"TestLaunchModeActivity",
							"TestCrashHandlerActivity"};
//	private String clsString[] = new String[]{"InputMethodTestActivity","ListWithEditTextActivity","ViewEventTestActivity"};
	private String packageName = "com.bityan.androidskills";
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		listView = (ListView) findViewById(R.id.indexListView);
		listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, clsString));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
//				//方法1：根据包名和类名构造intent
//				Intent intent = new Intent();
//				intent.setClassName(packageName, packageName+"."+clsString[position]);
//				Log.d("ywb", packageName+"."+clsString[position]);
//				startActivity(intent);
				
				//方法2：通过反射构造class传入intent
				try 
				{
					Class intentClass;
					intentClass = Class.forName(packageName+"."+clsString[position]);
					Intent intent = new Intent(IndexActivity.this, intentClass);
					startActivity(intent);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.index, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
