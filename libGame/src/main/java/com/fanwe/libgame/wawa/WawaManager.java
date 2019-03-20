package com.fanwe.libgame.wawa;

import com.fanwe.libgame.BetGameManager;

import java.util.List;

/**
 * Created by yhz on 2017/6/19.
 */

public class WawaManager extends BetGameManager
{
    private WawaManagerCallback mCallback;

    public void setCallback(WawaManagerCallback callback)
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

    public interface WawaManagerCallback extends BetGameManagerCallback
    {
        void onShowResult(List<Integer> listData);
    }
}
