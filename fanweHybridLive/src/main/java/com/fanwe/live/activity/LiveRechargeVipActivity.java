package com.fanwe.live.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.common.CommonOpenSDK;
import com.fanwe.hybrid.constant.Constant;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.listner.PayResultListner;
import com.fanwe.hybrid.model.PaySdkModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.library.dialog.SDDialogProgress;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveRechargePaymentAdapter;
import com.fanwe.live.adapter.LiveRechrgeVipPaymentRuleAdapter;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_UserVipPurchaseActModel;
import com.fanwe.live.model.App_payActModel;
import com.fanwe.live.model.PayItemModel;
import com.fanwe.live.model.PayModel;
import com.fanwe.live.model.RuleItemModel;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 充值VIP界面
 * Created by shibx on 2010/01/16.
 */
public class LiveRechargeVipActivity extends BaseTitleActivity
{

    @ViewInject(R.id.tv_user_vip_deadline)
    private TextView tv_user_vip_deadline;

    @ViewInject(R.id.ll_payment)
    private SDGridLinearLayout ll_payment;

    @ViewInject(R.id.ll_payment_rule)
    private SDGridLinearLayout ll_payment_rule;

    private LiveRechargePaymentAdapter adapterPayment;
    private List<PayItemModel> listPayment = new ArrayList<>();

    private LiveRechrgeVipPaymentRuleAdapter adapterPaymentRule;
    private List<RuleItemModel> listPaymentRule = new ArrayList<>();


    private int pay_id;
    private int rule_id;

    @Override
    protected int onCreateContentView()
    {
        return R.layout.act_live_recharge_vip;
    }

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        mTitle.setMiddleTextTop("VIP会员");

        //支付方式
        adapterPayment = new LiveRechargePaymentAdapter(listPayment, this);
        adapterPayment.setItemClickCallback(new SDItemClickCallback<PayItemModel>()
        {
            @Override
            public void onItemClick(int position, PayItemModel item, View view)
            {
                adapterPayment.getSelectManager().performClick(item);
            }
        });
        ll_payment.setAdapter(adapterPayment);

        //支付金额
        adapterPaymentRule = new LiveRechrgeVipPaymentRuleAdapter(listPaymentRule, this);
        adapterPaymentRule.setItemClickCallback(new SDItemClickCallback<RuleItemModel>()
        {
            @Override
            public void onItemClick(int position, RuleItemModel item, View view)
            {
                rule_id = item.getId();
                clickPaymentRule(item);
            }
        });
        ll_payment_rule.setAdapter(adapterPaymentRule);
    }

    /**
     * 购买会员套餐
     *
     * @param model
     */
    private void clickPaymentRule(RuleItemModel model)
    {
        if (!validatePayment())
        {
            return;
        }
        requestPay();
    }

    private void requestPay()
    {
        CommonInterface.requestPayVip(pay_id, rule_id, new AppRequestCallback<App_payActModel>()
        {
            private SDDialogProgress dialog = new SDDialogProgress(getActivity());

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
                if (actModel.isOk())
                {
                    PayModel payModel = actModel.getPay();
                    if (payModel != null)
                    {
                        PaySdkModel paySdkModel = payModel.getSdk_code();
                        if (paySdkModel != null)
                        {
                            String payCode = paySdkModel.getPay_sdk_type();
                            if (!TextUtils.isEmpty(payCode))
                            {
                                if (Constant.PaymentType.UPAPP.equalsIgnoreCase(payCode))
                                {
                                    CommonOpenSDK.payUpApp(paySdkModel, getActivity(), payResultListner);
                                } else if (Constant.PaymentType.BAOFOO.equalsIgnoreCase(payCode))
                                {
                                    CommonOpenSDK.payBaofoo(paySdkModel, getActivity(), 1, payResultListner);
                                } else if (Constant.PaymentType.ALIPAY.equalsIgnoreCase(payCode))
                                {
                                    CommonOpenSDK.payAlipay(paySdkModel, getActivity(), payResultListner);
                                } else if (Constant.PaymentType.WXPAY.equals(payCode))
                                {
                                    CommonOpenSDK.payWxPay(paySdkModel, getActivity(), payResultListner);
                                }
                            } else
                            {
                                SDToast.showToast("payCode为空");
                            }
                        } else
                        {
                            SDToast.showToast("sdk_code为空");
                        }
                    } else
                    {
                        SDToast.showToast("pay为空");
                    }
                }
            }
        });
    }

    private PayResultListner payResultListner = new PayResultListner()
    {
        @Override
        public void onSuccess()
        {

        }

        @Override
        public void onDealing()
        {

        }

        @Override
        public void onFail()
        {

        }

        @Override
        public void onCancel()
        {

        }

        @Override
        public void onNetWork()
        {

        }

        @Override
        public void onOther()
        {

        }
    };

    private boolean validatePayment()
    {
        PayItemModel payment = adapterPayment.getSelectManager().getSelectedItem();
        if (payment == null)
        {
            SDToast.showToast("请选择支付方式");
            return false;
        }
        pay_id = payment.getId();

        return true;
    }

    private void requestVipData()
    {
        CommonInterface.requestVipPurchase(new AppRequestCallback<App_UserVipPurchaseActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {

                    if (actModel.getIs_vip() == 0)
                    {
                        tv_user_vip_deadline.setTextColor(SDResourcesUtil.getColor(R.color.text_content_deep));
                    } else
                    {
                        tv_user_vip_deadline.setTextColor(SDResourcesUtil.getColor(R.color.main_color));
                    }
                    SDViewBinder.setTextView(tv_user_vip_deadline, actModel.getVip_expire_time());
                    adapterPayment.updateData(actModel.getPay_list());
                    adapterPaymentRule.updateData(actModel.getRule_list());

                    int defaultPayIndex = -1;
                    List<PayItemModel> listPay = actModel.getPay_list();
                    if (listPay != null)
                    {
                        int i = 0;
                        for (PayItemModel pay : listPay)
                        {
                            if (pay_id == pay.getId())
                            {
                                defaultPayIndex = i;
                                break;
                            }
                            i++;
                        }
                        if (defaultPayIndex < 0)
                        {
                            defaultPayIndex = 0;
                            pay_id = 0;
                        }
                    }
                    adapterPayment.getSelectManager().setSelected(defaultPayIndex, true);
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
            }
        });
    }

    @Override
    protected void onResume()
    {
        requestVipData();
        super.onResume();
    }
}
