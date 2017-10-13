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
                                                       //社团活动管理
public class OrgActionActivity extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.org_action);                      //套用xml文件布局
		int id = getIntent().getIntExtra("id", 0);                      //获得社团id
		List<Action> list = dbHelper.getActionsById(id);                      //查询社团已经发布的活动
		ListView lv = (ListView) findViewById(R.id.orglist);                      //初始化视图列表
		MyActionAdapter adapter = new MyActionAdapter(list);                      //初始化我的活动适配器
		lv.setAdapter(adapter);
	}
	
	class MyActionAdapter extends BaseAdapter
	{
		List<Action> actions;                //创建活动表 actions
		
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
											.inflate(R.layout.action_item, null);//在线性布局中套用xml布局文件
				holder = new ViewHolder();                                       //来实现更加丰富的布局效果。
				holder.name = (TextView) convertView.findViewById(R.id.actname);     
				holder.time = (TextView) convertView.findViewById(R.id.acttime);     
				holder.info = (TextView) convertView.findViewById(R.id.actinfo);     //初始化并传入id对应的活动对象
				holder.money = (TextView) convertView.findViewById(R.id.actmoney);   
				holder.size = (TextView) convertView.findViewById(R.id.actsize);     
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			Action action = actions.get(position);
			holder.name.setText(action.name);                     //显示活动名字
			holder.time.setText(action.time);                     //显示活动事件
			holder.info.setText(action.info);                     //显示活动简介
			holder.money.setText(action.money + "");              //显示活动费用
			holder.size.setText(action.size + "");                //显示活动人数
			return convertView;
		}
		
		class ViewHolder                //viewholder的定义
		{
			TextView name, time, info, money, size;
		}
	}
}
