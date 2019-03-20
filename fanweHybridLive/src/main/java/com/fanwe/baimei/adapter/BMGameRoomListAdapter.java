package com.fanwe.baimei.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.baimei.adapter.holder.BMGameRoomListViewHolder;
import com.fanwe.baimei.model.BMGameRoomListModel;
import com.fanwe.library.adapter.SDRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.live.R;

/**
 * 包名: com.fanwe.baimei.adapter
 * 描述: 游戏房间列表适配器
 * 作者: Su
 * 创建时间: 2017/5/18 14:31
 **/
public class BMGameRoomListAdapter extends SDRecyclerAdapter<BMGameRoomListModel>
{
    private BMGameRoomListAdapterCallback mCallback;

    public BMGameRoomListAdapter(Activity activity)
    {
        super(activity);
    }

    @Override
    public SDRecyclerViewHolder<BMGameRoomListModel> onCreateVHolder(ViewGroup parent, int viewType)
    {
        return new BMGameRoomListViewHolder(parent, R.layout.bm_view_holder_game_room_list);
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<BMGameRoomListModel> holder, int position, BMGameRoomListModel model)
    {
        BMGameRoomListViewHolder viewHolder = (BMGameRoomListViewHolder) holder;
        viewHolder.onBindData(position, model);
        viewHolder.setCallback(new BMGameRoomListViewHolder.BMGameRoomListViewHolderCallback()
        {

            @Override
            public void onEnterClick(View view, BMGameRoomListModel model, int position)
            {
                getCallback().onEnterClick(view, model, position);
            }
        });
    }

    private BMGameRoomListAdapterCallback getCallback()
    {
        if (mCallback == null)
        {
            mCallback = new BMGameRoomListAdapterCallback()
            {

                @Override
                public void onEnterClick(View view, BMGameRoomListModel model, int position)
                {

                }
            };
        }
        return mCallback;
    }

    public void setCallback(BMGameRoomListAdapterCallback callback)
    {
        mCallback = callback;
    }

    public interface BMGameRoomListAdapterCallback extends BMGameRoomListViewHolder.BMGameRoomListViewHolderCallback
    {

    }

}
