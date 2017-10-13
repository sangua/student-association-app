package com.stu.inventedteam;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.stu.bean.Organization;
import com.stu.bean.Type;

/**
 * 推荐社团
 * 
 * @author Administrator
 */
public class MemberNonActivity extends BaseActivity
{
	int id;
	// 是否选择了社团
	boolean flag = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.org_choice);
		id = getIntent().getIntExtra("id", 0);
		List<Organization> organizations = dbHelper.getFavOrgsByUid(id);
		ListView lv = (ListView) findViewById(R.id.orgltuijianist);
		MyOrgAdapter adapter = new MyOrgAdapter(organizations);
		lv.setAdapter(adapter);
	}
	
	@Override
	protected void onDestroy()
	{
		if (flag)
		{
			Intent intent = getIntent();
			intent.setClass(MemberNonActivity.this, MyOrgListActivity.class);
			startActivity(intent);
		}
		super.onDestroy();
	}
	
	/**
	 * 推荐社团的适配器
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
				convertView = LayoutInflater.from(MemberNonActivity.this)
											.inflate(R.layout.org_item, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView.findViewById(R.id.oname);
				holder.info = (TextView) convertView.findViewById(R.id.oinfo);
				holder.type = (TextView) convertView.findViewById(R.id.otype);
				holder.but = (Button) convertView.findViewById(R.id.oadd);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			final Organization organization = organizations.get(position);
			holder.name.setText("名字：" + organization.name);
			for (Type type : types)
			{
				if (organization.type == type.id)
				{
					holder.type.setText("类型：" + type.name);
					break;
				}
			}
			holder.info.setText("简介：" + organization.info);
			holder.but.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					boolean result = dbHelper.AddOrg(id, organization.id);
					if (result)
					{
						toast("添加成功");
						flag = true;
						v.setEnabled(false);
					}
				}
			});
			return convertView;
		}
		
		class ViewHolder
		{
			TextView name, type, info;
			Button but;
		}
	}
}
