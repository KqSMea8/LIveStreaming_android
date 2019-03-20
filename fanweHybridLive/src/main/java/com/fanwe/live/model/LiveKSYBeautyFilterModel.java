package com.fanwe.live.model;

import com.fanwe.library.common.SDSelectManager;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/7.
 */

public class LiveKSYBeautyFilterModel implements SDSelectManager.Selectable, Serializable
{

    private static final long serialVersionUID = 0L;

    /**
     * 美颜滤镜名称
     */
    private String name;
    /**
     * 美颜滤镜类型
     */
    private int beautyFilter;
    /**
     * 磨皮百分比[0-100]
     */
    private int grindProgress = -1;
    /**
     * 美白百分比[0-100]
     */
    private int whitenProgress = -1;
    /**
     * 红润百分比[0-100]
     */
    private int ruddyProgress = -1;

    //add
    private boolean selected;


    public int getGrindProgress()
    {
        return grindProgress;
    }

    public void setGrindProgress(int grindProgress)
    {
        this.grindProgress = grindProgress;
    }

    public int getWhitenProgress()
    {
        return whitenProgress;
    }

    public void setWhitenProgress(int whitenProgress)
    {
        this.whitenProgress = whitenProgress;
    }

    public int getRuddyProgress()
    {
        return ruddyProgress;
    }

    public void setRuddyProgress(int ruddyProgress)
    {
        this.ruddyProgress = ruddyProgress;
    }

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
