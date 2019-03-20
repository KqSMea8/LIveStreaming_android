package com.fanwe.live.activity;

import com.fanwe.live.event.EFinishAdImg;
import com.sunday.eventbus.SDEventManager;

/**
 * Created by luodong on 2016/10/26 .
 */
public class AdImgWebViewActivity extends LiveWebViewActivity
{
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        onSendEvent();
    }

    /**
     * 发送关闭事件
     */
    private void onSendEvent()
    {
        EFinishAdImg event = new EFinishAdImg();
        SDEventManager.post(event);
    }
}
