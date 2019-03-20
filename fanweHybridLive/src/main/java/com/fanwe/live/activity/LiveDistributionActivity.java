package com.fanwe.live.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveDistributionAdatper;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_distribution_indexActModel;
import com.fanwe.live.model.DistributionItemModel;
import com.fanwe.live.model.PageModel;
import com.fanwe.live.view.SDProgressPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yhz on 2017/1/3.我的分销
 */

public class LiveDistributionActivity extends BaseTitleActivity
{
    @ViewInject(R.id.list)
    protected SDProgressPullToRefreshListView list;

    private List<DistributionItemModel> listModel = new ArrayList<DistributionItemModel>();
    private LiveDistributionAdatper liveDistributionAdatper;
    private PageModel pageModel;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_live_distribution);
        x.view().inject(this);
        init();
    }

    private void init()
    {
        initTitle();
        register();
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("分享收益");
    }

    protected void register()
    {
        liveDistributionAdatper = new LiveDistributionAdatper(listModel, this);
        list.setAdapter(liveDistributionAdatper);

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

        refreshViewer();
    }

    private void loadMoreViewer()
    {
        if (pageModel != null)
        {
            if (pageModel.getHas_next() == 1)
            {
                page++;
                requestDistribution(true);
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
        requestDistribution(false);
    }

    protected void requestDistribution(final boolean isLoadMore)
    {
        CommonInterface.requestDistribution(page, new AppRequestCallback<App_distribution_indexActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    pageModel = actModel.getPage();
                    SDViewUtil.updateAdapterByList(listModel, actModel.getData(), liveDistributionAdatper, isLoadMore);
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
