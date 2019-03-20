package com.fanwe.o2o.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.hybrid.fragment.BaseFragment;
import com.fanwe.library.adapter.SDPagerAdapter;
import com.fanwe.library.config.SDConfig;
import com.fanwe.library.customview.SDViewPager;
import com.fanwe.library.event.EOnClick;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.view.SDViewPageIndicator;
import com.fanwe.live.LiveConstant;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveSearchUserActivity;
import com.fanwe.live.adapter.LiveHomeTitleTabAdapter;
import com.fanwe.live.appview.main.LiveTabBaseView;
import com.fanwe.live.appview.main.LiveTabFollowView;
import com.fanwe.live.appview.main.LiveTabHotView;
import com.fanwe.live.appview.main.LiveTabNewView;
import com.fanwe.live.event.EReSelectTabLiveBottom;
import com.fanwe.live.event.ESelectLiveFinish;
import com.fanwe.live.model.HomeTabTitleModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */

public class O2OShoppingLiveTabLiveFragment extends BaseFragment
{
    private View ll_search;
    private SDViewPageIndicator vpg_indicator;
    private SDViewPager vpg_content;

    private List<HomeTabTitleModel> mListModel = new ArrayList<>();
    private LiveHomeTitleTabAdapter mAdapterTitleTab;

    private SparseArray<LiveTabBaseView> mArrContentView = new SparseArray<>();

    @Override
    protected int onCreateContentView()
    {
        return R.layout.o2o_shopping_frag_live_tab_live;
    }

    @Override
    protected void init()
    {
        super.init();
        ll_search = findViewById(R.id.ll_search);
        vpg_indicator = (SDViewPageIndicator) findViewById(R.id.vpg_indicator);
        vpg_content = (SDViewPager) findViewById(R.id.vpg_content);

        ll_search.setOnClickListener(this);

        initTabsData();
        initViewPager();
        initViewPagerIndicator();
    }

    private void initTabsData()
    {
        HomeTabTitleModel tabFollow = new HomeTabTitleModel();
        tabFollow.setTitle("关注");

        HomeTabTitleModel tabHot = new HomeTabTitleModel();
        tabHot.setTitle("热门");

        mListModel.add(tabFollow);
        mListModel.add(tabHot);
    }

    private void initViewPager()
    {
        vpg_indicator.setViewPager(vpg_content);

        vpg_content.setOffscreenPageLimit(2);
        vpg_content.setAdapter(new SDPagerAdapter<HomeTabTitleModel>(mListModel, getActivity())
        {
            @Override
            public View getView(ViewGroup container, int position)
            {
                LiveTabBaseView view = null;
                switch (position)
                {
                    case 0:
                        view = new LiveTabFollowView(getActivity());
                        break;
                    case 1:
                        view = new LiveTabHotView(getActivity());
                        break;
                    case 2:
                        view = new LiveTabNewView(getActivity());
                        break;

                    default:
                        break;
                }
                if (view != null)
                {
                    mArrContentView.put(position, view);
                    view.setParentViewPager(vpg_content);
                }

                return view;
            }
        });
    }

    private void initViewPagerIndicator()
    {
        mAdapterTitleTab = new LiveHomeTitleTabAdapter(mListModel, getActivity());
        mAdapterTitleTab.setItemClickCallback(new SDItemClickCallback<HomeTabTitleModel>()
        {
            @Override
            public void onItemClick(int position, HomeTabTitleModel item, View view)
            {
                vpg_indicator.setCurrentItem(position);
            }
        });

        vpg_indicator.setAdapter(mAdapterTitleTab);
        vpg_indicator.setCurrentItem(1);
    }

    public void onEventMainThread(ESelectLiveFinish event)
    {
        updateTabHotText();
    }

    private void updateTabHotText()
    {
        String text = SDConfig.getInstance().getString(R.string.config_live_select_city, "");
        if (TextUtils.isEmpty(text))
        {
            text = LiveConstant.LIVE_HOT_CITY;
        }
        mAdapterTitleTab.getData(1).setTitle(text);
        mAdapterTitleTab.updateData(1);
    }

    @Override
    public void onClick(View v)
    {
        if (v == ll_search)
        {
            clickSearch();
        }
        super.onClick(v);
    }

    /**
     * 搜索
     */
    private void clickSearch()
    {
        Intent intent = new Intent(getActivity(), LiveSearchUserActivity.class);
        startActivity(intent);
    }

    public void onEventMainThread(EReSelectTabLiveBottom event)
    {
        if (event.index == 0)
        {
            int index = vpg_content.getCurrentItem();
            LiveTabBaseView view = mArrContentView.get(index);
            if (view != null)
            {
                view.scrollToTop();
            }
        }
    }

    public void onEventMainThread(EOnClick event)
    {
        if (R.id.tv_tab_live_follow_goto_live == event.view.getId())
        {
            vpg_indicator.setCurrentItem(0);
        }
    }
}
