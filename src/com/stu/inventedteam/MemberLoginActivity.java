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
 * 会员登录
 * @author Administrator
 *
 */
public class MemberLoginActivity extends BaseActivity implements OnClickListener//通过实现接口来实现监听事件
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("会员登录");           //设置标题
		setContentView(R.layout.member_login);//使用member_login.xml文件进行布局。
		findViewById(R.id.memlogin).setOnClickListener(this);//登录按钮绑定监听事件
	}
	
	@Override
	public void onClick(View v)//重写监听事件
	{
		switch (v.getId())          //用switch case 来保证程序的逻辑，实现程序登入的功能。
		{
			case R.id.memlogin:
				String user = ((EditText) findViewById(R.id.memuserEt)).getText().toString().trim();
				//通过EditText来接收用户名，获取用户输入，.trim是为了保证string被完整的拷贝过去 包括空格。
				if (user.isEmpty())//如果用户名为空则给以提示
				{
					toast("用户名不能为空");
					return;
				}
				String pwd = ((EditText) findViewById(R.id.mempwdet)).getText().toString().trim();
				//同理，接收密码
				if (pwd.isEmpty())
				{
					toast("密码不能为空");//如果密码为空则给以提示
					return;
				}
				dialog.setMessage("正在登录...");//显示提示，以实现友好的界面
				dialog.show();
				final int result = dbHelper.checkMemberLogin(user, pwd);//在
				if (result != -1)
				{
					new Handler().postDelayed(new Runnable()
					{
						@Override
						public void run()
						{
							dialog.dismiss();
							toast("登录成功");
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
							toast("登录失败,用户名/密码错误");
						}
					}, 1800);
				}
				break;
			default:
				break;
		}
	}
}
