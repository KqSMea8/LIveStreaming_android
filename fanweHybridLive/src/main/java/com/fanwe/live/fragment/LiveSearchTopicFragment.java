package com.fanwe.live.fragment;

import android.text.TextUtils;

import com.fanwe.library.adapter.http.handler.SDRequestHandler;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveTopicListAdapter;
import com.fanwe.live.appview.LiveTopicView;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by shibx on 2016/7/25.
 */
public class LiveSearchTopicFragment extends LiveBaseSearchFragment {

    @ViewInject(R.id.view_topic)
    private LiveTopicView view_topic;

    private SDRequestHandler mHandler = null;
    private String keyWord = "";

    private LiveTopicListAdapter.TopicClickListener mListener;

    @Override
    protected int onCreateContentView() {
        return R.layout.frag_live_search_topic;
    }

    public void setOnTopicClickListener(LiveTopicListAdapter.TopicClickListener listener) {
        this.mListener = listener;
    }

    @Override
    protected void init() {
        super.init();
        view_topic.setOnTopicClickListener(mListener);
        view_topic.search(keyWord);
    }

    @Override
    public void search(String keyWord) {
        this.keyWord = keyWord;

        if (view_topic == null)
        {
            return;
        }

        if(!TextUtils.isEmpty(keyWord) && TextUtils.equals(keyWord,view_topic.keyword)) {
//                SDToast.showToast("查找相同字符串");
            return ;
        }
        if(mHandler != null) {
            mHandler.cancel();
        }
        mHandler = view_topic.search(keyWord);
    }
}
