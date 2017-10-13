package com.stu.inventedteam;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.stu.bean.Action;

/*
 *  �μӻ
 */
public class AddActionActivity extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.action_add);
		final Action action = (Action) getIntent().getSerializableExtra("action");//��action�����л���
		
		((TextView) findViewById(R.id.hname)).setText(action.name);//��ʼ���
		((TextView) findViewById(R.id.htime)).setText(action.time);
		((TextView) findViewById(R.id.hinfo)).setText(action.info);
		((TextView) findViewById(R.id.hmoney)).setText(action.money + "");
		((TextView) findViewById(R.id.hsize)).setText(action.size + "");
		Button but = ((Button) findViewById(R.id.hsub));
		but.setText("��Ҫ�����μ�");
		but.setOnClickListener(new OnClickListener()//���õ���¼���newһ��������������������Ĳ���
		{
			@Override
			public void onClick(View v)
			{
				boolean result = dbHelper.AddAction(getIntent().getIntExtra("id", 0), action.id);
				if (result)			//��Ա����
				{
					toast("�����ɹ�");
					finish();
				}
			}
		});
	}
}
