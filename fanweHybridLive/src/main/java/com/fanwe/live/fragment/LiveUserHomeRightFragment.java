package com.fanwe.live.fragment;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.view.SDRecyclerView;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.view.SDProgressPullToRefreshRecyclerView;
import com.fanwe.shortvideo.adapter.LiveTabShortVideoAdapter;
import com.fanwe.shortvideo.model.ShortVideoListModel;
import com.fanwe.shortvideo.model.ShortVideoModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-11 下午3:30:11 类说明
 */
public class LiveUserHomeRightFragment extends LiveUserHomeBaseFragment {
    public static final String TAG = "LiveUserHomeRightFragment";

    private SDProgressPullToRefreshRecyclerView lv_content;
    private List<ShortVideoModel> listModel = new ArrayList<>();

    private LiveTabShortVideoAdapter adapter;
    private int page = 0;
    private UserModel user;
    private boolean has_next = true;


    @Override
    protected int onCreateContentView() {
        return R.layout.frag_user_home_right;
    }

    @Override
    protected void init() {
        super.init();
        lv_content = (SDProgressPullToRefreshRecyclerView) findViewById(R.id.right_short_video_content);
        lv_content.getRefreshableView().setGridVertical(2);
        user = app_user_homeActModel.getUser();
        setAdapter();
        initPullToRefresh();
        requestData(user.getUser_id());

    }

    private void requestData(String user_id) {
        CommonInterface.requestShortVideoList(page, user_id, new AppRequestCallback<ShortVideoListModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.isOk()) {
                    listModel = actModel.getList();
                    if(page==0) {
                        adapter.updateData(listModel);
                    }else {
                        adapter.appendData(listModel);
                    }
                    has_next = listModel.size() < 20 ? false : true;
                }
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
            }
        });
    }

    private void initPullToRefresh() {
        lv_content.setMode(PullToRefreshBase.Mode.BOTH);
        lv_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<SDRecyclerView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<SDRecyclerView> refreshView) {
                page = 0;
                requestData(user.getUser_id());
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<SDRecyclerView> refreshView) {
                if (has_next) {
                    page++;
                    requestData(user.getUser_id());
                } else {
                    SDToast.showToast("没有更多数据了");
                    lv_content.onRefreshComplete();
                }
            }
        });
    }

    private void setAdapter() {
        if (user != null) {
            adapter = new LiveTabShortVideoAdapter(new ArrayList<ShortVideoModel>(), getActivity());
//        mRecycycleView.setLayoutManager(new GridLayoutManager(this.getContext(),2));
            lv_content.getRefreshableView().setAdapter(adapter);
        }
    }

}
