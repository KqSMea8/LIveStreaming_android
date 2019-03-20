package com.fanwe.libgame;

import android.os.CountDownTimer;

import com.fanwe.libgame.model.GameBetCoinsOptionModel;
import com.fanwe.library.model.SDDelayRunnable;
import com.fanwe.library.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 投注游戏管理类
 */
public abstract class BetGameManager extends BaseGameManager
{
    private CountDownTimer mCountDownTimer;
    /**
     * 赢的投注位置
     */
    private int mWinPosition;
    /**
     * 是否可以投注
     */
    private boolean mCanBet = true;

    @Override
    public void setCreater(boolean creater)
    {
        super.setCreater(creater);
        if (isCreater())
        {
            setCanBet(false);
        }
    }

    /**
     * 设置赢的位置
     *
     * @param winPosition
     */
    public void setWinPosition(int winPosition)
    {
        mWinPosition = winPosition;
    }

    /**
     * 获得赢的位置
     *
     * @return
     */
    public int getWinPosition()
    {
        return mWinPosition;
    }

    /**
     * 是否可以投注
     *
     * @return
     */
    public boolean isCanBet()
    {
        return mCanBet;
    }

    /**
     * 开始游戏
     *
     * @param countTime 倒计时(毫秒)
     */
    public void start(long countTime)
    {
        LogUtil.i("start countTime:" + countTime);
        stopMarkWinPosition();

        getBetGameManagerCallback().onStart(countTime);

        stopCountDownTimer();
        mCountDownTimer = new CountDownTimer(countTime, 1000)
        {
            @Override
            public void onTick(long leftTime)
            {
                getBetGameManagerCallback().onCountDownTick(leftTime);
            }

            @Override
            public void onFinish()
            {
                getBetGameManagerCallback().onCountDownFinish();
            }
        };
        mCountDownTimer.start();
        getBetGameManagerCallback().onShowCountDown(true);
    }

    /**
     * 设置投注倍数
     *
     * @param listData
     */
    public void setBetMultipleData(List<String> listData)
    {
        if (listData != null)
        {
            getBetGameManagerCallback().onUpdateBetMultiple(listData);
        }
    }

    /**
     * 设置总投注
     *
     * @param listData
     */
    public void setTotalBetData(List<Integer> listData)
    {
        if (listData != null)
        {
            getBetGameManagerCallback().onUpdateTotalBet(listData);
        }
    }

    /**
     * 设置用户的投注
     *
     * @param listData
     */
    public void setUserBetData(List<Integer> listData)
    {
        if (listData != null)
        {
            getBetGameManagerCallback().onUpdateUserBet(listData);
        }
    }

    /**
     * 设置可选的游戏币投注选项
     *
     * @param listData
     */
    public void setBetCoinsOptionData(List<Integer> listData)
    {
        if (listData == null || listData.isEmpty())
        {
            return;
        }

        List<GameBetCoinsOptionModel> listModel = new ArrayList<>();
        for (Integer item : listData)
        {
            if (item != null)
            {
                GameBetCoinsOptionModel model = new GameBetCoinsOptionModel();
                model.setCoins(item);
                listModel.add(model);
            }
        }
        getBetGameManagerCallback().onUpdateBetCoinsOption(listModel);
    }

    /**
     * 标注赢的位置
     *
     * @param delay 延迟多久后触发标注
     */
    public void startMarkWinPosition(long delay)
    {
        stopMarkWinPosition();

        if (delay > 0)
        {
            mMarkWinPositionRunnable.runDelay(delay);
        } else
        {
            mMarkWinPositionRunnable.run();
        }
    }

    private SDDelayRunnable mMarkWinPositionRunnable = new SDDelayRunnable()
    {
        @Override
        public void run()
        {
            LogUtil.i("markWinPositionRunnable:" + mWinPosition);
            getBetGameManagerCallback().onMarkWinPosition(mWinPosition);
        }
    };

    /**
     * 设置是否可以投注
     *
     * @param canBet
     */
    public void setCanBet(boolean canBet)
    {
        LogUtil.i("setCanBet:" + canBet);
        mCanBet = canBet;
        getBetGameManagerCallback().onCanBetChanged(canBet);
    }

    /**
     * 下注成功
     *
     * @param betPosition 下注位置
     * @param betCoins    下注金额
     */
    public void onBetSuccess(int betPosition, long betCoins)
    {
        getBetGameManagerCallback().onBetSuccess(betPosition, betCoins);
    }

    /**
     * 停止倒数倒计时
     */
    protected void stopCountDownTimer()
    {
        if (mCountDownTimer != null)
        {
            mCountDownTimer.cancel();
        }
        getBetGameManagerCallback().onShowCountDown(false);
    }

    /**
     * 停止标注赢的牌组任务
     */
    private void stopMarkWinPosition()
    {
        if (mMarkWinPositionRunnable != null)
        {
            mMarkWinPositionRunnable.removeDelay();
        }
    }

    @Override
    protected BaseGameManagerCallback getBaseGameManagerCallback()
    {
        return getBetGameManagerCallback();
    }

    protected abstract BetGameManagerCallback getBetGameManagerCallback();

    @Override
    public void onDestroy()
    {
        stopCountDownTimer();
        stopMarkWinPosition();
    }

    public interface BetGameManagerCallback extends BaseGameManagerCallback
    {
        /**
         * 开始游戏
         *
         * @param countTime 倒计时(毫秒)
         */
        void onStart(long countTime);

        /**
         * 倒计时每秒触发
         *
         * @param leftTime 剩余时长(毫秒)
         */
        void onCountDownTick(long leftTime);

        /**
         * 倒计时结束
         */
        void onCountDownFinish();

        /**
         * 显示隐藏倒计时
         *
         * @param show
         */
        void onShowCountDown(boolean show);

        /**
         * 标注赢的位置
         *
         * @param winPosition
         */
        void onMarkWinPosition(int winPosition);

        /**
         * 设置倍数数据
         *
         * @param listData
         */
        void onUpdateBetMultiple(List<String> listData);

        /**
         * 设置总投注数据
         *
         * @param listData
         */
        void onUpdateTotalBet(List<Integer> listData);

        /**
         * 设置我的投注数据
         *
         * @param listData
         */
        void onUpdateUserBet(List<Integer> listData);

        /**
         * 设置可选的游戏币投注选项
         *
         * @param listData
         */
        void onUpdateBetCoinsOption(List<GameBetCoinsOptionModel> listData);

        /**
         * 设置是否可以投注
         *
         * @param canBet
         */
        void onCanBetChanged(boolean canBet);

        /**
         * 下注成功
         *
         * @param betPosition 下注位置
         * @param betCoins    下注金额
         */
        void onBetSuccess(int betPosition, long betCoins);
    }
}
