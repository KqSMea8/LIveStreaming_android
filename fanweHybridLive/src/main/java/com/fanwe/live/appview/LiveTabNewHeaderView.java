package com.fanwe.live.appview;

import android.content.Context;
import android.util.AttributeSet;

import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveTabNewTopicAdapter;
import com.fanwe.live.model.LiveTopicModel;

import java.util.ArrayList;
import java.util.List;

public class LiveTabNewHeaderView extends BaseAppView
{

    private SDGridLinearLayout ll_topic;

    private LiveTabNewTopicAdapter adapter;
    private List<LiveTopicModel> listModel = new ArrayList<LiveTopicModel>();

    public LiveTabNewHeaderView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public LiveTabNewHeaderView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LiveTabNewHeaderView(Context context)
    {
        super(context);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.view_live_tab_new_header);

        ll_topic = find(R.id.ll_topic);
        ll_topic.setColNumber(2);

        ll_topic.setVerticalStrokeWidth(1);
        ll_topic.setVerticalStrokeColor(getResources().getColor(R.color.stroke));

        ll_topic.setHorizontalStrokeWidth(1);
        ll_topic.setHorizontalStrokeColor(getResources().getColor(R.color.stroke));

        adapter = new LiveTabNewTopicAdapter(listModel, getActivity());
        ll_topic.setAdapter(adapter);
    }

    public void setListLiveTopicModel(List<LiveTopicModel> listModel)
    {
        adapter.updateData(listModel);
    }

}
