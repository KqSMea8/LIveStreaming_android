package com.fanwe.live.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.library.customview.SDGridLinearLayout.ItemClickListener;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveUserHomeActivity;
import com.fanwe.live.adapter.LiveUserHomeRightAdapter;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_user_reviewActModel;
import com.fanwe.live.model.ItemApp_user_reviewModel;
import com.fanwe.live.model.PlayBackData;
import com.fanwe.live.view.SlideToBottomScrollView;
import com.fanwe.live.view.TabLeftImage;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-11 下午3:30:30 类说明
 */
public class LiveUserHomeCenterFragment extends LiveUserHomeBaseFragment
{
    public static final String TAG = "LiveUserHomeCenterFragment";

    @ViewInject(R.id.tv_count)
    private TextView tv_count;
    @ViewInject(R.id.ll_no_jilu)
    private LinearLayout ll_no_jilu;
    @ViewInject(R.id.gll_info)
    private SDGridLinearLayout gll_info;
    @ViewInject(R.id.ll_loading)
    private LinearLayout ll_loading;
    @ViewInject(R.id.left_tab_sort0)
    private TabLeftImage left_tab_sort0;
    @ViewInject(R.id.left_tab_sort1)
    private TabLeftImage left_tab_sort1;

    private SDSelectViewManager<TabLeftImage> mSelectManager = new SDSelectViewManager<TabLeftImage>();
    private int mSelectTabIndex = 0;
    private LiveUserHomeRightAdapter adapter;
    private List<ItemApp_user_reviewModel> listModel = new ArrayList<ItemApp_user_reviewModel>();

    private App_user_reviewActModel app_user_reviewActModel;

    private LiveUserHomeActivity act;
    private SlideToBottomScrollView scrollView;
    private boolean isLoading;

    private int page = 1;
    private int sort = 0;
    private String to_user_id;

    @Override
    protected int onCreateContentView()
    {
        return R.layout.frag_user_home_center;
    }

    @Override
    protected void init()
    {
        super.init();
        register();
        registerScrollListner();
        getIntentExtra();
        addSortTab();
        bindAdapter();
        requestUser_review(false);
    }

    private void getIntentExtra()
    {
        to_user_id = getActivity().getIntent().getExtras().getString(LiveUserHomeActivity.EXTRA_USER_ID);
    }

    private void registerScrollListner()
    {
        act = (LiveUserHomeActivity) getActivity();
        scrollView = act.getLsv();
        scrollView.setOnScrollToBottomListener(new SlideToBottomScrollView.OnScrollToBottomListener()
        {
            @Override
            public void onScrollToBottom()
            {
                loadMoreViewer();
            }
        });
    }

    private void register()
    {
        gll_info.setItemClickListener(new ItemClickListener()
        {

            @Override
            public void onItemClick(int position, View view, ViewGroup parent)
            {
                ItemApp_user_reviewModel model = adapter.getItem(position);
                if (model != null)
                {
                    PlayBackData data = new PlayBackData();
                    data.setRoomId(model.getId());
                    AppRuntimeWorker.startPlayback(data, getActivity());
                }
            }
        });
    }

    private void addSortTab()
    {
        int textSizeNormal = SDViewUtil.sp2px(13);
        int textSizeSelected = SDViewUtil.sp2px(14);

        left_tab_sort0.setTextTitle("最新");
        left_tab_sort0.getViewConfig(left_tab_sort0.mTvTitle).setTextColorNormalResId(R.color.black).setTextColorSelectedResId(R.color.main_color).setTextSizeNormal(textSizeNormal).setTextSizeSelected(textSizeSelected);
        left_tab_sort0.getViewConfig(left_tab_sort0.mIvLeft).setBackgroundNormalResId(R.drawable.ic_me_jiantou2).setBackgroundSelectedResId(R.drawable.ic_me_jiantou2);

        left_tab_sort1.setTextTitle("最热");
        left_tab_sort1.getViewConfig(left_tab_sort1.mTvTitle).setTextColorNormalResId(R.color.black).setTextColorSelectedResId(R.color.main_color).setTextSizeNormal(textSizeNormal).setTextSizeSelected(textSizeSelected);
        left_tab_sort1.getViewConfig(left_tab_sort1.mIvLeft).setBackgroundNormalResId(R.drawable.ic_me_jiantou2).setBackgroundSelectedResId(R.drawable.ic_me_jiantou2);

        mSelectManager.addSelectCallback(new SDSelectManager.SelectCallback<TabLeftImage>()
        {

            @Override
            public void onNormal(int index, TabLeftImage item)
            {
            }

            @Override
            public void onSelected(int index, TabLeftImage item)
            {
                switch (index)
                {
                    case 0:
                        click0();
                        break;
                    case 1:
                        click1();
                        break;
                    default:
                        break;
                }
            }

        });

        mSelectManager.setItems(new TabLeftImage[]
                {left_tab_sort0, left_tab_sort1});
        mSelectManager.performClick(mSelectTabIndex);
    }

    protected void click0()
    {
        left_tab_sort0.mIvLeft.setVisibility(View.INVISIBLE);
        left_tab_sort1.mIvLeft.setVisibility(View.INVISIBLE);

        sort = 0;
        refreshViewer();
    }

    protected void click1()
    {
        left_tab_sort0.mIvLeft.setVisibility(View.INVISIBLE);
        left_tab_sort1.mIvLeft.setVisibility(View.INVISIBLE);

        sort = 1;
        refreshViewer();
    }

    private void bindAdapter()
    {
        gll_info.setColNumber(1);
        adapter = new LiveUserHomeRightAdapter(listModel, getActivity(), app_user_homeActModel);
        gll_info.setAdapter(adapter);
    }

    private void loadMoreViewer()
    {
        if (isVisible())
        {
            if (app_user_reviewActModel != null)
            {
                if (app_user_reviewActModel.getHas_next() == 1)
                {
                    if (!isLoading)
                    {
                        isLoading = true;
                        SDViewUtil.setVisible(ll_loading);
                        ll_loading.postDelayed(new Runnable()
                        {

                            @Override
                            public void run()
                            {
                                page++;
                                requestUser_review(true);
                            }
                        }, 1500);
                    }
                } else
                {

                }
            } else
            {
                refreshViewer();
            }
        }
    }

    private void refreshViewer()
    {
        page = 1;
        requestUser_review(false);
    }

    private void requestUser_review(final boolean isLoadMore)
    {
        CommonInterface.requestUser_review(page, sort, to_user_id, new AppRequestCallback<App_user_reviewActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    app_user_reviewActModel = actModel;

                    if (page == 1 && actModel.getCount() <= 0)
                    {
                        SDViewUtil.setVisible(ll_no_jilu);
                        SDViewUtil.setGone(gll_info);
                    } else
                    {
                        if (page == 1 && actModel.getCount() > 0)
                        {
                            SDViewBinder.setTextView(tv_count, actModel.getCount() + "");
                        }
                        SDViewUtil.setGone(ll_no_jilu);
                    }

                    SDViewUtil.updateAdapterByList(listModel, actModel.getList(), adapter, isLoadMore);
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                // TODO Auto-generated method stub
                super.onFinish(resp);
                SDViewUtil.setGone(ll_loading);
                isLoading = false;
            }

        });
    }

    ;
}
