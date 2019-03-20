package com.fanwe.shop.jshandler;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import com.fanwe.hybrid.jshandler.LiveJsHandler;

/**
 * Created by Administrator on 2016/12/14.
 */

public class ShopJsHandler extends LiveJsHandler
{
    public ShopJsHandler(Activity activity)
    {
        super(activity);
    }

    @JavascriptInterface
    public void js_shopping_comeback_live_app()
    {
        getActivity().finish();
    }
}
