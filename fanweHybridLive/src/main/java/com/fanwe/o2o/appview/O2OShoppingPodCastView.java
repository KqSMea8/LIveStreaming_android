package com.fanwe.o2o.appview;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.fanwe.auction.model.PageModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveWebViewActivity;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.view.SDProgressPullToRefreshListView;
import com.fanwe.o2o.adapter.O2OShoppingPodCastAdapter;
import com.fanwe.o2o.common.O2OShoppingCommonInterface;
import com.fanwe.o2o.dialog.O2OShoppingPodCastDialog;
import com.fanwe.shop.common.ShopCommonInterface;
import com.fanwe.shop.model.App_shop_mystoreActModel;
import com.fanwe.shop.model.App_shop_mystoreItemModel;
import com.fanwe.shop.model.App_shop_push_goodsActModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物直播商品列表
 * Created by Administrator on 2016/9/18.
 */
public class O2OShoppingPodCastView extends BaseAppView
{
    @ViewInject(R.id.list_pod_cast)
    private SDProgressPullToRefreshListView listView;
    @ViewInject(R.id.img_shop_cart)
    private ImageView img_shop_cart;
    @ViewInject(R.id.btn_store)
    private Button btn_store;

    private O2OShoppingPodCastAdapter adapter;
    private List<App_shop_mystoreItemModel> listModel;
    private String createrId;//主播Id
    private O2OShoppingPodCastDialog shoppingPodCastDialog;
    private String url;
    private int page;
    private PageModel pageModel = new PageModel();

    public O2OShoppingPodCastView(Context context, String id, O2OShoppingPodCastDialog dialog)
    {
        super(context);
        createrId = id;
        this.shoppingPodCastDialog = dialog;
        init();
    }

    public O2OShoppingPodCastView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public O2OShoppingPodCastView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.view_pod_cast);
        register();
        bindAdapterPodCast();
        refreshViewer();
    }

    private void register()
    {
        UserModel dao = UserModelDao.query();
        if (!dao.getUser_id().equals(createrId))
        {
            btn_store.setVisibility(VISIBLE);
        }

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

        img_shop_cart.setOnClickListener(this);
        btn_store.setOnClickListener(this);
    }

    private void bindAdapterPodCast()
    {
        listModel = new ArrayList<App_shop_mystoreItemModel>();
        adapter = new O2OShoppingPodCastAdapter(listModel, getActivity(), createrId);
        listView.setAdapter(adapter);

        /**
         * 跳转至详情页
         */
        adapter.setClickToDetailListener(new SDItemClickCallback<App_shop_mystoreItemModel>()
        {
            @Override
            public void onItemClick(int position, App_shop_mystoreItemModel item, View view)
            {
                String itemUrl = item.getUrl();
                if (!TextUtils.isEmpty(itemUrl))
                {
                    Intent intent = new Intent(getActivity(), LiveWebViewActivity.class);
                    intent.putExtra(LiveWebViewActivity.EXTRA_URL, itemUrl);
                    intent.putExtra(LiveWebViewActivity.EXTRA_IS_SHOW_TITLE, false);
                    getActivity().startActivity(intent);
                } else
                {
                    SDToast.showToast("url为空");
                }
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
                String goodId = item.getId();
                requestPush(Integer.parseInt(goodId));
            }
        });

    }

    /**
     * 推送
     */
    private void requestPush(int goodId)
    {
        requestShopGoodsPush(goodId);
    }

    private void requestShopGoodsPush(int goodId)
    {
        ShopCommonInterface.requestShopGoodsPush(Integer.parseInt(createrId), String.valueOf(goodId), new AppRequestCallback<App_shop_push_goodsActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.getStatus() == 1)
                {
                    SDToast.showToast("推送成功");
                    shoppingPodCastDialog.dismiss();
                }
            }
        });
    }

    /**
     * 获取商品列表数据
     */
    private void refreshViewer()
    {
        page = 1;
        requestShopMyStore(false);
    }

    private void loadMoreViewer()
    {
        if (pageModel.getHas_next() == 1)
        {
            page++;
            requestShopMyStore(true);
        } else
        {
            listView.onRefreshComplete();
            SDToast.showToast("没有更多数据了");
        }
    }

    private void requestShopMyStore(final boolean isLoadMore)
    {
        O2OShoppingCommonInterface.requestShopMystore(page, Integer.parseInt(createrId), new AppRequestCallback<App_shop_mystoreActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    url = actModel.getUrl();
                    if (actModel.getList() != null && actModel.getList().size() > 0)
                    {
                        pageModel = actModel.getPage();
                        SDViewUtil.updateAdapterByList(listModel, actModel.getList(), adapter, isLoadMore);
                    }
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                listView.onRefreshComplete();
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.img_shop_cart:
                SDToast.showToast("加入购物车！");
                break;
            case R.id.btn_store:
                shoppingPodCastDialog.dismiss();
                clickToPodCast(url);
                break;
        }
    }

    /**
     * 进入主播星店
     */
    private void clickToPodCast(String url)
    {
        Intent intent = new Intent(getActivity(), LiveWebViewActivity.class);
        intent.putExtra(LiveWebViewActivity.EXTRA_URL, url);
        intent.putExtra(LiveWebViewActivity.EXTRA_IS_SHOW_TITLE, false);
        getActivity().startActivity(intent);
    }
}
