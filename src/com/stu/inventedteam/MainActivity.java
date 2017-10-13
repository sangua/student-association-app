package com.stu.inventedteam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 程序启动主界面
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
				// 会员登录
				finish();
				intent.setClass(MainActivity.this, MemberLoginActivity.class);//通过intent触发事件
				break;                                                        //来跳转到MemberLoginActivity.class进行活动
			case R.id.login2:                                                 //如下，功能，Activity的实现都在.class文件中
				// 社团登录
				finish();
				intent.setClass(MainActivity.this, OrgLoginActivity.class);
				break;
			case R.id.register1:
				// 会员注册
				intent.setClass(MainActivity.this, MemberRegisterActivity.class);
				break;
			case R.id.register2:
				// 社团注册
				intent.setClass(MainActivity.this, OrgRegisterActivity.class);
				break;
			case R.id.newaction:
				// 最新活动
				intent.setClass(MainActivity.this, NewActtionActivity.class);
				break;
			case R.id.showaction:
				// 活动成果
				intent.setClass(MainActivity.this, ActionResultActivity.class);
				break;
			default:
				break;
		}
		startActivity(intent);//启动Activity
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}
}