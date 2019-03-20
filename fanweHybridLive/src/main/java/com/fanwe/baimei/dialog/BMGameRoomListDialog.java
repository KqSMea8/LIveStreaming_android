package com.fanwe.baimei.dialog;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fanwe.baimei.adapter.BMGameRoomListAdapter;
import com.fanwe.baimei.common.BMCommonInterface;
import com.fanwe.baimei.model.BMGameRoomListModel;
import com.fanwe.baimei.model.BMGameRoomListResponseModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;

import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

/**
 * 包名: com.fanwe.baimei.dialog
 * 描述: 游戏房间列表弹窗
 * 作者: Su
 * 创建时间: 2017/5/18 14:14
 **/
public class BMGameRoomListDialog extends BMBaseCommonDialog
{
    private String mGameId="";
    private RecyclerView mRoomRecyclerView;
    private BMGameRoomListAdapter mRoomAdapter;
    private Button mRefreshButton;
    private BMGameRoomListDialogCallback mBMGameRoomListDialogCallback;


    public BMGameRoomListDialog(Activity activity)
    {
        super(activity);

        paddingLeft(SDViewUtil.getScreenWidthPercent(0.05f));
        paddingRight(SDViewUtil.getScreenWidthPercent(0.05f));

        AlphaInAnimationAdapter animAdapter = new AlphaInAnimationAdapter(getRoomAdapter());
        animAdapter.setFirstOnly(false);

        getRoomRecyclerView().setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        getRoomRecyclerView().setAdapter(animAdapter );

        getRoomAdapter().setCallback(new BMGameRoomListAdapter.BMGameRoomListAdapterCallback()
        {
            @Override
            public void onEnterClick(View view, BMGameRoomListModel model, int position)
            {
                getBMGameRoomListDialogCallback().onEnterClick(view, model, position);
            }
        });

        getRefreshButton().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
              requestGameRoomList(mGameId);
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        requestGameRoomList(mGameId);
    }
    /**
     * 请求指定游戏房间列表
     */
    private void requestGameRoomList(  String gameId)
    {
        BMCommonInterface.requestGameRoomList(gameId, new AppRequestCallback<BMGameRoomListResponseModel>()
        {
            @Override
            protected void onStart()
            {
                super.onStart();
            }

            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (1 == actModel.getStatus())
                {
                    setData(actModel.getList());
                } else
                {
                    SDToast.showToast(actModel.getError());
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
            }
        });

    }

    private void setData(List<BMGameRoomListModel> list)
    {
        getRoomAdapter().setData(list);
        getRoomAdapter().notifyDataSetChanged();
        getRoomRecyclerView().scrollToPosition(0);
    }

    public String getGameId()
    {
        return mGameId;
    }

    public void setGameId(String gameId)
    {
        mGameId = gameId;
    }

    @Override
    protected int getContentLayoutId()
    {
        return R.layout.bm_dialog_game_room_list;
    }

    private BMGameRoomListAdapter getRoomAdapter()
    {
        if (mRoomAdapter == null)
        {
            mRoomAdapter = new BMGameRoomListAdapter(getOwnerActivity());
        }
        return mRoomAdapter;
    }

    private RecyclerView getRoomRecyclerView()
    {
        if (mRoomRecyclerView == null)
        {
            mRoomRecyclerView = (RecyclerView) findViewById(R.id.rv_game_room_list);
        }
        return mRoomRecyclerView;
    }

    private Button getRefreshButton()
    {
        if (mRefreshButton == null)
        {
            mRefreshButton = (Button) findViewById(R.id.btn_refresh_list);
        }
        return mRefreshButton;
    }

    private BMGameRoomListDialogCallback getBMGameRoomListDialogCallback(){
        if (mBMGameRoomListDialogCallback == null)
        {
            mBMGameRoomListDialogCallback=new BMGameRoomListDialogCallback(){

                @Override
                public void onEnterClick(View view, BMGameRoomListModel model, int position)
                {

                }
            };
        }
        return mBMGameRoomListDialogCallback;
    }

    public void setBMGameRoomListDialogCallback(BMGameRoomListDialogCallback callback){
        this.mBMGameRoomListDialogCallback=callback;
    }

    public interface BMGameRoomListDialogCallback extends BMGameRoomListAdapter.BMGameRoomListAdapterCallback{
    }


}
