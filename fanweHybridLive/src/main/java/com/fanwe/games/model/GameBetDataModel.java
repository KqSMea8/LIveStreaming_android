package com.fanwe.games.model;

/**
 * Created by shibx on 2016/11/25.
 */

public class GameBetDataModel
{
    private int game_status;
    private long time;
    private long account_diamonds;//可用秀豆
    private long coin; //可用游戏币
    private GameBetItemModel game_data;

    public long getCoin()
    {
        return coin;
    }

    public void setCoin(long coin)
    {
        this.coin = coin;
    }

    public int getGame_status()
    {
        return game_status;
    }

    public void setGame_status(int game_status)
    {
        this.game_status = game_status;
    }

    public long getTime()
    {
        return time;
    }

    public void setTime(long time)
    {
        this.time = time;
    }

    public long getAccount_diamonds()
    {
        return account_diamonds;
    }

    public void setAccount_diamonds(long account_diamonds)
    {
        this.account_diamonds = account_diamonds;
    }

    public GameBetItemModel getGame_data()
    {
        return game_data;
    }

    public void setGame_data(GameBetItemModel game_data)
    {
        this.game_data = game_data;
    }
}
