package com.fanwe.live.appview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.fanwe.hybrid.jshandler.AppJsHandler;
import com.fanwe.library.receiver.SDNetworkReceiver;
import com.fanwe.library.webview.CustomWebView;
import com.fanwe.library.webview.DefaultWebViewClient;
import com.fanwe.live.R;

import org.xutils.http.cookie.DbCookieStore;

import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

/**
 * Created by Administrator on 2016/8/15.
 */
public class H5AppViewWeb extends BaseAppView
{
    public static final String no_network_url = "file:///android_asset/error_network.html";
    // webview 要加载的链接
    public static final String EXTRA_URL = "extra_url";
    //要显示的HTML内容
    public static final String EXTRA_HTML_CONTENT = "extra_html_content";

    private String url;
    private String httmContent;

    private CustomWebView customWebView;

    public CustomWebView getCustomWebView()
    {
        return customWebView;
    }

    public void setCustomWebView(CustomWebView customWebView)
    {
        this.customWebView = customWebView;
    }

    public H5AppViewWeb(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public H5AppViewWeb(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public H5AppViewWeb(Context context)
    {
        super(context);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.view_h5_app_view_web);
        register();
        getIntentInfo();
        initWebView();
    }

    private void register()
    {
        customWebView = find(R.id.webview);
    }


    private void getIntentInfo()
    {
        if (getActivity().getIntent().hasExtra(EXTRA_URL))
        {
            url = getActivity().getIntent().getExtras().getString(EXTRA_URL);
        }
        if (getActivity().getIntent().hasExtra(EXTRA_HTML_CONTENT))
        {
            httmContent = getActivity().getIntent().getExtras().getString(EXTRA_HTML_CONTENT);
        }
    }

    private void initWebView()
    {
        customWebView.getSettings().setBuiltInZoomControls(true);
        customWebView.setWebViewClient(new DefaultWebViewClient()
        {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                failingUrl = no_network_url;
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
        customWebView.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result)
            {
                if (!SDNetworkReceiver.isNetworkConnected(view.getContext()))
                {
                    return true;
                }
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public void onReceivedTitle(WebView view, String title)
            {
                super.onReceivedTitle(view, title);
                if (onWebChromeClientListene != null)
                {
                    onWebChromeClientListene.onReceivedTitle(title);
                }
            }
        });

        initCookies();
    }

    public void addJavascriptInterface(AppJsHandler handler)
    {
        if (handler == null)
        {
            customWebView.addJavascriptInterface(new AppJsHandler(getActivity()));
        } else
        {
            customWebView.addJavascriptInterface(handler);
        }
    }

    public void loadWebView()
    {
        if (!TextUtils.isEmpty(url))
        {
            loadWebViewHtml(url);
        } else if (!TextUtils.isEmpty(httmContent))
        {
            loadWebViewUrl(httmContent);
        }
    }

    public void loadWebViewUrl(String url)
    {
        customWebView.get(url);
    }

    public void loadWebViewHtml(String htmlContent)
    {
        customWebView.loadData(htmlContent, "text/html", "utf-8");
    }

    public void goWebBack()
    {
        if (customWebView.canGoBack())
        {
            customWebView.goBack();
        }
    }

    public boolean isWebCanBack()
    {
        return customWebView.canGoBack();
    }

    private void initCookies()
    {
        if (!TextUtils.isEmpty(url))
        {
            CookieSyncManager.createInstance(getActivity());
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();//移除
            try
            {
                URI uri = new URI(url);
                List<HttpCookie> list = DbCookieStore.INSTANCE.get(uri);
                for (HttpCookie httpCookie : list)
                {
                    String name = httpCookie.getName();
                    String value = httpCookie.getValue();
                    String cookieString = name + "=" + value;
                    cookieManager.setCookie(url, cookieString);
                }
            } catch (Exception e)
            {

            }
            CookieSyncManager.getInstance().sync();
        }
    }

    private OnWebChromeClientListene onWebChromeClientListene;

    public void setOnWebChromeClientListene(OnWebChromeClientListene onWebChromeClientListene)
    {
        this.onWebChromeClientListene = onWebChromeClientListene;
    }

    public interface OnWebChromeClientListene
    {
        void onReceivedTitle(String title);
    }
}
