package com.fanwe.baimei.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.fanwe.baimei.adapter.BMGameRankContentAdatper;
import com.fanwe.baimei.common.BMCommonInterface;
import com.fanwe.baimei.model.BMGameRankActModel;
import com.fanwe.games.constant.GameParamsConstant;
import com.fanwe.hybrid.fragment.BaseFragment;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveUserHomeActivity;
import com.fanwe.live.view.SDProgressPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yhz on 2017/5/25.
 */

public class BMGameRankContentBaseFragment extends BaseFragment
{
    public final static String EXTRA_GAME_ID = "extra_game_id";

    public final static String RANKING_NAME_DAY = "day";
    public final static String RANKING_NAME_ALL = "all";

    protected String mRankName = RANKING_NAME_DAY;

    public void setRankName(String rankName)
    {
        mRankName = rankName;
    }

    private int mGameID = GameParamsConstant.GAME_ID_1;

    @ViewInject(R.id.list)
    protected SDProgressPullToRefreshListView list;

    protected List<BMGameRankActModel.BMGameRankBean> mListModel = new ArrayList<BMGameRankActModel.BMGameRankBean>();
    protected BMGameRankContentAdatper mAdapter;

    protected int mPage;
    protected int mHasNext;

    @Override
    protected int onCreateContentView()
    {
        return R.layout.include_base_list;
    }

    @Override
    protected void init()
    {
        super.init();
        initGameID();
        setAdapter();
        initPullToRefresh();
        requestData(false);
    }

    private void initGameID()
    {
        mGameID = getArguments().getInt(BMGameRankContentBaseFragment.EXTRA_GAME_ID);
    }

    protected void setAdapter()
    {
        mAdapter = new BMGameRankContentAdatper(mListModel, getActivity());
        mAdapter.setItemClickCallback(new SDItemClickCallback<BMGameRankActModel.BMGameRankBean>()
        {
            @Override
            public void onItemClick(int position, BMGameRankActModel.BMGameRankBean item, View view)
            {
                Intent intent = new Intent(getActivity(), LiveUserHomeActivity.class);
                intent.putExtra(LiveUserHomeActivity.EXTRA_USER_ID, item.getUser_id());
                startActivity(intent);
            }
        });
        list.setAdapter(mAdapter);
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

    private void requestData(final boolean isLoadMore)
    {
        if (isLoadMore)
        {
            if (mHasNext == 1)
            {
                mPage++;
            } else
            {
                list.onRefreshComplete();
                SDToast.showToast("没有更多数据了");
                return;
            }
        } else
        {
            mPage = 1;
        }

        BMCommonInterface.requestRankGame(mRankName, mGameID, mPage, new AppRequestCallback<BMGameRankActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    mAdapter.setUnit(actModel.getDesc());
                    mHasNext = actModel.getHas_next();
                    if (isLoadMore)
                    {
                        mAdapter.appendData(actModel.getList());
                    } else
                    {
                        mAdapter.updateData(actModel.getList());
                    }
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                list.onRefreshComplete();
                super.onFinish(resp);
            }
        });
    }
}
