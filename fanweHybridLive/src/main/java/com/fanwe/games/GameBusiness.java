package com.fanwe.games;

import android.util.Log;
import android.view.View;

import com.fanwe.games.common.GameInterface;
import com.fanwe.games.model.App_getGamesActModel;
import com.fanwe.games.model.App_requestGameIncomeActModel;
import com.fanwe.games.model.App_startGameActModel;
import com.fanwe.games.model.Games_autoStartActModel;
import com.fanwe.games.model.PluginModel;
import com.fanwe.games.model.custommsg.GameMsgModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.handler.SDRequestHandler;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.model.SDDelayRunnable;
import com.fanwe.live.LiveConstant;
import com.fanwe.live.activity.room.ILiveActivity;
import com.fanwe.live.business.LiveBaseBusiness;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.custommsg.MsgModel;

/**
 * 游戏基础业务类
 */
public class GameBusiness extends LiveBaseBusiness
{
    private static final String TAG = "GameBusiness";

    /**
     * 主播通过插件选择的游戏id
     */
    private int mSelectedGameId;
    /**
     * 当前游戏id
     */
    private int mGameId;
    /**
     * 游戏轮数id
     */
    private int mGameLogId;
    /**
     * 游戏是否已经开始
     */
    private boolean mIsGameStarted;
    /**
     * 是否正在游戏轮数中(投注中)
     */
    private boolean mIsInGameRound;
    /**
     * 最后一次游戏推送消息的时间
     */
    private double mLastGameMsgTime;
    /**
     * 当前是否是自动开始游戏模式
     */
    private boolean mIsAutoStartMode;

    private GameBusinessCallback mCallback;

    private SDRequestHandler mRequestStartGameHandler;

    private static final long TIMEOUT_QUERY = 5000L;

    public GameBusiness(ILiveActivity liveActivity)
    {
        super(liveActivity);
    }

    public void setCallback(GameBusinessCallback callback)
    {
        this.mCallback = callback;
    }

    /**
     * 游戏是否已经开始
     *
     * @return
     */
    public boolean isGameStarted()
    {
        return mIsGameStarted;
    }

    /**
     * 当前游戏是否处于游戏轮数中(投注中)
     *
     * @return
     */
    public boolean isInGameRound()
    {
        return mIsInGameRound;
    }

    /**
     * 获得当前游戏id
     *
     * @return
     */
    public int getGameId()
    {
        return mGameId;
    }

    /**
     * 获得当前游戏轮数id
     *
     * @return
     */
    public int getGameLogId()
    {
        return mGameLogId;
    }

    /**
     * 是否自动开始游戏模式
     *
     * @return
     */
    public boolean isAutoStartMode()
    {
        return mIsAutoStartMode;
    }

    /**
     * 获得游戏币单位
     *
     * @return
     */
    public String getGameCurrencyUnit()
    {
        return AppRuntimeWorker.getGameCurrencyUnit();
    }

    /**
     * 设置当前是否是自动开始游戏模式
     *
     * @param autoStartMode
     */
    private void setAutoStartMode(boolean autoStartMode)
    {
        if (mIsAutoStartMode != autoStartMode)
        {
            mIsAutoStartMode = autoStartMode;
            mCallback.onGameAutoStartModeChanged(mIsAutoStartMode);
        }
    }

    /**
     * 处理游戏消息（外部需要调用）
     *
     * @param msg
     */
    public void onMsgGame(MsgModel msg)
    {
        if (!msg.isGameMsg())
        {
            return;
        }
        GameMsgModel msgModel = msg.getCustomMsgGame();
        if (msgModel.isOtherUserMsg())
        {
            return;
        }
        if (!isTimestampLegal(msgModel.getTimestamp()))
        {
            Log.e(TAG, "不合法的timestamp消息(gameAction:" + msgModel.getGame_action() + ")");
            return;
        }
        saveTimestamp(msgModel.getTimestamp());

        int type = msg.getCustomMsgType();
        if (type == LiveConstant.CustomMsgType.MSG_GAMES_STOP)
        {
            notifyStopGame();
            Log.i(TAG, "游戏结束消息----------");
        } else if (type == LiveConstant.CustomMsgType.MSG_GAME)
        {
            dealGameMsg(msgModel, true);
        }
    }

    /**
     * 消息时间戳是否合法
     *
     * @param timestamp
     * @return
     */
    private boolean isTimestampLegal(double timestamp)
    {
        return timestamp >= mLastGameMsgTime;
    }

    /**
     * 保存游戏时间戳
     *
     * @param timestamp
     */
    private void saveTimestamp(double timestamp)
    {
        if (timestamp > mLastGameMsgTime)
        {
            mLastGameMsgTime = timestamp;
        }
    }

    /**
     * 处理游戏消息
     *
     * @param msgModel
     * @param isPush
     */
    private void dealGameMsg(GameMsgModel msgModel, boolean isPush)
    {
        if (msgModel == null)
        {
            return;
        }
        String logInfo = "(gameAction:" + msgModel.getGame_action() + ") (gameStatus:" + msgModel.getGame_status() + ") (isPush:" + isPush + ")";

        int gameLogId = msgModel.getGame_log_id();
        if (!isGameLogIdLegal(gameLogId))
        {
            Log.e(TAG, "不合法的gameLogId消息:" + logInfo);
            return;
        }

        saveGameLogId(gameLogId);
        saveGameId(msgModel);
        Log.i(TAG, "----------合法消息：" + logInfo);
        notifyGameMsg(msgModel, isPush);
    }

    /**
     * 游戏轮数id是否合法
     *
     * @param gameLogId
     * @return
     */
    private boolean isGameLogIdLegal(int gameLogId)
    {
        return gameLogId >= mGameLogId;
    }

    /**
     * 保存游戏轮数id
     *
     * @param gameLogId
     */
    private void saveGameLogId(int gameLogId)
    {
        if (gameLogId > mGameLogId)
        {
            mGameLogId = gameLogId;
            Log.i(TAG, "保存游戏轮数id:" + gameLogId);
        }
    }

    /**
     * 保存当前游戏id
     *
     * @param msgModel
     */
    private void saveGameId(GameMsgModel msgModel)
    {
        int gameId = msgModel.getGame_id();
        if (gameId <= 0)
        {
            return;
        }

        if (!getLiveActivity().isCreater())
        {
            setGameStarted(true);
        }

        if (mGameId != gameId)
        {
            if (mGameId > 0)
            {
                notifyRemovePanel(); //如果有游戏存在，先移除
            }
            mGameId = gameId;
            Log.i(TAG, "保存当前游戏id:" + gameId);
            notifyInitPanel(msgModel);
        }
    }

    /**
     * 选择启动游戏插件（主播外部需要调用）
     *
     * @param model
     */
    public final void selectGame(PluginModel model)
    {
        if (model == null || model.getChild_id() <= 0)
        {
            return;
        }
        if (mSelectedGameId > 0)
        {
            return;
        }

        mSelectedGameId = model.getChild_id();//保存主播选择的游戏
        setStateReadyToStart();

        requestStartGame();
    }

    public void startRequestGameInfoDelay()
    {
        Log.i(TAG, "开始延时查询任务");
        mRequestGameInfoRunnable.runDelay(TIMEOUT_QUERY);
    }

    void cancelRequestGameInfoDelay()
    {
        Log.i(TAG, "取消延时查询任务");
        mRequestGameInfoRunnable.removeDelay();
    }

    private SDDelayRunnable mRequestGameInfoRunnable = new SDDelayRunnable()
    {
        @Override
        public void run()
        {
            requestGameInfo();
        }
    };

    /**
     * 请求游戏信息
     */
    public void requestGameInfo()
    {
        Log.i(TAG, "尝试发起调用查询游戏信息接口");
        if (getLiveActivity().getRoomInfo() == null)
        {
            Log.e(TAG, "发起调用查询游戏信息接口失败：房间id为空");
            return;
        }
        if (getLiveActivity().getRoomInfo().getGame_log_id() <= 0 && this.mGameLogId <= 0)
        {
            Log.e(TAG, "发起调用查询游戏信息接口失败：非法game_log_id");
            return;
        }

        int roomId = getLiveActivity().getRoomId();
        CommonInterface.requestGamesInfo(roomId, new AppRequestCallback<App_getGamesActModel>()
        {
            @Override
            public String getCancelTag()
            {
                return getHttpCancelTag();
            }

            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    setAutoStartMode(actModel.getData().getAuto_start() == 1);

                    //处理游戏信息
                    dealGameMsg(actModel.getData(), false);
                } else
                {
                    Log.e(TAG, "查询游戏信息接口失败");
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
                Log.e(TAG, "查询游戏信息接口异常:" + resp.getThrowable());
            }
        });
    }

    /**
     * 请求更新用户的游戏币
     */
    public void requestGameCurrency()
    {
        requestGameIncomeInternal(0);
    }

    /**
     * 请求游戏收入
     */
    public void requestGameIncome()
    {
        requestGameIncomeInternal(mGameLogId);
    }

    /**
     * 请求游戏收入
     *
     * @param gameLogId 游戏轮数id
     */
    private void requestGameIncomeInternal(int gameLogId)
    {
        CommonInterface.requestGameIncome(gameLogId, new AppRequestCallback<App_requestGameIncomeActModel>()
        {
            @Override
            public String getCancelTag()
            {
                return getHttpCancelTag();
            }

            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    //更新游戏币
                    updateAccount(actModel.getUser_diamonds(), actModel.getCoin());
                    mCallback.onGameRequestGameIncomeSuccess(actModel);
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
            }
        });
    }

    /**
     * 获得游戏币数量
     *
     * @return
     */
    public long getGameCurrency()
    {
        return UserModelDao.getGameCurrency();
    }

    /**
     * 更新秀豆或游戏币
     *
     * @param coins
     */
    public void updateAccount(long diamonds, long coins)
    {
        if (AppRuntimeWorker.isUseGameCurrency())
        {
            UserModelDao.updateCoins(coins);
        } else
        {
            UserModelDao.updateDiamonds(diamonds);
        }
        refreshGameCurrency();
    }

    /**
     * 余额是否够支付
     *
     * @param value
     * @return
     */
    public boolean canGameCurrencyPay(long value)
    {
        return UserModelDao.canGameCurrencyPay(value);
    }

    /**
     * 刷新游戏币
     */
    public void refreshGameCurrency()
    {
        notifyUpdateGameCurrency(UserModelDao.getGameCurrency());
    }

    private void setGameStarted(boolean gameStarted)
    {
        mIsGameStarted = gameStarted;
    }

    /**
     * 设置状态为可以准备开始
     */
    private void setStateReadyToStart()
    {
        notifyShowGameCtrlStart(true); // 显示开始
        notifyShowGameCtrlClose(mIsGameStarted); //根据游戏是否已经开始来决定是否显示关闭
        notifyShowGameCtrlWaiting(false); //隐藏等待
    }

    /**
     * 设置状态为请求开始游戏
     */
    private void setStateWaiting()
    {
        notifyShowGameCtrlStart(false); //隐藏开始
        notifyShowGameCtrlClose(false); //隐藏关闭
        notifyShowGameCtrlWaiting(true); //显示等待
    }

    /**
     * 设置状态为游戏结束
     */
    private void setStateStopped()
    {
        notifyShowGameCtrlStart(false); //隐藏开始
        notifyShowGameCtrlClose(false); //隐藏关闭
        notifyShowGameCtrlWaiting(false); //隐藏等待
    }

    /**
     * 设置是否正在游戏轮数中
     *
     * @param isInGameRound true-是
     */
    public void setInGameRound(boolean isInGameRound)
    {
        if (!mIsGameStarted)
        {
            return;
        }
        this.mIsInGameRound = isInGameRound;
        if (isInGameRound)
        {
            notifyShowGameCtrlStart(false); //隐藏开始
            notifyShowGameCtrlClose(true); // 显示关闭
            notifyShowGameCtrlWaiting(true); //显示等待
        } else
        {
            notifyShowGameCtrlStart(true); // 显示开始
            notifyShowGameCtrlClose(true); //显示关闭
            notifyShowGameCtrlWaiting(false); //隐藏等待
        }
    }

    /**
     * 请求开始游戏
     */
    public void requestStartGame()
    {
        if (mSelectedGameId <= 0 && mGameId <= 0)
        {
            return;
        }
        setStateWaiting();
        cancelRequestStartGameHandler();

        int id = mSelectedGameId > 0 ? mSelectedGameId : mGameId; //优先用选择的游戏id来启动

        Log.i(TAG, "请求开始游戏接口:" + id);
        mRequestStartGameHandler = CommonInterface.requestStartPlugin(id, new AppRequestCallback<App_startGameActModel>()
        {
            @Override
            public String getCancelTag()
            {
                return getHttpCancelTag();
            }

            @Override
            protected void onStart()
            {
                super.onStart();
                showProgress("");
            }

            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    startRequestGameInfoDelay(); //为了防止开始推送没收到，延迟请求游戏信息

                    setGameStarted(true);
                    setInGameRound(true);
                    mCallback.onGameRequestStartGameSuccess(actModel);
                } else
                {
                    setStateReadyToStart();
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
                setStateReadyToStart();
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                hideProgress();
            }
        });
    }

    /**
     * 设置是否是自动开始游戏模式
     *
     * @param autoStart
     */
    public void requestAutoStartGame(boolean autoStart)
    {
        int auto_start = autoStart ? 1 : 0;
        GameInterface.requestAutoStartGame(auto_start, new AppRequestCallback<Games_autoStartActModel>()
        {
            @Override
            public String getCancelTag()
            {
                return getHttpCancelTag();
            }

            @Override
            protected void onStart()
            {
                super.onStart();
                showProgress("正在切换");
            }

            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    setAutoStartMode(actModel.getAuto_start() == 1);
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                hideProgress();
            }
        });
    }

    /**
     * 请求结束游戏
     */
    public void requestStopGame()
    {
        cancelRequestStartGameHandler();
        cancelRequestGameInfoDelay();
        if (mIsGameStarted)
        {
            CommonInterface.requestStopGame(new AppRequestCallback<BaseActModel>()
            {
                @Override
                public String getCancelTag()
                {
                    return getHttpCancelTag();
                }

                @Override
                protected void onStart()
                {
                    super.onStart();
                    showProgress("");
                }

                @Override
                protected void onSuccess(SDResponse sdResponse)
                {
                    if (actModel.isOk())
                    {
                        setGameStarted(false);
                        mCallback.onGameRequestStopGameSuccess(actModel);
                    }
                }

                @Override
                protected void onFinish(SDResponse resp)
                {
                    super.onFinish(resp);
                    hideProgress();
                }
            });
        } else
        {
            onStopGame();
        }
    }

    private void onStopGame()
    {
        mGameLogId = 0;
        mGameId = 0;
        mSelectedGameId = 0;
        mIsGameStarted = false;

        setInGameRound(false);
        setStateStopped();
    }

    /**
     * 通知初始化游戏面板
     *
     * @param msg
     */
    public void notifyInitPanel(GameMsgModel msg)
    {
        mCallback.onGameInitPanel(msg);
        notifyHasAutoStartMode();
        Log.i(TAG, "初始化游戏面板:" + msg.getGame_id());
    }

    /**
     * 通知移除游戏面板
     */
    public void notifyRemovePanel()
    {
        mCallback.onGameRemovePanel();
        Log.i(TAG, "移除游戏面板:" + mGameId);
    }

    /**
     * 通知是否有自动开始游戏功能
     */
    public void notifyHasAutoStartMode()
    {
        //TODO 读取配置或者根据服务端返回来决定是否显示打开自动开始游戏功能
        mCallback.onGameHasAutoStartMode(true);
    }

    /**
     * 通知游戏消息
     *
     * @param msg
     * @param isPush
     */
    private void notifyGameMsg(GameMsgModel msg, boolean isPush)
    {
        mCallback.onGameMsg(msg, isPush);
    }

    /**
     * 通知游戏结束
     */
    private void notifyStopGame()
    {
        onStopGame();
        mCallback.onGameMsgStopGame();
    }

    /**
     * 通知刷新游戏币
     *
     * @param value
     */
    private void notifyUpdateGameCurrency(long value)
    {
        mCallback.onGameUpdateGameCurrency(value);
    }

    /**
     * 显示隐藏开始按钮
     *
     * @param show
     */
    public void notifyShowGameCtrlStart(boolean show)
    {
        mCallback.onGameCtrlShowStart(show, mGameId);
    }

    /**
     * 显示隐藏关闭按钮
     *
     * @param show
     */
    public void notifyShowGameCtrlClose(boolean show)
    {
        mCallback.onGameCtrlShowClose(show, mGameId);
    }

    /**
     * 显示隐藏等待按钮
     *
     * @param show
     */
    public void notifyShowGameCtrlWaiting(boolean show)
    {
        mCallback.onGameCtrlShowWaiting(show, mGameId);
    }

    private void cancelRequestStartGameHandler()
    {
        if (mRequestStartGameHandler != null)
        {
            mRequestStartGameHandler.cancel();
        }
    }

    @Override
    protected BaseBusinessCallback getBaseBusinessCallback()
    {
        return mCallback;
    }

    public void onDestroy()
    {
        cancelRequestStartGameHandler();
        cancelRequestGameInfoDelay();
        super.onDestroy();
    }

    public interface GameCtrlViewClickCallback
    {
        void onClickGameCtrlStart(View view);

        void onClickGameCtrlClose(View view);
    }

    public interface GameCtrlView
    {
        /**
         * 显示隐藏开始按钮
         *
         * @param show
         */
        void onGameCtrlShowStart(boolean show, int gameId);

        /**
         * 显示隐藏关闭按钮
         *
         * @param show
         */
        void onGameCtrlShowClose(boolean show, int gameId);

        /**
         * 显示隐藏等待按钮
         *
         * @param show
         */
        void onGameCtrlShowWaiting(boolean show, int gameId);
    }

    public interface GameBusinessCallback extends GameCtrlView, BaseBusinessCallback
    {
        /**
         * 初始化游戏面板
         *
         * @param msg
         */
        void onGameInitPanel(GameMsgModel msg);

        /**
         * 移除游戏面板
         */
        void onGameRemovePanel();

        /**
         * 游戏消息
         *
         * @param msg
         * @param isPush
         */
        void onGameMsg(GameMsgModel msg, boolean isPush);

        /**
         * 游戏结束消息
         */
        void onGameMsgStopGame();

        /**
         * 请求游戏轮数收益成功
         *
         * @param actModel
         */
        void onGameRequestGameIncomeSuccess(App_requestGameIncomeActModel actModel);

        /**
         * 请求开始游戏接口成功回调
         *
         * @param actModel
         */
        void onGameRequestStartGameSuccess(App_startGameActModel actModel);

        /**
         * 请求结束游戏接口成功回调
         *
         * @param actModel
         */
        void onGameRequestStopGameSuccess(BaseActModel actModel);

        /**
         * 是否有自动开始游戏功能
         *
         * @param hasAutoStartMode
         */
        void onGameHasAutoStartMode(boolean hasAutoStartMode);

        /**
         * 是否自动开始游戏模式发生变化
         *
         * @param isAutoStartMode true-自动开始游戏模式
         */
        void onGameAutoStartModeChanged(boolean isAutoStartMode);

        /**
         * 更新游戏币
         *
         * @param value
         */
        void onGameUpdateGameCurrency(long value);
    }
}
