package com.fanwe.hybrid.push;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.fanwe.hybrid.push.msg.AuctionPayPushUrlMsg;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.activity.LiveWebViewActivity;
import com.umeng.message.entity.UMessage;

/**
 * Created by Administrator on 2016/9/13.
 */
public class AuctionPayPushRunnable extends PushRunnable
{
    public AuctionPayPushRunnable(Context context, UMessage msg)
    {
        super(context, msg);
    }

    @Override
    public void run()
    {
        final Activity activity = SDActivityManager.getInstance().getLastActivity();
        if (activity == null)
        {
            return;
        }

        AuctionPayPushUrlMsg msg = parseObject(AuctionPayPushUrlMsg.class);
        if (msg != null)
        {
            if (TextUtils.isEmpty(msg.getUrl()))
            {
                SDToast.showToast("url为空");
                return;
            }
            Intent intent = new Intent(activity, LiveWebViewActivity.class);
            intent.putExtra(LiveWebViewActivity.EXTRA_URL, msg.getUrl());
            activity.startActivity(intent);
        }
    }
}
