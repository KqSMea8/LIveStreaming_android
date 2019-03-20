package com.fanwe.live.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.handler.SDRequestHandler;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveTopicListAdapter;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.Index_topicActModel;
import com.fanwe.live.model.LiveTopicModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/5.
 */
public class LiveTopicView extends BaseAppView
{

    private PullToRefreshListView lv_content;

    private List<LiveTopicModel> listModel;
    private LiveTopicListAdapter adapter;

    public String keyword;
    private int page;
    private int has_next;


    public LiveTopicView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public LiveTopicView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LiveTopicView(Context context)
    {
        super(context);
        init();
    }

    public void setOnTopicClickListener(LiveTopicListAdapter.TopicClickListener listener)
    {
        adapter.setOnTopicClickListener(listener);
    }

    protected void init()
    {
        setContentView(R.layout.view_live_topic);
        lv_content = find(R.id.lv_content);

        listModel = new ArrayList<>();
        adapter = new LiveTopicListAdapter(listModel, getActivity());
        lv_content.setAdapter(adapter);
        initPullToRefresh();
    }

    private void initPullToRefresh()
    {
        lv_content.setMode(PullToRefreshBase.Mode.BOTH);
        lv_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
        {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                requestData(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                requestData(true);
            }
        });
    }

    private SDRequestHandler requestData(final boolean isLoadMore)
    {

        if (isLoadMore)
        {
            if (has_next == 1)
            {
                page++;
            } else
            {
                lv_content.onRefreshComplete();
                SDToast.showToast("没有更多数据了");
                return null;
            }
        } else
        {
            page = 1;
        }

        return CommonInterface.requestToptic(keyword, page, new AppRequestCallback<Index_topicActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    has_next = actModel.getHas_next();
                    SDViewUtil.updateAdapterByList(listModel, actModel.getList(), adapter, isLoadMore);
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                lv_content.onRefreshComplete();
                super.onFinish(resp);
            }
        });
    }

    public SDRequestHandler search(String keyword)
    {
        this.keyword = keyword;
        return requestData(false);
    }
}
