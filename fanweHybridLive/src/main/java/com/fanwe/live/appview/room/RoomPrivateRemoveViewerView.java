package com.fanwe.live.appview.room;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveFriendsListAdapter;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.Video_private_room_friendsActModel;
import com.fanwe.live.view.SDProgressPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

import java.util.ArrayList;
import java.util.List;

public class RoomPrivateRemoveViewerView extends RoomView implements OnItemClickListener
{
    public RoomPrivateRemoveViewerView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public RoomPrivateRemoveViewerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RoomPrivateRemoveViewerView(Context context)
    {
        super(context);
    }

    private TextView mTvRemove;
    private ImageView mIvClose;
    private LinearLayout ll_no_viewers_tips;
    private SDProgressPullToRefreshListView mListView;

    private List<UserModel> mListUser;
    private List<UserModel> mListSelected;
    private LiveFriendsListAdapter mAdapter;
    private int page = 1;
    private int has_next;
    private Video_private_room_friendsActModel currentModels;//最新获取的观众

    @Override
    protected int onCreateContentView()
    {
        return R.layout.view_room_private_remove_viewer;
    }

    @Override
    protected void onBaseInit()
    {
        super.onBaseInit();
        initView();
        setInterceptTouchEvent(true);
        requestData(false);
    }

    private void initView()
    {
        mTvRemove = find(R.id.tv_remove_viewer_btn);
        mIvClose = find(R.id.iv_remove_viewer_close);
        ll_no_viewers_tips = find(R.id.ll_no_viewers_tips);
        mListView = find(R.id.lv_remove_friends_list);

        mTvRemove.setOnClickListener(this);
        mIvClose.setOnClickListener(this);
        mListUser = new ArrayList<>();
        mListSelected = new ArrayList<>();
        mAdapter = new LiveFriendsListAdapter(mListUser, getActivity(), LiveFriendsListAdapter.MODE_REMOVE_VIEWER);
        mListView.setOnItemClickListener(this);
        mListView.setMode(Mode.BOTH);

        mListView.setOnRefreshListener(new OnRefreshListener2<ListView>()
        {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                requestData(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                requestData(true);
            }
        });
        mListView.setAdapter(mAdapter);
    }

    /**
     * 获取观众数据
     *
     * @param isLoadMore 是否为加载更多
     */
    private void requestData(final boolean isLoadMore)
    {

        if (isLoadMore)
        {
            if (has_next == 1)
            {
                page++;
            } else
            {
                mListView.onRefreshComplete();
                SDToast.showToast("没有更多数据了");
                return;
            }
        } else
        {
            page = 1;
        }
        CommonInterface.requestPrivateRoomFriends(getLiveActivity().getRoomId(), page, new AppRequestCallback<Video_private_room_friendsActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    has_next = actModel.getHas_next();
                    SDViewUtil.updateAdapterByList(mListUser, actModel.getList(), mAdapter, isLoadMore);
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                mListView.onRefreshComplete();
                super.onFinish(resp);
                if (mAdapter.getCount() > 0)
                {
                    SDViewUtil.setGone(ll_no_viewers_tips);
                } else
                {
                    SDViewUtil.setVisible(ll_no_viewers_tips);
                }
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_remove_viewer_btn:
                requestRemoveViewer();
                removeSelf();
                break;
            case R.id.iv_remove_viewer_close:
                removeSelf();
                break;
            default:
                break;
        }
    }

    private void requestRemoveViewer()
    {
        //当前未选中好友
        if (mListSelected.size() == 0)
        {
            return;
        }
        CommonInterface.requestPrivateDropUser(getLiveActivity().getRoomId(), getUserIds(mListSelected), new AppRequestCallback<BaseActModel>()
        {

            @Override
            protected void onSuccess(SDResponse resp)
            {
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int position, long itemId)
    {
        boolean isSelscted = mAdapter.getSelectManager().getItem((int) itemId).isSelected();
//		if(mListSelected.size() == 100 && !isSelscted) {
//			SDToast.showToast("选取人数不能超过100");
//			return;
//		}
        mAdapter.getSelectManager().performClick((int) itemId);
        mListSelected = mAdapter.getSelectManager().getSelectedItems();
        updateView(mListSelected.size());
    }

    /**
     * 更新按钮UI
     *
     * @param size
     */
    private void updateView(int size)
    {
        switch (size)
        {
            //未选中
            case 0:
                mTvRemove.setEnabled(false);
                SDViewUtil.setTextViewColorResId(mTvRemove, R.color.gray);
                break;
            //选中
            default:
                mTvRemove.setEnabled(true);
                SDViewUtil.setTextViewColorResId(mTvRemove, R.color.main_color);
                break;
        }
    }

    /**
     * 获取接口参数所需的 user_id 字符串
     *
     * @param selectedList 选中观众列表
     * @return "user_id_1,user_id_2,user_id_3"
     */
    private String getUserIds(List<UserModel> selectedList)
    {
        List<String> result = new ArrayList<String>();
        for (UserModel model : selectedList)
        {
            result.add(model.getUser_id());
        }
        return TextUtils.join(",", result);
    }

    @Override
    public boolean onBackPressed()
    {
        removeSelf();
        return true;
    }
}
