package com.stu.inventedteam;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Viewpaper的适配器对象
 * 
 * @author wj
 */
class MyViewPaperAdapter extends PagerAdapter//实现左右两个屏幕平滑地切换的一个类,实现页面切换的动画效果，在此不多解释。
{
	List<View> pageViews;
	
	public MyViewPaperAdapter(List<View> pageViews)
	{
		this.pageViews = pageViews;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getCount()
	{
		return pageViews.size();
	}
	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1)
	{
		return arg0 == arg1;
	}
	
	@Override
	public int getItemPosition(Object object)
	{
		// TODO Auto-generated method stub
		return super.getItemPosition(object);
	}
	
	@Override
	public void destroyItem(View arg0, int arg1, Object arg2)
	{
		// TODO Auto-generated method stub
		((ViewPager) arg0).removeView(pageViews.get(arg1));
	}
	
	@Override
	public Object instantiateItem(View arg0, int arg1)
	{
		// TODO Auto-generated method stub
		((ViewPager) arg0).addView(pageViews.get(arg1));
		return pageViews.get(arg1);
	}
	
	@Override
	public void finishUpdate(View arg0)
	{
		// TODO Auto-generated method stub
	}
}
