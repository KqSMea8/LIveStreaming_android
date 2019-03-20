package com.fanwe.auction.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.fanwe.auction.adapter.AuctionTradeMsgAdapter;
import com.fanwe.auction.common.AuctionCommonInterface;
import com.fanwe.auction.model.App_message_getlistActModel;
import com.fanwe.auction.model.MessageGetListDataListItemModel;
import com.fanwe.auction.model.PageModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.view.SDProgressPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/11.
 */
public class AuctionTradeMsgView extends BaseAppView
{
    private SDProgressPullToRefreshListView list_trade;
    private List<MessageGetListDataListItemModel> listModel;
    private AuctionTradeMsgAdapter adapterTrade;
    private PageModel pageModel = new PageModel();
    private int page = 1;

    public AuctionTradeMsgView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public AuctionTradeMsgView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public AuctionTradeMsgView(Context context)
    {
        super(context);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.view_auction_trade_msg);
        register();
        bindAdapterTrade();
        refreshViewer();
    }

    private void register()
    {
        list_trade = find(R.id.list_trade);
        list_trade.setMode(PullToRefreshBase.Mode.BOTH);
        list_trade.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
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
    }

    private void bindAdapterTrade()
    {
        listModel = new ArrayList<MessageGetListDataListItemModel>();
        adapterTrade = new AuctionTradeMsgAdapter(listModel, getActivity());
        list_trade.setAdapter(adapterTrade);
    }

    public void refreshViewer()
    {
        page = 1;
        request(false);
    }

    private void loadMoreViewer()
    {
        if (pageModel.getHas_next() == 1)
        {
            page++;
            request(true);
        } else
        {
            SDToast.showToast("没有更多数据了");
            list_trade.onRefreshComplete();
        }
    }

    public void request(final boolean isLoadMore)
    {
        AuctionCommonInterface.requestMessage_getlist(page, new AppRequestCallback<App_message_getlistActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    if (actModel.getData() != null)
                    {
                        pageModel = actModel.getData().getPage();
                        SDViewUtil.updateAdapterByList(listModel, actModel.getData().getList(), adapterTrade, isLoadMore);
                    }
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                list_trade.onRefreshComplete();
            }
        });
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
    }
}
