package com.fanwe.games.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * Created by shibx on 2016/11/23.
 */

public class App_plugin_initActModel extends BaseActModel
{
    private String coin;
    private String user_diamonds;
    private int rs_count;//开放插件
    private List<PluginModel> list;//插件列表

    public String getCoin()
    {
        return coin;
    }

    public void setCoin(String coin)
    {
        this.coin = coin;
    }

    public String getUser_diamonds()
    {
        return user_diamonds;
    }

    public void setUser_diamonds(String user_diamonds)
    {
        this.user_diamonds = user_diamonds;
    }

    public int getRs_count()
    {
        return rs_count;
    }

    public void setRs_count(int rs_count)
    {
        this.rs_count = rs_count;
    }

    public List<PluginModel> getList()
    {
        return list;
    }

    public void setList(List<PluginModel> list)
    {
        this.list = list;
    }
}
