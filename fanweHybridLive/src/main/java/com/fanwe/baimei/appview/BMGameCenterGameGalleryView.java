package com.fanwe.baimei.appview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.baimei.adapter.BMCommonPagerAdapter;
import com.fanwe.baimei.model.BMGameCenterGameModel;
import com.fanwe.baimei.widget.BMGalleryPagerTransformer;
import com.fanwe.library.view.SDAppView;
import com.fanwe.live.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 包名 com.fanwe.baimei.appview
 * 描述 游戏主页游戏展示界面
 * 作者 Su
 * 创建时间 2017/5/16 8:47
 **/
public class BMGameCenterGameGalleryView extends SDAppView
{
    private ViewPager mViewPager;
    private List<View> mPageViews;
    private List<BMGameCenterGameModel> mGameModels;
    private BMCommonPagerAdapter mPagerAdapter;
    private BMGameCenterGameGalleryViewCallback mCallback;


    public BMGameCenterGameGalleryView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);

        initBMGameCenterGameGalleryView(context);
    }

    public BMGameCenterGameGalleryView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        initBMGameCenterGameGalleryView(context);
    }

    public BMGameCenterGameGalleryView(Context context)
    {
        super(context);

        initBMGameCenterGameGalleryView(context);
    }

    private void initBMGameCenterGameGalleryView(Context context)
    {
        setContentView(R.layout.bm_view_game_gallery_game_center);

        initPager();
    }

    private void initPager()
    {
        getViewPager().setAdapter(getPagerAdapter());
        getViewPager().setPageTransformer(false, new BMGalleryPagerTransformer(getContext()));

        getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            private int currentPosition;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
                if (ViewPager.SCROLL_STATE_IDLE == state)
                {
                    // (2) 0 1 2 (0)
                    int n = mGameModels.size();
                    if (n >= 2)
                    {
                        final int posRealHead = 1;
                        final int posRealTail = n - 2;
                        int pos = currentPosition;

                        if (0 == currentPosition)
                        {
                            pos = posRealTail;
                        } else if (n - 1 == currentPosition)
                        {
                            pos = posRealHead;
                        }
                        getViewPager().setCurrentItem(pos, false);
                    }
                }

            }
        });

    }

    private List<View> getPageViews()
    {
        if (mPageViews == null)
        {
            mPageViews = new ArrayList<>();
        }
        return mPageViews;
    }

    private ViewPager getViewPager()
    {
        if (mViewPager == null)
        {
            mViewPager = (ViewPager) findViewById(R.id.vp_game_gallery);
        }
        return mViewPager;
    }

    private BMCommonPagerAdapter getPagerAdapter()
    {
        if (mPagerAdapter == null)
        {
            mPagerAdapter = new BMCommonPagerAdapter(getPageViews());
        }
        return mPagerAdapter;
    }

    public List<BMGameCenterGameModel> getGameModels()
    {
        if (mGameModels == null)
        {
            mGameModels = new ArrayList<>();
        }
        return mGameModels;
    }

    public void setGameModels(@NonNull List<BMGameCenterGameModel> gameModels)
    {
        this.mGameModels = gameModels;
        getPageViews().clear();

        final int n = gameModels.size();
        if (n >= 2)
        {
            //     0 1 2
            // (2) 0 1 2 (0)
            BMGameCenterGameModel tail = gameModels.get(n - 1);
            BMGameCenterGameModel head = gameModels.get(0);

            gameModels.add(0, tail);
            gameModels.add(head);
        }

        final int m = gameModels.size();

        for (int i = 0; i < m; i++)
        {
            BMGameCenterGameModel model = gameModels.get(i);
            final BMGameCenterGameGalleryPageView pageView = new BMGameCenterGameGalleryPageView(getContext());
            pageView.setGameModel(model);

            final int finalI = i;

            pageView.setCallback(new BMGameCenterGameGalleryPageView.BMGameCenterGameGalleryPageViewCallback()
            {
                @Override
                public void onPageClick(View view, BMGameCenterGameModel model)
                {
                    //屏蔽未完全展示页面的点击回调（移至当前位置）
                    int currentPagePosition = getViewPager().getCurrentItem();
                    if (currentPagePosition != finalI)
                    {
                        getViewPager().setCurrentItem(finalI, true);
                        return;
                    }

                    //获取数据实际下标
                    int modelPos = finalI;
                    if (m >= 4)
                    {
                        if (0 == finalI)
                        {
                            modelPos = m - 2;
                        } else if (m - 1 == finalI)
                        {
                            modelPos = 1;
                        } else
                        {
                            modelPos = finalI - 1;
                        }
                    }
                    getCallback().onGamePageClick(view, model, modelPos);
                }
            });
            getPageViews().add(pageView);
        }

        getPagerAdapter().setPageViews(getPageViews());
        getPagerAdapter().notifyDataSetChanged();

        if (n > 1)
        {
            getViewPager().setCurrentItem(1);
        }
    }

    private BMGameCenterGameGalleryViewCallback getCallback()
    {
        if (mCallback == null)
        {
            mCallback = new BMGameCenterGameGalleryViewCallback()
            {

                @Override
                public void onGamePageClick(View view, BMGameCenterGameModel model, int modelPosition)
                {

                }
            };
        }
        return mCallback;
    }

    public void setCallback(BMGameCenterGameGalleryViewCallback mCallback)
    {
        this.mCallback = mCallback;
    }

    public interface BMGameCenterGameGalleryViewCallback
    {
        void onGamePageClick(View view, BMGameCenterGameModel model, int modelPosition);
    }


}
