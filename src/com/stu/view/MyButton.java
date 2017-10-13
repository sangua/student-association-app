package com.stu.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

import com.stu.inventedteam.R;

public class MyButton extends Button implements OnTouchListener //ͨ��ʵ�ֽӿ���ʵ�ּ����¼�
{
	public MyButton(Context context, AttributeSet attrs)
	{//����һ��button��������button��ť��Ч��������ɫ��
		super(context, attrs);
		setTextColor(Color.WHITE);
		setBackgroundResource(R.drawable.btn_bottom_black_normal);
		setOnTouchListener(this);
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		switch (event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				v.setBackgroundResource(R.drawable.btn_bottom_black_pressed);
				break;
			case MotionEvent.ACTION_UP:
				v.setBackgroundResource(R.drawable.btn_bottom_black_normal);
				break;
			default:
				break;
		}
		return false;
	}
}
