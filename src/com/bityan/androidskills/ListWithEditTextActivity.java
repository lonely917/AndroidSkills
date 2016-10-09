package com.bityan.androidskills;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
		
	}

	private void initData() {
		datas.add(new Bean("1","1"));
		datas.add(new Bean("2","2"));
		datas.add(new Bean("3","3"));
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
			
			final ViewHolder viewHolder;
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
			
			Log.i("ywb", viewHolder.toString());
//			convertView.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					Toast.makeText(context, "position:"+position, Toast.LENGTH_SHORT).show();
//				}
//			});
			
			//更新ref，避免缓存机制导致getview的混乱
			viewHolder.ref = position;
			
			viewHolder.textView.setText(datas.get(position).getTag());
			viewHolder.editText.setText(datas.get(position).getContent());
			Log.i("ywb", "getview "+ viewHolder.textView.getText());

			viewHolder.editText.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					Log.i(position+"ontextchange", s.toString());
					
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					Log.i(position+"beforeTextChanged", s.toString());
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					Log.i(position+"afterTextChanged", s.toString());
					datas.get(viewHolder.ref).setContent(s.toString());
				}
			});
			
			return convertView;
		}
	}
	
	final static class ViewHolder
	{
		public TextView textView;
		public EditText editText;
		public int ref;
	}
	
	class Bean
	{
		private String tag = "";
		private String content = "";
		
		public Bean()
		{
			
		}
		
		public Bean(String tag, String content)
		{
			this.tag = tag;
			this.content = content;
		}
		
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
