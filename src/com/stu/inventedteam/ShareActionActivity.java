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
 * �����ɹ�
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
		final Action action = (Action) getIntent().getSerializableExtra("action");//���л�
		
		((TextView) findViewById(R.id.hname)).setText(action.name);//��ʼ���
		((TextView) findViewById(R.id.htime)).setText(action.time);
		((TextView) findViewById(R.id.hinfo)).setText(action.info);
		((TextView) findViewById(R.id.hmoney)).setText(action.money + "");
		((TextView) findViewById(R.id.hsize)).setText(action.size + "");
		photoEt = (TextView) findViewById(R.id.photo);
		Button but = ((Button) findViewById(R.id.hsub));
		but.setText("����ɹ�");
		but.setOnClickListener(new OnClickListener()//���õ���¼���onclick����
		{
			@Override
			public void onClick(View v)
			{
				String text = ((TextView) findViewById(R.id.replay)).getText().toString().trim();
				String photo = photoEt.getText().toString();
				if (text.isEmpty() && photo.isEmpty())
				{
					toast("û������");
					return;
				}
				boolean result = dbHelper.insertShowAction(action.id, text, photo);
				if (result)
				{
					toast("����ɹ�");
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
						// ���ĳ������ContentProvider���ṩ���� ����ͨ��ContentResolver�ӿ�
						ContentResolver resolver = getContentResolver();
						Uri originalUri = data.getData();
						bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
						// �Եõ�bitmapͼƬ
						String[] proj = { MediaStore.Images.Media.DATA };
						                  // ����android��ý�����ݿ�ķ�װ�ӿڣ�����Ļ�Ҫ��Android�ĵ�
						Cursor cursor = managedQuery(originalUri, proj, null, null, null);
						// ���Ҹ������ ����ǻ���û�ѡ���ͼƬ������ֵ
						int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
						// �����������ͷ ���������Ҫ����С�ĺ���������Խ��
						cursor.moveToFirst();
						// ����������ֵ��ȡͼƬ·��
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
	 * ����ϵͳͼ��
	 */
	private void startGalleryActivity()
	{
		Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(picture, PICTURE);
	}
}
