package com.fanwe.baimei.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.baimei.adapter.holder.BMGameFriendsRoomViewHolder;
import com.fanwe.baimei.model.BMGameFriendsRoomModel;
import com.fanwe.library.adapter.SDRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.live.R;

/**
 * 包名: com.fanwe.baimei.adapter
 * 描述: 好友游戏房间列表适配器
 * 作者: Su
 * 创建时间: 2017/5/19 17:35
 **/
public abstract class BMGameFriendsRoomAdapter extends SDRecyclerAdapter<BMGameFriendsRoomModel>
{
    public BMGameFriendsRoomAdapter(Activity activity)
    {
        super(activity);
    }

    public abstract void onItemClick(View view, int position, BMGameFriendsRoomModel model);


    @Override
    public SDRecyclerViewHolder<BMGameFriendsRoomModel> onCreateVHolder(ViewGroup parent, int viewType)
    {
        return new BMGameFriendsRoomViewHolder(parent);
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<BMGameFriendsRoomModel> holder, final int position, final BMGameFriendsRoomModel model)
    {
        BMGameFriendsRoomViewHolder viewHolder = (BMGameFriendsRoomViewHolder) holder;

        viewHolder.onBindData(position, model);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onItemClick(v, position, model);
            }
        });
    }
}
