package com.fanwe.libgame;

import com.fanwe.library.utils.LogUtil;

/**
 * 游戏管理类
 */
public abstract class BaseGameManager
{

    //用户底部金币图标
    private int mUserCoinsImageRes;

    /**
     * 是否是游戏的创建者
     */
    private boolean mIsCreater = false;
    /**
     * 用户可用游戏币
     */
    private long mUserCoins;

    /**
     * 游戏状态，不同的游戏值不同
     */
    private int mGameState = -1;

    /**
     * 设置游戏状态
     *
     * @param gameState
     */
    public final void setGameState(int gameState)
    {
        if (gameState != mGameState)
        {
            onGameStateChanged(mGameState, gameState);
            getBaseGameManagerCallback().onGameStateChanged(mGameState, gameState);
            mGameState = gameState;
        }
    }

    /**
     * 游戏状态变化回调
     *
     * @param oldState
     * @param newState
     */
    protected void onGameStateChanged(int oldState, int newState)
    {

    }

    /**
     * 获得游戏状态
     *
     * @return
     */
    public int getGameState()
    {
        return mGameState;
    }

    public boolean isCreater()
    {
        return mIsCreater;
    }

    public void setCreater(boolean creater)
    {
        LogUtil.i("setCreater:" + mIsCreater);
        mIsCreater = creater;
    }

    /**
     * 获得用户游戏币
     *
     * @return
     */
    public long getUserCoins()
    {
        return mUserCoins;
    }

    /**
     * 设置用户游戏币
     *
     * @param userCoins
     */
    public void setUserCoins(long userCoins)
    {
        LogUtil.i("setUserCoins:" + userCoins);
        mUserCoins = userCoins;
        getBaseGameManagerCallback().onUpdateUserCoins(userCoins);
    }

    /**
     * 底部充值秀豆图标
     *
     * @param imageRes
     */
    public void setUserCoinsImageRes(int imageRes)
    {
        mUserCoinsImageRes = imageRes;
        getBaseGameManagerCallback().onUserCoinsImageRes(imageRes);
    }

    /**
     * 设置是否有自动开始游戏功能
     *
     * @param hasAutoStartMode
     */
    public void setHasAutoStartMode(boolean hasAutoStartMode)
    {
        if (mIsCreater)
        {
            getBaseGameManagerCallback().onHasAutoStartMode(hasAutoStartMode);
        } else
        {
            getBaseGameManagerCallback().onHasAutoStartMode(false);
        }
    }

    /**
     * 设置当前是否是自动开始游戏模式
     *
     * @param isAutoStartMode
     */
    public void setAutoStartMode(boolean isAutoStartMode)
    {
        getBaseGameManagerCallback().onAutoStartModeChanged(isAutoStartMode);
    }

    protected abstract BaseGameManagerCallback getBaseGameManagerCallback();

    public abstract void onDestroy();

    public interface BaseGameManagerCallback
    {
        /**
         * 更新用户游戏币
         *
         * @param userCoins
         */
        void onUpdateUserCoins(long userCoins);

        /**
         * 游戏状态变化回调
         *
         * @param oldState
         * @param newState
         */
        void onGameStateChanged(int oldState, int newState);

        /**
         * 设置是否有自动开始功能
         *
         * @param hasAutoStartMode
         */
        void onHasAutoStartMode(boolean hasAutoStartMode);

        /**
         * 是否自动开始游戏模式发生变化
         *
         * @param isAutoStartMode
         */
        void onAutoStartModeChanged(boolean isAutoStartMode);

        /**
         * 底部充值图标
         * @param res
         */
        void onUserCoinsImageRes(int res);
    }

}
