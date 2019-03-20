package com.fanwe.live.model.custommsg;

import com.fanwe.live.LiveConstant;

/**
 * 大型礼物通知消息
 */
public class CustomMsgHeatRank extends CustomMsg
{
    private int heat_rank;

    public CustomMsgHeatRank()
    {
        super();
        setType(LiveConstant.CustomMsgType.MSG_HEAT_RANK);
    }

    public int getHeat_rank() {
        return heat_rank;
    }

    public void setHeat_rank(int heat_rank) {
        this.heat_rank = heat_rank;
    }
}
