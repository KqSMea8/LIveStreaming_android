package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.appview.ItemLiveTabCategorySingle;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.model.LiveRoomModel;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */

public class LiveTabCategoryAdapter extends SDSimpleAdapter<LiveRoomModel>
{

    public LiveTabCategoryAdapter(List<LiveRoomModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_live_tab_category;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, LiveRoomModel model)
    {
        ItemLiveTabCategorySingle item0 = get(R.id.item_view0, convertView);
        item0.setModel(model);
        item0.setOnClickListener(clickHeadImageListener);
    }

    private OnClickListener clickHeadImageListener = new OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            ItemLiveTabCategorySingle view = (ItemLiveTabCategorySingle) v;
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
