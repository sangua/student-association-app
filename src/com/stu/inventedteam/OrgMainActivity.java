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

//���ŵ���֮����й����һЩActivity
public class OrgMainActivity extends BaseActivity implements OnClickListener//ͨ��ʵ�ֽӿ���ʵ�ּ����¼�
{
	int id = 0;// ��ʼ�� id ��ʾ��int �͵�

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.org_main);// ���ò����ļ�

		id = getIntent().getIntExtra("id", 0); // ��ȡ��ͼid
		Organization organization = dbHelper.getOrgById(id); // ����id�����ݿ��в�ѯ���ű�������ֵ��organization
		((TextView) findViewById(R.id.orgname)).setText(organization.name); // ��textview��ʾ���ŵ�����
		findViewById(R.id.orgpublish).setOnClickListener(this); // �������ť��ʼ��
		findViewById(R.id.orgmyaction).setOnClickListener(this); // �ҵĻ��ť��ʼ��
		findViewById(R.id.orgset).setOnClickListener(this); // �ŷ����ð�ť��ʼ��
		findViewById(R.id.orgexit).setOnClickListener(this); // �˳���¼��ť��ʼ��
	}

	@Override
	public void onClick(View v) // ���ü����¼�
	{
		Intent intent = getIntent(); // ����һ����ͼ��intent����������ֱ��ʹ�á�
		switch (v.getId())
		{
		case R.id.orgpublish:
			// �����
			intent.setClass(OrgMainActivity.this, PublishActionActivity.class);
			startActivity(intent); // ����������ť������ת�������Activity��ȥ��
			break;
		case R.id.orgmyaction:
			intent.setClass(OrgMainActivity.this, OrgActionActivity.class);// ͬ����ת���ҵĻ����Activity��ȥ
			startActivity(intent);
			// �ҵĻ
			break;
		case R.id.orgset: // �˴������ŷѡ�
			// �����ŷ�
			final EditText editText = new EditText(OrgMainActivity.this);
			editText.setInputType(InputType.TYPE_CLASS_NUMBER);
			new AlertDialog.Builder(OrgMainActivity.this).setTitle("�������ŷѽ��").setIcon(android.R.drawable.ic_dialog_info).setView(editText).setPositiveButton("ȷ��", new DialogInterface.OnClickListener()
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
							toast("���óɹ�");
						}
					}
				}
			}).setNegativeButton("ȡ��", null).show();
			break;
		case R.id.orgexit: // �����orgexit��id ֱ��finish ��
			               // �˳� �������Activity�������ϸ�����onstop��Activity��
			finish();
			intent.setClass(OrgMainActivity.this, MainActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
