package com.fanwe.live.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.common.CommonOpenLoginSDK;
import com.fanwe.hybrid.constant.ApkConstant;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.drawable.SDDrawable;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDOtherUtil;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.UrlLinkBuilder;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_ProfitBindingActModel;
import com.fanwe.live.model.App_profitActModel;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.xutils.view.annotation.ViewInject;

import java.util.Map;

/**
 * Created by shibx on 2016/7/18.
 */
public class LiveUserProfitActivity extends BaseTitleActivity implements SDDialogCustom.SDDialogCustomListener
{

    @ViewInject(R.id.tv_useable_ticket)
    private TextView tv_useable_ticket;//钱票
    @ViewInject(R.id.tv_reward)
    private TextView tv_reward;//红包
    @ViewInject(R.id.tv_profit_unit)
    private TextView tv_profit_unit;

    @ViewInject(R.id.tv_do_exchange)
    private TextView tv_do_exchange;//兑换

    @ViewInject(R.id.tv_ordinal_question)
    private TextView tv_ordinal_question;//兑换

    @ViewInject(R.id.rb_alipay)
    private RadioButton rb_alipay;
    @ViewInject(R.id.rb_weixin)
    private RadioButton rb_weixin;
    @ViewInject(R.id.rb_card)
    private RadioButton rb_card;

    @ViewInject(R.id.group)
    private RadioGroup radioGroup;
    @ViewInject(R.id.et_money)
    private EditText et_money;
    private int subscribe;//是否关注微信公众号
    private int mobile_exist;//是否绑定手机
    private int binding_wx;//是否绑定微信
    private int binding_alipay;//是否绑定支付宝
    private int binding_bankcard;//是否绑定支付宝
    private int withdrawals_wx;//是否开启微信提现
    private int withdrawals_alipay;//是否开启支付宝提现
    private int withdrawals_bankcard;//是否开启支付宝提现
    private String useable_ticket;//可提现秀豆
    private String total_tickets;//今日可提现秀豆
    private String ratio;
    private int refund_exist; //是否有未处理的提现订单

    private String subscription; //公众号名称

    private SDDialogConfirm mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_live_user_profit);
        init();
    }

    private void init()
    {
        initTitle();
        tv_profit_unit.setText("获得" + AppRuntimeWorker.getTicketName());
        tv_do_exchange.setOnClickListener(this);
    }

    private void requestProfit()
    {
        showProgressDialog("");
        CommonInterface.requestProfit(new AppRequestCallback<App_profitActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {

                    tv_useable_ticket.setText(actModel.getUseable_ticket());
                    tv_reward.setText(actModel.getMoney());
                    withdrawals_alipay = actModel.getWithdrawals_alipay();
                    withdrawals_wx = actModel.getWithdrawals_wx();
                    withdrawals_bankcard = actModel.getWithdrawals_bankcard();
                    subscribe = actModel.getSubscribe();
                    mobile_exist = actModel.getMobile_exist();
                    binding_wx = actModel.getBinding_wx();
                    subscription = actModel.getSubscription();
                    binding_alipay = actModel.getBinding_alipay();
                    binding_bankcard = actModel.getBinding_bankcard();
                    useable_ticket = actModel.getUseable_ticket();
                    total_tickets = actModel.getDay_ticket_max();
                    ratio = actModel.getTicket_catty_ratio();
                    refund_exist = actModel.getRefund_exist();
                    tv_ordinal_question.setText(actModel.getRefund_explain());
                    if (1!=withdrawals_wx) {
                        rb_weixin.setVisibility(View.GONE);
                    }
                    if (1!=withdrawals_alipay) {
                        rb_alipay.setVisibility(View.GONE);
                    }
                    if (1!=withdrawals_bankcard) {
                        rb_card.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                dismissProgressDialog();
                if (resp.getThrowable() != null)
                {
                    showConfirmDialog("网络错误，无法操作", "离开", "", true);
                }
            }
        });
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("收益");
        mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
        mTitle.initRightItem(1);
        mTitle.getItemRight(0).setTextBot("领取记录");
        mTitle.setOnClickListener(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        requestProfit();
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (TextUtils.isEmpty(et_money.getText().toString().trim()))
        {
            SDToast.showToast("提现金额不合法！");
            return;
        }
        switch (v.getId())
        {
            case R.id.tv_do_exchange:
                if(radioGroup.getCheckedRadioButtonId()==R.id.rb_alipay){
                    doBinding(1);
                }else if(radioGroup.getCheckedRadioButtonId()==R.id.rb_weixin){
                    doBinding(2);
                }else if(radioGroup.getCheckedRadioButtonId()==R.id.rb_card){
                    doBinding(3);
                }
            default:
                break;
        }
    }

    /**
     * 查看详细页面
     *
     * @param isSell false为拍卖详情  true为销售详情
     */
    private void openIncomeDetail(boolean isSell)
    {
        UrlLinkBuilder builder = new UrlLinkBuilder(ApkConstant.SERVER_URL_API);
        builder.add("ctl", "user_center");
        builder.add("act", "income");
        if (isSell)
        {
            builder.add("is_pai", "1");
        }
        String url = builder.build();
        Intent intent = new Intent(this, LiveWebViewActivity.class);
        intent.putExtra(LiveWebViewActivity.EXTRA_URL, url);
        startActivity(intent);
    }


    private void doExchange()
    {
        Intent intent = new Intent(this, LiveUserProfitExchangeActivity.class);
        startActivity(intent);
    }

    private void doBinding(int type)
    {
        if (type==1)
        {
            if (mobile_exist != 1)
            {
                Intent intent = new Intent(this, LiveMobileBindActivity.class);
                startActivity(intent);
            } else if (binding_alipay != 1)
            {
                //绑定支付宝
                Intent intent = new Intent(this, LiveAlipayBindingActivity.class);
                startActivity(intent);
            } else
            {
                if (refund_exist == 1)
                {
                    showConfirmDialog("尚有提现未完成", "好的", "", false);
                    return;
                }

                //填写提现金额，提交至接口
                Intent intent = new Intent(this, LiveTakeRewardActivity.class);

                if (TextUtils.isEmpty(et_money.getText().toString()))
                {
                    SDToast.showToast("参数错误");
                    return;
                }
                intent.putExtra(LiveTakeRewardActivity.EXTRA_USEABLE_TICKET, useable_ticket);
                intent.putExtra(LiveTakeRewardActivity.EXTRA_TOTAL_TICKET, total_tickets);
                intent.putExtra(LiveTakeRewardActivity.EXTRA_RATIO, et_money.getText().toString());
                intent.putExtra(LiveTakeRewardActivity.EXTRA_TYPE, 1);
                startActivity(intent);
            }
        } else if(type==2)
        {
            if (binding_wx != 1)
            {
                CommonOpenLoginSDK.loginWx(this, wxListener);
            } else if (mobile_exist != 1)
            {
                Intent intent = new Intent(this, LiveMobileBindActivity.class);
                startActivity(intent);
            } else if (subscribe != 1)
            {
                SDOtherUtil.copyText(subscription);
                SDToast.showToast("已复制公众号：" + subscription);
                showConfirmDialog("微信搜索并关注：“" + subscription + "”公众号领取红包", "知道了", "", false);
            } else
            {
                showConfirmDialog("请前往公众号领取", "知道了", "", false);
            }
        }else if (type==3)
        {
            if (mobile_exist != 1)
            {
                Intent intent = new Intent(this, LiveMobileBindActivity.class);
                startActivity(intent);
            } else if (binding_bankcard != 1)
            {
                //绑定支付宝
                Intent intent = new Intent(this, LiveBankCardBindingActivity.class);
                startActivity(intent);
            } else
            {
                if (refund_exist == 1)
                {
                    showConfirmDialog("尚有提现未完成", "好的", "", false);
                    return;
                }

                //填写提现金额，提交至接口
                Intent intent = new Intent(this, LiveTakeRewardActivity.class);

                if (TextUtils.isEmpty(et_money.getText().toString()))
                {
                    SDToast.showToast("参数错误");
                    return;
                }
                intent.putExtra(LiveTakeRewardActivity.EXTRA_USEABLE_TICKET, useable_ticket);
                intent.putExtra(LiveTakeRewardActivity.EXTRA_TOTAL_TICKET, total_tickets);
                intent.putExtra(LiveTakeRewardActivity.EXTRA_RATIO, et_money.getText().toString());
                intent.putExtra(LiveTakeRewardActivity.EXTRA_TYPE, 3);
                startActivity(intent);
            }
        }
    }

    private void showConfirmDialog(String content, String confirmText, String cancelText, boolean addListener)
    {
        if (mDialog == null)
        {
            mDialog = new SDDialogConfirm(this);
            mDialog.setTextGravity(Gravity.CENTER);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setCancelable(false);
        }
        if (addListener)
        {
            mDialog.setmListener(this);
        } else
        {
            mDialog.setmListener(null);
        }
        mDialog.setTextContent(content);
        mDialog.setTextCancel(cancelText);
        mDialog.setTextConfirm(confirmText);
        mDialog.show();
    }

    private void requestWeiXinBinding(String openid, String access_token)
    {
        CommonInterface.requestBindingWz(openid, access_token, new AppRequestCallback<App_ProfitBindingActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    subscribe = actModel.getSubscribe();
                    mobile_exist = actModel.getMobile_exist();
                    binding_wx = actModel.getBinding_wx();
                }
            }
        });
    }

    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
    {
        tookRecord();
    }

    private void tookRecord()
    {
        Intent intent = new Intent(this, LiveUserProfitRecordActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClickCancel(View v, SDDialogCustom dialog)
    {

    }

    @Override
    public void onClickConfirm(View v, SDDialogCustom dialog)
    {
        finish();
    }

    @Override
    public void onDismiss(SDDialogCustom dialog)
    {

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (mDialog != null)
        {
            mDialog = null;
        }
    }


    /**
     * 微信授权监听
     */
    private UMAuthListener wxListener = new UMAuthListener()
    {

        @Override
        public void onStart(SHARE_MEDIA share_media)
        {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map)
        {
            String openid = map.get("openid");
            String access_token = map.get("access_token");
            requestWeiXinBinding(openid, access_token);
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable)
        {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i)
        {

        }
    };

}
