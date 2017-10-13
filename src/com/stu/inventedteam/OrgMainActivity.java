package com.stu.inventedteam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.stu.bean.Organization;

//社团登入之后进行管理的一些Activity
public class OrgMainActivity extends BaseActivity implements OnClickListener//通过实现接口来实现监听事件
{
	int id = 0;// 初始化 id 表示是int 型的

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.org_main);// 套用布局文件

		id = getIntent().getIntExtra("id", 0); // 获取意图id
		Organization organization = dbHelper.getOrgById(id); // 根据id在数据库中查询社团表，将它赋值给organization
		((TextView) findViewById(R.id.orgname)).setText(organization.name); // 用textview显示社团的名字
		findViewById(R.id.orgpublish).setOnClickListener(this); // 发布活动按钮初始化
		findViewById(R.id.orgmyaction).setOnClickListener(this); // 我的活动按钮初始化
		findViewById(R.id.orgset).setOnClickListener(this); // 团费设置按钮初始化
		findViewById(R.id.orgexit).setOnClickListener(this); // 退出登录按钮初始化
	}

	@Override
	public void onClick(View v) // 设置监听事件
	{
		Intent intent = getIntent(); // 定义一个意图，intent，以在下文直接使用。
		switch (v.getId())
		{
		case R.id.orgpublish:
			// 发布活动
			intent.setClass(OrgMainActivity.this, PublishActionActivity.class);
			startActivity(intent); // 点击发布活动按钮，将跳转到发布活动Activity中去。
			break;
		case R.id.orgmyaction:
			intent.setClass(OrgMainActivity.this, OrgActionActivity.class);// 同理，跳转至我的活动管理Activity中去
			startActivity(intent);
			// 我的活动
			break;
		case R.id.orgset: // 此处设置团费。
			// 设置团费
			final EditText editText = new EditText(OrgMainActivity.this);
			editText.setInputType(InputType.TYPE_CLASS_NUMBER);
			new AlertDialog.Builder(OrgMainActivity.this).setTitle("请输入团费金额").setIcon(android.R.drawable.ic_dialog_info).setView(editText).setPositiveButton("确定", new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					String money = editText.getText().toString().trim();
					if (!money.isEmpty())
					{
						int mon = Integer.parseInt(money);
						boolean result = dbHelper.updateOrgMoney(getIntent().getIntExtra("id", 0), mon);
						if (result)
						{
							toast("设置成功");
						}
					}
				}
			}).setNegativeButton("取消", null).show();
			break;
		case R.id.orgexit: // 如果是orgexit的id 直接finish ，
			               // 退出 结束这个Activity，返回上个处在onstop的Activity上
			finish();
			intent.setClass(OrgMainActivity.this, MainActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
