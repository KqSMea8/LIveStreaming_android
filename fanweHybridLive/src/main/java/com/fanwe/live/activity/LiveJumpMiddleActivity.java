package com.fanwe.live.activity;

import android.content.Intent;
import android.os.Bundle;

import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.live.R;
import com.fanwe.live.activity.room.LivePushViewerActivity;
import com.fanwe.live.appview.LiveChatC2CNewView;
import com.fanwe.live.model.LiveConversationListModel;

/**
 * Created by Administrator on 2016/7/18.
 */
public class LiveJumpMiddleActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_live_jump_middle);
        init();
    }

    /**
     * 加载聊天fragment
     */
    private void init()
    {
        Intent intent = new Intent(this, LivePushViewerActivity.class);
        intent.putExtra(LivePushViewerActivity.EXTRA_ROOM_ID, getIntent().getIntExtra("extra_room_id",0));
        intent.putExtra(LivePushViewerActivity.EXTRA_CREATER_ID, getIntent().getStringExtra("extra_creater_id"));
        intent.putExtra(LivePushViewerActivity.EXTRA_GROUP_ID, getIntent().getStringExtra("extra_group_id"));
        startActivity(intent);
        finish();
    }
}
