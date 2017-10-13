package com.stu.inventedteam;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.stu.bean.Action;
                                                       //���Ż����
public class OrgActionActivity extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.org_action);                      //����xml�ļ�����
		int id = getIntent().getIntExtra("id", 0);                      //�������id
		List<Action> list = dbHelper.getActionsById(id);                      //��ѯ�����Ѿ������Ļ
		ListView lv = (ListView) findViewById(R.id.orglist);                      //��ʼ����ͼ�б�
		MyActionAdapter adapter = new MyActionAdapter(list);                      //��ʼ���ҵĻ������
		lv.setAdapter(adapter);
	}
	
	class MyActionAdapter extends BaseAdapter
	{
		List<Action> actions;                //������� actions
		
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
				convertView = LayoutInflater.from(OrgActionActivity.this)
											.inflate(R.layout.action_item, null);//�����Բ���������xml�����ļ�
				holder = new ViewHolder();                                       //��ʵ�ָ��ӷḻ�Ĳ���Ч����
				holder.name = (TextView) convertView.findViewById(R.id.actname);     
				holder.time = (TextView) convertView.findViewById(R.id.acttime);     
				holder.info = (TextView) convertView.findViewById(R.id.actinfo);     //��ʼ��������id��Ӧ�Ļ����
				holder.money = (TextView) convertView.findViewById(R.id.actmoney);   
				holder.size = (TextView) convertView.findViewById(R.id.actsize);     
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			Action action = actions.get(position);
			holder.name.setText(action.name);                     //��ʾ�����
			holder.time.setText(action.time);                     //��ʾ��¼�
			holder.info.setText(action.info);                     //��ʾ����
			holder.money.setText(action.money + "");              //��ʾ�����
			holder.size.setText(action.size + "");                //��ʾ�����
			return convertView;
		}
		
		class ViewHolder                //viewholder�Ķ���
		{
			TextView name, time, info, money, size;
		}
	}
}
