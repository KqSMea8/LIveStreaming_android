package com.fanwe.live.activity;

import android.content.Intent;
import android.os.Bundle;

import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveTopicListAdapter;
import com.fanwe.live.appview.LiveTopicView;
import com.fanwe.live.model.LiveTopicModel;

import org.xutils.view.annotation.ViewInject;

/**
 * 热门话题
 * Created by Administrator on 2016/7/6.
 */
public class LiveHotTopicActivity extends BaseTitleActivity
{

    @ViewInject(R.id.view_topic)
    private LiveTopicView view_topic;

    @Override
    protected int onCreateContentView()
    {
        return R.layout.act_live_hot_topic;
    }

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);

        mTitle.setMiddleTextTop("热门话题");

        view_topic.setOnTopicClickListener(new LiveTopicListAdapter.TopicClickListener()
        {
            @Override
            public void onTopicClick(LiveTopicModel model)
            {
                Intent intent = new Intent(getApplicationContext(), LiveTopicRoomActivity.class);
                intent.putExtra(LiveTopicRoomActivity.EXTRA_TOPIC_ID, model.getCate_id());
                intent.putExtra(LiveTopicRoomActivity.EXTRA_TOPIC_TITLE, model.getTitle());
                startActivity(intent);
            }
        });
        view_topic.search(null);
    }
}
