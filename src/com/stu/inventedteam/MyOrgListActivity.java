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
 * �ҵ������б�
 * 
 * @author Administrator
 */
public class MyOrgListActivity extends BaseActivity
{
	@Override          //Bundle���͵�������Map���͵��������ƣ�������key-value����ʽ�����洢���ݵ�
	protected void onCreate(Bundle savedInstanceState)   //savedInstanceState����������Activity��״̬��
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("�ҵ�����");
		setContentView(R.layout.myorglist_main);//����xml����
		ListView lv = (ListView) findViewById(R.id.myorglist);
		List<Organization> organizations = dbHelper.getOrganizationsByUid(getIntent().getIntExtra("id", 0));
		                                 //���ݻ�ԱiD��ѯ���μӵ�����,���������ű�
		MyOrgAdapter adapter = new MyOrgAdapter(organizations);//		��ʼ��������
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
	 * ���ŵ�������
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
				holder = (ViewHolder) convertView.getTag();   //��ȡ��ǩ��Ҳ���ǻ�ȡViewHolder������������ݣ�Ҳ���ǲ���
			}
			
			final Organization organization = organizations.get(position);
			holder.but.setText(organization.name);                        //��ʾ��������
			holder.but.setOnClickListener(new OnClickListener()//��ӵ���¼�
			{
				@Override
				public void onClick(View v)
				{
					Intent intent = getIntent();
					intent.setClass(MyOrgListActivity.this, MemberMainActivity.class);
					intent.putExtra("oid", organization.id);        //��Ҫ���ݵ�ֵ���ӵ�Intent���󣬴˴�����id
					intent.putExtra("title", organization.name);    //��Ҫ���ݵ�ֵ���ӵ�Intent���󣬴˴���������
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
