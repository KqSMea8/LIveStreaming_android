package com.fanwe.shop.dialog;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.fanwe.auction.model.PageModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveWebViewActivity;
import com.fanwe.live.view.SDProgressPullToRefreshListView;
import com.fanwe.shop.adapter.ShopMyStoreAdapter;
import com.fanwe.shop.common.ShopCommonInterface;
import com.fanwe.shop.model.App_shop_goodsActModel;
import com.fanwe.shop.model.App_shop_mystoreActModel;
import com.fanwe.shop.model.App_shop_mystoreItemModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yhz on 2017/3/22.我的小店商品列表
 */

public class ShopPodcastGoodsDialog extends SDDialogBase
{
    private SDProgressPullToRefreshListView list;
    private ShopMyStoreAdapter adapter;
    private List<App_shop_mystoreItemModel> listModel = new ArrayList<App_shop_mystoreItemModel>();
    private PageModel pageModel = new PageModel();
    private int page = 1;
    private boolean mIsCreater;//是否是主播，如果是主播才有推送按钮
    private String mCreaterId;

    public ShopPodcastGoodsDialog(Activity activity, boolean isCreater, String createrId)
    {
        super(activity);
        this.mIsCreater = isCreater;
        this.mCreaterId = createrId;
        init();
    }

    private void init()
    {
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.shop_dialog_podcast_goods);
        paddings(0);
        int screenHeight = (SDViewUtil.getScreenHeight() / 2);
        setHeight(screenHeight);
        initView();
        initData();
        refreshViewer();
    }

    private void initView()
    {
        list = (SDProgressPullToRefreshListView) findViewById(R.id.list);
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
    }

    private void initData()
    {
        adapter = new ShopMyStoreAdapter(listModel, getOwnerActivity());
        adapter.setmIsCreater(mIsCreater);
        list.setAdapter(adapter);
        adapter.setItemClickCallback(new SDItemClickCallback<App_shop_mystoreItemModel>()
        {
            @Override
            public void onItemClick(int position, App_shop_mystoreItemModel item, View view)
            {
                Intent intent = new Intent(getOwnerActivity(), LiveWebViewActivity.class);
                intent.putExtra(LiveWebViewActivity.EXTRA_URL, item.getUrl());
                getOwnerActivity().startActivity(intent);
            }
        });
        adapter.setClickPushListener(new SDItemClickCallback<App_shop_mystoreItemModel>()
        {
            @Override
            public void onItemClick(int position, App_shop_mystoreItemModel item, View view)
            {
                String id = item.getId();
                if (!TextUtils.isEmpty(id))
                {
                    requestInterface(id);
                }
            }
        });
    }

    public void refreshViewer()
    {
        page = 1;
        requestInterface(false);
    }

    private void loadMoreViewer()
    {
        if (pageModel.getHas_next() == 1)
        {
            page++;
            requestInterface(true);
        } else
        {
            SDToast.showToast("没有更多数据了");
            list.onRefreshComplete();
        }
    }

    private void requestInterface(final boolean isLoadMore)
    {
        ShopCommonInterface.requestShopPodcastMyStore(mCreaterId, page, new AppRequestCallback<App_shop_mystoreActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    if (actModel.getPage() != null)
                    {
                        pageModel = actModel.getPage();
                    }

                    SDViewUtil.updateAdapterByList(listModel, actModel.getList(), adapter, isLoadMore);
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

    private void requestInterface(String id)
    {
        ShopCommonInterface.requestShopPushPodcastGoods(id, new AppRequestCallback<App_shop_goodsActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    dismiss();
                }
            }
        });
    }
}
