package com.fanwe.live.dialog;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.library.dialog.SDDialogProgress;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveUserHomeActivity;
import com.fanwe.live.adapter.LiveShouhuAdatper;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.ShouhuListModel;
import com.fanwe.live.view.SDProgressPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

/**
 * 守护列表窗
 */
public class LiveShouhuDialog extends SDDialogBase {
    Activity activity;
    List<ShouhuListModel.ListBean> list=new ArrayList<>();
    String created_id;
    int room_id;
    public LiveShouhuDialog(Activity activity,String created_id,int room_id) {
        super(activity);
        this.activity = activity;
        this.created_id=created_id;
        this.room_id=room_id;
        init();
    }
    private int page = 1;
    private int has_next=0;
    private SDProgressPullToRefreshListView lv_content;
    private LiveShouhuAdatper adapter;
    private View empty;
    private SDItemClickCallback<ShouhuListModel.ListBean> mItemClickCallback;

    private void init() {
        setContentView(R.layout.dialog_live_shouhu);
        paddings(0);
        setCanceledOnTouchOutside(true);
        lv_content = (SDProgressPullToRefreshListView) findViewById(R.id.lv_content);
        empty = findViewById(R.id.empty);
        setGrativity(Gravity.BOTTOM);
        requestShouhuList(false);
        setAdapter();
    }

    private Activity getActivity() {
        return activity;
    }

    public void setItemClickCallback(SDItemClickCallback<ShouhuListModel.ListBean> itemClickCallback) {
        mItemClickCallback = itemClickCallback;
    }

    private void initPullToRefresh()
    {
        lv_content.setMode(PullToRefreshBase.Mode.BOTH);
        lv_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
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
    public void refreshViewer()
    {
        page = 1;
        requestShouhuList(false);
    }

    private void loadMoreViewer()
    {
        if (has_next == 1)
        {
            page++;
            requestShouhuList(true);
        } else
        {
            SDToast.showToast("没有更多数据了");
            lv_content.onRefreshComplete();
        }
    }
    /**
     * 请求pk列表
     */
    private void requestShouhuList(final boolean isLoadMore)
    {
        CommonInterface.requestShouhuList(created_id,room_id, new AppRequestCallback<ShouhuListModel>()
        {
            private SDDialogProgress dialog = new SDDialogProgress(getActivity());
            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                dialog.dismiss();
                lv_content.onRefreshComplete();
            }

            @Override
            protected void onStart()
            {
                super.onStart();
                dialog.setTextMsg("");
                dialog.show();
            }

            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.getStatus() == 1)
                {
                    has_next = actModel.getHas_next();
                    page=actModel.getPage();
                    list.clear();
                    list.addAll(actModel.getList());
                    adapter.notifyDataSetChanged();
                }
                dialog.dismiss();
            }
        });
    }
    private void setAdapter() {
        adapter = new LiveShouhuAdatper(list, getActivity());
        lv_content.setAdapter(adapter);
        lv_content.setEmptyView(empty);
        initPullToRefresh();
        adapter.setItemClickCallback(new SDItemClickCallback<ShouhuListModel.ListBean>() {
            @Override
            public void onItemClick(int position, ShouhuListModel.ListBean item, View view) {
                Intent intent = new Intent(getActivity(), LiveUserHomeActivity.class);
                intent.putExtra(LiveUserHomeActivity.EXTRA_USER_ID, item.getUser_id());
                intent.putExtra(LiveUserHomeActivity.EXTRA_USER_IMG_URL, item.getHead_image());
                getActivity().startActivity(intent);
            }
        });
    }

}
