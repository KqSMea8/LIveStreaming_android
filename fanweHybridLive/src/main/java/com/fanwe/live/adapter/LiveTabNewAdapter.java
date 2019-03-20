package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.appview.ItemLiveTabNewSingle;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.model.LiveRoomModel;

import java.util.List;

public class LiveTabNewAdapter extends SDSimpleRecyclerAdapter<LiveRoomModel>
{

    public LiveTabNewAdapter(List<LiveRoomModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<LiveRoomModel> holder, int position, LiveRoomModel model)
    {
        ItemLiveTabNewSingle item0 = holder.get(R.id.item_view0);
        item0.setModel(model);
        item0.setOnClickListener(clickHeadImageListener);
    }

    @Override
    public int getLayoutId(ViewGroup parent, int viewType)
    {
        return R.layout.item_live_tab_new;
    }

    private View.OnClickListener clickHeadImageListener = new OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            ItemLiveTabNewSingle view = (ItemLiveTabNewSingle) v;
            LiveRoomModel model = view.getModel();
            if (model == null)
            {
                SDToast.showToast("数据为空");
                return;
            }
            AppRuntimeWorker.joinRoom(model, getActivity());
        }
    };

}
