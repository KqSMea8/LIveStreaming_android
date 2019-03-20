package com.fanwe.baimei.appview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.baimei.adapter.BMGameFriendsRoomAdapter;
import com.fanwe.baimei.model.BMGameFriendsRoomModel;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.view.SDAppView;
import com.fanwe.live.R;

import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

/**
 * 包名: com.fanwe.baimei.appview
 * 描述:
 * 作者: Su
 * 创建时间: 2017/5/19 17:37
 **/
public class BMGameFriendsRoomView extends SDAppView
{
    private RecyclerView mRecyclerView;
    private BMGameFriendsRoomAdapter mAdapter;
    private BMGameFriendsRoomViewCallback mBMGameFriendsRoomViewCallback;


    public BMGameFriendsRoomView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initBMGameFriendsRoomView(context);
    }

    public BMGameFriendsRoomView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initBMGameFriendsRoomView(context);
    }

    public BMGameFriendsRoomView(Context context)
    {
        super(context);
        initBMGameFriendsRoomView(context);

        AlphaInAnimationAdapter animAdapter = new AlphaInAnimationAdapter(getAdapter());
        animAdapter.setFirstOnly(false);

        getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        getRecyclerView().setAdapter(animAdapter);
    }

    private void initBMGameFriendsRoomView(Context context)
    {
        setContentView(R.layout.bm_view_game_friends_room);
    }

    private RecyclerView getRecyclerView()
    {
        if (mRecyclerView == null)
        {
            mRecyclerView = (RecyclerView) findViewById(R.id.rv_bm_view_game_friends_room);
        }
        return mRecyclerView;
    }

    private BMGameFriendsRoomAdapter getAdapter()
    {
        if (mAdapter == null)
        {
            mAdapter = new BMGameFriendsRoomAdapter(getActivity())
            {
                @Override
                public void onItemClick(View view, int position, BMGameFriendsRoomModel model)
                {
                    getBMGameFriendsRoomViewCallback().onItemClick(view, position, model);
                }
            };
        }
        return mAdapter;
    }

    public void setData(List<BMGameFriendsRoomModel> data)
    {
        getAdapter().setData(data);
        getAdapter().notifyDataSetChanged();
        getRecyclerView().scrollToPosition(0);
    }

     private BMGameFriendsRoomViewCallback getBMGameFriendsRoomViewCallback(){
         if (mBMGameFriendsRoomViewCallback == null)
         {
             mBMGameFriendsRoomViewCallback=new BMGameFriendsRoomViewCallback(){
                 @Override
                 public void onItemClick(View view, int position, BMGameFriendsRoomModel model)
                 {

                 }
             };
         }
         return mBMGameFriendsRoomViewCallback;
     }

     public void setBMGameFriendsRoomViewCallback(BMGameFriendsRoomViewCallback callback){
         this.mBMGameFriendsRoomViewCallback=callback;
     }

     public interface BMGameFriendsRoomViewCallback{
         void onItemClick(View view, int position, BMGameFriendsRoomModel model);
     }

}
