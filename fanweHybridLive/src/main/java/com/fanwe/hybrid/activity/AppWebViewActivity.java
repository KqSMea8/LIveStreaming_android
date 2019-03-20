package com.fanwe.hybrid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.fanwe.hybrid.jshandler.AppJsHandler;
import com.fanwe.library.webview.CustomWebView;
import com.fanwe.live.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2015-12-25 上午10:27:09 类说明
 */
public class AppWebViewActivity extends BaseTitleActivity
{
    public static final String EXTRA_CODE = "open_url_type";
    /**
     * webview 要加载的链接
     */
    public static final String EXTRA_URL = "extra_url";
    /**
     * 要显示的HTML内容
     */
    public static final String EXTRA_HTML_CONTENT = "extra_html_content";

    public static final String EXTRA_FINISH_TO_MAIN = "extra_finish_to_mai";

    /**
     * (boolean)
     */
    public static final String EXTRA_IS_SCALE_TO_SHOW_ALL = "extra_is_scale_to_show_all";

    private int mCurrentExtraCode;
    private boolean isScaleToShowAll;
    private String mUrl;
    private String mHttmContent;

    @ViewInject(R.id.webview)
    private CustomWebView mWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_app_webview);
        x.view().inject(this);
        init();
    }

    private void init()
    {
        getIntentInfo();
        initWebView();
    }

    private void getIntentInfo()
    {
        if (getIntent().hasExtra(EXTRA_FINISH_TO_MAIN))
        {
            getIntent().getBooleanExtra(EXTRA_FINISH_TO_MAIN,false);
        }

        if (getIntent().hasExtra(EXTRA_CODE))
        {
            mCurrentExtraCode = getIntent().getIntExtra(EXTRA_CODE,0);
        }

        mUrl = getIntent().getStringExtra(EXTRA_URL);
        mHttmContent = getIntent().getStringExtra(EXTRA_HTML_CONTENT);
        isScaleToShowAll = getIntent().getBooleanExtra(EXTRA_IS_SCALE_TO_SHOW_ALL, false);
    }

    private void initWebView()
    {
        mWeb.addJavascriptInterface(new AppJsHandler(this));
        mWeb.setScaleToShowAll(isScaleToShowAll);

        mWeb.setWebChromeClient(new WebChromeClient()
        {

            @Override
            public void onReceivedTitle(WebView view, String title)
            {
                mTitle.setMiddleTextTop(view.getTitle());
            }

        });

        if (!TextUtils.isEmpty(mUrl))
        {
            mWeb.get(mUrl);
        } else if (!TextUtils.isEmpty(mHttmContent))
        {
            mWeb.loadData(mHttmContent, "text/html", "utf-8");
        }
    }

    @Override
    public void finish()
    {
        if (getIntent().hasExtra(EXTRA_FINISH_TO_MAIN))
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        if (mCurrentExtraCode == 2)
        {
            setResult(Activity.RESULT_OK);
        }

        super.finish();
    }
}
