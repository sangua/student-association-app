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
                                                  //��Աע�Ṧ�ܵ�ʵ�֣�
public class MemberRegisterActivity extends BaseActivity implements OnClickListener //ͨ��ʵ�ֽӿ���ʵ�ּ����¼�
{
	// ��¼��Ȥ���õĿؼ�
	List<CheckBox> boxs = new ArrayList<CheckBox>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTitle("��Աע��");
		setContentView(R.layout.member_register);           // ����XML�ļ����в��� 
		initCheckbox();                                
		findViewById(R.id.memreg).setOnClickListener(this);
	}
	
	/**
	 * ��ʼ���·���checkbox������checkbox�����ջ�Ա��ѡ�İ���
	 */
	private void initCheckbox()
	{
		LinearLayout lin = (LinearLayout) findViewById(R.id.memhobby); //��ʼ�����Բ��ֿؼ�
		int a = types.size() / 3;
		int b = types.size() % 3;
		if (b != 0)
		{
			a += 1;
		}
		for (int i = 0; i < a; i++)          //ͨ��java�ֶ�����������checkbox ��ʾ�ĸ�ʽ
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
	public void onClick(View v)           //���ü����¼�
	{
		switch (v.getId())
		{
			case R.id.memreg:
				String user = ((EditText) findViewById(R.id.memuser)).getText().toString().trim();
				if (user.isEmpty())
				{
					toast("�û�������Ϊ��");
					return;
				}
				String pwd = ((EditText) findViewById(R.id.mempwd)).getText().toString().trim();
				if (pwd.isEmpty())
				{
					toast("���벻��Ϊ��");
					return;
				}
				String name = ((EditText) findViewById(R.id.memname)).getText().toString().trim();
				if (name.isEmpty())
				{
					toast("�ǳƲ���Ϊ��");
					return;
				}
				dialog.setMessage("����ע��...");
				dialog.show();
				
				StringBuffer sb = new StringBuffer();//����StringBuffer��������checkbox��ȡ�Ļ�Ա��������
				for (int i = 0; i < boxs.size(); i++) //StringBuffer���ַ������������Ķ����ǿ���������޸ĵ�
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
				boolean result = dbHelper.insertMember(name, user, pwd, sb.toString());//ע���Ա
				if (result)
				{
					new Handler().postDelayed(new Runnable()
					{
						@Override
						public void run()
						{
							dialog.dismiss();
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
