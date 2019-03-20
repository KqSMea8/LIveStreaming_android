package com.live.qianxiu.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.fanwe.hybrid.wxapp.SDWxappPay;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.event.EWxPayResultCodeComplete;
import com.sunday.eventbus.SDEventManager;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler
{

    private IWXAPI mApi;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init()
    {
        mApi = SDWxappPay.getInstance().getWXAPI();
        mApi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        setIntent(intent);
        mApi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req)
    {

    }

    @Override
    public void onResp(BaseResp resp)
    {
        int respType = resp.getType();
        if (respType == ConstantsAPI.COMMAND_PAY_BY_WX)
        {
            EWxPayResultCodeComplete event = new EWxPayResultCodeComplete();
            String content = null;
            switch (resp.errCode)
            {
                case 0: // 成功
                    content = "支付成功";
                    event.content = content;
                    event.WxPayResultCode = RespErrCode.CODE_SUCCESS;
                    SDEventManager.post(event);
                    break;
                case -1: // 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
                    content = "支付失败";
                    event.content = content;
                    event.WxPayResultCode = RespErrCode.CODE_FAIL;
                    SDEventManager.post(event);
                    break;
                case -2: // 无需处理。发生场景：用户不支付了，点击取消，返回APP。
                    content = "取消支付";
                    event.content = content;
                    event.WxPayResultCode = RespErrCode.CODE_CANCEL;
                    SDEventManager.post(event);
                    break;
                default:
                    break;
            }
            if (!TextUtils.isEmpty(content))
            {
                SDToast.showToast(content);
            }
        }
        finish();
    }

    public static final class RespErrCode
    {
        public static final int CODE_SUCCESS = 0;
        public static final int CODE_FAIL = -1;
        public static final int CODE_CANCEL = -2;
    }
}