package com.fanwe.baimei.common;

import android.app.Activity;
import android.content.Intent;

import com.fanwe.baimei.activity.BMAnchorRankListActivity;
import com.fanwe.baimei.activity.BMGameHeroRankActivity;

/**
 * Created by yhz on 2017/5/15.
 */

public class BMActivityLauncher
{
    /**
     * 收入贡献排行榜
     * @param activity
     */
    public static void launcherLiveRankingActivity(Activity activity)
    {
        Intent intent = new Intent(activity, BMAnchorRankListActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 游戏英雄榜
     * @param activity
     */
    public static void launcherGameHeroRankActivity(Activity activity)
    {
        Intent intent = new Intent(activity, BMGameHeroRankActivity.class);
        activity.startActivity(intent);
    }
}
