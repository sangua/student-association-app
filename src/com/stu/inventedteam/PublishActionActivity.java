package com.stu.inventedteam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;



//�����
public class PublishActionActivity extends BaseActivity implements OnClickListener //ͨ��ʵ�ֽӿ���ʵ�ּ����¼�
{
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("����");
		setContentView(R.layout.publish_action);
		findViewById(R.id.actiontime).setOnClickListener(this);
		findViewById(R.id.actionpub).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v)          //�����¼�
	{
		switch (v.getId())
		{
			case R.id.actiontime:
				showDateChoiceDialog((TextView) v);
				break;
			case R.id.actionpub:
				String name = ((EditText) findViewById(R.id.actionname)).getText().toString();
				if (name.isEmpty())
				{
					toast("����Ʋ���Ϊ��");
					return;
				}
				String time = ((TextView) findViewById(R.id.actiontime)).getText().toString();
				if (time.equals("δ����"))
				{
					toast("�����δ����");
					return;
				}
				String info = ((EditText) findViewById(R.id.actioninfo)).getText().toString();
				if (info.isEmpty())
				{
					toast("����δ��д");
					return;
				}
				String size = ((EditText) findViewById(R.id.actionsize)).getText().toString();
				if (size.isEmpty())
				{
					toast("�����δ��д");
					return;
				}
				int actionsize = Integer.parseInt(size);
				int actionmoney = 0;
				String money = ((EditText) findViewById(R.id.actionmoney)).getText().toString();
				if (!money.isEmpty())
				{
					actionmoney = Integer.parseInt(money);
				}
				boolean result = dbHelper.insertAction(name, time, info, actionmoney, actionsize, getIntent().getIntExtra("id", 0));
				if (result)
				{
					new Handler().postDelayed(new Runnable()//ʹ��PostDelayed�������������ô�Runnable����
					{
						@Override
						public void run()
						{
							dialog.dismiss();
							toast("������ɹ�");
							finish();
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
						}
					}, 1800);
				}
				break;
			default:
				break;
		}
	}
	
	/**
	 * ��ʾ����ѡ��Ի���
	 * 
	 * @param tv
	 */
	public void showDateChoiceDialog(final TextView tv)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(PublishActionActivity.this);
		dialog.setTitle("ѡ������");
		dialog.setIcon(android.R.drawable.ic_dialog_info);
		View contentView = LayoutInflater.from(PublishActionActivity.this)
											.inflate(R.layout.datepicker, null);
		String dateandtime = tv.getText().toString();
		final DatePicker datePicker = (DatePicker) contentView.findViewById(R.id.date_picker);
		Date date = null;
		try
		{
			date = format.parse(dateandtime);
		}
		catch (ParseException e)
		{
		}
		if (date != null)
		{
			datePicker.init(date.getYear() + 1900, date.getMonth(), date.getDate(), null);
		}
		dialog.setView(contentView);
		dialog.setPositiveButton("ȷ��", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				Date date = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
				tv.setText(format.format(date));
			}
		});
		dialog.create().show();
	}
}
