package com.fanwe.games.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * Created by shibx on 2016/12/5.
 */

public class App_requestGameIncomeActModel extends BaseActModel
{

    private long user_diamonds;//玩家秀豆
    private long coin;//玩家游戏币
    private int gain;//玩家本轮游戏获取的货币
    private Winner winner;

    public Winner getWinner()
    {
        return winner;
    }

    public void setWinner(Winner winner)
    {
        this.winner = winner;
    }

    public long getUser_diamonds()
    {
        return user_diamonds;
    }

    public void setUser_diamonds(long user_diamonds)
    {
        this.user_diamonds = user_diamonds;
    }

    public int getGain()
    {
        return gain;
    }

    public void setGain(int gain)
    {
        this.gain = gain;
    }

    public long getCoin()
    {
        return coin;
    }

    public void setCoin(long coin)
    {
        this.coin = coin;
    }

    public static class Winner
    {
        public int money;
        public String nick_name;

        public int getMoney()
        {
            return money;
        }

        public void setMoney(int money)
        {
            this.money = money;
        }

        public String getNick_name()
        {
            return nick_name;
        }

        public void setNick_name(String nick_name)
        {
            this.nick_name = nick_name;
        }
    }
}
