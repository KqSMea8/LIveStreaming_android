package com.fanwe.games.model.custommsg;

import com.fanwe.games.model.GameBankerModel;
import com.fanwe.live.model.custommsg.CustomMsg;

import java.util.List;

/**
 * Created by shibx on 2017/02/23.
 * 游戏上庄数据对象
 */

public class CustomMsgGameBanker extends CustomMsg
{
    private int room_id;//房间号
    /**
     * @see com.fanwe.games.BankerBusiness.Action
     */
    private int action;
    /**
     * @see com.fanwe.games.BankerBusiness.State
     */
    private int banker_status;
    private String desc;
    private BankerData data;

    public int getRoom_id()
    {
        return room_id;
    }

    public void setRoom_id(int room_id)
    {
        this.room_id = room_id;
    }

    public int getAction()
    {
        return action;
    }

    public void setAction(int action)
    {
        this.action = action;
    }

    public int getBanker_status()
    {
        return banker_status;
    }

    public void setBanker_status(int banker_status)
    {
        this.banker_status = banker_status;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public BankerData getData()
    {
        return data;
    }

    public void setData(BankerData data)
    {
        this.data = data;
    }

    public static class BankerData
    {
        private long principal;
        private List<GameBankerModel> banker_list;
        private GameBankerModel banker;

        public long getPrincipal()
        {
            return principal;
        }

        public void setPrincipal(long principal)
        {
            this.principal = principal;
        }

        public List<GameBankerModel> getBanker_list()
        {
            return banker_list;
        }

        public void setBanker_list(List<GameBankerModel> banker_list)
        {
            this.banker_list = banker_list;
        }

        public GameBankerModel getBanker()
        {
            return banker;
        }

        public void setBanker(GameBankerModel banker)
        {
            this.banker = banker;
        }
    }
}
