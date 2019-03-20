package com.fanwe.games;

import com.fanwe.games.model.custommsg.GameMsgModel;
import com.fanwe.libgame.poker.model.PokerGroupResultData;

import java.util.List;

/**
 * 炸金花，牛牛游戏业务类
 */
public class PokerGameBusiness extends BetGameBusiness
{
    private PokerGameBusinessCallback mCallback;

    public PokerGameBusiness(GameBusiness gameBusiness)
    {
        super(gameBusiness);
    }

    public void setCallback(PokerGameBusinessCallback callback)
    {
        this.mCallback = callback;
    }

    @Override
    protected void onGameSettlement(GameMsgModel msg, boolean isPush)
    {
        super.onGameSettlement(msg, isPush);

        mCallback.onBsGamePokerUpdatePokerDatas(msg.getGame_data().getListGroupResultData(), msg.getGame_data().getWin() - 1, isPush);
    }

    @Override
    protected BetGameBusinessCallback getBetGameBusinessCallback()
    {
        return mCallback;
    }

    public interface PokerGameBusinessCallback extends BetGameBusinessCallback
    {
        /**
         * 更新扑克牌
         *
         * @param listData    牌组数据
         * @param winPosition 赢的牌组[0-2]
         * @param isPush
         */
        void onBsGamePokerUpdatePokerDatas(List<PokerGroupResultData> listData, int winPosition, boolean isPush);
    }
}
