package com.fanwe.libgame.model;

import com.fanwe.library.model.SelectableModel;

/**
 * Created by Administrator on 2017/6/13.
 */

public class GameBetCoinsOptionModel extends SelectableModel
{
    private long coins;

    public long getCoins()
    {
        return coins;
    }

    public void setCoins(long coins)
    {
        this.coins = coins;
    }
}
