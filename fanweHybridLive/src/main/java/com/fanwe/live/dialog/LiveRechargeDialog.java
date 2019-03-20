package com.fanwe.live.dialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fanwe.hybrid.common.CommonOpenSDK;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.listner.PayResultListner;
import com.fanwe.hybrid.model.YJWAPPayModel;
import com.fanwe.library.adapter.http.handler.SDRequestHandler;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.dialog.SDDialogProgress;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDTabUnderline;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.live.R;
import com.fanwe.live.activity.LivePayWebViewActivity;
import com.fanwe.live.appview.LiveGameExchangeView;
import com.fanwe.live.appview.LiveRechargeDiamondsView;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.event.EPayWebViewClose;
import com.fanwe.live.event.EWxPayResultCodeComplete;
import com.fanwe.live.model.App_gameCoinsExchangeActModel;
import com.fanwe.live.model.App_payActModel;
import com.fanwe.live.model.App_rechargeActModel;
import com.fanwe.live.wxapi.WXPayEntryActivity;
import com.fanwei.jubaosdk.common.core.OnPayResultListener;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-7 上午9:33:34 类说明
 */
public class LiveRechargeDialog extends LiveBaseDialog implements LiveGameExchangeView.OnClickExchangeListener,
        LiveRechargeDiamondsView.OnChosePayRuleListener
{

    @ViewInject(R.id.progress)
    private ProgressBar progress;
    @ViewInject(R.id.tv_empty_text)
    private TextView tv_empty_text;

    @ViewInject(R.id.ll_close)
    private LinearLayout ll_close;

    @ViewInject(R.id.fl_recharge_container)
    private FrameLayout fl_recharge_container;

    @ViewInject(R.id.tab_recharge)
    private SDTabUnderline tab_recharge;

    @ViewInject(R.id.tab_game_exchange)
    private SDTabUnderline tab_game_exchange;

    private SDSelectViewManager<SDTabUnderline> selectViewManager;

    private LiveRechargeDiamondsView mViewRecharge;
    private LiveGameExchangeView mViewExchange;

    private boolean isFailed = true;

    private App_rechargeActModel mActModel;

    private SDRequestHandler mHandler;

//    private boolean openExchange = true;

    public LiveRechargeDialog(Activity activity)
    {
        super(activity);
        init();
    }

    private void init()
    {
        setContentView(R.layout.dialog_live_recharge);
        setCanceledOnTouchOutside(true);
        paddingLeft(60);
        paddingRight(60);
        x.view().inject(this, getContentView());
        ll_close.setOnClickListener(this);
        selectViewManager = new SDSelectViewManager<>();
        tv_empty_text.setOnClickListener(this);
        initTabs();
        requestData();
    }

    private void initTabs()
    {
        if (!AppRuntimeWorker.isUseGameCurrency())
        {
            SDViewUtil.setGone(tab_game_exchange);
        }
        tab_recharge.setTextTitle("充值");
        tab_recharge.getViewConfig(tab_recharge.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT).setBackgroundColorSelected(Color.TRANSPARENT);
        tab_recharge.getViewConfig(tab_recharge.mTvTitle).setTextSizeNormal(SDViewUtil.sp2px(16)).setTextSizeSelected(SDViewUtil.sp2px(16));
        tab_recharge.getViewConfig(tab_recharge.mTvTitle).setTextColorNormal(R.color.text_title).setTextColorSelected(SDResourcesUtil.getColor(R.color.black));

        tab_game_exchange.setTextTitle("兑换");
        tab_game_exchange.getViewConfig(tab_game_exchange.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT).setBackgroundColorSelected(Color.TRANSPARENT);
        tab_game_exchange.getViewConfig(tab_game_exchange.mTvTitle).setTextSizeNormal(SDViewUtil.sp2px(16)).setTextSizeSelected(SDViewUtil.sp2px(16));
        tab_game_exchange.getViewConfig(tab_game_exchange.mTvTitle).setTextColorNormal(Color.parseColor("#80FFFFFF")).setTextColorSelected(SDResourcesUtil.getColor(R.color.white));

        SDTabUnderline[] items = new SDTabUnderline[]{tab_recharge, tab_game_exchange};

        selectViewManager.addSelectCallback(new SDSelectManager.SelectCallback<SDTabUnderline>()
        {

            @Override
            public void onNormal(int index, SDTabUnderline item)
            {
            }

            @Override
            public void onSelected(int index, SDTabUnderline item)
            {

                if (isFailed)
                    return;
                showTabView(index);
            }
        });
        selectViewManager.setItems(items);
        selectViewManager.setSelected(0, true);
    }

    private void showTabView(int index)
    {
        switch (index)
        {
            case 0:
                showRechargeView();
                break;
            case 1:
                showExchangeView();
                break;
            default:
                break;
        }
    }

    private void requestData()
    {
        isFailed = true;
        fl_recharge_container.removeAllViews();
        SDViewUtil.setVisible(progress);
        SDViewUtil.setInvisible(tv_empty_text);
        mHandler = CommonInterface.requestRecharge(new AppRequestCallback<App_rechargeActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    isFailed = false;
                    mActModel = actModel;
                    showTabView(selectViewManager.getSelectedIndex());
                    UserModelDao.updateDiamondsAndCoins(actModel.getDiamonds(), actModel.getCoin());
                } else
                {
                    onFailedView();
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
                onFailedView();
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                SDViewUtil.setGone(progress);
            }
        });
    }

    private void showRechargeView()
    {
        fl_recharge_container.removeAllViews();
        if (mViewRecharge == null)
        {
            mViewRecharge = new LiveRechargeDiamondsView(getOwnerActivity(), mActModel);
            mViewRecharge.setOnChosePayRuleListener(this);
        }
        mViewRecharge.updateData(mActModel);
        fl_recharge_container.addView(mViewRecharge);
    }

    private void showExchangeView()
    {
        fl_recharge_container.removeAllViews();
        long diamonds;
        float rate;
        if (mViewExchange == null)
        {
            diamonds = mActModel.getDiamonds();
            rate = mActModel.getExchange_rate();
            mViewExchange = new LiveGameExchangeView(getOwnerActivity(), diamonds, rate);
            mViewExchange.setOnClickExchangeListener(this);
        }
        fl_recharge_container.addView(mViewExchange);
    }

    private void onFailedView()
    {
        isFailed = true;
        SDViewUtil.setVisible(tv_empty_text);
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.tv_empty_text:
                requestData();
                break;
            case R.id.ll_close:
                closeDialog();
                break;
            default:
                break;
        }
    }

    private void closeDialog()
    {
        View view = fl_recharge_container.getChildAt(0);
        if (mViewExchange != null && view == mViewExchange)
        {
            selectViewManager.performClick(0);
        } else if (mViewRecharge != null && mViewRecharge.isShownOtherView())
        {
            mViewRecharge.showRules();
        } else
        {
            if (mHandler != null)
            {
                mHandler.cancel();
            }
            dismiss();
        }
    }

    private void requestPayMoney(double money, int pay_id)
    {
        requestPay(money, 0, pay_id);
    }

    private void requestPayRuleId(int rule_id, int pay_id)
    {
        requestPay(0, rule_id, pay_id);
    }

    private void requestPay(double money, int rule_id, int pay_id)
    {
        CommonInterface.requestPay(pay_id, rule_id, money, new AppRequestCallback<App_payActModel>()
        {
            private SDDialogProgress dialog = new SDDialogProgress(getOwnerActivity());

            @Override
            protected void onStart()
            {
                super.onStart();
                dialog.setTextMsg("正在启动插件");
                dialog.show();
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                dialog.dismiss();
            }

            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    CommonOpenSDK.dealPayRequestSuccess(actModel, getOwnerActivity(), payResultListner, jbfPayResultListener);
                }
            }
        });
    }

    private PayResultListner payResultListner = new PayResultListner()
    {
        @Override
        public void onSuccess()
        {
            SDToast.showToast("支付成功");

            CommonInterface.requestMyUserInfo(null);
            dismiss();
        }

        @Override
        public void onOther()
        {

        }

        @Override
        public void onNetWork()
        {

        }

        @Override
        public void onFail()
        {

        }

        @Override
        public void onDealing()
        {

        }

        @Override
        public void onCancel()
        {

        }
    };

    private OnPayResultListener jbfPayResultListener = new OnPayResultListener()
    {

        @Override
        public void onSuccess(Integer integer, String s, String s1)
        {
            SDToast.showToast("支付成功");

            CommonInterface.requestMyUserInfo(null);
            dismiss();
        }

        @Override
        public void onFailed(Integer integer, String s, String s1)
        {

        }
    };

    public void onEventMainThread(EWxPayResultCodeComplete event)
    {
        if (event.WxPayResultCode == WXPayEntryActivity.RespErrCode.CODE_SUCCESS)
        {
            CommonInterface.requestMyUserInfo(null);
            dismiss();
        }
    }

    public void onEventMainThread(EPayWebViewClose event)
    {
        requestData();
    }

    private void openPayWap(@NonNull YJWAPPayModel model)
    {
        String url = model.getUrl();
        if (TextUtils.isEmpty(url))
        {
            SDToast.showToast("获取参数错误:url为空");
            return;
        }
        Intent intent = new Intent(getOwnerActivity(), LivePayWebViewActivity.class);
        intent.putExtra(LivePayWebViewActivity.EXTRA_URL, url);
        getOwnerActivity().startActivity(intent);
    }

    @Override
    public void onClickCancel()
    {
        dismiss();
    }

    @Override
    public void onClickConfirm(long diamonds)
    {
        CommonInterface.requestCoinExchange(diamonds, new AppRequestCallback<App_gameCoinsExchangeActModel>()
        {

            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    UserModelDao.updateDiamondsAndCoins(actModel.getAccount_diamonds(), actModel.getCoin());
                    SDToast.showToast("兑换成功");
                    dismiss();
                }
            }
        });
    }

    @Override
    public void onChosePayRule(int payId, int ruleId)
    {
        requestPayRuleId(ruleId, payId);
    }

    @Override
    public void onSubmitOther(int payId, int money)
    {
        requestPayMoney(money, payId);
    }
}
