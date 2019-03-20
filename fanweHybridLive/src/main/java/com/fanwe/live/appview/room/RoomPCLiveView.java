package com.fanwe.live.appview.room;

import android.content.Context;
import android.util.AttributeSet;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.HeaderGridView;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveRoomTabLiveAdapter;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.Index_new_videoActModel;
import com.fanwe.live.model.LiveRoomModel;
import com.fanwe.live.view.SDProgressPullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shibx on 2017/2/6.
 * PC端直播间 直播列表页面(代替fragment)
 */

public class RoomPCLiveView extends RoomView
{

    protected SDProgressPullToRefreshGridView lv_content;

    protected List<LiveRoomModel> listModel = new ArrayList<>();

    private LiveRoomTabLiveAdapter mAdapter;

    private int p = 1;
    private int has_next;

    public RoomPCLiveView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public RoomPCLiveView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public RoomPCLiveView(Context context)
    {
        super(context);
        init();
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.frag_live_tab_new;
    }

    protected void init()
    {
        lv_content = find(R.id.lv_content);
        setAdapter();
        initPullToRefresh();
    }

    private void initPullToRefresh()
    {
        lv_content.setMode(PullToRefreshBase.Mode.BOTH);
        lv_content.setOnRefreshListener(new SDProgressPullToRefreshGridView.OnRefreshListener2<HeaderGridView>()
        {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase refreshView)
            {
                requestDatas(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase refreshView)
            {
                requestDatas(true);
            }
        });
        requestDatas(false);
    }

    private void setAdapter()
    {
        mAdapter = new LiveRoomTabLiveAdapter(new ArrayList<List<LiveRoomModel>>(), getActivity());
        lv_content.setAdapter(mAdapter);
    }

    private void requestDatas(final boolean isLoadMore)
    {

        if (isLoadMore)
        {
            if (has_next == 1)
            {
                p++;
            } else
            {
                lv_content.onRefreshComplete();
                SDToast.showToast("没有更多数据了");
                return;
            }
        } else
        {
            p = 1;
        }

        CommonInterface.requestPcLive(p, new AppRequestCallback<Index_new_videoActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    has_next = actModel.getHas_next();
                    synchronized (RoomPCLiveView.this)
                    {
                        SDViewUtil.updateList(listModel, actModel.getList(), false);
                        mAdapter.updateData(SDCollectionUtil.splitList(listModel, 2));
                    }
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                lv_content.onRefreshComplete();
            }
        });
    }
}
