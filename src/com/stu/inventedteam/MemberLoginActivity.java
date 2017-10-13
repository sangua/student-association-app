package com.stu.inventedteam;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.stu.bean.Organization;

/**
 * ��Ա��¼
 * @author Administrator
 *
 */
public class MemberLoginActivity extends BaseActivity implements OnClickListener//ͨ��ʵ�ֽӿ���ʵ�ּ����¼�
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("��Ա��¼");           //���ñ���
		setContentView(R.layout.member_login);//ʹ��member_login.xml�ļ����в��֡�
		findViewById(R.id.memlogin).setOnClickListener(this);//��¼��ť�󶨼����¼�
	}
	
	@Override
	public void onClick(View v)//��д�����¼�
	{
		switch (v.getId())          //��switch case ����֤������߼���ʵ�ֳ������Ĺ��ܡ�
		{
			case R.id.memlogin:
				String user = ((EditText) findViewById(R.id.memuserEt)).getText().toString().trim();
				//ͨ��EditText�������û�������ȡ�û����룬.trim��Ϊ�˱�֤string�������Ŀ�����ȥ �����ո�
				if (user.isEmpty())//����û���Ϊ���������ʾ
				{
					toast("�û�������Ϊ��");
					return;
				}
				String pwd = ((EditText) findViewById(R.id.mempwdet)).getText().toString().trim();
				//ͬ����������
				if (pwd.isEmpty())
				{
					toast("���벻��Ϊ��");//�������Ϊ���������ʾ
					return;
				}
				dialog.setMessage("���ڵ�¼...");//��ʾ��ʾ����ʵ���ѺõĽ���
				dialog.show();
				final int result = dbHelper.checkMemberLogin(user, pwd);//��
				if (result != -1)
				{
					new Handler().postDelayed(new Runnable()
					{
						@Override
						public void run()
						{
							dialog.dismiss();
							toast("��¼�ɹ�");
							finish();
							Intent intent = new Intent();
							intent.putExtra("id", result);
							List<Organization> organizations = dbHelper.getOrganizationsByUid(result);
							if (organizations.size() == 0)
							{
								intent.setClass(MemberLoginActivity.this, MemberNonActivity.class);
							}
							else
							{
								intent.setClass(MemberLoginActivity.this, MyOrgListActivity.class);
							}
							startActivity(intent);
						}
					}, 1800);
				}
				else
				{
					new Handler().postDelayed(new Runnable()
					{
						@Override
						public void run()
						{
							dialog.dismiss();
							toast("��¼ʧ��,�û���/�������");
						}
					}, 1800);
				}
				break;
			default:
				break;
		}
	}
}
