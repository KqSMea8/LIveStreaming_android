package com.fanwe.live.appview.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.library.config.SDConfig;
import com.fanwe.library.customview.SDViewPager;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.model.SDTaskRunnable;
import com.fanwe.library.model.ViewRecter;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveTabHotAdapter;
import com.fanwe.live.appview.LiveTabHotHeaderView;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.event.ESelectLiveFinish;
import com.fanwe.live.model.Index_indexActModel;
import com.fanwe.live.model.LiveBannerModel;
import com.fanwe.live.model.LiveRoomModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 热门直播列表
 *
 * @author Administrator
 * @date 2016-7-2 上午11:28:04
 */
public class LiveTabHotView extends LiveTabBaseView {

    public LiveTabHotView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public LiveTabHotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LiveTabHotView(Context context) {
        super(context);
        init();
    }

    protected PullToRefreshListView lv_content;
    protected LiveTabHotHeaderView headerView;
    protected List<LiveRoomModel> mListModel = new ArrayList<>();
    protected LiveTabHotAdapter mAdapter;

    protected int mTopicId;
    protected int mSex;
    protected String mCity;

    protected void init() {
        setContentView(R.layout.frag_live_tab_hot);
        lv_content = find(R.id.lv_content);
        addHeaderView();
        setAdapter();
        updateParams();
        initPullToRefresh();
    }

    public void setTopicId(int topicId) {
        this.mTopicId = topicId;
    }

    protected void setAdapter() {
        mAdapter = new LiveTabHotAdapter(mListModel, getActivity());
        lv_content.setAdapter(mAdapter);
    }

    protected void initPullToRefresh() {
        lv_content.setMode(Mode.PULL_FROM_START);
        lv_content.setOnRefreshListener(new OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                startLoopRunnable();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            }
        });
        startLoopRunnable();
    }

    protected void addHeaderView() {
        headerView = new LiveTabHotHeaderView(getActivity());
        headerView.setBannerItemClickCallback(new SDItemClickCallback<LiveBannerModel>() {
            @Override
            public void onItemClick(int position, LiveBannerModel item, View view) {
                Intent intent = item.parseType(getActivity());
                if (intent != null) {
                    getActivity().startActivity(intent);
                }
            }
        });
        SDViewPager viewPager = getParentViewPager();
        if (viewPager != null) {
            viewPager.addIgnoreRecter(new ViewRecter(headerView.getSlidingPlayView()));
        }
        lv_content.getRefreshableView().addHeaderView(headerView);

    }

    protected void updateParams() {
        mSex = SDConfig.getInstance().getInt(R.string.config_live_select_sex, 0);
        mCity = SDConfig.getInstance().getString(R.string.config_live_select_city, "");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        super.onActivityResumed(activity);
        startLoopRunnable();
    }

    public void onEventMainThread(ESelectLiveFinish event) {
        updateParams();
        startLoopRunnable();
    }

    @Override
    protected void onLoopRun() {
        requestData();
    }

    protected void requestData() {
        CommonInterface.requestIndex(1, mSex, mTopicId, mCity, new AppRequestCallback<Index_indexActModel>() {
            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.isOk()) {
                    headerView.setListLiveBannerModel(actModel.getBanner());
                    headerView.setTopicInfoModel(actModel.getCate());
                    headerView.setGameTabData(actModel.getTag_list());
                    //刷新火箭榜
                    if(null!=actModel.getRocket_list()){
                        headerView.updataRocket(actModel.getRocket_list());
                    }else{
                        headerView.initRocket();
                    }
                    synchronized (LiveTabHotView.this) {
                        mListModel = actModel.getList();
                        mAdapter.updateData(mListModel);
                    }
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
    public void scrollToTop() {
        lv_content.getRefreshableView().setSelection(1);
    }

    @Override
    protected void onRoomClosed(final int roomId) {
        super.onRoomClosed(roomId);
        SDHandlerManager.getBackgroundHandler().post(new SDTaskRunnable<LiveRoomModel>() {
            @Override
            public LiveRoomModel onBackground() {
                synchronized (LiveTabHotView.this) {
                    if (SDCollectionUtil.isEmpty(mListModel)) {
                        return null;
                    }
                    Iterator<LiveRoomModel> it = mListModel.iterator();
                    while (it.hasNext()) {
                        LiveRoomModel item = it.next();
                        if (roomId == item.getRoom_id()) {
                            return item;
                        }
                    }
                }
                return null;
            }

            @Override
            public void onMainThread(LiveRoomModel result) {
                if (result != null) {
                    synchronized (LiveTabHotView.this) {
                        mAdapter.removeData(result);
                    }
                }
            }
        });
    }
}
