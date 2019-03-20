package com.fanwe.baimei.fragment;

import android.graphics.Color;
import android.os.Bundle;

import com.fanwe.games.constant.GameParamsConstant;
import com.fanwe.hybrid.fragment.BaseFragment;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.view.SDTabUnderline;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.live.R;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by yhz on 2017/5/25.
 */

public abstract class BMGameRankCGDBaseFragment extends BaseFragment
{
    @ViewInject(R.id.tab_con_today)
    private SDTabUnderline mTabToday;
    @ViewInject(R.id.tab_con_total)
    private SDTabUnderline mTabTotal;

    private SDSelectViewManager<SDTabUnderline> mSelectManager = new SDSelectViewManager<>();
    private Bundle mBundle;
    protected int mGameID = GameParamsConstant.GAME_ID_1;


    @Override
    protected int onCreateContentView()
    {
        return R.layout.bm_frag_cdg_base;
    }

    @Override
    protected void init()
    {
        super.init();
        initBundle();
        initConTab();
    }

    private void initBundle()
    {
        if (mBundle == null)
        {
            mBundle = new Bundle();
        }
        mBundle.putInt(BMGameRankContentBaseFragment.EXTRA_GAME_ID, mGameID);
    }

    private void initConTab()
    {
        mTabToday.setTextTitle("当天排行");
        mTabToday.getViewConfig(mTabToday.mTvTitle).setTextColorNormalResId(R.color.white).setTextColorSelectedResId(R.color.main_color);
        mTabToday.getViewConfig(mTabToday.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT).setBackgroundColorSelectedResId(R.color.main_color);

        mTabTotal.setTextTitle("累计排行");
        mTabTotal.getViewConfig(mTabTotal.mTvTitle).setTextColorNormalResId(R.color.white).setTextColorSelectedResId(R.color.main_color);
        mTabTotal.getViewConfig(mTabTotal.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT)
                .setBackgroundColorSelectedResId(R.color.main_color);

        mSelectManager.addSelectCallback(new SDSelectManager.SelectCallback<SDTabUnderline>()
        {

            @Override
            public void onNormal(int index, SDTabUnderline item)
            {
            }

            @Override
            public void onSelected(int index, SDTabUnderline item)
            {
                switch (index)
                {
                    case 0:
                        clickTodayConFrag();
                        break;
                    case 1:
                        clickTotalConFrag();
                        break;
                    default:
                        break;
                }
            }
        });

        mSelectManager.setItems(new SDTabUnderline[]{mTabToday, mTabTotal});
        mSelectManager.performClick(0);
    }

    private void clickTodayConFrag()
    {
        getSDFragmentManager().toggle(R.id.fl_content_cdg, null, BGGameRankDayFragment.class, mBundle);
    }

    private void clickTotalConFrag()
    {
        getSDFragmentManager().toggle(R.id.fl_content_cdg, null, BGGameRankAllFragment.class, mBundle);
    }
}
