package com.stu.bean;

import java.io.Serializable;

/**
 * 活动对象
 * 
 * 
 */
public class Action implements Serializable
{
	/**
	 * Serializable:一个标记接口，用于类的序列化（讲一个对象持久储存）。
	 */
	private static final long serialVersionUID = 1L;
	// ID
	public int id;
	// 活动名称
	public String name;
	// 活动日期
	public String time;
	// 活动简介
	public String info;
	// 活动费用
	public int money;
	// 活动人数
	public int size;
	// 状态
	public int state;
	//所属社团
	public String org;
}
