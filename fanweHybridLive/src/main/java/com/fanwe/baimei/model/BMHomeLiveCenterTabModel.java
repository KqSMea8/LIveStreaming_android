package com.fanwe.baimei.model;

/**
 * Created by Administrator on 2017/5/16.
 */

public class BMHomeLiveCenterTabModel
{
    private String name;
    private String icon;
    private String tag;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

    public static class BMHomeLiveCenterTabTAG
    {
        public static final String ANCHOR_RANK = "anchor_rank";
        public static final String RANDOM_GAME = "random_game";
        public static final String HERO_RANK = "hero_rank";
    }
}
