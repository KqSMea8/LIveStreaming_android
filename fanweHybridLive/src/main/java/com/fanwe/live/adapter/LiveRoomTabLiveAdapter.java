package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.appview.ItemLiveRoomTabLiveSingle;
import com.fanwe.live.appview.ItemLiveTabNewSingle;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.model.JoinLiveData;
import com.fanwe.live.model.LiveRoomModel;
import com.fanwe.live.model.PlayBackData;

import java.util.List;

/**
 * PC端直播 直播页面 适配器
 */
public class LiveRoomTabLiveAdapter extends SDSimpleAdapter<List<LiveRoomModel>>
{

    public LiveRoomTabLiveAdapter(List<List<LiveRoomModel>> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_live_room_tab_live;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, List<LiveRoomModel> model)
    {
        ItemLiveRoomTabLiveSingle item0 = get(R.id.item_view0, convertView);
        ItemLiveRoomTabLiveSingle item1 = get(R.id.item_view1, convertView);

        item0.setModel(SDCollectionUtil.get(model, 0));
        item1.setModel(SDCollectionUtil.get(model, 1));

//        item0.setOnClickListener(clickHeadImageListener);
//        item1.setOnClickListener(clickHeadImageListener);
    }

    private OnClickListener clickHeadImageListener = new OnClickListener()
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

            if (model.getLive_in() == 1)
            {
                JoinLiveData data = new JoinLiveData();
                data.setCreaterId(model.getUser_id());
                data.setGroupId(model.getGroup_id());
                data.setLoadingVideoImageUrl(model.getLive_image());
                data.setRoomId(model.getRoom_id());
                data.setSdkType(model.getSdk_type());
                AppRuntimeWorker.joinLive(data, getActivity());
            } else if (model.getLive_in() == 3)
            {
                PlayBackData data = new PlayBackData();
                data.setRoomId(model.getRoom_id());
                AppRuntimeWorker.startPlayback(data, getActivity());
            }
        }
    };

}
