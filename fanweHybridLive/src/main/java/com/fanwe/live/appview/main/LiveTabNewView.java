package com.fanwe.live.appview.main;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.map.tencent.SDTencentMapManager;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.library.model.SDTaskRunnable;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.view.SDRecyclerView;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveTabNewAdapter;
import com.fanwe.live.appview.LiveTabNewHeaderView;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.Index_new_videoActModel;
import com.fanwe.live.model.LiveRoomModel;
import com.fanwe.live.view.SDProgressPullToRefreshRecyclerView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.headerfooter.songhang.library.SmartRecyclerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * 最新直播列表
 *
 * @author Administrator
 * @date 2016-7-2 上午11:27:49
 */
public class LiveTabNewView extends LiveTabBaseView
{
    public LiveTabNewView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public LiveTabNewView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LiveTabNewView(Context context)
    {
        super(context);
        init();
    }

    protected SDProgressPullToRefreshRecyclerView lv_content;

    protected List<LiveRoomModel> listModel = new ArrayList<>();
    protected LiveTabNewAdapter adapter;

    protected LiveTabNewHeaderView headerView;
    /**
     * 请求接口的次数
     */
    protected int requestCount;

    protected void init()
    {
        setContentView(R.layout.frag_live_tab_new);
        lv_content = find(R.id.lv_content);
        lv_content.getRefreshableView().setGridVertical(3);

        addHeaderView();
        setAdapter();
        initPullToRefresh();
    }

    protected void addHeaderView()
    {
    }

    protected void setAdapter()
    {
        adapter = new LiveTabNewAdapter(new ArrayList<LiveRoomModel>(), getActivity());
        SmartRecyclerAdapter smartRecyclerAdapter = new SmartRecyclerAdapter(adapter);
        headerView = new LiveTabNewHeaderView(getActivity());
        smartRecyclerAdapter.setHeaderView(headerView);

        lv_content.getRefreshableView().setAdapter(smartRecyclerAdapter);
    }

    protected void initPullToRefresh()
    {
        lv_content.setMode(Mode.PULL_FROM_START);
        lv_content.setOnRefreshListener(new OnRefreshListener2<SDRecyclerView>()
        {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<SDRecyclerView> refreshView)
            {
                startLoopRunnable();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<SDRecyclerView> refreshView)
            {
            }
        });
        startLoopRunnable();
    }

    @Override
    public void onActivityResumed(Activity activity)
    {
        super.onActivityResumed(activity);
        startLoopRunnable();
    }

    @Override
    protected void onLoopRun()
    {
        requestData();
    }

    protected void requestData()
    {
        if (requestCount % 5 == 0)
        {
            SDTencentMapManager.getInstance().startLocation(null);
        }

        CommonInterface.requestNewVideo(1, new AppRequestCallback<Index_new_videoActModel>()
        {

            @Override
            protected void onSuccess(final SDResponse resp)
            {
                if (actModel.isOk())
                {
                    headerView.setListLiveTopicModel(actModel.getCate_top());

                    synchronized (LiveTabNewView.this)
                    {
                        listModel = actModel.getList();
                        sortData(listModel);
                        adapter.updateData(listModel);
                    }
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                lv_content.onRefreshComplete();
                super.onFinish(resp);
            }
        });

        requestCount++;
    }

    private void sortData(List<LiveRoomModel> list)
    {
        if (SDCollectionUtil.isEmpty(list))
        {
            return;
        }
        if (SDTencentMapManager.getInstance().hasLocationSuccess())
        {
            Collections.sort(list, new Comparator<LiveRoomModel>()
            {
                @Override
                public int compare(LiveRoomModel lhs, LiveRoomModel rhs)
                {
                    double result = lhs.getDistance() - rhs.getDistance();
                    if (result < 0)
                    {
                        return -1;
                    } else if (result == 0)
                    {
                        return 0;
                    } else
                    {
                        return 1;
                    }
                }
            });
        }
    }

    @Override
    public void scrollToTop()
    {
        lv_content.getRefreshableView().scrollToStart();
    }

    @Override
    protected void onRoomClosed(final int roomId)
    {
        super.onRoomClosed(roomId);
        SDHandlerManager.getBackgroundHandler().post(new SDTaskRunnable<LiveRoomModel>()
        {

            @Override
            public LiveRoomModel onBackground()
            {
                synchronized (LiveTabNewView.this)
                {
                    if (SDCollectionUtil.isEmpty(listModel))
                    {
                        return null;
                    }
                    Iterator<LiveRoomModel> it = listModel.iterator();
                    while (it.hasNext())
                    {
                        LiveRoomModel item = it.next();
                        if (roomId == item.getRoom_id())
                        {
                            it.remove();
                            return item;
                        }
                    }
                }
                return null;
            }

            @Override
            public void onMainThread(LiveRoomModel result)
            {
                if (result != null)
                {
                    synchronized (LiveTabNewView.this)
                    {
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
