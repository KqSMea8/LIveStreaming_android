package com.fanwe.baimei.model;

/**
 * 包名: com.librarygames.model
 * 描述: 猜大小结果历史列表项实体类
 * 作者: Su
 * 创建时间: 2017/5/27 15:46
 **/
public class BMDiceResultHistoryModel
{
    private boolean lastest;
    private int result;

    public BMDiceResultHistoryModel(boolean lastest, int result)
    {
        this.lastest = lastest;
        this.result = result;
    }

    public boolean isLastest()
    {
        return lastest;
    }

    public void setLastest(boolean lastest)
    {
        this.lastest = lastest;
    }

    public int getResult()
    {
        return result;
    }

    public void setResult(int result)
    {
        this.result = result;
    }



}
