package com.fanwe.live.fragment;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_RankConsumptionModel;

/**
 * 功德排行榜列表
 *
 * @author luodong
 * @date 2016-10-10
 */
public class LiveRankingListMeritsFragment extends LiveRankingListBaseFragment
{

    public final static String RANKING_NAME_DAY = "day";
    public final static String RANKING_NAME_MONTH = "month";
    public final static String RANKING_NAME_ALL = "all";
    public final static String RANKING_NAME_WEEK = "week";
    private String rank_name;

    public void setRankName(String rank_name){
        this.rank_name = rank_name;
    }

    @Override
    protected void requestData(final boolean isLoadMore)
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
                return;
            }
        } else
        {
            page = 1;
        }

        CommonInterface.requestRankConsumption(page,rank_name,new AppRequestCallback<App_RankConsumptionModel>()
        {

            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    has_next = actModel.getHas_next();

                    synchronized (LiveRankingListMeritsFragment.this)
                    {
                        SDViewUtil.updateAdapterByList(listModel, actModel.getList(), adapter, isLoadMore);
                    }
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                lv_content.onRefreshComplete();
                super.onFinish(resp);
            }

            @Override
            protected void onError(SDResponse resp) {
                super.onError(resp);
            }
        });
    }

    @Override
    protected String getRankingType() {
        return "获得";
    }

}
