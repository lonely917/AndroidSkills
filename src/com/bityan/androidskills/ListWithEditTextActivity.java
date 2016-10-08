package com.bityan.androidskills;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListWithEditTextActivity extends Activity {

	public List<Bean> datas = new ArrayList<Bean>();
	public ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_with_edit_text);
		
		listView = (ListView) findViewById(R.id.listview);
		
		initData();
		
		MyAdapter adapter = new MyAdapter(ListWithEditTextActivity.this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(ListWithEditTextActivity.this, "onitemclick: position-"+ position+" id-"+id, Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void initData() {
		datas.add(new Bean());
		datas.add(new Bean());
		datas.add(new Bean());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_with_edit_text, menu);
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
	
	
	public class MyAdapter extends BaseAdapter
	{

		Context context;
		
		public MyAdapter(Context context) {
			this.context = context;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return datas.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return datas.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			
			ViewHolder viewHolder = null;
			if(convertView == null)
			{
				convertView = LayoutInflater.from(context).inflate(R.layout.items_text_edit, null);
				viewHolder = new ViewHolder();
				viewHolder.textView = (TextView) convertView.findViewById(R.id.textview1);
				viewHolder.editText = (EditText) convertView.findViewById(R.id.edittext1);
				convertView.setTag(viewHolder);
			}
			else
			{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
//			convertView.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					Toast.makeText(context, "position:"+position, Toast.LENGTH_SHORT).show();
//				}
//			});
//			
//			convertView.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					Toast.makeText(context, "**position:"+position, Toast.LENGTH_SHORT).show();
//				}
//			});
			
			return convertView;
		}
	}
	
	static class ViewHolder
	{
		public TextView textView;
		public EditText editText;
	}
	
	class Bean
	{
		private String tag = "";
		private String content = "";
		
		public String getTag() {
			return tag;
		}
		public void setTag(String tag) {
			this.tag = tag;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
	}
}
