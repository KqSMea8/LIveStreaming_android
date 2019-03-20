package com.fanwe.live.model;

public class LiveTopicModel
{

    private int cate_id;
    private String title;
    private int num;

    //add
    private String titleShort = "";

    public String getTitleShort()
    {
        return titleShort;
    }

    public int getCate_id()
    {
        return cate_id;
    }

    public void setCate_id(int cate_id)
    {
        this.cate_id = cate_id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
        if (title != null && title.length() >= 2)
        {
            this.titleShort = title.substring(1, title.length() - 1);
        }
    }

    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
    }
}
