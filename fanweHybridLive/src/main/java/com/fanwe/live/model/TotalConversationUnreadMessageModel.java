package com.fanwe.live.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/21.
 */
public class TotalConversationUnreadMessageModel
{
    public Map<String, ConversationUnreadMessageModel> hashConver = new HashMap<>();
    private long totalUnreadNum;
    private String str_totalUnreadNum;

    public long geUnreadNum(String peer) {
        return hashConver.get(peer).getUnreadnum();
    }

    public String getStr_totalUnreadNum()
    {
        return str_totalUnreadNum;
    }

    public void setStr_totalUnreadNum(String str_totalUnreadNum)
    {
        this.str_totalUnreadNum = str_totalUnreadNum;
    }

    public long getTotalUnreadNum()
    {
        return totalUnreadNum;
    }

    public void setTotalUnreadNum(long totalUnreadNum)
    {
        if (totalUnreadNum < 99)
        {
            str_totalUnreadNum = Long.toString(totalUnreadNum);
        } else
        {
            str_totalUnreadNum = "99+";
        }
        this.totalUnreadNum = totalUnreadNum;
    }
}
