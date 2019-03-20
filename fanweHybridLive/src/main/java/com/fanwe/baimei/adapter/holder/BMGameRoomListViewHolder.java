package com.fanwe.baimei.adapter.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.baimei.model.BMGameRoomListModel;
import com.fanwe.baimei.util.ViewUtil;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;

import java.util.Random;

/**
 * 包名: com.fanwe.baimei.adapter.holder
 * 描述:
 * 作者: Su
 * 创建时间: 2017/5/18 14:33
 **/
public class BMGameRoomListViewHolder extends SDRecyclerViewHolder<BMGameRoomListModel>
{
    private TextView mRoomNumberTextView, mRoomNameTextView, mAudienceCountTextView;
    private ImageView mLiveImageView;
    private Button mEnterButton;
    private BMGameRoomListViewHolderCallback mCallback;


    public BMGameRoomListViewHolder(ViewGroup parent, int layoutId)
    {
        super(parent, layoutId);
    }

    @Override
    public void onBindData(final int position, final BMGameRoomListModel model)
    {
        getEnterButton().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getCallback().onEnterClick(v, model, position);
            }
        });

        ViewUtil.setViewVisibleAndGone(getRoomNumberTextView(), getLiveImageView(), model.getLive_in().equals("1"));
        getAudienceCountTextView().setText(model.getWatch_number());
        getRoomNumberTextView().setText(model.getRoom_id());
        getRoomNameTextView().setText(model.getNick_name());
    }

    private TextView getRoomNumberTextView()
    {
        if (mRoomNumberTextView == null)
        {
            mRoomNumberTextView = (TextView) findViewById(R.id.tv_room_number);
        }
        return mRoomNumberTextView;
    }

    private TextView getRoomNameTextView()
    {
        if (mRoomNameTextView == null)
        {
            mRoomNameTextView = (TextView) findViewById(R.id.tv_room_name);
        }
        return mRoomNameTextView;
    }

    private TextView getAudienceCountTextView()
    {
        if (mAudienceCountTextView == null)
        {
            mAudienceCountTextView = (TextView) findViewById(R.id.tv_audience_count);
        }
        return mAudienceCountTextView;
    }

    private ImageView getLiveImageView()
    {
        if (mLiveImageView == null)
        {
            mLiveImageView = (ImageView) findViewById(R.id.iv_live);
        }
        return mLiveImageView;
    }

    private Button getEnterButton()
    {
        if (mEnterButton == null)
        {
            mEnterButton = (Button) findViewById(R.id.btn_enter);
        }
        return mEnterButton;
    }

    private BMGameRoomListViewHolderCallback getCallback()
    {
        if (mCallback == null)
        {
            mCallback = new BMGameRoomListViewHolderCallback()
            {

                @Override
                public void onEnterClick(View view, BMGameRoomListModel model, int position)
                {

                }
            };
        }
        return mCallback;
    }

    public void setCallback(BMGameRoomListViewHolderCallback callback)
    {
        mCallback = callback;
    }

    public interface BMGameRoomListViewHolderCallback
    {
        void onEnterClick(View view, BMGameRoomListModel model, int position);
    }

}
