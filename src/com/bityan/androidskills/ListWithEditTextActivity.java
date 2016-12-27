package com.bityan.androidskills;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
		datas.add(new Bean("4","4"));
		datas.add(new Bean("5","5"));
		datas.add(new Bean("6","6"));
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
				Log.i("ywb", "new viewholder-"+viewHolder.toString());
			}
			else
			{
				viewHolder = (ViewHolder) convertView.getTag();
				Log.i("ywb", "get tag-"+viewHolder.toString());
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
			Log.i("ywb", position+"-getview "+ viewHolder.textView.getText());

			//添加textwatcher，add开头而非点击响应的set命名方式，可以想到类似观察者模式，可能有一个观察者列表，即添加多个watcher
			viewHolder.editText.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					Log.i("ywb",position+" ontextchange-"+s.toString());
					
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					Log.i("ywb",position+" afterTextChanged-"+ s.toString());
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

/**
 总结：
 #1 listview 的itemclick和convertview以及子view事件响应问题
 不去除子view的foucusable，itemclick难以触发。
 有关edittext的重叠区域点击问题还要探究一下??? 
 
 #2 listview中edittext焦点问题
 软键盘的adjustspan属性可以解决，其他的在弹出键盘，listview重绘，逐个getview，会剥夺编辑框焦点，需要再点一次，关闭的时候也一样重绘listview。
 
 #3 listview中对edittext使用textwatcher
 大坑啊，本来巧妙的缓存机制convertview的settag，利用viewholder，不清楚细节导致大问题。
重绘的时候可能会出现新的viewholder，本身设计滑动的时候就是循环的，及时不滑动也会有富余，而addtextwatcher是叠加的，多个观察者，
这一次利用的viewholder可能是之前的某个，也就意味着它关联了其他的textwatcher，因此编辑框内容变化的时候出发多个watcher，
如果watcher要更改数据源，使用原先的position则就出乱了，可以对viewholder添加一个实例变量，每次getview的时候及时赋值更新，
这样即使多个watcher也只会更改对应一个数据源。
另外的解决办法，不用缓存，每次inflate一个convertview，添加新的viewholder，或者重写edittext（textview子类），添加removeallwatcher的方法，
每次add的时候先把之前所有的删除 ，textview只有对某个指定的textwatcher删除的方法，没有清除添加的所有监听器集合的方法。
Note：上述添加textwatcher的代码放到第一次new ViewHolder的地方是否就避免了多个监听器的情况(虽然 多个监听器已经指向同一个目标)

 service不结束的话，程序退出后(系统有缓存)，会有一个进程和一个服务依然运行，如果是thread，则是只有缓存没有任何在运行的进程(管理器里面正在运行的程序)
 */

