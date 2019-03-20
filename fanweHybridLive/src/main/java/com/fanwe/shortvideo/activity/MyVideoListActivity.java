package com.fanwe.shortvideo.activity;

import android.os.Bundle;

import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.live.R;
import com.fanwe.shortvideo.fragment.LiveTabShortVideoFragment;


public class MyVideoListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_short_video_my);
        Bundle bundle=new Bundle();
        bundle.putString("comeFrom",MyVideoListActivity.class.getSimpleName());
        getSDFragmentManager().toggle(R.id.myShortVideosFragment, null, LiveTabShortVideoFragment.class,bundle);
    }


}
