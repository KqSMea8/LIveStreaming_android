package com.fanwe.live.activity;

import android.content.Intent;

import com.fanwe.auction.FloatViewPermissionHelp;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.live.activity.room.LivePushCreaterActivity;

/**
 * Created by yhz on 2016/7/9 0009.
 */
public class LiveFloatViewWebViewActivity extends LiveWebViewActivity
{
    protected void init()
    {
        super.init();
        if (!SDActivityManager.getInstance().containActivity(LivePushCreaterActivity.class))
        {
            FloatViewPermissionHelp.checkPermissionAndSwitchSmallScreen(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        FloatViewPermissionHelp.onActivityResult(this, requestCode, resultCode, data);
    }
}
