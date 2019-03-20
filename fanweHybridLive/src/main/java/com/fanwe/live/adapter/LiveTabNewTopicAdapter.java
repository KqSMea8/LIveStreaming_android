package com.fanwe.live.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveHotTopicActivity;
import com.fanwe.live.activity.LiveTopicRoomActivity;
import com.fanwe.live.model.LiveTopicModel;

import java.util.List;

public class LiveTabNewTopicAdapter extends SDSimpleAdapter<LiveTopicModel>
{

    public LiveTabNewTopicAdapter(List<LiveTopicModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_live_tab_new_topic;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, LiveTopicModel model)
    {
        TextView tv_title = get(R.id.tv_title, convertView);
        SDViewBinder.setTextView(tv_title, model.getTitle());

        tv_title.setTag(model);
        tv_title.setOnClickListener(clickTopicListener);

    }

    private View.OnClickListener clickTopicListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            LiveTopicModel model = (LiveTopicModel) v.getTag();

            if (model.getCate_id() > 0)
            {
                Intent intent = new Intent(getActivity(), LiveTopicRoomActivity.class);
                intent.putExtra(LiveTopicRoomActivity.EXTRA_TOPIC_ID, model.getCate_id());
                intent.putExtra(LiveTopicRoomActivity.EXTRA_TOPIC_TITLE, model.getTitle());
                getActivity().startActivity(intent);
            } else
            {
                Intent intent = new Intent(getActivity(), LiveHotTopicActivity.class);
                getActivity().startActivity(intent);
            }
        }
    };

}
