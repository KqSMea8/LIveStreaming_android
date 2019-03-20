package com.fanwe.live.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.fanwe.hybrid.fragment.BaseFragment;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveUserHomeActivity;
import com.fanwe.live.adapter.LiveRankingListAdapter;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_followActModel;
import com.fanwe.live.model.RankUserItemModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 排行榜列表base
 *
 * @author luodong
 * @date 2016-10-10
 */
public abstract class LiveRankingListBaseFragment extends BaseFragment
{

    @ViewInject(R.id.lv_content)
    protected PullToRefreshListView lv_content;

    protected List<RankUserItemModel> listModel = new ArrayList<RankUserItemModel>();
    protected LiveRankingListAdapter adapter;

    protected int page;
    protected int has_next;

    @Override
    protected int onCreateContentView()
    {
        return R.layout.frag_live_ranking_list;
    }

    @Override
    protected void init()
    {
        super.init();
        setAdapter();
        initPullToRefresh();
    }

    protected void setAdapter()
    {
        adapter = new LiveRankingListAdapter(listModel, getActivity());
        adapter.setRankingType(getRankingType());
        adapter.setOnRankingClickListener(new LiveRankingListAdapter.OnRankingClickListener() {
            @Override
            public void clickFollow(View view, int position, RankUserItemModel model) {
                requestFollow(position,model);

            }

            @Override
            public void clickItem(View view, int position, RankUserItemModel model) {
                Intent intent = new Intent(getActivity(), LiveUserHomeActivity.class);
                intent.putExtra(LiveUserHomeActivity.EXTRA_USER_ID, model.getUser_id()+"");
                intent.putExtra(LiveUserHomeActivity.EXTRA_USER_IMG_URL, model.getHead_image());
                getActivity().startActivity(intent);
            }
        });
        lv_content.setAdapter(adapter);
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            requestData(false);
        }
    }

    private void requestFollow(final int position, final RankUserItemModel model)
    {
        showProgressDialog("");
        CommonInterface.requestFollow(model.getUser_id()+"", 0, new AppRequestCallback<App_followActModel>()
        {

            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    if (actModel.getHas_focus() == 1)
                    {
                        model.setIs_focus(1);
                    }else {
                        model.setIs_focus(0);
                    }
                    adapter.updateData(position,model);
                }
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
                dismissProgressDialog();
            }
        });
    }

    protected abstract void requestData(boolean isLoadMore);
    protected abstract String getRankingType();
}
