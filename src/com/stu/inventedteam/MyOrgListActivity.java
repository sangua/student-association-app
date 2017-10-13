package com.stu.inventedteam;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.stu.bean.Organization;
import com.stu.view.MyButton;

/**
 * 我的社团列表
 * 
 * @author Administrator
 */
public class MyOrgListActivity extends BaseActivity
{
	@Override          //Bundle类型的数据与Map类型的数据相似，都是以key-value的形式用来存储数据的
	protected void onCreate(Bundle savedInstanceState)   //savedInstanceState是用来保存Activity的状态的
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("我的社团");
		setContentView(R.layout.myorglist_main);//套用xml布局
		ListView lv = (ListView) findViewById(R.id.myorglist);
		List<Organization> organizations = dbHelper.getOrganizationsByUid(getIntent().getIntExtra("id", 0));
		                                 //根据会员iD查询所参加的社团,并传入社团表
		MyOrgAdapter adapter = new MyOrgAdapter(organizations);//		初始化适配器
		lv.setAdapter(adapter);
		findViewById(R.id.myorgexit).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
				Intent intent = new Intent();
				intent.setClass(MyOrgListActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
		
	}
	
	/**
	 * 社团的适配器
	 * 
	 * @author Administrator
	 */
	class MyOrgAdapter extends BaseAdapter
	{
		List<Organization> organizations;
		
		public MyOrgAdapter(List<Organization> organizations)
		{
			this.organizations = organizations;
		}
		
		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
			return organizations.size() > 3 ? 3 : organizations.size();
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
				holder = new ViewHolder();
				holder.but = new MyButton(MyOrgListActivity.this, null);
				convertView = holder.but;
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();   //获取标签，也就是获取ViewHolder中所填入的数据，也就是布局
			}
			
			final Organization organization = organizations.get(position);
			holder.but.setText(organization.name);                        //显示社团名字
			holder.but.setOnClickListener(new OnClickListener()//添加点击事件
			{
				@Override
				public void onClick(View v)
				{
					Intent intent = getIntent();
					intent.setClass(MyOrgListActivity.this, MemberMainActivity.class);
					intent.putExtra("oid", organization.id);        //将要传递的值附加到Intent对象，此处传递id
					intent.putExtra("title", organization.name);    //将要传递的值附加到Intent对象，此处传递名字
					startActivity(intent);
				}
			});
			return convertView;
		}
		
		class ViewHolder
		{
			Button but;
		}
	}
}
