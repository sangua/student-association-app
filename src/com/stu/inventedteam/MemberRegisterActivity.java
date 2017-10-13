package com.stu.inventedteam;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.LinearLayout.LayoutParams;

import com.stu.bean.Type;
                                                  //会员注册功能的实现：
public class MemberRegisterActivity extends BaseActivity implements OnClickListener //通过实现接口来实现监听事件
{
	// 记录兴趣爱好的控件
	List<CheckBox> boxs = new ArrayList<CheckBox>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTitle("会员注册");
		setContentView(R.layout.member_register);           // 套用XML文件进行布局 
		initCheckbox();                                
		findViewById(R.id.memreg).setOnClickListener(this);
	}
	
	/**
	 * 初始化下方的checkbox，利用checkbox来接收会员勾选的爱好
	 */
	private void initCheckbox()
	{
		LinearLayout lin = (LinearLayout) findViewById(R.id.memhobby); //初始化线性布局控件
		int a = types.size() / 3;
		int b = types.size() % 3;
		if (b != 0)
		{
			a += 1;
		}
		for (int i = 0; i < a; i++)          //通过java手动编码设置了checkbox 显示的格式
		{
			LinearLayout contLin = new LinearLayout(MemberRegisterActivity.this);
			for (int j = 0; j < 3; j++)
			{
				LinearLayout aLin = new LinearLayout(MemberRegisterActivity.this);
				if (types.size() > (3 * i + j))
				{
					CheckBox cb = new CheckBox(MemberRegisterActivity.this);
					cb.setId(types.get(3 * i + j).id);
					cb.setTextSize(14);
					cb.setText(types.get(3 * i + j).name);
					cb.setTextColor(Color.BLACK);
					boxs.add(cb);
					aLin.addView(cb, new LayoutParams(-1, -2));
				}
				contLin.addView(aLin, new LayoutParams(-1, -2, 1));
			}
			lin.addView(contLin, new LayoutParams(-1, -1));
		}
	}
	
	@Override
	public void onClick(View v)           //设置监听事件
	{
		switch (v.getId())
		{
			case R.id.memreg:
				String user = ((EditText) findViewById(R.id.memuser)).getText().toString().trim();
				if (user.isEmpty())
				{
					toast("用户名不能为空");
					return;
				}
				String pwd = ((EditText) findViewById(R.id.mempwd)).getText().toString().trim();
				if (pwd.isEmpty())
				{
					toast("密码不能为空");
					return;
				}
				String name = ((EditText) findViewById(R.id.memname)).getText().toString().trim();
				if (name.isEmpty())
				{
					toast("昵称不能为空");
					return;
				}
				dialog.setMessage("正在注册...");
				dialog.show();
				
				StringBuffer sb = new StringBuffer();//利用StringBuffer类来保存checkbox获取的会员爱好类型
				for (int i = 0; i < boxs.size(); i++) //StringBuffer是字符串变量，它的对象是可以扩充和修改的
				{                                       
					if (boxs.get(i).isChecked())
					{
						sb.append(boxs.get(i).getId() + ",");
					}
				}
				if (sb.toString().endsWith(","))
				{
					sb.deleteCharAt(sb.length() - 1);
				}
				boolean result = dbHelper.insertMember(name, user, pwd, sb.toString());//注册会员
				if (result)
				{
					new Handler().postDelayed(new Runnable()
					{
						@Override
						public void run()
						{
							dialog.dismiss();
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
