package com.fanwe.shortvideo.fragment;

import com.fanwe.hybrid.fragment.BaseFragment;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.title.SDTitleSimple;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.view.SDRecyclerView;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.view.SDProgressPullToRefreshRecyclerView;
import com.fanwe.shortvideo.activity.MyVideoListActivity;
import com.fanwe.shortvideo.adapter.LiveTabShortVideoAdapter;
import com.fanwe.shortvideo.model.ShortVideoListModel;
import com.fanwe.shortvideo.model.ShortVideoModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

/**
 * 小视频
 *
 * @author wxy
 */
public class LiveTabShortVideoFragment extends BaseFragment {

    private SDTitleSimple tv_title;
    private SDProgressPullToRefreshRecyclerView lv_content;
    private LiveTabShortVideoAdapter adapter;
    private List<ShortVideoModel> listModel = new ArrayList<>();
    private int tag = 0;
    private boolean isComeFromUser = false;
    private int page = 0;
    private boolean has_next = true;

    @Override
    protected int onCreateContentView() {
        return R.layout.fragment_live_tab_shortvideo;
    }

    @Override
    protected void init() {
        super.init();
        tv_title = (SDTitleSimple) findViewById(R.id.title);
        String comeFrom = getArguments().getString("comeFrom");
        if (MyVideoListActivity.class.getSimpleName().equals(comeFrom)) {
            tv_title.setLeftImageLeft(R.drawable.ic_arrow_left_white);
            tv_title.initRightItem(1);
            tv_title.setMiddleTextBot("我的小视频");
            tv_title.getItemRight(0).setTextBot("编辑");
            isComeFromUser = true;
        } else {
            tv_title.setMiddleTextBot("小视频");
            isComeFromUser = false;
        }

        tv_title.setmListener(new SDTitleSimple.SDTitleSimpleListener() {
            @Override
            public void onCLickLeft_SDTitleSimple(SDTitleItem v) {
                getActivity().finish();
            }

            @Override
            public void onCLickMiddle_SDTitleSimple(SDTitleItem v) {

            }

            @Override
            public void onCLickRight_SDTitleSimple(SDTitleItem v, int index) {
                if (tag == 0) {
                    tv_title.getItemRight(0).setTextBot("完成");
                    tag = 1;
                } else {
                    tv_title.getItemRight(0).setTextBot("编辑");
                    tag = 0;
                }
                adapter.setTag(tag);

            }
        });

        lv_content = (SDProgressPullToRefreshRecyclerView) findViewById(R.id.lv_short_video_content);
        lv_content.getRefreshableView().setGridVertical(2);
        setAdapter();
        initPullToRefresh();
        requestData();
    }

    private void setAdapter() {
        adapter = new LiveTabShortVideoAdapter(new ArrayList<ShortVideoModel>(), getActivity());
        lv_content.getRefreshableView().setAdapter(adapter);
    }

    private void initPullToRefresh() {
        lv_content.setMode(PullToRefreshBase.Mode.BOTH);
        lv_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<SDRecyclerView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<SDRecyclerView> refreshView) {
                page = 0;
                requestData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<SDRecyclerView> refreshView) {
                if (has_next) {
                    page++;
                    requestData();
                } else {
                    SDToast.showToast("没有更多数据了");
                    lv_content.onRefreshComplete();
                }
            }
        });
    }


    protected void requestData() {
        String user_id = "";
        if (isComeFromUser) {
            UserModel user = UserModelDao.query();
            if (user == null) {
                return;
            }
            user_id = user.getUser_id();
        }
        CommonInterface.requestShortVideoList(page, user_id, new AppRequestCallback<ShortVideoListModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.isOk()) {
                    listModel = actModel.getList();
                    if (page == 0) {
                        adapter.updateData(listModel);
                    } else {
                        adapter.appendData(listModel);
                    }
                    has_next = listModel.size() < 20 ? false : true;
                }
            }

            @Override
            protected void onFinish(SDResponse resp) {
                lv_content.onRefreshComplete();
                super.onFinish(resp);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        requestData();
    }
}
