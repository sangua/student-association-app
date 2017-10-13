package com.stu.inventedteam;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
                                             //社团登录Activity
public class OrgLoginActivity extends BaseActivity implements OnClickListener //通过实现接口来实现监听事件
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("社团登录");
		setContentView(R.layout.org_login);           //套用xml文件进行布局。
		findViewById(R.id.orglogin).setOnClickListener(this);//绑定按钮监听器
	}
	
	@Override
	public void onClick(View v)//
	{
		switch (v.getId())
		{
			case R.id.orglogin:
				String user = ((EditText) findViewById(R.id.orguserEt)).getText().toString().trim();//接收用户名
				if (user.isEmpty())
				{
					toast("用户名不能为空");
					return;
				}
				String pwd = ((EditText) findViewById(R.id.orgpwdet)).getText().toString().trim();//接收密码
				if (pwd.isEmpty())
				{
					toast("密码不能为空");
					return;
				}
				dialog.setMessage("正在登录...");
				dialog.show();
				final int result = dbHelper.checkOrgLogin(user, pwd);//检查社团的登录，校验社团的用户名和密码
				if (result!=-1)                    //checkOrgLogin 校验不匹配时返回-1 。所以result不等于-1时 用户名 密码成果匹配
				{
					new Handler().postDelayed(new Runnable()
					{
						@Override
						public void run()
						{
							dialog.dismiss();
							toast("登录成功");
							finish();                           //结束Activity
							Intent intent = new Intent();
							intent.putExtra("id", result);
							intent.setClass(OrgLoginActivity.this, OrgMainActivity.class);  //跳转至社团管理Activity。
							startActivity(intent);
						}
					}, 1500);
				}
				else              //result =-1  的情况，...
				{
					new Handler().postDelayed(new Runnable()
					{
						@Override
						public void run()
						{
							dialog.dismiss();
							toast("登录失败,用户名/密码错误");
						}
					}, 1500);
				}
				break;
			default:
				break;
		}
	}
}
