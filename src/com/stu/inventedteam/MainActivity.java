package com.stu.inventedteam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * ��������������
 * 
 * @author Administrator
 */
public class MainActivity extends BaseActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	
	public void onViewClick(View view)
	{
		Intent intent = new Intent();
		switch (view.getId())
		{
			case R.id.login1:
				// ��Ա��¼
				finish();
				intent.setClass(MainActivity.this, MemberLoginActivity.class);//ͨ��intent�����¼�
				break;                                                        //����ת��MemberLoginActivity.class���л
			case R.id.login2:                                                 //���£����ܣ�Activity��ʵ�ֶ���.class�ļ���
				// ���ŵ�¼
				finish();
				intent.setClass(MainActivity.this, OrgLoginActivity.class);
				break;
			case R.id.register1:
				// ��Աע��
				intent.setClass(MainActivity.this, MemberRegisterActivity.class);
				break;
			case R.id.register2:
				// ����ע��
				intent.setClass(MainActivity.this, OrgRegisterActivity.class);
				break;
			case R.id.newaction:
				// ���»
				intent.setClass(MainActivity.this, NewActtionActivity.class);
				break;
			case R.id.showaction:
				// ��ɹ�
				intent.setClass(MainActivity.this, ActionResultActivity.class);
				break;
			default:
				break;
		}
		startActivity(intent);//����Activity
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}
}