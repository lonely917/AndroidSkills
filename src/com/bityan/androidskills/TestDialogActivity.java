package com.bityan.androidskills;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources.Theme;
import android.database.DataSetObserver;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
/**
 * 各种dialog示例，自定义dialog以及dialog的theme设定
 * arrayadapter以及simpleadapter的应用
 * @author yan
 *
 */
public class TestDialogActivity extends Activity {

	ListView listView;
	
	//对数组和list初始化的几种写法
	List<String> itemsA = new ArrayList<String>();
	{
		itemsA.add("1");
		itemsA.add("2");
		itemsA.add("3");
	}
	List<String> itemsB = new ArrayList<String>(){
		{
			this.add("1");
			this.add("2");
			this.add("3");
		}
		};
		
	List<String> items = Arrays.asList("0","1","2","3","4","5","6","7","8");//返回的是一个Arrays的内部类，不是普通的List
	
	String[] itemStringsA = new String[3];
	{
		itemStringsA[0] = "1";
		itemStringsA[1] = "2";
		itemStringsA[2] = "3";
	}
	String[] itemStrings = new String[]{"1","2","3"};//根据初始化数据个数决定长度
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_dialog);
		initView();
	}

	private void initView() {
		listView = (ListView) findViewById(R.id.listviewInDialogTest);
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,items);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					showAlertDialogA();
					break;
				case 1:
					showAlertDialogB();
					break;
				case 2:
					showAlertDialogC();
					break;
				case 3:
					showAlertDialogD();
					break;
				case 4:
					showAlertDialogE();
					break;
				case 5:
					showAlertDialogCustomStyle();
					break;
				case 6:
					showDialogCustom();
					break;
				case 7:
					showAlertDialogCustom();
					break;
				case 8:
					showAlertDialogCustomByDialog();
					break;
				default:
					break;
				}
			}
		});
	}

	void showAlertDialogCustomByDialog()
	{
		
		final String[] items = {"1","2","3"};
		AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
		View v = getLayoutInflater().inflate(R.layout.dialog_custom, null);
//		LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		Dialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
//		dialog.setContentView(v,lp);//这句话要写在show之后，但是不通过builder构造的dialog(非alertDialog)却可以 参考showDialogCustom
		dialog.setContentView(v);
		
		//自定义的布局文件如果完全用weight据自动适配，会使得布局很小，可能是内部构建的时候会除去最外部的布局尺寸设定
		//注意加入的view和dialog的window之前的关系，view的matchparent和wrapcontent以及具体指定大小都会使得效果产生变化
	}
	
	void showAlertDialogCustom()
	{
		
		final String[] items = {"1","2","3"};
		AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
		View v = getLayoutInflater().inflate(R.layout.dialog_custom, null);
		builder.setView(v);
		builder.setPositiveButton("Yes", null)
		.setNegativeButton("No", null);
		builder.create().show();
		//可以对view中的控件进行事件绑定
		
		
//		LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//		Dialog dialog = builder.create();
//		dialog.setCanceledOnTouchOutside(true);
//		dialog.show();
//		dialog.setContentView(v,lp);//这句话要写在show之后，但是不通过builder构造的dialog(非alertDialog)却可以 参考showDialogCustom
//		dialog.setContentView(v);
		
		//自定义的布局文件如果完全用weight据自动适配，会使得布局很小，可能是内部构建的时候会除去最外部的布局尺寸设定
		//注意加入的view和dialog的window之前的关系，view的matchparent和wrapcontent以及具体指定大小都会使得效果产生变化
	}
	
	//默认的设置比如布局填充对齐方式等都会和主题有关，为了主题无关，最好主动设定。
	protected void showDialogCustom() {
		Dialog dialog = new Dialog(this,R.style.CustomDialogNoTitle);
//		Dialog dialog = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar);
		dialog.setContentView(R.layout.dialog_custom_2);//这句话设定回去调用对应的dialogwindow相关设置函数
		dialog.setTitle("haha");
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		
		//设置dialog的窗体尺寸和位置
		Window dialogWindow = dialog.getWindow();//后去dialog的window窗体
		dialogWindow.setGravity(Gravity.CENTER);
		int width = getWindowManager().getDefaultDisplay().getWidth()/2;    
		int height = getWindowManager().getDefaultDisplay().getHeight()/2; 
		dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		
		//以下是利用Attributes来进行设置，一个是设置绝对大小，一个是相对大小
//		{
//	       
//	        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//	        dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
//	        lp.x = 100; // 新位置X坐标
//	        lp.y = 100; // 新位置Y坐标
//	        lp.width = 300; // 宽度
//	        lp.height = 300; // 高度
//	        lp.alpha = 0.7f; // 透明度
//	  
//	        // 当Window的Attributes改变时系统会调用此函数,可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
//	        // dialog.onWindowAttributesChanged(lp);
//	        dialogWindow.setAttributes(lp);   
//		}

        
//        {
//            WindowManager m = getWindowManager();
//            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
//            WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
//            p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
//            p.width = (int) (d.getWidth() * 0.65); // 宽度设置为屏幕的0.95
//            dialogWindow.setAttributes(p);
//        }
        
		dialog.show();
		
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	protected void showAlertDialogCustomStyle() {
		final String[] items = {"1","2","3"};
		AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog2);
		builder.setTitle("F")
		.setItems(items, null);
		builder.create().show();	
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	protected void showAlertDialogE() {
		final String[] items = {"1","2","3"};
		AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Dialog);
		builder.setTitle("E")
		.setItems(items, null);
		builder.create().show();	
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	protected void showAlertDialogD() {
		final String[] items = {"1","2","3"};
		
		// android.R.attr.alertDialogTheme
		AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.attr.alertDialogTheme);
		builder.setTitle("D")
		.setItems(items, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(getApplicationContext(), items[which], Toast.LENGTH_SHORT).show();
			}
		});
		builder.create().show();	
	}
	
	/**
	 * 自定义布局，自定义主题，添加list(系统的或者自定义的)
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	protected void showAlertDialogC() {
		String[] items = {"1","2","3"};
		
		//android.R.style.Theme_Holo_Dialog 这是一个针对dialog的主题 对应dialog activity或者window。
		AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Holo_Dialog);
		builder.setTitle("c")
		.setItems(items, null);
		builder.create().show();	
		
	}

	/**
	 * 指定主题进行构造alert dialog
	 * AlertDialog.THEME_HOLO_LIGHT主题
	 * 设置成不可撤销的，必须点击选项进行dismiss
	 * 使用指定主题的builder至少11
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	protected void showAlertDialogB() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
		builder.setTitle("B")
		.setIcon(R.drawable.ic_launcher)
		.setPositiveButton("positive", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		}).setNegativeButton("negative", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		}).setNeutralButton("neutral", null)
		  .setCancelable(false);
		builder.create().show();
	}

	/**
	 * 默认主题
	 * 相当于builder(this,0)
	 * 源码中使用resolveDialogTheme这个函数解析主题
	 * 测试cancel和dismiss的触发
	 * setOnDismissListener至少17才可以使用
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	protected void showAlertDialogA() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("A")
		.setOnDismissListener(new DialogInterface.OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				Toast.makeText(getApplicationContext(), "onDismiss", Toast.LENGTH_SHORT).show();
			}
		}).setOnCancelListener(new DialogInterface.OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				Toast.makeText(getApplicationContext(), "onCancel", Toast.LENGTH_SHORT).show();
			}
		}).setPositiveButton("positive", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		}).setNegativeButton("negative", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		}).setNeutralButton("neutral", null);
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}

/**
 * listview 的观察者模式，适配器模式
 * adapter的框架集合
 * 
 * BaseAdapter抽象类(实现接口ListAdater和SpinnerAdapter)
 * 一般我们可以自定义adapter作为BaseAdapter的子类，自定义view，实现相关逻辑。
 * 系统也提供了一些简单实现，对于简单效果使得我们可以直接使用现成的工具，不必造轮子，当然也提供了造轮子的一个示例。
 * ArrayAdapter用于实现一维的对应item主元素为TextView的listview实现，只需提供item的layout资源id和其中textview的id(对应其中一个构造函数)，
 * 或者只提供一个layoutid(对应另一个构造函数)，则会默认将其当作一个textview的id来使用，对于此系统提供了一些直接可用的资源，比如android.R.layout.simple_list_item_1.
 * 这里的整体思想就是：系统提供一个框架，同时提供了一些框架实例，我们可以去自己扩展实现框架，对于一些简单情况我们也能够高效地利用已有实现完成原型。
 * SimpleAdapter是归纳了另外一个场景，不像ArrayAdapter那样传入一维数据源，而是传入了一个映射列表，即一列映射，每一个map对应一个item，
 * map里的多个entry对应item上的多个子元素，from[]里定义了key值组合，to[]里保存了对应数据绑定的目标资源id集合，也就是每一个map里的所有键值对通过from[]里的元素作为
 * key获取value后对应到to[]的资源上，更准确地描述应该是将to[]里资源id对应到from[]的列名集合，根据对应from[]的元素值去对应的map中作为key寻找value，然后绑定到资源上。
 * 
 * 默认的将String或者Boolean数值绑定到TextView ImageView 或者实现了Checkable接口的资源上。
 * 也可以使用setViewBinder传入自定义的binder。
 * 
 * 对于AlertDialog，同样可以自定义view，主题。系统提供了简单了示例，AlertDialog.XXXX主题，setitems可以填充默认列表，setView可以添加自定义view。
 * 自定义主题的时候 style中许多元素名不能提示，比如windowBackground属性，但是可以直接使用，加上前缀android:，自定义style的时候如何更为详细的控制属性？
 * 
 */