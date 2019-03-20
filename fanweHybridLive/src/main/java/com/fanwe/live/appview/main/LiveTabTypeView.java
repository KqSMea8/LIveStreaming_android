package com.fanwe.live.appview.main;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.library.model.SDTaskRunnable;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveTabFollowAdapter;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.HomeTabTitleModel;
import com.fanwe.live.model.Index_focus_videoActModel;
import com.fanwe.live.model.Index_indexActModel;
import com.fanwe.live.model.LivePlaybackModel;
import com.fanwe.live.model.LiveRoomModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 分类直播列表
 *
 * @author Administrator
 * @date 2016-7-2 上午11:28:13
 */
public class LiveTabTypeView extends LiveTabBaseView
{
    public LiveTabTypeView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }
    int type;
    public LiveTabTypeView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LiveTabTypeView(Context context,HomeTabTitleModel mTabTitleModel,int type)
    {
        super(context);
        this.mTabTitleModel=mTabTitleModel;
        this.type=type;
        init();
    }

    protected PullToRefreshListView lv_content;

    protected List<Object> listModel = new ArrayList<>();
    protected LiveTabFollowAdapter adapter;

    protected List<LiveRoomModel> listRoom;
    protected List<LivePlaybackModel> listPlayback;

    protected void init()
    {
        setContentView(R.layout.frag_live_tab_follow);
        lv_content = find(R.id.lv_content);

        setAdapter();
        initPullToRefresh();
    }
    private HomeTabTitleModel mTabTitleModel;
    protected void setAdapter()
    {
        adapter = new LiveTabFollowAdapter(listModel, getActivity());
        lv_content.setAdapter(adapter);
    }

    protected void initPullToRefresh()
    {
        lv_content.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        lv_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
        {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                requestData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
            {
            }
        });
        requestData();
    }

    @Override
    public void onActivityResumed(Activity activity)
    {
        requestData();
        super.onActivityResumed(activity);
    }

    protected void requestData()
    {
        if(type==1){
            CommonInterface.requestCommendVideo(new AppRequestCallback<Index_indexActModel>()
            {
                @Override
                protected void onSuccess(SDResponse resp)
                {
                    if (actModel.isOk())
                    {
                        synchronized (LiveTabTypeView.this)
                        {
                            listRoom = actModel.getList();
                            orderData();
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
        }else{
            CommonInterface.requestCategoryVideo(mTabTitleModel.getClassified_id(),new AppRequestCallback<Index_indexActModel>()
            {
                @Override
                protected void onSuccess(SDResponse resp)
                {
                    if (actModel.isOk())
                    {
                        synchronized (LiveTabTypeView.this)
                        {
                            listRoom = actModel.getList();
                            orderData();
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
        }
    }

    protected void orderData()
    {
        listModel.clear();
        if (SDCollectionUtil.isEmpty(listRoom))
        {
            listModel.add(new LiveTabFollowAdapter.TypeNoLiveRoomModel());
        } else
        {
            for (LiveRoomModel room : listRoom)
            {
                listModel.add(room);
            }
        }
        if (!SDCollectionUtil.isEmpty(listPlayback))
        {
            for (LivePlaybackModel playback : listPlayback)
            {
                listModel.add(playback);
            }
        }
    }

    @Override
    public void scrollToTop()
    {
        lv_content.getRefreshableView().setSelection(1);
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
                synchronized (LiveTabTypeView.this)
                {
                    if (SDCollectionUtil.isEmpty(listRoom))
                    {
                        return null;
                    }
                    Iterator<LiveRoomModel> it = listRoom.iterator();
                    while (it.hasNext())
                    {
                        LiveRoomModel item = it.next();
                        if (roomId == item.getRoom_id())
                        {
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
                    synchronized (LiveTabTypeView.this)
                    {
                        adapter.removeData(result);
                    }
                }
            }
        });
    }
}
