package com.fanwe.live.model;

import com.fanwe.library.common.SDSelectManager;

/**
 * Created by Administrator on 2017/4/1.
 */

public class LiveBeautyFilterModel implements SDSelectManager.Selectable
{
    /**
     * 滤镜名称
     */
    private String name;
    /**
     * 滤镜资源图片id
     */
    private int beautyFilter;

    //add
    private boolean selected;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getBeautyFilter()
    {
        return beautyFilter;
    }

    public void setBeautyFilter(int beautyFilter)
    {
        this.beautyFilter = beautyFilter;
    }

    @Override
    public boolean isSelected()
    {
        return selected;
    }

    @Override
    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }
}
