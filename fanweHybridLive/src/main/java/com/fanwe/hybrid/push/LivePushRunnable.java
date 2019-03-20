package com.fanwe.hybrid.push;

import android.app.Activity;
import android.content.Context;

import com.fanwe.hybrid.push.msg.LivePushMsg;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.live.utils.LiveVideoChecker;
import com.umeng.message.entity.UMessage;

public class LivePushRunnable extends PushRunnable
{

    public LivePushRunnable(Context context, UMessage msg)
    {
        super(context, msg);
    }

    @Override
    public void run()
    {
        LivePushMsg live = parseObject(LivePushMsg.class);
        if (live == null)
        {
            return;
        }

        final Activity activity = SDActivityManager.getInstance().getLastActivity();
        if (activity == null)
        {
            return;
        }

        LiveVideoChecker checker = new LiveVideoChecker(activity);
        checker.check(live.getRoom_id());
    }
}
