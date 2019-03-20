package com.fanwe.live.adapter;

import java.util.List;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.live.R;
import com.fanwe.live.model.LiveTopicModel;

public class LiveTopicListAdapter extends SDSimpleAdapter<LiveTopicModel> {

	private TopicClickListener mListener;
	
	public LiveTopicListAdapter(List<LiveTopicModel> listModel, Activity activity) {
		super(listModel, activity);
	}
	
	public void setOnTopicClickListener (TopicClickListener listener) {
		this.mListener = listener;
	}

	@Override
	public int getLayoutId(int position, View convertView, ViewGroup parent) {
		return R.layout.item_live_topic_list;
	}

	@Override
	public void bindData(int position, View convertView, ViewGroup parent, final LiveTopicModel model) {
		TextView tv_topic_content = get(R.id.tv_topic_content, convertView);
		TextView tv_topic_number = get(R.id.tv_topic_number, convertView);
		tv_topic_content.setText(model.getTitleShort());
		tv_topic_number.setText(String.valueOf(model.getNum()));
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mListener.onTopicClick(model);
			}
		});
	}
	
	public interface TopicClickListener {
		public void onTopicClick(LiveTopicModel model);
	}
}
