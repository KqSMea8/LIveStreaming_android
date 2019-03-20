package com.fanwe.shop.appview;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.auction.model.PageModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveFloatViewWebViewActivity;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.view.SDProgressPullToRefreshListView;
import com.fanwe.shop.adapter.ShopMyStoreAdapter;
import com.fanwe.shop.common.ShopCommonInterface;
import com.fanwe.shop.dialog.ShopMyStoreDialog;
import com.fanwe.shop.model.App_pai_user_open_goods_urlActModel;
import com.fanwe.shop.model.App_shop_mystoreActModel;
import com.fanwe.shop.model.App_shop_mystoreItemModel;
import com.fanwe.shop.model.App_shop_push_goodsActModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/18.
 */

public class ShopMyStoreView extends BaseAppView
{
    @ViewInject(R.id.lv_store)
    private SDProgressPullToRefreshListView listView;
    @ViewInject(R.id.tv_no_content)
    private TextView tv_no_content;
    @ViewInject(R.id.btn_store)
    private Button btn_store;

    private ShopMyStoreDialog shopMystoreDialog;
    private String createrId;//主播Id
    private ShopMyStoreAdapter adapter;
    private List<App_shop_mystoreItemModel> listModel;
    private PageModel pageModel = new PageModel();
    private int page = 1;
    private boolean mIsCreater;

    public ShopMyStoreView(Context context, String id, ShopMyStoreDialog shopMystoreDialog, boolean isCreater)
    {
        super(context);
        this.createrId = id;
        this.shopMystoreDialog = shopMystoreDialog;
        this.mIsCreater = isCreater;
        init();
    }

    protected void init()
    {
        setContentView(R.layout.view_shop_mystore);
        setBtn_storeShow();
        register();
        bindAdapterPodCast();
        refreshViewer();
    }

    private void setBtn_storeShow()
    {
        SDViewUtil.setGone(btn_store);
        boolean is_show_btn_shop = getResources().getBoolean(R.bool.is_show_btn_shop);
        if (is_show_btn_shop)
        {
            SDViewUtil.setVisible(btn_store);
        }
    }

    private void register()
    {
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
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

        btn_store.setOnClickListener(this);
    }

    private void bindAdapterPodCast()
    {
        listModel = new ArrayList<App_shop_mystoreItemModel>();
        adapter = new ShopMyStoreAdapter(listModel, getActivity());
        adapter.setmIsCreater(mIsCreater);
        listView.setAdapter(adapter);

        /**
         * 跳转至详情页
         */
        adapter.setItemClickCallback(new SDItemClickCallback<App_shop_mystoreItemModel>()
        {
            @Override
            public void onItemClick(int position, App_shop_mystoreItemModel item, View view)
            {
                String itemId = item.getId();
                requestToDetail(itemId);
            }
        });

        /**
         * 推送
         */
        adapter.setClickPushListener(new SDItemClickCallback<App_shop_mystoreItemModel>()
        {
            @Override
            public void onItemClick(int position, App_shop_mystoreItemModel item, View view)
            {
                requestPush(item.getId());
            }
        });

    }

    /**
     * 跳转至详情页
     */
    private void requestToDetail(String goods_id)
    {
        ShopCommonInterface.requestShopGoodsUrl(goods_id, createrId, new AppRequestCallback<App_pai_user_open_goods_urlActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.getStatus() == 1)
                {
                    clickToWebView(actModel.getUrl());
                }
            }
        });
    }

    /**
     * 跳转商品详情页
     */
    private void clickToWebView(String url)
    {
        if (!TextUtils.isEmpty(url))
        {
            Intent intent = new Intent(getActivity(), LiveFloatViewWebViewActivity.class);
            intent.putExtra(LiveFloatViewWebViewActivity.EXTRA_URL, url);
            getActivity().startActivity(intent);
        } else
        {
            SDToast.showToast("url为空");
        }
    }

    /**
     * 推送
     */
    private void requestPush(String goodId)
    {
        requestShopGoodsPush(goodId);
    }

    private void requestShopGoodsPush(String goodId)
    {
        ShopCommonInterface.requestShopGoodsPush(Integer.parseInt(createrId), goodId, new AppRequestCallback<App_shop_push_goodsActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.getStatus() == 1)
                {
                    SDToast.showToast("推送成功");
                    shopMystoreDialog.dismiss();
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
            }
        });
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
            listView.onRefreshComplete();
        }
    }

    /**
     * 获取商品列表数据
     */
    private void request(final boolean isLoadMore)
    {
        ShopCommonInterface.requestAuctionShopMystore(page, Integer.parseInt(createrId), new AppRequestCallback<App_shop_mystoreActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.getStatus() == 1)
                {
                    if (actModel.getList() != null && actModel.getList().size() > 0)
                    {
                        showContent();
                        pageModel = actModel.getPage();
                        SDViewUtil.updateAdapterByList(listModel, actModel.getList(), adapter, isLoadMore);
                    } else
                    {
                        showNoContent();
                    }
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                listView.onRefreshComplete();
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
            }
        });

    }

    private void showContent()
    {
        SDViewUtil.setVisible(listView);
        SDViewUtil.setGone(tv_no_content);
    }

    private void showNoContent()
    {
        SDViewUtil.setVisible(tv_no_content);
        SDViewUtil.setGone(listView);
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.btn_store:
                clickBtnStore();
                break;
        }
    }

    /**
     * 进入主播星店
     */
    private void clickBtnStore()
    {
        shopMystoreDialog.dismiss();

        String url_podcast_goods = AppRuntimeWorker.getUrl_podcast_goods();
        Intent intent = new Intent(getActivity(), LiveFloatViewWebViewActivity.class);
        intent.putExtra(LiveFloatViewWebViewActivity.EXTRA_URL, url_podcast_goods);
        getActivity().startActivity(intent);
    }
}
