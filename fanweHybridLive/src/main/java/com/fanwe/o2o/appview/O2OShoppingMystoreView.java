package com.fanwe.o2o.appview;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.fanwe.auction.model.PageModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.view.SDProgressPullToRefreshListView;
import com.fanwe.o2o.adapter.O2OShoppingMystoreAdapter;
import com.fanwe.o2o.common.O2OShoppingCommonInterface;
import com.fanwe.shop.model.App_shop_mystoreActModel;
import com.fanwe.shop.model.App_shop_mystoreItemModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/18.
 */
public class O2OShoppingMystoreView extends BaseAppView
{
    @ViewInject(R.id.lv_store)
    private SDProgressPullToRefreshListView lv_store;
    private O2OShoppingMystoreAdapter adapter;
    private List<App_shop_mystoreItemModel> listModel;
    private PageModel pageModel = new PageModel();
    private int page = 1;

    public O2OShoppingMystoreView(Context context)
    {
        super(context);
        init();
    }

    public O2OShoppingMystoreView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public O2OShoppingMystoreView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.o2o_view_shopping_mystore);
        register();
        bindAdapter();
        refreshViewer();
    }

    private void register()
    {
        lv_store.setMode(PullToRefreshBase.Mode.BOTH);
        lv_store.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
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

    private void bindAdapter()
    {
        listModel = new ArrayList<App_shop_mystoreItemModel>();
        adapter = new O2OShoppingMystoreAdapter(listModel, getActivity());
        lv_store.setAdapter(adapter);
    }

    /**
     * 获取商品列表数据
     */
    public void refreshViewer()
    {
        page = 1;
        requestShopMystore(false);

    }

    private void loadMoreViewer()
    {
        if (pageModel.getHas_next() == 1)
        {
            page++;
            requestShopMystore(true);
        } else
        {
            lv_store.onRefreshComplete();
            SDToast.showToast("没有更多数据了");
        }
    }

    private void requestShopMystore(final boolean isLoadMore)
    {
        UserModel user = UserModelDao.query();
        if (user == null)
        {
            return;
        }
        O2OShoppingCommonInterface.requestShopMystore(page, Integer.parseInt(user.getUser_id()), new AppRequestCallback<App_shop_mystoreActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
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
                lv_store.onRefreshComplete();
            }
        });
    }

    @Override
    public void onActivityResumed(Activity activity)
    {
        super.onActivityResumed(activity);
        refreshViewer();
    }
}
