package com.fanwe.live.activity;

import android.os.Bundle;

import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.live.R;
import com.fanwe.live.fragment.LiveTabMeNewFragment;

/**
 * 包名: com.fanwe.live.activity
 * 描述: LiveTabMeNewFragment wrapper.
 * 作者: Su
 * 创建时间: 2017/6/7 18:35
 **/
public class LiveTabMeNewActivity extends BaseActivity
{
    private LiveTabMeNewFragment mLiveTabMeNewFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_live_tab_me_new);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container_act_live_tab_me_new, getLiveTabMeNewFragment())
                .commitNow();
    }

    private LiveTabMeNewFragment getLiveTabMeNewFragment()
    {
        if (mLiveTabMeNewFragment == null)
        {
            mLiveTabMeNewFragment = new LiveTabMeNewFragment();
        }
        return mLiveTabMeNewFragment;
    }


}
