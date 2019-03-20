package com.fanwe.hybrid.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.baofoo.sdk.vip.BaofooPayActivity;
import com.fanwe.hybrid.constant.Constant;
import com.fanwe.hybrid.listner.PayResultListner;
import com.fanwe.hybrid.model.BfappModel;
import com.fanwe.hybrid.model.BfupwapModel;
import com.fanwe.hybrid.model.MalipayModel;
import com.fanwe.hybrid.model.PaySdkModel;
import com.fanwe.hybrid.model.WFTWxAppPayModel;
import com.fanwe.hybrid.model.WxappModel;
import com.fanwe.hybrid.model.YJWAPPayModel;
import com.fanwe.hybrid.utils.IntentUtil;
import com.fanwe.hybrid.wxapp.SDWxappPay;
import com.fanwe.library.pay.alipay.PayResult;
import com.fanwe.library.pay.alipay.SDAlipayer;
import com.fanwe.library.pay.alipay.SDAlipayer.SDAlipayerListener;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.activity.LivePayWebViewActivity;
import com.fanwe.live.model.App_payActModel;
import com.fanwe.live.model.PayModel;
import com.fanwei.jubaosdk.common.core.OnPayResultListener;
import com.fanwei.jubaosdk.shell.FWPay;
import com.fanwei.jubaosdk.shell.PayOrder;
import com.switfpass.pay.MainApplication;
import com.switfpass.pay.activity.PayPlugin;
import com.switfpass.pay.bean.RequestMsg;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

import org.json.JSONArray;
import org.json.JSONException;


/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-1-25 下午4:43:54 类说明
 */
public class CommonOpenSDK
{
    /**
     * 宝付SDK
     */
    public static void payBaofoo(PaySdkModel actModel, Activity context, int requestCode, final PayResultListner listner)
    {
        if (actModel == null)
        {
            return;
        }

        BfappModel model = actModel.getBfappModel();
        if (model == null)
        {
            SDToast.showToast("支付参数获取失败");
            listner.onOther();
            return;
        }

        String respCode = model.getRetCode();

        if (!"0000".equals(respCode))
        {
            SDToast.showToast(respCode + model.getRetMsg());
            listner.onOther();
            return;
        }

        String tradeNo = model.getTradeNo();
        if (TextUtils.isEmpty(tradeNo))
        {
            SDToast.showToast("tradeNo为空");
            listner.onOther();
            return;
        }

        boolean isDebug = actModel.getBfappModel().getIs_debug() == 1 ? false : true;
        Intent payintent = new Intent(context, BaofooPayActivity.class);
        payintent.putExtra(BaofooPayActivity.PAY_TOKEN, tradeNo);
        payintent.putExtra(BaofooPayActivity.PAY_BUSINESS, isDebug);
        context.startActivityForResult(payintent, requestCode);
    }

    /**
     * 银联SDK
     */
    public static void payUpApp(PaySdkModel actModel, Activity context, final PayResultListner listner)
    {
        if (actModel == null)
        {
            return;
        }

        BfupwapModel model = actModel.getBfupwapModel();
        if (model == null)
        {
            SDToast.showToast("支付参数获取失败");
            listner.onOther();
            return;
        }

        String tradeNo = model.getTn();
        if (TextUtils.isEmpty(tradeNo))
        {
            SDToast.showToast("tn为空");
            listner.onOther();
            return;
        }
        UPPayAssistEx.startPayByJAR(context, PayActivity.class, null, null, tradeNo, "01");
    }

    /**
     * 支付宝sdk支付(新)
     */
    public static void payAlipay(PaySdkModel model, final Activity activity, final PayResultListner listner)
    {
        if (model == null)
        {
            return;
        }
        MalipayModel mainpayModel = model.getMalipay();
        if (mainpayModel == null)
        {
            SDToast.showToast("获取支付参数失败");
            listner.onOther();
            return;
        }

        String orderSpec = mainpayModel.getOrder_spec();
        String sign = mainpayModel.getSign();
        String signType = mainpayModel.getSign_type();

        if (TextUtils.isEmpty(orderSpec))
        {
            SDToast.showToast("order_spec为空");
            listner.onOther();
            return;
        }

        if (TextUtils.isEmpty(sign))
        {
            SDToast.showToast("sign为空");
            listner.onOther();
            return;
        }

        if (TextUtils.isEmpty(signType))
        {
            SDToast.showToast("signType为空");
            listner.onOther();
            return;
        }

        SDAlipayer payer = new SDAlipayer(activity);
        payer.setListener(new SDAlipayerListener()
        {

            @Override
            public void onResult(PayResult result)
            {
                String info = result.getMemo();
                String status = result.getResultStatus();

                if ("9000".equals(status)) // 支付成功
                {
                    SDToast.showToast("支付成功");
                    listner.onSuccess();
                } else if ("8000".equals(status)) // 支付结果确认中
                {
                    SDToast.showToast("支付结果确认中");
                    listner.onDealing();
                } else if ("4000".equals(status))
                {
                    if (!TextUtils.isEmpty(info))
                    {
                        SDToast.showToast(info);
                    }
                    listner.onFail();
                } else if ("6001".equals(status))
                {
                    if (!TextUtils.isEmpty(info))
                    {
                        SDToast.showToast(info);
                    }
                    listner.onCancel();
                } else if ("6002".equals(status))
                {
                    if (!TextUtils.isEmpty(info))
                    {
                        SDToast.showToast(info);
                    }
                    listner.onNetWork();
                } else
                {
                    if (!TextUtils.isEmpty(info))
                    {
                        SDToast.showToast(info);
                    }
                    listner.onOther();
                }
            }

            @Override
            public void onFailure(Exception e, String msg)
            {
                if (e != null)
                {
                    listner.onOther();
                    SDToast.showToast("错误:" + e.toString());
                } else
                {
                    if (!TextUtils.isEmpty(msg))
                    {
                        listner.onOther();
                        SDToast.showToast(msg);
                    }
                }
            }
        });
        payer.pay(orderSpec, sign, signType);
    }

    /**
     * 微信SDK支付
     */
    public static void payWxPay(PaySdkModel paySdkModel, Activity activity, final PayResultListner listner)
    {
        if (paySdkModel == null)
        {
            return;
        }

        WxappModel model = paySdkModel.getWxapp();
        if (model == null)
        {
            SDToast.showToast("获取支付参数失败");
            listner.onOther();
            return;
        }

        String appId = model.getAppid();
        if (TextUtils.isEmpty(appId))
        {
            SDToast.showToast("appId为空");
            listner.onOther();
            return;
        }

        String partnerId = model.getPartnerid();
        if (TextUtils.isEmpty(partnerId))
        {
            SDToast.showToast("partnerId为空");
            listner.onOther();
            return;
        }

        String prepayId = model.getPrepayid();
        if (TextUtils.isEmpty(prepayId))
        {
            SDToast.showToast("prepayId为空");
            listner.onOther();
            return;
        }

        String nonceStr = model.getNoncestr();
        if (TextUtils.isEmpty(nonceStr))
        {
            SDToast.showToast("nonceStr为空");
            listner.onOther();
            return;
        }

        String timeStamp = model.getTimestamp();
        if (TextUtils.isEmpty(timeStamp))
        {
            SDToast.showToast("timeStamp为空");
            listner.onOther();
            return;
        }

        String packageValue = model.getPackagevalue();
        if (TextUtils.isEmpty(packageValue))
        {
            SDToast.showToast("packageValue为空");
            listner.onOther();
            return;
        }

        String sign = model.getSign();
        if (TextUtils.isEmpty(sign))
        {
            SDToast.showToast("sign为空");
            listner.onOther();
            return;
        }

        SDWxappPay.getInstance().setAppId(appId);

        PayReq req = new PayReq();
        req.appId = appId;
        req.partnerId = partnerId;
        req.prepayId = prepayId;
        req.nonceStr = nonceStr;
        req.timeStamp = timeStamp;
        //req.packageValue = "Sign=WXPay";
        req.packageValue = "prepay_id=" + prepayId;
        req.sign = sign;

        SDWxappPay.getInstance().pay(req);
    }

    public static void WFTWxAppPay(@NonNull WFTWxAppPayModel model, Activity activity)
    {
        String token_id = model.getToken_id();
        String appid = model.getAppid();
//		appid = "wx2a5538052969956e"; //测试appid
        if (TextUtils.isEmpty(token_id))
        {
            SDToast.showToast("获取参数为空:token_id");
            return;
        }
        if (TextUtils.isEmpty(appid))
        {
            SDToast.showToast("获取参数为空:appid");
            return;
        }
        RequestMsg msg = new RequestMsg();
        msg.setTokenId(token_id);
        msg.setTradeType(MainApplication.WX_APP_TYPE);
        msg.setAppId(appid);
        PayPlugin.unifiedAppPay(activity, msg);
    }

    /**
     * 聚宝付支付宝
     * @param paySdkModel
     * @param activity
     * @param listner
     */
    public static void payJBF(final PaySdkModel paySdkModel, final Activity activity, final OnPayResultListener listner)
    {
        final String payCode = paySdkModel.getPay_sdk_type();
        final String amount = paySdkModel.getJbfPay().getAmount();
        final String goodsName = paySdkModel.getJbfPay().getGoodsName();
        final String payId = paySdkModel.getJbfPay().getPayid();
        final String playerId = paySdkModel.getJbfPay().getPlayerid();
        if (TextUtils.isEmpty(amount))
        {
            SDToast.showToast("amount为空");
            return;
        }
        if (TextUtils.isEmpty(goodsName))
        {
            SDToast.showToast("goodsName为空");
            return;
        }
        if (TextUtils.isEmpty(payId))
        {
            SDToast.showToast("payId为空");
            return;
        }
        if (TextUtils.isEmpty(playerId))
        {
            SDToast.showToast("playerId为空");
            return;
        }

        final Handler mHandler = new Handler(Looper.getMainLooper())
        {
            @Override
            public void handleMessage(Message msg)
            {
                switch (msg.what)
                {
                    case 1:
                        try
                        {
                            String channelType = (String) msg.obj;
                            JSONArray array = new JSONArray(channelType);
                            // 在这里只取json数组的第一位，用户可自定义使用相对应的值
                            int firstIndex = 0;
                            if (Constant.PaymentType.JBFALIPAY.equalsIgnoreCase(payCode))
                            {
                                firstIndex = array.getInt(0);
                            }else if (Constant.PaymentType.JBFWXPAY.equalsIgnoreCase(payCode))
                            {
                                firstIndex = array.getInt(1);
                            }
                            final PayOrder order = new PayOrder()
                                    //金额(必需)
                                    .setAmount(amount)
                                    //商品名称(必需)
                                    .setGoodsName(goodsName)
                                    //商户订单号(必需)
                                    .setPayId(payId)
                                    //玩家Id(必需)
                                    .setPlayerId(playerId)
                                    .setRemark("测试备注");
                            // 主线程中调用 FWPay.pay 方法
                            FWPay.pay(activity, order, firstIndex, listner);
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        };

        // 子线程中调用 FWPay.getChannelType() 方法
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    PayOrder order = new PayOrder()
                            //金额(必需)
                            .setAmount(amount)
                            //商品名称(必需)
                            .setGoodsName(goodsName)
                            //商户订单号(必需)
                            .setPayId(payId)
                            //玩家Id(必需)
                            .setPlayerId(playerId);
                    String channelType = FWPay.getChannelType(order);
                    Message message = Message.obtain();
                    message.obj = channelType;
                    message.what = 1;
                    mHandler.sendMessage(message);
                } catch (Exception e)
                {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(0);
                }
            }
        }).start();

    }

    public static void dealPayRequestSuccess(App_payActModel actModel, Activity activity, PayResultListner payResultListner, OnPayResultListener jbfPayResultListener)
    {
        if (payResultListner == null)
        {
            return;
        }

        PayModel payModel = actModel.getPay();
        if (payModel != null)
        {
            if (payModel.getIs_wap() == 1 && payModel.getIs_without() == 1)
            {
                startOutWebPay(activity, payModel.getUrl());
                return;
            }

            PaySdkModel paySdkModel = payModel.getSdk_code();
            if (paySdkModel != null)
            {
                String payCode = paySdkModel.getPay_sdk_type();
                if (!TextUtils.isEmpty(payCode))
                {
                    if (Constant.PaymentType.UPAPP.equalsIgnoreCase(payCode))
                    {
                        CommonOpenSDK.payUpApp(paySdkModel, activity, payResultListner);
                    } else if (Constant.PaymentType.BAOFOO.equalsIgnoreCase(payCode))
                    {
                        CommonOpenSDK.payBaofoo(paySdkModel, activity, 1, payResultListner);
                    } else if (Constant.PaymentType.ALIPAY.equalsIgnoreCase(payCode))
                    {
                        CommonOpenSDK.payAlipay(paySdkModel, activity, payResultListner);
                    } else if (Constant.PaymentType.WXPAY.equalsIgnoreCase(payCode))
                    {
                        CommonOpenSDK.payWxPay(paySdkModel, activity, payResultListner);
                    } else if (Constant.PaymentType.WFTWXPAY.equalsIgnoreCase(payCode))
                    {
                        CommonOpenSDK.WFTWxAppPay(paySdkModel.getWFTWxAppPayModel(), activity);
                    } else if (Constant.PaymentType.YJWAP.equalsIgnoreCase(payCode))
                    {
                        openPayWap(paySdkModel.getYJWAPPayModel(), activity);
                    }else if (Constant.PaymentType.JBFALIPAY.equalsIgnoreCase(payCode))
                    {
                        CommonOpenSDK.payJBF(paySdkModel,activity,jbfPayResultListener);
                    }else if (Constant.PaymentType.JBFWXPAY.equalsIgnoreCase(payCode))
                    {
                        CommonOpenSDK.payJBF(paySdkModel,activity,jbfPayResultListener);
                    }
                } else
                {
                    SDToast.showToast("参数错误:payCode为空");
                }
            } else
            {
                SDToast.showToast("参数错误:sdk_code为空");
            }
        } else
        {
            SDToast.showToast("参数错误:pay为空");
        }
    }

    public static void openPayWap(@NonNull YJWAPPayModel model, Activity activity)
    {
        String url = model.getUrl();
        if (TextUtils.isEmpty(url))
        {
            SDToast.showToast("获取参数错误:url为空");
            return;
        }
        Intent intent = new Intent(activity, LivePayWebViewActivity.class);
        intent.putExtra(LivePayWebViewActivity.EXTRA_URL, url);
        activity.startActivity(intent);
    }

    /**
     * 跳转外部浏览器支付
     *
     * @param activity
     * @param url
     */
    public static void startOutWebPay(Activity activity, String url)
    {
        if (TextUtils.isEmpty(url))
        {
            SDToast.showToast("获取参数错误:url为空");
            return;
        }
        Intent intent_open_type = IntentUtil.showHTML(url);
        activity.startActivity(intent_open_type);
    }
}
