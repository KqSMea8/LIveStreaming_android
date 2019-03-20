package com.fanwe.games.model.custommsg;

import com.fanwe.games.model.GamePokerGroupResultData;
import com.fanwe.libgame.poker.model.PokerGroupResultData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shibx on 2016/12/7.
 * 扑克游戏信息内容
 */

public class GameGoldFlowerGameData
{
    private int win;
    private List<Integer> bet;
    private List<Integer> user_bet;
    private List<GamePokerGroupResultData> cards_data;
    private List<Integer> dices;

    //add
    private List<PokerGroupResultData> listGroupResultData = new ArrayList<>();

    //add
    public List<PokerGroupResultData> getListGroupResultData()
    {
        return listGroupResultData;
    }

    public int getWin()
    {
        return win;
    }

    public void setWin(int win)
    {
        this.win = win;
    }

    public List<Integer> getBet()
    {
        return bet;
    }

    public void setBet(List<Integer> bet)
    {
        this.bet = bet;
    }

    public List<Integer> getUser_bet()
    {
        return user_bet;
    }

    public void setUser_bet(List<Integer> user_bet)
    {
        this.user_bet = user_bet;
    }

    public List<GamePokerGroupResultData> getCards_data()
    {
        return cards_data;
    }

    public void setCards_data(List<GamePokerGroupResultData> cards_data)
    {
        this.cards_data = cards_data;
        parseData();
    }

    /**
     * 将数据格式转换为游戏需要的数据格式
     */
    private void parseData()
    {
        if (cards_data != null)
        {
            for (GamePokerGroupResultData item : cards_data)
            {
                PokerGroupResultData resultData = new PokerGroupResultData();
                resultData.setResultType(item.getType());
                resultData.setListPokerData(item.getListPokerData());
                listGroupResultData.add(resultData);
            }
        }
    }

    public List<Integer> getDices()
    {
        return dices;
    }

    public void setDices(List<Integer> dices)
    {
        this.dices = dices;
    }
}
