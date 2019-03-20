package com.fanwe.live.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.appview.main.LiveTabHotView;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/7/5.
 */
public class LiveTopicRoomActivity extends BaseTitleActivity
{
    /**
     * 话题的名字(String)
     */
    public static final String EXTRA_TOPIC_TITLE = "extra_topic_title";
    /**
     * 话题的id(int)
     */
    public static final String EXTRA_TOPIC_ID = "extra_topic_id";

    @ViewInject(R.id.view_create_topic_room)
    private View view_create_topic_room;

    private String mStrTopic;
    private int mTopicId;

    @Override
    protected void onNewIntent(Intent intent)
    {
        setIntent(intent);
        init(null);
        super.onNewIntent(intent);
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.act_live_topic_room;
    }

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        mStrTopic = getIntent().getStringExtra(EXTRA_TOPIC_TITLE);
        mTopicId = getIntent().getIntExtra(EXTRA_TOPIC_ID, 0);

        if (mTopicId <= 0)
        {
            SDToast.showToast("话题id为空");
            finish();
            return;
        }

        initTitle();

        LiveTabHotView tabHotView = new LiveTabHotView(this);
        tabHotView.setTopicId(mTopicId);
        replaceView(R.id.fl_content, tabHotView);
        view_create_topic_room.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), LiveCreateRoomActivity.class);
                intent.putExtra(LiveCreateRoomActivity.EXTRA_CATE_ID, mTopicId);
                intent.putExtra(LiveCreateRoomActivity.EXTRA_TITLE, mStrTopic);
                startActivity(intent);
            }
        });
    }

    private void initTitle()
    {
        if (mStrTopic != null)
        {
            mTitle.setMiddleTextTop(mStrTopic);
        }
    }
}
