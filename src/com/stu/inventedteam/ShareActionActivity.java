package com.stu.inventedteam;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.stu.bean.Action;

/**
 * 分享活动成果
 * 
 * @author Administrator
 */
public class ShareActionActivity extends BaseActivity
{
	public static final int PICTURE = 2;
	TextView photoEt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.action_shared);
		final Action action = (Action) getIntent().getSerializableExtra("action");//序列化
		
		((TextView) findViewById(R.id.hname)).setText(action.name);//初始化活动
		((TextView) findViewById(R.id.htime)).setText(action.time);
		((TextView) findViewById(R.id.hinfo)).setText(action.info);
		((TextView) findViewById(R.id.hmoney)).setText(action.money + "");
		((TextView) findViewById(R.id.hsize)).setText(action.size + "");
		photoEt = (TextView) findViewById(R.id.photo);
		Button but = ((Button) findViewById(R.id.hsub));
		but.setText("分享成果");
		but.setOnClickListener(new OnClickListener()//设置点击事件，onclick方法
		{
			@Override
			public void onClick(View v)
			{
				String text = ((TextView) findViewById(R.id.replay)).getText().toString().trim();
				String photo = photoEt.getText().toString();
				if (text.isEmpty() && photo.isEmpty())
				{
					toast("没有内容");
					return;
				}
				boolean result = dbHelper.insertShowAction(action.id, text, photo);
				if (result)
				{
					toast("分享成功");
					finish();
				}
			}
		});
		findViewById(R.id.img).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				startGalleryActivity();
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == Activity.RESULT_OK)
		{
			switch (requestCode)
			{
				case PICTURE:
					try
					{
						Bitmap bm = null;
						// 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
						ContentResolver resolver = getContentResolver();
						Uri originalUri = data.getData();
						bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
						// 显得到bitmap图片
						String[] proj = { MediaStore.Images.Media.DATA };
						                  // 这是android多媒体数据库的封装接口，具体的还要看Android文档
						Cursor cursor = managedQuery(originalUri, proj, null, null, null);
						// 按我个人理解 这个是获得用户选择的图片的索引值
						int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
						// 将光标移至开头 ，这个很重要，不小心很容易引起越界
						cursor.moveToFirst();
						// 最后根据索引值获取图片路径
						String path = cursor.getString(column_index);
						photoEt.setText(path);
					}
					catch (Exception e)
					{
						// TODO: handle exception
					}
					break;
				default:
					break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	/**
	 * 调用系统图库
	 */
	private void startGalleryActivity()
	{
		Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(picture, PICTURE);
	}
}
