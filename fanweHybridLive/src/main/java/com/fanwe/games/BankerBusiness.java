package com.fanwe.games;

import com.fanwe.games.model.App_banker_applyActModel;
import com.fanwe.games.model.GameBankerModel;
import com.fanwe.games.model.custommsg.CustomMsgGameBanker;
import com.fanwe.games.model.custommsg.GameMsgModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.live.activity.room.ILiveActivity;
import com.fanwe.live.business.LiveBaseBusiness;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;

/**
 * 游戏上庄功能业务类
 */
public class BankerBusiness extends LiveBaseBusiness
{

    /**
     * App上庄模块是否打开
     */
    private boolean mIsOpenBankerModule;
    /**
     * 当前上庄状态
     */
    private int mState = State.BANKER_STOPPED;
    /**
     * 当前庄家
     */
    private GameBankerModel mBankerModel;
    /**
     * 申请上庄底金
     */
    private long mApplyBankerPrincipal;

    private BankerBusinessCallback mCallback;

    public BankerBusiness(ILiveActivity liveActivity)
    {
        super(liveActivity);
        mIsOpenBankerModule = AppRuntimeWorker.isOpenBankerModule();
    }

    public void setCallback(BankerBusinessCallback callback)
    {
        mCallback = callback;
    }

    /**
     * 当前用户是否是庄家
     *
     * @return
     */
    public boolean isMyBanker()
    {
        String userId = UserModelDao.getUserId();
        return mBankerModel != null && userId.equals(mBankerModel.getBanker_id());
    }

    /**
     * 上庄消息触发
     *
     * @param msg
     */
    public void onMsgGameBanker(CustomMsgGameBanker msg)
    {
        if (!mIsOpenBankerModule)
        {
            return;
        }
        switch (msg.getAction())
        {
            case Action.CREATER_OPEN_BANKER:
                mApplyBankerPrincipal = msg.getData().getPrincipal();
                setState(State.WAIT_BANKER);
                break;
            case Action.HAS_VIEWER_APPLY_BANKER:
                mCallback.onBsBankerCreaterShowHasViewerApplyBanker(true); //给主播小红点提示有观众申请上庄
                break;
            case Action.CREATER_CHOOSE_AS_BANKER:
                mBankerModel = msg.getData().getBanker();
                setState(State.HAS_BANKER);
                break;
            case Action.STOP_BANKER:
                setState(State.BANKER_STOPPED);
                break;
            default:
                break;
        }
    }

    /**
     * 游戏消息触发
     *
     * @param msg
     */
    public void onGameMsg(GameMsgModel msg)
    {
        if (!mIsOpenBankerModule)
        {
            return;
        }
        if (msg.getBanker_status() < 0)
        {
            //由于下注消息不返回上庄状态，所以不需要更新状态
            return;
        }

        mApplyBankerPrincipal = msg.getPrincipal();

        if (State.HAS_BANKER == msg.getBanker_status())
        {
            mBankerModel = msg.getBanker();
        }
        setState(msg.getBanker_status());

        //如果游戏处于下注中的状态，则隐藏所有可操作上庄的view
        boolean isBetting = msg.getGame_status() == 1;
        if (isBetting)
        {
            hideAllCtrl();
        }
    }

    /**
     * 设置上庄状态
     *
     * @param state
     * @see State
     */
    public void setState(int state)
    {
        if (!mIsOpenBankerModule)
        {
            return;
        }
        if (state < 0)
        {
            return;
        }

        this.mState = state;
        switch (mState)
        {
            case State.BANKER_STOPPED:
                setStateBankerStopped();
                LogUtil.i("setState:" + state + " BANKER_STOPPED");
                break;
            case State.WAIT_BANKER:
                LogUtil.i("setState:" + state + " WAIT_BANKER");
                setStateWaitBanker();
                break;
            case State.HAS_BANKER:
                LogUtil.i("setState:" + state + " HAS_BANKER");
                setStateHasBanker();
                break;
            case State.GAME_STOPPED:
                LogUtil.i("setState:" + state + " GAME_STOPPED");
                hideAllCtrl();
                break;
            default:
                break;
        }
    }

    private void setStateBankerStopped()
    {
        //按钮逻辑
        mCallback.onBankerCtrlCreaterShowOpenBanker(true); //显示开启上庄
        mCallback.onBankerCtrlCreaterShowOpenBankerList(false); //隐藏打开庄家列表
        mCallback.onBankerCtrlCreaterShowStopBanker(false); //隐藏下庄
        mCallback.onBankerCtrlViewerShowApplyBanker(false); //隐藏申请上庄

        //业务逻辑
        mCallback.onBsBankerCreaterShowHasViewerApplyBanker(false); //隐藏主播小红点提示有观众申请上庄
        mCallback.onBsBankerRemoveBankerInfo(); //移除庄家信息
        mBankerModel = null;
    }

    private void setStateWaitBanker()
    {
        //按钮逻辑
        mCallback.onBankerCtrlCreaterShowOpenBanker(false); //隐藏开启上庄
        mCallback.onBankerCtrlCreaterShowOpenBankerList(true); //显示打开庄家列表
        mCallback.onBankerCtrlCreaterShowStopBanker(false); //隐藏下庄
        mCallback.onBankerCtrlViewerShowApplyBanker(true); //显示申请上庄
    }

    private void setStateHasBanker()
    {
        //按钮逻辑
        mCallback.onBankerCtrlCreaterShowOpenBanker(false); //隐藏开启上庄
        mCallback.onBankerCtrlCreaterShowOpenBankerList(false); //隐藏打开庄家列表
        mCallback.onBankerCtrlCreaterShowStopBanker(true); //显示下庄
        mCallback.onBankerCtrlViewerShowApplyBanker(false); //隐藏申请上庄

        //业务逻辑
        mCallback.onBsBankerShowBankerInfo(mBankerModel); //显示庄家信息
    }

    private void hideAllCtrl()
    {
        mCallback.onBankerCtrlCreaterShowOpenBanker(false); //隐藏开启上庄
        mCallback.onBankerCtrlCreaterShowOpenBankerList(false); //隐藏打开庄家列表
        mCallback.onBankerCtrlCreaterShowStopBanker(false); //隐藏下庄
        mCallback.onBankerCtrlViewerShowApplyBanker(false); //隐藏申请上庄

        LogUtil.i("hideAllCtrl:" + mState);
    }

    /**
     * 打开上庄功能(主播调用)
     */
    public void requestOpenGameBanker()
    {
        CommonInterface.requestOpenGameBanker(new AppRequestCallback<BaseActModel>()
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
     * 下庄并且关闭上庄功能(主播调用)
     */
    public void requestStopGameBanker()
    {
        CommonInterface.requestStopBanker(new AppRequestCallback<BaseActModel>()
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
     * 选择庄家(主播调用)
     *
     * @param banker_log_id
     */
    public void requestChooseBanker(String banker_log_id)
    {
        CommonInterface.requestChooseBanker(banker_log_id, new AppRequestCallback<BaseActModel>()
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
     * 获得申请上庄所需要的游戏币
     *
     * @return
     */
    public long getApplyBankerPrincipal()
    {
        return mApplyBankerPrincipal;
    }

    /**
     * 申请上庄(观众调用)
     *
     * @param principal
     */
    public void requestApplyBanker(long principal)
    {
        int roomId = getLiveActivity().getRoomId();
        CommonInterface.requestApplyBanker(roomId, principal, new AppRequestCallback<App_banker_applyActModel>()
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
                    UserModelDao.updateCoins(actModel.getCoin());
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

    @Override
    protected BaseBusinessCallback getBaseBusinessCallback()
    {
        return mCallback;
    }


    /**
     * 当前上庄状态
     */
    public interface State
    {
        /**
         * 主播未开启上庄功能
         */
        int BANKER_STOPPED = 0;
        /**
         * 主播已经开启上庄功能，等待玩家申请上庄
         */
        int WAIT_BANKER = 1;
        /**
         * 主播已经开启上庄功能，有玩家上庄
         */
        int HAS_BANKER = 2;

        /**
         * 游戏已经结束(app自己定义的状态)
         */
        int GAME_STOPPED = 3;
    }

    /**
     * 推送的动作类型
     */
    public interface Action
    {
        /**
         * 主播开启上庄功能
         */
        int CREATER_OPEN_BANKER = 1;
        /**
         * 有观众申请上庄
         */
        int HAS_VIEWER_APPLY_BANKER = 2;
        /**
         * 主播选择了某个观众成为庄家
         */
        int CREATER_CHOOSE_AS_BANKER = 3;
        /**
         * 下庄并关闭上庄功能(1:主播点击下庄时触发，2：主播开启上庄后没有观众上庄，并且主播点击了发牌重新开始新一轮游戏时触发)
         */
        int STOP_BANKER = 4;
    }

    /**
     * 上庄控制view点击回调
     */
    public interface BankerCtrlViewClickCallback
    {
        /**
         * 主播点击开启上庄
         */
        void onClickBankerCtrlCreaterOpenBanker();

        /**
         * 主播点击查看申请上庄的观众列表
         */
        void onClickBankerCtrlCreaterOpenBankerList();

        /**
         * 主播点击下庄
         */
        void onClickBankerCtrlCreaterStopBanker();

        /**
         * 观众点击申请上庄
         */
        void onClickBankerCtrlViewerApplyBanker();
    }

    /**
     * 主播上庄view控制接口
     */
    public interface BankerCreaterCtrlView
    {
        /**
         * 主播显示隐藏开启上庄
         *
         * @param show
         */
        void onBankerCtrlCreaterShowOpenBanker(boolean show);

        /**
         * 主播显示隐藏打开庄家列表
         *
         * @param show
         */
        void onBankerCtrlCreaterShowOpenBankerList(boolean show);

        /**
         * 主播显示隐藏下庄
         *
         * @param show
         */
        void onBankerCtrlCreaterShowStopBanker(boolean show);
    }

    /**
     * 观众上庄view控制接口
     */
    public interface BankerViewerCtrlView
    {
        /**
         * 观众显示隐藏申请上庄
         *
         * @param show
         */
        void onBankerCtrlViewerShowApplyBanker(boolean show);
    }

    public interface BankerBusinessCallback extends
            BaseBusinessCallback,
            BankerCreaterCtrlView,
            BankerViewerCtrlView
    {
        /**
         * 对主播提示有观众申请上庄
         *
         * @param show
         */
        void onBsBankerCreaterShowHasViewerApplyBanker(boolean show);

        /**
         * 显示庄家信息
         *
         * @param banker
         */
        void onBsBankerShowBankerInfo(GameBankerModel banker);

        /**
         * 移除庄家信息
         */
        void onBsBankerRemoveBankerInfo();
    }

}
