package com.fanwe.live.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fanwe.hybrid.fragment.BaseFragment;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.view.SDRecyclerView;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveMyPackAdapter;
import com.fanwe.live.adapter.LiveShopAdapter;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dialog.LiveShopDetailDialog;
import com.fanwe.live.model.MyPackListModel;
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
public class LiveShopMyPackFragment extends BaseFragment implements LiveMyPackAdapter.ButtonClickListener{

    private LiveMyPackAdapter adapter;
    @ViewInject(R.id.lv_shop)
    SDProgressPullToRefreshRecyclerView lv_content;

    @Override
    protected int onCreateContentView() {
        return R.layout.frag_live_mypack;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        x.view().inject(getActivity());
        init();
    }

    protected void init() {
        lv_content = (SDProgressPullToRefreshRecyclerView) findViewById(R.id.lv_pack);
        lv_content.getRefreshableView().setGridVertical(1);
        lv_content.setPullToRefreshOverScrollEnabled(false);
        setAdapter();
        initPullToRefresh();
        requestData();
    }
    private void requestData() {
        CommonInterface.requestMyPackList(new AppRequestCallback<MyPackListModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.getStatus()==1) {
                    adapter.updateData(actModel.getMounts_list());
                }
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
                lv_content.onRefreshComplete();
            }
        });
    }
    private void setMount(String mountid) {
        CommonInterface.requestSetMount(mountid,new AppRequestCallback<BaseActModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.getStatus()==1) {
                    Toast.makeText(getActivity(),"设置成功！",Toast.LENGTH_SHORT).show();
                    requestData();
                }
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
            }
        });
    }
    protected void initPullToRefresh() {
        lv_content.setMode(PullToRefreshBase.Mode.PULL_DOWN_TO_REFRESH);
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
        adapter = new LiveMyPackAdapter(getActivity());
        adapter.setButtonClickListener(this);
        lv_content.getRefreshableView().setAdapter(adapter);
    }


    @Override
    public void btnClick(MyPackListModel.MountsListBean model) {
        setMount(model.getMount_id());
    }
}
