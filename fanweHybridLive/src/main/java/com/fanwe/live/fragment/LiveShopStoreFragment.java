package com.fanwe.live.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.fragment.BaseFragment;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.view.SDRecyclerView;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveShopAdapter;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dialog.LiveShopDetailDialog;
import com.fanwe.live.model.ShopModel;
import com.fanwe.live.view.SDProgressPullToRefreshRecyclerView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-12 下午8:33:19 类说明
 */
public class LiveShopStoreFragment extends BaseFragment implements LiveShopAdapter.OnShopItemClickListener, LiveShopDetailDialog.ShopBuyListener {

    private LiveShopAdapter adapter;
    @ViewInject(R.id.lv_shop)
    SDProgressPullToRefreshRecyclerView lv_content;
    LiveShopDetailDialog detailDialog;

    @Override
    protected int onCreateContentView() {
        return R.layout.frag_live_shop;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        x.view().inject(getActivity());
        init();
    }
    private String dimands;
    protected void init() {
        lv_content = (SDProgressPullToRefreshRecyclerView) findViewById(R.id.lv_shop);
        lv_content.getRefreshableView().setGridVertical(2);
        lv_content.setPullToRefreshOverScrollEnabled(false);
        setAdapter();
        initPullToRefresh();
        requestData();
    }

    protected void initPullToRefresh() {
        lv_content.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        lv_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<SDRecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<SDRecyclerView> pullToRefreshBase) {
                requestData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<SDRecyclerView> pullToRefreshBase) {

            }
        });
    }

    private void setAdapter() {
        adapter = new LiveShopAdapter(new ArrayList<ShopModel.MountRuleListBean>(), getActivity());
        adapter.setOnShopItemClickListener(this);
        lv_content.getRefreshableView().setAdapter(adapter);
    }

    private void requestData() {
        showProgressDialog("");
        CommonInterface.requestShopList(new AppRequestCallback<ShopModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.getStatus() == 1) {
                    dimands=actModel.getDiamonds();
                    adapter.updateData(actModel.getMount_rule_list());
                }
            }

            @Override
            protected void onFinish(SDResponse resp) {
                lv_content.onRefreshComplete();
                super.onFinish(resp);
                dismissProgressDialog();
            }
        });
    }

    @Override
    public void onClickItemView(ShopModel.MountRuleListBean model) {
        if (null == detailDialog) {
            detailDialog = new LiveShopDetailDialog(getActivity(), model);
            detailDialog.setShopBuyListener(this);
        } else {
            detailDialog.updataData(model);
        }
        detailDialog.updateDimands(dimands);
        detailDialog.showBottom();
    }

    @Override
    public void buyShopSuccess() {
        requestData();
    }
}
