package com.fanwe.baimei.fragment;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_RankContributionModel;

/**
 * Created by yhz on 2017/5/25. 土豪榜列表内容
 */

public class BMTyrantContentFragment extends BMPTContentBaseFramgnet
{
    @Override
    protected void init()
    {
        mType = 1;
        super.init();
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
                list.onRefreshComplete();
                SDToast.showToast("没有更多数据了");
                return;
            }
        } else
        {
            page = 1;
        }

        CommonInterface.requestRankContribution(page, mRankName, new AppRequestCallback<App_RankContributionModel>()
        {

            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    has_next = actModel.getHas_next();
                    if (isLoadMore)
                    {
                        adapter.appendData(actModel.getList());
                    } else
                    {
                        adapter.updateData(actModel.getList());
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
