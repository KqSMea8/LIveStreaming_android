package com.fanwe.games;

import com.fanwe.games.model.custommsg.GameGoldFlowerGameData;
import com.fanwe.games.model.custommsg.GameMsgModel;

import java.util.List;

/**
 * Created by yhz on 2017/5/27. 投大小
 */

public class DiceGameBusiness extends BetGameBusiness
{
    private GameDiceBusinessListener mGameDiceBusinessListener;

    public void setGameDiceBusinessListener(GameDiceBusinessListener gameDiceBusinessListener)
    {
        mGameDiceBusinessListener = gameDiceBusinessListener;
    }

    public DiceGameBusiness(GameBusiness gameBusiness)
    {
        super(gameBusiness);
    }

    @Override
    protected BetGameBusinessCallback getBetGameBusinessCallback()
    {
        return mGameDiceBusinessListener;
    }

    @Override
    protected void onGameSettlement(GameMsgModel msg, boolean isPush)
    {
        super.onGameSettlement(msg, isPush);
        onGameThrowDice(msg, isPush);
    }

    private void onGameThrowDice(GameMsgModel msg, boolean isPush)
    {
        GameGoldFlowerGameData data = msg.getGame_data();
        if (data == null)
        {
            return;
        }
        if (data.getDices() == null)
        {
            return;
        }
        if (data.getDices().size() != 2)
        {
            return;
        }
        mGameDiceBusinessListener.onBsGameDiceThrowDice(data.getDices(), data.getWin(), isPush);
    }

    public interface GameDiceBusinessListener extends BetGameBusinessCallback
    {
        void onBsGameDiceThrowDice(List<Integer> listData, int winPosition, boolean isPush);
    }
}
