package com.fanwe.pay;

import android.os.CountDownTimer;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.dialog.SDDialogProgress;
import com.fanwe.live.activity.room.ILiveActivity;
import com.fanwe.live.model.App_monitorActModel;
import com.fanwe.pay.common.PayCommonInterface;
import com.fanwe.pay.model.App_live_live_payActModel;
import com.fanwe.pay.model.App_monitorLiveModel;

/**
 * Created by Administrator on 2016/12/16.
 */

public class LiveTimePayCreaterBusiness extends LivePayBusiness
{
    private CountDownTimer timer;
    /**
     * 是否允许切换付费模式
     */
    private boolean isAllowLivePay = false;
    private LiveTimePayCreaterBusinessListener businessListener;

    public LiveTimePayCreaterBusiness(ILiveActivity liveInfo)
    {
        super(liveInfo);
    }

    /**
     * 是否允许付费模式
     *
     * @return
     */
    public boolean isAllowLivePay()
    {
        return isAllowLivePay;
    }

    public void setBusinessListener(LiveTimePayCreaterBusinessListener businessListener)
    {
        this.businessListener = businessListener;
    }

    /**
     * 付费开始倒计时
     *
     * @param left_time 秒
     */
    private void startCountdownTime(long left_time)
    {
        if (left_time <= 0)
        {
            return;
        }
        if (timer != null)
        {
            timer.cancel();
        }
        timer = new CountDownTimer(left_time * 1000, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                businessListener.onTimePayCreaterCountDown(millisUntilFinished);
            }

            @Override
            public void onFinish()
            {
                businessListener.onTimePayCreaterCountDown(0);
            }
        };
        timer.start();
    }

    private void stopCountdownTime()
    {
        if (timer != null)
        {
            timer.cancel();
        }
    }

    /**
     * @param live_fee 价格
     */
    public void requestSwitchPayMode(int live_fee)
    {
        requestLiveLive_pay(live_fee, 0, 0);
    }

    /**
     * 提档
     */
    public void requestPayModeUpgrade()
    {
        requestLiveLive_pay(0, 1, 0);
    }

    private void requestLiveLive_pay(int live_fee, final int is_mention, final int live_pay_type)
    {
        if (getLiveActivity().getRoomInfo() != null)
        {
            int room_id = getLiveActivity().getRoomInfo().getRoom_id();
            PayCommonInterface.requestLiveLivePay(live_fee, live_pay_type, room_id, is_mention, new AppRequestCallback<App_live_live_payActModel>()
            {
                private SDDialogProgress dialog = new SDDialogProgress(SDActivityManager.getInstance().getLastActivity());

                @Override
                protected void onStart()
                {
                    super.onStart();
                    dialog.setTextMsg("正在切换");
                    dialog.show();
                }

                @Override
                protected void onFinish(SDResponse resp)
                {
                    super.onFinish(resp);
                    dialog.dismiss();
                }

                @Override
                protected void onSuccess(SDResponse sdResponse)
                {
                    if (actModel.getStatus() == 1)
                    {
                        if (live_pay_type == 0)
                        {
                            // 显示主播付费view
                            businessListener.onTimePayCreaterShowPayModeView();

                            if (is_mention == 1)
                            {
                                // 提档成功，隐藏提档按钮
                                businessListener.onTimePayCreaterShowHideUpgrade(false);
                            } else
                            {
                                // 付费模式切换成功，开始倒计时
                                startCountdownTime(actModel.getCount_down());
                                // 付费模式切换成功，隐藏切换按钮
                                businessListener.onTimePayCreaterShowHideSwitchPay(false);
                            }

                            businessListener.onTimePayCreaterRequestLiveLive_paySuccess(actModel);
                        }

                    }
                }
            });
        }
    }

    /**
     * 主播心跳回调业务处理
     *
     * @param actModel
     */
    public void onRequestMonitorSuccess(App_monitorActModel actModel)
    {
        App_monitorLiveModel live = actModel.getLive();
        if (live != null)
        {
            if (live.getAllow_live_pay() == 1)
            {
                this.isAllowLivePay = true;
            } else
            {
                this.isAllowLivePay = false;
            }

            if (live.getAllow_mention() == 1)
            {
                // 显示提档
                businessListener.onTimePayCreaterShowHideUpgrade(true);
            } else
            {
                // 隐藏提档
                businessListener.onTimePayCreaterShowHideUpgrade(false);
            }

            businessListener.onTimePayCreaterRequestMonitorSuccess(live);
        }
    }

    @Override
    protected BaseBusinessCallback getBaseBusinessCallback()
    {
        return businessListener;
    }

    public void onDestroy()
    {
        stopCountdownTime();
        super.onDestroy();
    }

    public interface LiveTimePayCreaterBusinessListener extends BaseBusinessCallback
    {
        /**
         * 付费/提档 接口请求成功（绑定价格等信息）
         *
         * @param actModel
         */
        void onTimePayCreaterRequestLiveLive_paySuccess(App_live_live_payActModel actModel);

        /**
         * 显示隐藏提档
         *
         * @param show true-显示
         */
        void onTimePayCreaterShowHideUpgrade(boolean show);

        /**
         * 显示隐藏切换付费按钮
         *
         * @param show true-显示
         */
        void onTimePayCreaterShowHideSwitchPay(boolean show);

        /**
         * 付费开始倒计时
         *
         * @param leftTime 剩余时间
         */
        void onTimePayCreaterCountDown(long leftTime);

        /**
         * 显示主播付费模式view
         */
        void onTimePayCreaterShowPayModeView();

        /**
         * 主播心跳回调（更新秀豆等）
         *
         * @param actModel
         */
        void onTimePayCreaterRequestMonitorSuccess(App_monitorLiveModel actModel);
    }
}
