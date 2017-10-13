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
 * ע������Activity
 * 
 * @author Administrator
 */
public class OrgRegisterActivity extends BaseActivity implements OnClickListener//ͨ��ʵ�ֽӿ���ʵ�ּ����¼�
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("����ע��");
		setContentView(R.layout.org_register);//����XML�����ļ������в��֡�
		Spinner spinner = (Spinner) findViewById(R.id.orgspinner);//��ʼ�� spinner��ť��spinner��һ�������˵��ؼ�
		//��ʼ������������
		ArrayAdapter adapter1 = new ArrayAdapter<Type>(OrgRegisterActivity.this, 
				                                       android.R.layout.simple_spinner_item,types);
		
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//����xml����
		spinner.setAdapter(adapter1);
		spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
		{
			public void onItemSelected(AdapterView<?> arg0, View view, int positiion, long arg3)
			{
				((TextView) view).setTextColor(Color.BLACK);
				((TextView) view).setGravity(Gravity.CENTER);//����������ʾ��λ��
			}
			
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});
		findViewById(R.id.orgreg).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v)//���ü����¼���ʵ��ע����жϡ�
	{
		switch (v.getId())//��view��Ϳ��Խ�����һ��
		{
			case R.id.orgreg:
				String name = ((EditText) findViewById(R.id.orgname)).getText().toString().trim();
				if (name.isEmpty())
				{
					toast("�������Ʋ���Ϊ��");
					return;
				}
				String info = ((EditText) findViewById(R.id.orginfo)).getText().toString().trim();
				if (info.isEmpty())
				{
					toast("���ż�鲻��Ϊ��");
					return;
				}
				String phone = ((EditText) findViewById(R.id.orgphone)).getText().toString().trim();
				if (info.isEmpty())
				{
					toast("��ϵ�˵绰����Ϊ��");
					return;
				}
				String pwd = ((EditText) findViewById(R.id.orgpwd)).getText().toString().trim();
				if (info.isEmpty())
				{
					toast("���벻��Ϊ��");
					return;
				}                                         //���϶��������û�������Ŀ������Ϊ�ա�
				dialog.setMessage("����ע��...");
				dialog.show();                      //����
				int type = ((Type) ((Spinner) findViewById(R.id.orgspinner)).getSelectedItem()).id;//��ȡ����id
				boolean result = dbHelper.insertOrg(name, type, info, phone, pwd);//���������֣����ͣ��˴���type��ʵ������id
				if (result)                        //����  ��  id  �����  �� �绰     ������
				{
					new Handler().postDelayed(new Runnable()//�½�һ�������Ĵ��ڣ�ʵ����һЩ����Ч�� 
					{
						@Override
						public void run()
						{
							dialog.dismiss();//1.5�����ʧ
							toast("ע��ɹ�");
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
