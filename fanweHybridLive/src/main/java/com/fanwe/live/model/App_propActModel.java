package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * 礼物列表接口实体
 */
public class App_propActModel extends BaseActModel
{
    private static final long serialVersionUID = 1L;

    private List<LiveGiftModel> list;
    private List<LiveGiftModel> coins_list;

    public List<LiveGiftModel> getCoins_list()
    {
        return coins_list;
    }

    public void setCoins_list(List<LiveGiftModel> coins_list)
    {
        this.coins_list = coins_list;
    }

    public List<LiveGiftModel> getList()
    {
        return list;
    }

    public void setList(List<LiveGiftModel> list)
    {
        this.list = list;
    }

}
