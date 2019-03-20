package com.fanwe.pay.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.fanwe.hybrid.fragment.BaseFragment;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveUserHomeActivity;
import com.fanwe.live.model.PageModel;
import com.fanwe.live.view.SDProgressPullToRefreshListView;
import com.fanwe.pay.adapter.PayBalanceAdapter;
import com.fanwe.pay.common.PayCommonInterface;
import com.fanwe.pay.model.AppLivePayContActModel;
import com.fanwe.pay.model.LivePayContDataModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/30.
 */

public class PayBalanceBaseFragment extends BaseFragment
{
    @ViewInject(R.id.list)
    protected SDProgressPullToRefreshListView list;

    protected PayBalanceAdapter adapter;

    protected List<LivePayContDataModel> listModel = new ArrayList<LivePayContDataModel>();
    private PageModel pageModel = new PageModel();
    protected int page = 1;
    protected int type = 0;//0 付费记录 1 收费记录
    protected int live_pay_type = 0;//0 按时付费 1 按场付费

    @Override
    protected int onCreateContentView()
    {
        return R.layout.frag_pay_balance;
    }

    @Override
    protected void init()
    {
        super.init();
        register();
    }

    protected void register()
    {
        list.setMode(PullToRefreshBase.Mode.BOTH);
        list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
        {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                refreshViewer();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                loadMoreViewer();
            }
        });
        adapter = new PayBalanceAdapter(listModel, getActivity());
        adapter.setItemClickCallback(new SDItemClickCallback<LivePayContDataModel>()
        {
            @Override
            public void onItemClick(int position, LivePayContDataModel item, View view)
            {
                Intent intent = new Intent(getActivity(), LiveUserHomeActivity.class);
                intent.putExtra(LiveUserHomeActivity.EXTRA_USER_ID, item.getUser_id());
                startActivity(intent);
            }
        });
        list.setAdapter(adapter);

        refreshViewer();
    }

    private void loadMoreViewer()
    {
        if (pageModel != null)
        {
            if (pageModel.getHas_next() == 1)
            {
                page++;
                requestLivePayCont(true);
            } else
            {
                list.onRefreshComplete();
                SDToast.showToast("没有更多数据");
            }
        } else
        {
            refreshViewer();
        }
    }

    private void refreshViewer()
    {
        page = 1;
        requestLivePayCont(false);
    }

    protected void requestLivePayCont(final boolean isLoadMore)
    {
        PayCommonInterface.requestLivePayCont(page, type, live_pay_type, new AppRequestCallback<AppLivePayContActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    pageModel = actModel.getPage();
                    if (isLoadMore)
                    {
                        adapter.appendData(actModel.getData());
                    } else
                    {
                        adapter.updateData(actModel.getData());
                    }
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                list.onRefreshComplete();
            }
        });
    }

}
