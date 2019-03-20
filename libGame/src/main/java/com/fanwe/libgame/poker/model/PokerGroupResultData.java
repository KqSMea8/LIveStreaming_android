package com.fanwe.libgame.poker.model;

import java.util.List;

/**
 * 扑克牌组的数据
 */
public class PokerGroupResultData
{
    private List<PokerData> listPokerData;
    /**
     * 牌面类型
     */
    private int resultType;

    public List<PokerData> getListPokerData()
    {
        return listPokerData;
    }

    public void setListPokerData(List<PokerData> listPokerData)
    {
        this.listPokerData = listPokerData;
    }

    public int getResultType()
    {
        return resultType;
    }

    public void setResultType(int resultType)
    {
        this.resultType = resultType;
    }
}
