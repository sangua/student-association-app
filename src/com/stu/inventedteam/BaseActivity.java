package com.stu.inventedteam;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.stu.bean.Type;
import com.stu.db.DBHelper;

public class BaseActivity extends Activity
{
	public List<Type> types = null;
	public DBHelper dbHelper = null;
	ProgressDialog dialog = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		dbHelper = DBHelper.getInstance(getApplicationContext());//获取上下文
		types = dbHelper.getOrgTypeList();//获取会员爱好类型
		dialog = new ProgressDialog(BaseActivity.this);
	}
	
	public void toast(String text)
	{
		Toast.makeText(BaseActivity.this, text, 0).show();
	}
}
