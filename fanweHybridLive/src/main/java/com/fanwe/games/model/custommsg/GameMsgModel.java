package com.fanwe.games.model.custommsg;

import com.fanwe.games.model.GameBankerModel;

/**
 * Created by shibx on 2016/11/25.
 * 游戏开始推送消息
 */

public class GameMsgModel extends GameBaseModel
{
    private GameGoldFlowerGameData game_data;

    /**
     * @see com.fanwe.games.BankerBusiness.State
     */
    private int banker_status = -1; //当前上庄状态
    private GameBankerModel banker; //当前庄家信息
    private long principal; //申请上庄需要的底金

    public GameGoldFlowerGameData getGame_data()
    {
        return game_data;
    }

    public void setGame_data(GameGoldFlowerGameData game_data)
    {
        this.game_data = game_data;
    }

    public int getBanker_status()
    {
        return banker_status;
    }

    public void setBanker_status(int banker_status)
    {
        this.banker_status = banker_status;
    }

    public GameBankerModel getBanker()
    {
        return banker;
    }

    public void setBanker(GameBankerModel banker)
    {
        this.banker = banker;
    }

    public long getPrincipal()
    {
        return principal;
    }

    public void setPrincipal(long principal)
    {
        this.principal = principal;
    }
}
