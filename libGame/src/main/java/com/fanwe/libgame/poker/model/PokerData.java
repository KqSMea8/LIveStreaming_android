package com.fanwe.libgame.poker.model;

import com.fanwe.libgame.poker.constant.PokerType;

/**
 * 牌的数据
 */
public class PokerData
{
    /**
     * 牌的点数
     */
    private int pokerNumber;
    /**
     * 牌的类型{@link PokerType}
     */
    private int pokerType;

    public int getPokerNumber()
    {
        return pokerNumber;
    }

    public void setPokerNumber(int pokerNumber)
    {
        this.pokerNumber = pokerNumber;
    }

    public int getPokerType()
    {
        return pokerType;
    }

    public void setPokerType(int pokerType)
    {
        this.pokerType = pokerType;
    }
}
