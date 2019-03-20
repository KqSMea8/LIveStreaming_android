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
import com.fanwe.live.adapter.LiveUserModelAdapter;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_focus_follow_ActModel;
import com.fanwe.live.model.UserModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/25.
 */
public class LiveSearchUserView extends BaseAppView
{

    private PullToRefreshListView lv_search_result;

    private List<UserModel> listModel;
    private LiveUserModelAdapter adapter;

    private int has_next;
    private int page = 1;

    public String keyword = "";

    public void setOnItemClickListener(LiveUserModelAdapter.OnItemClickListener listener)
    {
        adapter.setOnItemClickListener(listener);
    }

    public LiveSearchUserView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public LiveSearchUserView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LiveSearchUserView(Context context)
    {
        super(context);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.view_live_search_user);
        lv_search_result = find(R.id.lv_search_result);
        listModel = new ArrayList<>();
        adapter = new LiveUserModelAdapter(listModel, getActivity());
        lv_search_result.setAdapter(adapter);
        initPullToRefresh();
    }

    private void initPullToRefresh()
    {
        lv_search_result.setMode(PullToRefreshBase.Mode.BOTH);
        lv_search_result.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
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

    protected SDRequestHandler requestData(final boolean isLoadMore)
    {
        if (isLoadMore)
        {
            if (has_next == 1)
            {
                page++;
            } else
            {
                lv_search_result.onRefreshComplete();
                SDToast.showToast("没有更多数据了");
                return null;
            }
        } else
        {
            page = 1;
        }
        return CommonInterface.requestSearchUser(page, keyword, new AppRequestCallback<App_focus_follow_ActModel>()
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
                lv_search_result.onRefreshComplete();
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
