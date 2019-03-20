package com.fanwe.games.model;

import com.fanwe.library.common.SDSelectManager;

/**
 * Created by shibx on 2017/2/24.
 * 游戏庄家
 */

public class GameBankerModel implements SDSelectManager.Selectable
{
    private String banker_log_id; //上庄id(用于主播选择某个庄家的时候请求接口传入id)
    private String banker_id; //庄家用户id
    private String banker_name; //庄家昵称
    private String banker_img; //庄家头像
    private long coin; //庄家的剩余底金

    private boolean selected;

    public String getBanker_log_id()
    {
        return banker_log_id;
    }

    public void setBanker_log_id(String banker_log_id)
    {
        this.banker_log_id = banker_log_id;
    }

    public String getBanker_name()
    {
        return banker_name;
    }

    public void setBanker_name(String banker_name)
    {
        this.banker_name = banker_name;
    }

    public String getBanker_img()
    {
        return banker_img;
    }

    public void setBanker_img(String banker_img)
    {
        this.banker_img = banker_img;
    }

    public long getCoin()
    {
        return coin;
    }

    public void setCoin(long coin)
    {
        this.coin = coin;
    }

    public String getBanker_id()
    {
        return banker_id;
    }

    public void setBanker_id(String banker_id)
    {
        this.banker_id = banker_id;
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
