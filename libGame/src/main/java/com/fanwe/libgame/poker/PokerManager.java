package com.fanwe.libgame.poker;

import com.fanwe.libgame.BetGameManager;
import com.fanwe.libgame.poker.model.PokerGroupResultData;
import com.fanwe.library.looper.ISDLooper;
import com.fanwe.library.looper.impl.SDSimpleLooper;
import com.fanwe.library.utils.LogUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/6/10.
 */

public class PokerManager extends BetGameManager
{
    private ISDLooper mResultSequentiallyLooper;
    private List<PokerGroupResultData> mListResultData;

    private PokerManagerCallback mCallback;

    public void setCallback(PokerManagerCallback callback)
    {
        mCallback = callback;
    }

    @Override
    public void start(long countTime)
    {
        super.start(countTime);
        stopResultSequentiallyLooper();
    }

    /**
     * 开始发牌
     *
     * @param anim 是否执行发牌动画
     */
    public void startDealPoker(boolean anim)
    {
        mCallback.onStartDealPoker(anim);
    }

    /**
     * 设置开牌数据
     *
     * @param listData 牌面数据
     */
    public void setResultData(List<PokerGroupResultData> listData)
    {
        if (listData == null)
        {
            return;
        }
        mListResultData = listData;
        mCallback.onResultData(mListResultData);
    }

    /**
     * 开牌
     *
     * @param sequentially 按顺序开牌
     */
    public void showResult(boolean sequentially)
    {
        LogUtil.i("showResult sequentially:" + sequentially);
        stopCountDownTimer();
        mCallback.onStartShowResult(sequentially);
        if (sequentially)
        {
            startResultSequentiallyLooper(3);
        } else
        {
            mCallback.onShowResult(0);
            mCallback.onShowResult(1);
            mCallback.onShowResult(2);
            startMarkWinPosition(0);
        }
    }

    /**
     * 按顺序开牌
     *
     * @param count 一共有几组牌要开
     */
    private void startResultSequentiallyLooper(final int count)
    {
        if (mResultSequentiallyLooper == null)
        {
            mResultSequentiallyLooper = new SDSimpleLooper();
        }
        mResultSequentiallyLooper.start(1000, 1000, new Runnable()
        {
            private int position = 0;

            @Override
            public void run()
            {
                mCallback.onShowResult(position);
                position++;
                if (position > count - 1)
                {
                    stopResultSequentiallyLooper();
                    startMarkWinPosition(500);
                }
            }
        });
    }

    /**
     * 停止开牌任务
     */
    private void stopResultSequentiallyLooper()
    {
        if (mResultSequentiallyLooper != null)
        {
            mResultSequentiallyLooper.stop();
        }
    }

    @Override
    protected BetGameManagerCallback getBetGameManagerCallback()
    {
        return mCallback;
    }

    @Override
    public void onDestroy()
    {
        stopResultSequentiallyLooper();
        super.onDestroy();
    }

    public interface PokerManagerCallback extends BetGameManagerCallback
    {
        /**
         * 开始发牌
         *
         * @param anim 是否执行发牌动画
         */
        void onStartDealPoker(boolean anim);

        /**
         * 设置牌面数据
         *
         * @param listData
         */
        void onResultData(List<PokerGroupResultData> listData);

        /**
         * 开牌
         *
         * @param position 要开第几组的牌
         */
        void onShowResult(int position);

        /**
         * 触发开牌回调
         */
        void onStartShowResult(boolean sequentially);
    }
}
