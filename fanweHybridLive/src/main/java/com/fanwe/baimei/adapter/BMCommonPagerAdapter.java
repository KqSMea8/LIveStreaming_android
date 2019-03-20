package com.fanwe.baimei.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

/**
 * 包名 com.fanwe.baimei.adapter
 * 描述 一般viewpager适配器
 * 作者 Su
 * 创建时间 2017/5/16 9:05
 **/
public class BMCommonPagerAdapter extends PagerAdapter
{
    private List<View> pageViews = new ArrayList<>();

    public BMCommonPagerAdapter()
    {
    }

    public BMCommonPagerAdapter(List<View> pageViews)
    {
        this.pageViews = pageViews;
    }

    public List<View> getPageViews()
    {
        return pageViews;
    }

    public View getPageView(int position)
    {
        if (position<0||position>=getCount()){
            return null;
        }

       return this.pageViews.get(position);
    }

    public void setPageViews(List<View> pageViews)
    {
        this.pageViews = pageViews;
    }

    public void addPageView(View pageView)
    {
        this.pageViews.add(pageView);
    }

    public void removePageView(int position)
    {
        if (position<0||position>=getCount()){
            return;
        }

        this.pageViews.remove(position);
    }

    public void removePageView(View view)
    {
        this.pageViews.remove(view);
    }

    @Override
    public int getCount()
    {
        return pageViews == null ? 0 : pageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        container.addView(pageViews.get(position), 0);
        return pageViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
//        container.removeView(pageViews.get(position));
        container.removeView((View) object);
    }

}
