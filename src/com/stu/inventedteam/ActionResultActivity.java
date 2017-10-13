package com.stu.inventedteam;

import java.io.File;
import java.util.List;

import com.stu.bean.ActionResult;
import com.stu.bean.Organization;
import com.stu.inventedteam.MemberMainActivity.MyFriendAdapter;
import com.stu.inventedteam.MyOrgListActivity.MyOrgAdapter.ViewHolder;
import com.stu.view.MyButton;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * ��ɹ�չʾ
 * 
 * 
 */
public class ActionResultActivity extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.action_result);
		List<ActionResult> list = dbHelper.getNewActionResults();
		ListView lv = (ListView) findViewById(R.id.myactionresult);
		MyResultAdapter myResultAdapter = new MyResultAdapter(list);
		lv.setAdapter(myResultAdapter);
	}
	
	/**
	 * ��ɹ���������
	 * 
	 * @author Administrator
	 */
	class MyResultAdapter extends BaseAdapter
	{
		List<ActionResult> actionResults;//��ɹ���
		
		public MyResultAdapter(List<ActionResult> actionResults)
		{
			this.actionResults = actionResults;
		}
		
		@Override
		public int getCount()
		{
			// ��ȡ��ɹ���Ĵ�С
			return actionResults.size();
		}
		@Override
		public Object getItem(int position)
		{
			//position��������
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
				convertView = LayoutInflater.from(ActionResultActivity.this)
											.inflate(R.layout.action_result_item, null);//��ȡ�����ļ�����ʾ����
				holder = new ViewHolder();
				holder.name = (TextView) convertView.findViewById(R.id.myname);
				holder.content = (TextView) convertView.findViewById(R.id.myreplay);
				holder.time = (TextView) convertView.findViewById(R.id.mytime);
				holder.iv = (ImageView) convertView.findViewById(R.id.myimg);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			ActionResult result = actionResults.get(position);
			holder.name.setText(result.aname);//��ʾ�����
			holder.content.setText(result.text);//��ʾ����
			holder.time.setText(result.time);//��ʾʱ��
			if (result.img == null || result.img.isEmpty())
			{
				holder.iv.setVisibility(View.GONE);
			}
			else
			{
				holder.iv.setVisibility(View.VISIBLE);
				holder.iv.setImageDrawable(new BitmapDrawable(BitmapFactory.decodeFile((result.img))));
			}
			return convertView;
		}
		
		class ViewHolder//����viewholder�࣬������3��TextView�����һ��ImageView����
		{
			ImageView iv;
			TextView name, content, time;
		}
	}
}
