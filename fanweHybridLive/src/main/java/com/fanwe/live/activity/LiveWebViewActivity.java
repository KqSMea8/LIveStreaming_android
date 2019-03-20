package com.fanwe.live.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.hybrid.common.CommonOpenSDK;
import com.fanwe.hybrid.constant.ApkConstant;
import com.fanwe.hybrid.constant.Constant;
import com.fanwe.hybrid.event.EPaySdk;
import com.fanwe.hybrid.event.ERefreshReload;
import com.fanwe.hybrid.listner.PayResultListner;
import com.fanwe.hybrid.model.PaySdkModel;
import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.webview.CustomWebView;
import com.fanwe.library.webview.DefaultWebViewClient;
import com.fanwe.live.R;
import com.fanwe.live.event.EWxPayResultCodeComplete;
import com.fanwe.live.wxapi.WXPayEntryActivity;
import com.fanwe.o2o.jshandler.O2OShoppingLiveJsHander;

import org.xutils.http.cookie.DbCookieStore;
import org.xutils.view.annotation.ViewInject;

import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

import static com.fanwe.live.appview.H5AppViewWeb.no_network_url;

/**
 * Created by Administrator on 2016/7/9 0009.
 */
public class LiveWebViewActivity extends BaseActivity
{
    private static final int REQUEST_CODE_BAOFOO_SDK_RZ = 100;// 宝付支付

    public static final String EXTRA_URL = "extra_url";

    public static final String EXTRA_IS_SHOW_TITLE = "extra_is_show_title";

    private String mUrl;

    private boolean mIsShowTitle;//是否展示标题

    @ViewInject(R.id.rl_title)
    private RelativeLayout rl_title;
    @ViewInject(R.id.view_back)
    private View view_back;
    @ViewInject(R.id.tv_finish)
    private TextView tv_finish;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.cus_webview)
    protected CustomWebView customWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_live_edit_data);
        init();
    }

    protected void init()
    {
        initIntent();
        initWebView();
        initListener();
    }

    private void initIntent()
    {
        mUrl = getIntent().getStringExtra(EXTRA_URL);
        mIsShowTitle = getIntent().getBooleanExtra(EXTRA_IS_SHOW_TITLE, true);
        if (!mIsShowTitle)
        {
            SDViewUtil.setGone(rl_title);
        }
    }

    private void initWebView()
    {
        customWebView.addJavascriptInterface(new O2OShoppingLiveJsHander(this, customWebView));
        customWebView.getSettings().setBuiltInZoomControls(true);

        customWebView.setWebViewClient(new DefaultWebViewClient()
        {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                super.onReceivedError(view, errorCode, description, failingUrl);
                customWebView.loadUrl(no_network_url);
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                tv_title.setText(view.getTitle());
            }
        });

        customWebView.setWebChromeClientListener(new WebChromeClient()
        {

            @Override
            public void onReceivedTitle(WebView view, String title)
            {
                tv_title.setText(view.getTitle());
            }
        });

        initCookies();
        customWebView.get(mUrl);
    }

    private void initListener()
    {
        view_back.setOnClickListener(this);
        tv_finish.setOnClickListener(this);
    }

    /**
     * 同步一下cookie
     */
    public void initCookies()
    {
        if (!TextUtils.isEmpty(mUrl))
        {
            CookieSyncManager.createInstance(this);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();//移除
            try
            {
                String url_domain = ApkConstant.SERVER_URL;
                URI uri = new URI(url_domain);
                List<HttpCookie> list = DbCookieStore.INSTANCE.get(uri);
                for (HttpCookie httpCookie : list)
                {
                    String name = httpCookie.getName();
                    String value = httpCookie.getValue();
                    String cookieString = name + "=" + value;
                    cookieManager.setCookie(mUrl, cookieString);
                }
            } catch (Exception e)
            {

            }
            CookieSyncManager.getInstance().sync();
        }
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        customWebView.reload();
    }

    @Override
    public void onBackPressed()
    {
        if (customWebView.canGoBack())
        {
            customWebView.goBack();
        } else
        {
            super.onBackPressed();
        }
    }

    /**
     * 网络刷新
     *
     * @param event
     */
    public void onEventMainThread(ERefreshReload event)
    {
        customWebView.loadUrl(mUrl);
        customWebView.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //清除历史记录
                customWebView.clearHistory();
            }
        }, 1000);
    }


    /**
     * 支付SDK
     */
    public void onEventMainThread(EPaySdk event)
    {
        openSDKPAY(event.model);
    }

    public void openSDKPAY(PaySdkModel model)
    {
        String payCode = model.getPay_sdk_type();
        if (!TextUtils.isEmpty(payCode))
        {
            if (Constant.PaymentType.UPAPP.equalsIgnoreCase(payCode))
            {
                CommonOpenSDK.payUpApp(model, this, mPayResultListner);
            } else if (Constant.PaymentType.BAOFOO.equalsIgnoreCase(payCode))
            {
                CommonOpenSDK.payBaofoo(model, this, REQUEST_CODE_BAOFOO_SDK_RZ, mPayResultListner);
            } else if (Constant.PaymentType.ALIPAY.equalsIgnoreCase(payCode))
            {
                CommonOpenSDK.payAlipay(model, this, mPayResultListner);
            } else if (Constant.PaymentType.WXPAY.equals(payCode))
            {
                CommonOpenSDK.payWxPay(model, this, mPayResultListner);
            }
        }
    }

    /*微信支付回调返回信息*/
    public void onEventMainThread(final EWxPayResultCodeComplete event)
    {
        switch (event.WxPayResultCode)
        {
            case WXPayEntryActivity.RespErrCode.CODE_CANCEL:
                mPayResultListner.onCancel();
                break;
            case WXPayEntryActivity.RespErrCode.CODE_FAIL:
                mPayResultListner.onFail();
                break;
            case WXPayEntryActivity.RespErrCode.CODE_SUCCESS:
                mPayResultListner.onSuccess();
                break;
        }
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == view_back)
        {
            if (customWebView.canGoBack())
            {
                customWebView.goBack();
            } else
            {
                finish();
            }
        } else if (v == tv_finish)
        {
            finish();
        }
    }

    private PayResultListner mPayResultListner = new PayResultListner()
    {
        @Override
        public void onSuccess()
        {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    customWebView.loadJsFunction(Constant.JsFunctionName.JS_PAY_SDK, 1);
                }
            });
        }

        @Override
        public void onDealing()
        {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    customWebView.loadJsFunction(Constant.JsFunctionName.JS_PAY_SDK, 2);
                }
            });
        }

        @Override
        public void onFail()
        {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    customWebView.loadJsFunction(Constant.JsFunctionName.JS_PAY_SDK, 3);
                }
            });
        }

        @Override
        public void onCancel()
        {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    customWebView.loadJsFunction(Constant.JsFunctionName.JS_PAY_SDK, 4);
                }
            });
        }

        @Override
        public void onNetWork()
        {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    customWebView.loadJsFunction(Constant.JsFunctionName.JS_PAY_SDK, 5);
                }
            });
        }

        @Override
        public void onOther()
        {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    customWebView.loadJsFunction(Constant.JsFunctionName.JS_PAY_SDK, 6);
                }
            });
        }
    };
}
