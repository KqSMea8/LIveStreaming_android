package com.fanwe.baimei.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.fanwe.baimei.adapter.BMPTContentAdapter;
import com.fanwe.hybrid.fragment.BaseFragment;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveUserHomeActivity;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_followActModel;
import com.fanwe.live.model.App_user_homeActModel;
import com.fanwe.live.model.RankUserItemModel;
import com.fanwe.live.view.SDProgressPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yhz on 2017/5/25.
 */

public abstract class BMPTContentBaseFramgnet extends BaseFragment
{
    public final static String RANKING_NAME_DAY = "day";
    public final static String RANKING_NAME_WEEK = "week";
    public final static String RANKING_NAME_MONTH = "month";
    public final static String RANKING_NAME_ALL = "all";

    protected String mRankName = RANKING_NAME_DAY;
    protected int mType;//0 获得秀豆 1 贡献砖石

    @ViewInject(R.id.list)
    protected SDProgressPullToRefreshListView list;

    protected List<RankUserItemModel> listModel = new ArrayList<RankUserItemModel>();
    protected BMPTContentAdapter adapter;

    protected int page;
    protected int has_next;

    @Override
    protected int onCreateContentView()
    {
        return R.layout.include_base_list;
    }

    @Override
    protected void init()
    {
        super.init();
        setAdapter();
        initPullToRefresh();
        requestData(false);
    }

    protected void setAdapter()
    {
        adapter = new BMPTContentAdapter(listModel, getActivity());
        adapter.setItemClickCallback(new SDItemClickCallback<RankUserItemModel>()
        {
            @Override
            public void onItemClick(int position, RankUserItemModel item, View view)
            {
                Intent intent = new Intent(getActivity(), LiveUserHomeActivity.class);
                intent.putExtra(LiveUserHomeActivity.EXTRA_USER_ID, item.getUser_id());
                startActivity(intent);
            }
        });
        adapter.setType(mType);
        adapter.setPoistionInterface(new BMPTContentAdapter.PositionInterface() {
            @Override
            public void getPosition(int position) {
                RankUserItemModel mode = adapter.getItem(position);
                requestFollow(mode.getUser_id());
                adapter.notifyDataSetChanged();
            }
        });


        list.setAdapter(adapter);
    }

    private void requestFollow(final String user_id)
    {
        CommonInterface.requestFollow(user_id, 0, new AppRequestCallback<App_followActModel>()
        {

            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    requestData(false);
                }
            }
        });
    }

    private void initPullToRefresh()
    {
        list.setMode(PullToRefreshBase.Mode.BOTH);
        list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
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

    protected abstract void requestData(boolean isLoadMore);
}
