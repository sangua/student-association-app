package com.stu.bean;

import java.io.Serializable;

/**
 * �����
 * 
 * 
 */
public class Action implements Serializable
{
	/**
	 * Serializable:һ����ǽӿڣ�����������л�����һ������־ô��棩��
	 */
	private static final long serialVersionUID = 1L;
	// ID
	public int id;
	// �����
	public String name;
	// �����
	public String time;
	// ����
	public String info;
	// �����
	public int money;
	// �����
	public int size;
	// ״̬
	public int state;
	//��������
	public String org;
}
