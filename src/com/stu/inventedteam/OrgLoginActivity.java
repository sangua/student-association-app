package com.stu.inventedteam;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
                                             //���ŵ�¼Activity
public class OrgLoginActivity extends BaseActivity implements OnClickListener //ͨ��ʵ�ֽӿ���ʵ�ּ����¼�
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("���ŵ�¼");
		setContentView(R.layout.org_login);           //����xml�ļ����в��֡�
		findViewById(R.id.orglogin).setOnClickListener(this);//�󶨰�ť������
	}
	
	@Override
	public void onClick(View v)//
	{
		switch (v.getId())
		{
			case R.id.orglogin:
				String user = ((EditText) findViewById(R.id.orguserEt)).getText().toString().trim();//�����û���
				if (user.isEmpty())
				{
					toast("�û�������Ϊ��");
					return;
				}
				String pwd = ((EditText) findViewById(R.id.orgpwdet)).getText().toString().trim();//��������
				if (pwd.isEmpty())
				{
					toast("���벻��Ϊ��");
					return;
				}
				dialog.setMessage("���ڵ�¼...");
				dialog.show();
				final int result = dbHelper.checkOrgLogin(user, pwd);//������ŵĵ�¼��У�����ŵ��û���������
				if (result!=-1)                    //checkOrgLogin У�鲻ƥ��ʱ����-1 ������result������-1ʱ �û��� ����ɹ�ƥ��
				{
					new Handler().postDelayed(new Runnable()
					{
						@Override
						public void run()
						{
							dialog.dismiss();
							toast("��¼�ɹ�");
							finish();                           //����Activity
							Intent intent = new Intent();
							intent.putExtra("id", result);
							intent.setClass(OrgLoginActivity.this, OrgMainActivity.class);  //��ת�����Ź���Activity��
							startActivity(intent);
						}
					}, 1500);
				}
				else              //result =-1  �������...
				{
					new Handler().postDelayed(new Runnable()
					{
						@Override
						public void run()
						{
							dialog.dismiss();
							toast("��¼ʧ��,�û���/�������");
						}
					}, 1500);
				}
				break;
			default:
				break;
		}
	}
}
