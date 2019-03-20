package com.fanwe.libgame.dice;

import com.fanwe.libgame.BetGameManager;

import java.util.List;

/**
 * Created by yhz on 2017/6/19.
 */

public class DiceManager extends BetGameManager
{
    private DiceManagerCallback mCallback;

    public void setCallback(DiceManagerCallback callback)
    {
        mCallback = callback;
    }

    /**
     * 显示游戏结果
     */
    public void showResult(List<Integer> listData)
    {
        if (listData == null)
        {
            return;
        }

        stopCountDownTimer();
        mCallback.onShowResult(listData);
    }

    @Override
    protected BetGameManagerCallback getBetGameManagerCallback()
    {
        return mCallback;
    }

    public interface DiceManagerCallback extends BetGameManagerCallback
    {
        void onShowResult(List<Integer> listData);
    }
}
