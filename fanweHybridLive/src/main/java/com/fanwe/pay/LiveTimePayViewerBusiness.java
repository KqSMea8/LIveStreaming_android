package com.fanwe.pay;

import android.os.CountDownTimer;
import android.util.Log;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.looper.ISDLooper;
import com.fanwe.library.looper.impl.SDSimpleLooper;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.live.activity.room.ILiveActivity;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.App_get_videoActModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.pay.common.PayCommonInterface;
import com.fanwe.pay.model.App_live_live_pay_deductActModel;
import com.fanwe.pay.model.custommsg.CustomMsgStartPayMode;

/**
 * 付费模式观众业务
 */
public class LiveTimePayViewerBusiness extends LivePayBusiness
{
    private boolean canJoinRoom = true;
    private ISDLooper looper;
    private CountDownTimer countDownTimer;
    private boolean isAgree; //是否同意观看
    private boolean isRecharging = false; //是否正在充值
    private LiveTimePayViewerBusinessListener businessListener;

    public LiveTimePayViewerBusiness(ILiveActivity liveInfo)
    {
        super(liveInfo);
    }

    public void setRecharging(boolean recharging)
    {
        isRecharging = recharging;
    }

    public void setBusinessListener(LiveTimePayViewerBusinessListener businessListener)
    {
        this.businessListener = businessListener;
    }

    public void setAgree(boolean agree)
    {
        isAgree = agree;
    }

    @Override
    protected void onMsgPayWillStart(CustomMsgStartPayMode customMsg)
    {
        startMonitor();
        super.onMsgPayWillStart(customMsg);
    }

    /**
     * 同意观看
     */
    public void agreePay()
    {
        isAgree = true;
        startMonitor();
    }

    /**
     * 拒绝观看
     */
    public void rejectPay()
    {
        isAgree = false;
    }


    /**
     * 按时付费请求RequestRoomInfoSuccess逻辑判断
     *
     * @param actModel
     */
    public void dealPayModelRoomInfoSuccess(App_get_videoActModel actModel)
    {
        Log.e("bmbmbmbmbm","dealPayModelRoomInfoSuccess"+actModel.toString());
        UserModel user = UserModelDao.query();
        if (user == null)
        {
            return;
        }
        String anchor_id = actModel.getUser_id();
        String login_id = user.getUser_id();
        if (login_id.equals(anchor_id))
        {
            //进入回放，本地ID和主播ID一样，则直接进入房间
            return;
        }
        //正在付费
        if (actModel.getIs_live_pay() == 1)
        {
            if (actModel.getLive_pay_type() == 0)
            {
                //是否加入过按时付费直播间
                LogUtil.i("is_pay_over:" + actModel.getIs_pay_over());
                if (actModel.getIs_pay_over() == 1)
                {
                    agreePay();
                } else
                {
                    setCanJoinRoom(false);
                    businessListener.onTimePayViewerShowCoveringPlayeVideo(actModel.getPreview_play_url(), actModel.getCountdown(), actModel.getIs_only_play_voice()); //显示背景
                    startMonitor();
                }
            }
        }
    }

    private void setCanJoinRoom(boolean canJoinRoom)
    {
        if (this.canJoinRoom != canJoinRoom)
        {
            this.canJoinRoom = canJoinRoom;
            businessListener.onTimePayViewerCanJoinRoom(canJoinRoom);
        }
    }

    /**
     * 按时收费逻辑判断
     */
    public void dealPayModeLiveInfo(App_live_live_pay_deductActModel model)
    {
        if (model == null)
        {
            return;
        }

        //保存余额
        UserModel user = UserModelDao.query();
        if (model.getDiamonds() >= 0)
        {
            user.setDiamonds(model.getDiamonds());
            UserModelDao.insertOrUpdate(user);
        }

        if (model.getIs_live_pay() == 1)
        {
            businessListener.onTimePayShowPayInfoView(model); //显示添加付费信息

            if (model.getOn_live_pay() == 1)
            {
                //收费中
                if (isAgree)
                {
                    if (model.getIs_recharge() == 1)
                    {
                        businessListener.onTimePayViewerShowCovering(true); //显示背景
                        if (!isRecharging)
                        {
                            businessListener.onTimePayViewerShowRecharge(model); //显示充值入口提示
                        }
                    } else
                    {
                        businessListener.onTimePayViewerShowCovering(false); //隐藏背景
                        if (model.getIs_diamonds_low() == 1)
                        {
                            if (!isRecharging)
                            {
                                businessListener.onTimePayViewerLowDiamonds(model); //提示余额即将用完
                            }
                        }

                        setCanJoinRoom(true);
                    }
                } else
                {
                    businessListener.onTimePayViewerShowCovering(true);
                    businessListener.onTimePayViewerShowWhetherJoin(model.getLive_fee());
                }
            } else
            {
                // 倒计时，即将开始收费
                startCountDown(model.getCount_down() * 1000);
                businessListener.onTimePayViewerShowCovering(false); //隐藏背景
                businessListener.onTimePayViewerShowWhetherJoinFuture(model); //提示即将开始，是否加入
            }
        } else
        {
            stopMonitor();
        }
    }

    /**
     * 开始定时检查
     */
    public void startMonitor()
    {
        if (looper == null)
        {
            looper = new SDSimpleLooper();
        }
        looper.start(30 * 1000, new Runnable()
        {
            @Override
            public void run()
            {
                requestLivePayDeduct();
            }
        });
    }

    public void requestLivePayDeduct()
    {
        int room_id = getLiveActivity().getRoomId();
        PayCommonInterface.requestLivelivePayDeduct(room_id, isAgree, new AppRequestCallback<App_live_live_pay_deductActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.getStatus() == 1)
                {
                    if (actModel.getLive_pay_type() == 0)
                    {
                        dealPayModeLiveInfo(actModel);
                        businessListener.onTimePayViewerRequestMonitorSuccess(actModel);
                    }
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
     * 开始倒计时
     *
     * @param time 毫秒
     */
    private void startCountDown(long time)
    {
        stopCountDown();
        if (time > 0)
        {
            countDownTimer = new CountDownTimer(time, 1000)
            {
                @Override
                public void onTick(long leftTime)
                {
                    businessListener.onTimePayViewerCountDown(leftTime);
                }

                @Override
                public void onFinish()
                {
                    businessListener.onTimePayViewerCountDown(0);
                    startMonitor();
                }
            };
            countDownTimer.start();
        }
    }

    /**
     * 停止倒计时
     */
    public void stopCountDown()
    {
        if (countDownTimer != null)
        {
            countDownTimer.cancel();
        }
    }

    /**
     * 停止定时检查
     */
    public void stopMonitor()
    {
        if (looper != null)
        {
            looper.stop();
        }
    }

    @Override
    protected BaseBusinessCallback getBaseBusinessCallback()
    {
        return businessListener;
    }

    public void onDestroy()
    {
        stopCountDown();
        stopMonitor();
        isAgree = false;
        super.onDestroy();
    }

    public interface LiveTimePayViewerBusinessListener extends BaseBusinessCallback
    {
        /**
         * 定时心跳结果
         */
        void onTimePayViewerRequestMonitorSuccess(App_live_live_pay_deductActModel model);

        /**
         * 是否要显示覆盖层
         */
        void onTimePayViewerShowCovering(boolean show);

        /**
         * 可用秀豆不够多
         */
        void onTimePayViewerLowDiamonds(App_live_live_pay_deductActModel model);

        /**
         * 显示充值提示
         */
        void onTimePayViewerShowRecharge(App_live_live_pay_deductActModel model);

        /**
         * 显示是否要加入付费直播（已经在收费中）
         */
        void onTimePayViewerShowWhetherJoin(int live_fee);

        /**
         * 显示是否要加入付费直播（付费即将开始的倒计时中）
         */
        void onTimePayViewerShowWhetherJoinFuture(App_live_live_pay_deductActModel model);

        /**
         * 显示观众即将开始的倒计时
         */
        void onTimePayShowPayInfoView(App_live_live_pay_deductActModel model);

        /**
         * 付费模式即将开始倒计时触发
         *
         * @param leftTime 剩余时间
         */
        void onTimePayViewerCountDown(long leftTime);

        /**
         * \
         * 是否可以加入房间
         */
        void onTimePayViewerCanJoinRoom(boolean canJoinRoom);

        /**
         * 刚进入是否预览视频
         *
         * @param preview_play_url 预览地址
         * @param countdown        预览倒计时
         */
        void onTimePayViewerShowCoveringPlayeVideo(String preview_play_url, int countdown, int is_only_play_voice);
    }

}
