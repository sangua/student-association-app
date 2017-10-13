package com.stu.inventedteam;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.stu.bean.Action;

/**
 * 最新活动展示
 * 
 * @author Administrator
 */
public class NewActtionActivity extends BaseActivity //BaseActivity是实现数据传输到view中去
{
	@Override                             //Bundle类型的数据与Map类型的数据相似，都是以key-value的形式用来存储数据的
	protected void onCreate(Bundle savedInstanceState)  //savedInstanceState是用来保存Activity的状态的
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_action);
		
		List<Action> actions = dbHelper.getNewActions();    // 获取最新的活动
		ListView lv = (ListView) findViewById(R.id.newactionlist);    // 初始化列表视图
		MyActionAdapter actionAdapter = new MyActionAdapter(actions);    //初始化适配器
		lv.setAdapter(actionAdapter);                                //lv视图设置适配器
	}
	
	class MyActionAdapter extends BaseAdapter  //基础数据适配器BaseAdapter，它的主要用途是将一组数据传到
	{                                          //像ListView、Spinner、Gallery及GridView等UI显示组件
		List<Action> actions;       //创建一个actions表              
		
		public MyActionAdapter(List<Action> actions)        //重写方法
		{
			this.actions = actions;
		}
		
		@Override
		public int getCount()
		{
			// getCount决定了listview一共有多少个item，此处并未实现
			return actions.size();
		}
		
		@Override
		public Object getItem(int position)//返回该对象本身
		{
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public long getItemId(int position)//getitem返回该对象本身，getitemid返回该对象的索引
		{
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override                     //重写getview方法  
		public View getView(int position, View convertView, ViewGroup parent)//getView返回了每个item项所显示的view
		{                                                                    
			                //position是Adapter里面item位置,而convertView拿来组建布局的
			ViewHolder holder = null;
			if (convertView == null)
			{
				convertView = LayoutInflater.from(NewActtionActivity.this)
											.inflate(R.layout.new_action_item, null);
				holder = new ViewHolder();
				//一个持有者的类，viewholder里面一般没有方法，只有属性，作用就是作为一个临时的储存器.
				//把你getView方法中每次返回的View存起来，可以下次再用。
				//这样做的好处就是不必每次都到布局文件中去寻找View，提高了效率。
				
				holder.name = (TextView) convertView.findViewById(R.id.newactname);
				holder.time = (TextView) convertView.findViewById(R.id.newacttime);
				holder.info = (TextView) convertView.findViewById(R.id.newactioninfo);
				holder.money = (TextView) convertView.findViewById(R.id.newactmoney);
				holder.size = (TextView) convertView.findViewById(R.id.newactsize);
				holder.org = (TextView) convertView.findViewById(R.id.newactorg);
				holder.but = (Button) convertView.findViewById(R.id.iwantto);
				convertView.setTag(holder);//设置标签
			}
			else//如果convertView非空，则...
			{
				holder = (ViewHolder) convertView.getTag();//获取标签，也就是获取ViewHolder中所填入的数据，也就是布局
			}
			Action action = actions.get(position);//获取view 视图
			holder.name.setText( action.name);         //显示活动名称
			holder.time.setText( action.time);         //显示活动时间
			holder.info.setText( action.info);         //显示活动简介
			holder.money.setText( action.money+"");    //显示活动经费
			holder.size.setText( action.size+"");      //显示活动参加的人数
			holder.org.setText( action.org);           //显示举办活动的社团名称
			holder.but .setOnClickListener(new OnClickListener()//添加点击事件
			{
				@Override
				public void onClick(View v)
				{
					toast("请于会员登录窗口登录后再报名参加");
				}
			});
			return convertView;
		}
		
		class ViewHolder  //一个持有者的类，viewholder里面一般没有方法，只有属性，作用就是作为一个临时的储存器.
		{
			TextView name, time, info, money, size, org;
			Button but;
		}
	}
}
