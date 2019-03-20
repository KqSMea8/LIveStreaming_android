package com.fanwe.baimei.adapter.holder;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.baimei.model.BMGameFriendsRoomModel;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;

/**
 * 包名: com.fanwe.baimei.adapter.holder
 * 描述:
 * 作者: Su
 * 创建时间: 2017/5/19 17:35
 **/
public class BMGameFriendsRoomViewHolder extends SDRecyclerViewHolder<BMGameFriendsRoomModel>
{
    private ImageView mThumbImageView;
    private TextView mNameTextView;


    public BMGameFriendsRoomViewHolder(ViewGroup parent)
    {
        super(parent, R.layout.bm_view_holder_game_friends_room_list);
    }

    @Override
    public void onBindData(int position, BMGameFriendsRoomModel model)
    {
        GlideUtil.load(model.getLive_image()).into(getThumbImageView());
        SDViewBinder.setTextView(getNameTextView(),model.getNick_name());
    }

    private ImageView getThumbImageView(){
        if (mThumbImageView == null) {
            mThumbImageView = (ImageView)findViewById(R.id.iv_thumb_game_room_friends);
        }
        return mThumbImageView;
    }

    private TextView getNameTextView(){
        if (mNameTextView == null) {
            mNameTextView = (TextView)findViewById(R.id.tv_name_game_room_friends);
        }
        return mNameTextView;
    }
}
