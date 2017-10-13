package com.stu.inventedteam;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.stu.bean.Type;

/**
 * 注册社团Activity
 * 
 * @author Administrator
 */
public class OrgRegisterActivity extends BaseActivity implements OnClickListener//通过实现接口来实现监听事件
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("社团注册");
		setContentView(R.layout.org_register);//套用XML布局文件来进行布局。
		Spinner spinner = (Spinner) findViewById(R.id.orgspinner);//初始化 spinner按钮，spinner是一个下拉菜单控件
		//初始化数组适配器
		ArrayAdapter adapter1 = new ArrayAdapter<Type>(OrgRegisterActivity.this, 
				                                       android.R.layout.simple_spinner_item,types);
		
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//套用xml布局
		spinner.setAdapter(adapter1);
		spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
		{
			public void onItemSelected(AdapterView<?> arg0, View view, int positiion, long arg3)
			{
				((TextView) view).setTextColor(Color.BLACK);
				((TextView) view).setGravity(Gravity.CENTER);//设置文字显示的位置
			}
			
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});
		findViewById(R.id.orgreg).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v)//设置监听事件来实现注册和判断。
	{
		switch (v.getId())//是view类就可以进入下一步
		{
			case R.id.orgreg:
				String name = ((EditText) findViewById(R.id.orgname)).getText().toString().trim();
				if (name.isEmpty())
				{
					toast("社团名称不能为空");
					return;
				}
				String info = ((EditText) findViewById(R.id.orginfo)).getText().toString().trim();
				if (info.isEmpty())
				{
					toast("社团简介不能为空");
					return;
				}
				String phone = ((EditText) findViewById(R.id.orgphone)).getText().toString().trim();
				if (info.isEmpty())
				{
					toast("联系人电话不能为空");
					return;
				}
				String pwd = ((EditText) findViewById(R.id.orgpwd)).getText().toString().trim();
				if (info.isEmpty())
				{
					toast("密码不能为空");
					return;
				}                                         //以上都是提醒用户所有栏目都不能为空。
				dialog.setMessage("正在注册...");
				dialog.show();                      //动画
				int type = ((Type) ((Spinner) findViewById(R.id.orgspinner)).getSelectedItem()).id;//获取社团id
				boolean result = dbHelper.insertOrg(name, type, info, phone, pwd);//将社团名字，类型，此处的type其实是社团id
				if (result)                        //名字  ，  id  ，简介  ， 电话     ，密码
				{
					new Handler().postDelayed(new Runnable()//新建一个弹出的窗口，实现了一些动画效果 
					{
						@Override
						public void run()
						{
							dialog.dismiss();//1.5秒后消失
							toast("注册成功");
							finish();
						}
					}, 1500);
				}
				break;
			default:
				break;
		}
	}
}
