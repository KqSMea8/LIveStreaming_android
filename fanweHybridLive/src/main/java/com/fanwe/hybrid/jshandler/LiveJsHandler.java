package com.fanwe.hybrid.jshandler;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.model.JoinLiveData;

/**
 * Created by Administrator on 2016/9/18.
 */
public class LiveJsHandler extends AppJsHandler
{
    protected Handler handler = new Handler(Looper.getMainLooper());

    public LiveJsHandler(Activity activity)
    {
        super(activity);
    }

    @JavascriptInterface
    public void join_live(final String json)
    {
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                main_join_live(json);
            }
        });
    }

    public void main_join_live(String json)
    {
        JoinLiveData jsonLiveData = JSON.parseObject(json, JoinLiveData.class);
        AppRuntimeWorker.joinLive(jsonLiveData, getActivity());
    }
}
