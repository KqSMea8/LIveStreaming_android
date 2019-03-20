package com.fanwe.live.fragment;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.hybrid.fragment.BaseFragment;
import com.fanwe.library.adapter.SDPagerAdapter;
import com.fanwe.library.common.SDFragmentManager;
import com.fanwe.library.customview.SDViewPager;
import com.fanwe.library.view.SDViewPageIndicator;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveHomeTitleTabAdapter;
import com.fanwe.live.appview.main.LiveTabBaseView;
import com.fanwe.live.appview.main.LiveTabFollowView;
import com.fanwe.live.appview.main.LiveTabHotView;
import com.fanwe.live.model.HomeTabTitleModel;

import java.util.ArrayList;
import java.util.List;

/**
 关注
 */
public class LiveTabFollowFragment extends BaseFragment {
  //  R.layout.fragment_live_tab_follow
  private View ll_search;
    private View ll_private_chat_list;
    private TextView tv_total_unreadnum;
    private SDViewPageIndicator vpg_indicator;
    private SDViewPager vpg_content;
     private  TextView tv_title;
    private List<HomeTabTitleModel> mListModel = new ArrayList<>();
    private LiveHomeTitleTabAdapter mAdapterTitleTab;
    private LiveTabLiveFragment context;
    private SparseArray<LiveTabBaseView> mArrContentView = new SparseArray<>();
    SDFragmentManager sdFragmentManager ;
    @Override
    protected int onCreateContentView()
    {
        return R.layout.fragment_live_tab_follow;
    }

    @Override
    protected void init()
    {
        super.init();
        tv_title = (TextView) findViewById(R.id.tv_title);
        vpg_content = (SDViewPager) findViewById(R.id.vpg_content);
        HomeTabTitleModel tabFollow = new HomeTabTitleModel();
        tabFollow.setTitle("关注");
        mListModel.add(tabFollow);
        tv_title.setText("关注");
        initViewPager();
    }
    private void initViewPager()
    {
        vpg_content.setOffscreenPageLimit(2);
        vpg_content.setAdapter(new SDPagerAdapter<HomeTabTitleModel>(mListModel, getActivity()) {
            @Override
            public View getView(ViewGroup container, int position) {
                LiveTabBaseView view = null;
                switch (position) {
                    case 0:
                        view = new LiveTabFollowView(getActivity());
                        break;
                    case 1:
                        view = new LiveTabHotView(getActivity());
                        break;
                    default:
                        break;
                }
                if (view != null) {
                    mArrContentView.put(position, view);
                    view.setParentViewPager(vpg_content);
                }

                return view;
            }
        });
    }

}
