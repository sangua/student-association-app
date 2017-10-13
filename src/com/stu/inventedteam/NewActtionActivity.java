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
 * ���»չʾ
 * 
 * @author Administrator
 */
public class NewActtionActivity extends BaseActivity //BaseActivity��ʵ�����ݴ��䵽view��ȥ
{
	@Override                             //Bundle���͵�������Map���͵��������ƣ�������key-value����ʽ�����洢���ݵ�
	protected void onCreate(Bundle savedInstanceState)  //savedInstanceState����������Activity��״̬��
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_action);
		
		List<Action> actions = dbHelper.getNewActions();    // ��ȡ���µĻ
		ListView lv = (ListView) findViewById(R.id.newactionlist);    // ��ʼ���б���ͼ
		MyActionAdapter actionAdapter = new MyActionAdapter(actions);    //��ʼ��������
		lv.setAdapter(actionAdapter);                                //lv��ͼ����������
	}
	
	class MyActionAdapter extends BaseAdapter  //��������������BaseAdapter��������Ҫ��;�ǽ�һ�����ݴ���
	{                                          //��ListView��Spinner��Gallery��GridView��UI��ʾ���
		List<Action> actions;       //����һ��actions��              
		
		public MyActionAdapter(List<Action> actions)        //��д����
		{
			this.actions = actions;
		}
		
		@Override
		public int getCount()
		{
			// getCount������listviewһ���ж��ٸ�item���˴���δʵ��
			return actions.size();
		}
		
		@Override
		public Object getItem(int position)//���ظö�����
		{
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public long getItemId(int position)//getitem���ظö�����getitemid���ظö��������
		{
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override                     //��дgetview����  
		public View getView(int position, View convertView, ViewGroup parent)//getView������ÿ��item������ʾ��view
		{                                                                    
			                //position��Adapter����itemλ��,��convertView�����齨���ֵ�
			ViewHolder holder = null;
			if (convertView == null)
			{
				convertView = LayoutInflater.from(NewActtionActivity.this)
											.inflate(R.layout.new_action_item, null);
				holder = new ViewHolder();
				//һ�������ߵ��࣬viewholder����һ��û�з�����ֻ�����ԣ����þ�����Ϊһ����ʱ�Ĵ�����.
				//����getView������ÿ�η��ص�View�������������´����á�
				//�������ĺô����ǲ���ÿ�ζ��������ļ���ȥѰ��View�������Ч�ʡ�
				
				holder.name = (TextView) convertView.findViewById(R.id.newactname);
				holder.time = (TextView) convertView.findViewById(R.id.newacttime);
				holder.info = (TextView) convertView.findViewById(R.id.newactioninfo);
				holder.money = (TextView) convertView.findViewById(R.id.newactmoney);
				holder.size = (TextView) convertView.findViewById(R.id.newactsize);
				holder.org = (TextView) convertView.findViewById(R.id.newactorg);
				holder.but = (Button) convertView.findViewById(R.id.iwantto);
				convertView.setTag(holder);//���ñ�ǩ
			}
			else//���convertView�ǿգ���...
			{
				holder = (ViewHolder) convertView.getTag();//��ȡ��ǩ��Ҳ���ǻ�ȡViewHolder������������ݣ�Ҳ���ǲ���
			}
			Action action = actions.get(position);//��ȡview ��ͼ
			holder.name.setText( action.name);         //��ʾ�����
			holder.time.setText( action.time);         //��ʾ�ʱ��
			holder.info.setText( action.info);         //��ʾ����
			holder.money.setText( action.money+"");    //��ʾ�����
			holder.size.setText( action.size+"");      //��ʾ��μӵ�����
			holder.org.setText( action.org);           //��ʾ�ٰ�����������
			holder.but .setOnClickListener(new OnClickListener()//��ӵ���¼�
			{
				@Override
				public void onClick(View v)
				{
					toast("���ڻ�Ա��¼���ڵ�¼���ٱ����μ�");
				}
			});
			return convertView;
		}
		
		class ViewHolder  //һ�������ߵ��࣬viewholder����һ��û�з�����ֻ�����ԣ����þ�����Ϊһ����ʱ�Ĵ�����.
		{
			TextView name, time, info, money, size, org;
			Button but;
		}
	}
}
