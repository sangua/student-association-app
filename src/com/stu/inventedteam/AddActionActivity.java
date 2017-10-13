package com.stu.inventedteam;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.stu.bean.Action;

/*
 *  参加活动
 */
public class AddActionActivity extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.action_add);
		final Action action = (Action) getIntent().getSerializableExtra("action");//是action表序列化。
		
		((TextView) findViewById(R.id.hname)).setText(action.name);//初始化活动
		((TextView) findViewById(R.id.htime)).setText(action.time);
		((TextView) findViewById(R.id.hinfo)).setText(action.info);
		((TextView) findViewById(R.id.hmoney)).setText(action.money + "");
		((TextView) findViewById(R.id.hsize)).setText(action.size + "");
		Button but = ((Button) findViewById(R.id.hsub));
		but.setText("我要报名参加");
		but.setOnClickListener(new OnClickListener()//设置点击事件，new一个监听器，用来监听活动的参与
		{
			@Override
			public void onClick(View v)
			{
				boolean result = dbHelper.AddAction(getIntent().getIntExtra("id", 0), action.id);
				if (result)			//会员加入活动
				{
					toast("报名成功");
					finish();
				}
			}
		});
	}
}
