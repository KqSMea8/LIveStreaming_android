package com.fanwe.live.fragment;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fanwe.hybrid.fragment.BaseFragment;
import com.fanwe.live.R;
import com.fanwe.live.appview.H5AppViewWeb;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/8/15.
 */
public class LiveWebViewFragment extends BaseFragment
{
    @ViewInject(R.id.ll_web_content)
    private LinearLayout ll_web_content;

    private String url;

    public void setUrl(String url)
    {
        this.url = url;
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.frag_base_webview;
    }

    @Override
    protected void init()
    {
        super.init();
        LoadWebView();
    }

    private void LoadWebView()
    {
        H5AppViewWeb appViewWeb = new H5AppViewWeb(getActivity());
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ll_web_content.addView(appViewWeb, lp);
        appViewWeb.loadWebViewUrl(url);
    }
}
