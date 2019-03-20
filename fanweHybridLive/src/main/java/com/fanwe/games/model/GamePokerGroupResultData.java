package com.fanwe.games.model;

import com.fanwe.libgame.poker.model.PokerData;

import java.util.ArrayList;
import java.util.List;

/**
 * 扑克牌组数据实体
 */
public class GamePokerGroupResultData
{
    private int type; //牌面类型
    private List<int[]> cards; //牌的信息

    //add
    private List<PokerData> listPokerData = new ArrayList<>();

    //add
    public List<PokerData> getListPokerData()
    {
        return listPokerData;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public List<int[]> getCards()
    {
        return cards;
    }

    public void setCards(List<int[]> cards)
    {
        this.cards = cards;
        parseData();
    }

    /**
     * 把数据解析为游戏所需要的数据格式
     */
    private void parseData()
    {
        if (cards != null)
        {
            for (int[] item : cards)
            {
                PokerData pokerData = new PokerData();
                pokerData.setPokerType(item[0]);
                pokerData.setPokerNumber(item[1]);
                listPokerData.add(pokerData);
            }
        }
    }
}
