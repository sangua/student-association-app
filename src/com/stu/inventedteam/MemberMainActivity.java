package com.stu.inventedteam;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.stu.bean.Action;

public class MemberMainActivity extends BaseActivity implements OnClickListener //通过实现接口来实现监听事件
{
	// 会员ID
	int id;
	// 社团ID
	int oid;
	TextView title;
	int textCor = Color.parseColor("#a2d080");
	List<TextView> tabList = new ArrayList<TextView>();
	String[] tabStrs = new String[] { "社团活动", "支  付 ", "好  友" };
	String[] friends = new String[] { "小王", "小李", "小红" };
	private List<View> pageViews;
	private ViewPager viewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle(getIntent().getStringExtra("title"));
		id = getIntent().getIntExtra("id", 0);
		oid = getIntent().getIntExtra("oid", 0);
		setContentView(R.layout.member_main);
		title = (TextView) findViewById(R.id.title);
		tabList.add((TextView) findViewById(R.id.home1));
		tabList.add((TextView) findViewById(R.id.home2));
		tabList.add((TextView) findViewById(R.id.home3));
		for (int i = 0; i < tabList.size(); i++)
		{
			TextView tv = tabList.get(i);
			tv.setOnClickListener(this);
			tv.setText(tabStrs[i]);
			tv.setTag(i + 1);
		}
		tabList.get(0).setTextColor(textCor);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		initPageViews();
		MyViewPaperAdapter adapter = new MyViewPaperAdapter(pageViews);
		viewPager.setAdapter(adapter);
		viewPager.setPageMargin(20);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}
	
	/**
	 * 初始化PageVIew的内容视图
	 */
	private void initPageViews()
	{
		pageViews = new ArrayList<View>();
		View home1View = LayoutInflater.from(MemberMainActivity.this).inflate(R.layout.home1, null);
		pageViews.add(home1View);
		// 获取社团所有的活动
		final List<Action> allActions = dbHelper.getActionsById(oid);
		// 获取社团所有的活动
		final List<Action> actions = dbHelper.getActionsByOidandMid(oid, id);
		// 从所有活动中 删除已经参加的活动
		for (int i = 0; i < actions.size(); i++)
		{
			for (int j = 0; j < allActions.size(); j++)
			{
				if (actions.get(i).id == allActions.get(j).id)
				{
					allActions.remove(j);
					break;
				}
			}
		}
		ListView lv1 = (ListView) home1View.findViewById(R.id.actionlist1);
		MyActionAdapter adapter = new MyActionAdapter(allActions);
		lv1.setAdapter(adapter);
		lv1.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
			{
				Action action = allActions.get(position);
				finish();
				Intent intent = getIntent();
				intent.putExtra("action", action);
				intent.setClass(MemberMainActivity.this, AddActionActivity.class);
				startActivity(intent);
			}
		});
		ListView lv2 = (ListView) home1View.findViewById(R.id.actionlist2);
		MyActionAdapter adapter2 = new MyActionAdapter(actions);
		lv2.setAdapter(adapter2);
		lv2.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
			{
				Action action = actions.get(position);
				finish();
				Intent intent = new Intent();
				intent.putExtra("action", action);
				intent.setClass(MemberMainActivity.this, ShareActionActivity.class);
				startActivity(intent);
			}
		});
		View home2View = LayoutInflater.from(MemberMainActivity.this).inflate(R.layout.home2, null);
		pageViews.add(home2View);
		Button pay = (Button) home2View.findViewById(R.id.pay);
		pay.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				final ProgressDialog pdialog = new ProgressDialog(MemberMainActivity.this);
				pdialog.setMessage("正在检测支付环境...");
				pdialog.show();
				new Handler().postDelayed(new Runnable()
				{
					@Override
					public void run()
					{
						pdialog.setMessage("检测成功..");
						new AlertDialog.Builder(MemberMainActivity.this).setTitle("请输入手机接受到的验证码")
																		.setIcon(android.R.drawable.ic_dialog_info)
																		.setView(new EditText(MemberMainActivity.this))
																		.setPositiveButton("确定", new DialogInterface.OnClickListener()
																		{
																			@Override
																			public void onClick(DialogInterface dialog, int which)
																			{
																				pdialog.dismiss();
																				dialog.dismiss();
																				toast("支付成功");
																			}
																		})
																		.setNegativeButton("取消", null)
																		.show();
					}
				}, 2400);
			}
		});
		View home3View = LayoutInflater.from(MemberMainActivity.this).inflate(R.layout.home3, null);
		pageViews.add(home3View);
		ListView lv3 = (ListView) home3View.findViewById(R.id.home3list);
		MyFriendAdapter friendAdapter = new MyFriendAdapter();
		lv3.setAdapter(friendAdapter);
		title.setText(tabStrs[0]);
	}
	
	/**
	 * 复位下方控件字体颜色
	 */
	private void resetTab()
	{
		for (TextView tv : tabList)
		{
			tv.setTextColor(Color.BLACK);
		}
	}
	
	@Override
	public void onClick(View v)
	{
		int index = (Integer) v.getTag() - 1;
		tabList.get(index).setTextColor(textCor);
		viewPager.setCurrentItem(index);
	}
	
	/**
	 * 页卡切换监听
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener
	{
		@Override
		public void onPageSelected(int arg0)
		{
			resetTab();
			tabList.get(arg0).setTextColor(textCor);
			title.setText(tabStrs[arg0]);
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2)
		{
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0)
		{
		}
	}
	
	/**
	 * 好友适配器
	 * 
	 * @author Administrator
	 */
	class MyFriendAdapter extends BaseAdapter
	{
		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
			return friends.length;
		}
		
		@Override
		public Object getItem(int position)
		{
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public long getItemId(int position)
		{
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder = null;
			if (convertView == null)
			{
				convertView = LayoutInflater.from(MemberMainActivity.this)
											.inflate(R.layout.friend_item, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView.findViewById(R.id.fname);
				holder.but = (Button) convertView.findViewById(R.id.iwant);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.name.setText(friends[position]);
			holder.but.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					String url = "http://intvasetest.com/letus/ijaishjhhjk244?hin";
					LinearLayout lin = new LinearLayout(MemberMainActivity.this);
					lin.setOrientation(LinearLayout.VERTICAL);
					lin.setPadding(10, 10, 10, 10);
					TextView textview = new TextView(MemberMainActivity.this);
					textview.setText("生成的招募链接为: ");
					textview.setTextColor(Color.WHITE);
					TextView tv = new TextView(MemberMainActivity.this);
					tv.setTextColor(Color.WHITE);
					tv.setText(url);
					lin.addView(textview, new LayoutParams(-1, -2));
					lin.addView(tv, new LayoutParams(-1, -2));
					new AlertDialog.Builder(MemberMainActivity.this).setTitle("招募确认对话框")
																	.setIcon(android.R.drawable.ic_dialog_info)
																	.setView(lin)
																	.setPositiveButton("确定", new DialogInterface.OnClickListener()
																	{
																		@Override
																		public void onClick(DialogInterface dialog, int which)
																		{
																			toast("招募链接已经发送");
																		}
																	})
																	.setNegativeButton("取消", null)
																	.show();
				}
			});
			return convertView;
		}
		
		class ViewHolder
		{
			TextView name;
			Button but;
		}
	}
	
	class MyActionAdapter extends BaseAdapter
	{
		List<Action> actions;
		
		public MyActionAdapter(List<Action> actions)
		{
			this.actions = actions;
		}
		
		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
			return actions.size();
		}
		
		@Override
		public Object getItem(int position)
		{
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public long getItemId(int position)
		{
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder = null;
			if (convertView == null)
			{
				convertView = LayoutInflater.from(MemberMainActivity.this)
											.inflate(R.layout.show_action_item, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView.findViewById(R.id.actioname);
				holder.time = (TextView) convertView.findViewById(R.id.actiotime);
				holder.info = (TextView) convertView.findViewById(R.id.actioinfo);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			Action action = actions.get(position);
			holder.name.setText("名称：" + action.name);
			holder.time.setText("时间：" + action.time);
			holder.info.setText("简介：" + action.info);
			return convertView;
		}
		
		class ViewHolder
		{
			TextView name, time, info;
		}
	}
}
