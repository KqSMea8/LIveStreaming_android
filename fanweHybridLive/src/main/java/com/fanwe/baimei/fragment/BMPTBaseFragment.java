package com.fanwe.baimei.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.hybrid.fragment.BaseFragment;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.view.SDTabUnderline;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.live.R;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by yhz on 2017/5/24.
 */

public abstract class BMPTBaseFragment extends BaseFragment
{
    @ViewInject(R.id.tab_rank_day)
    private SDTabUnderline tab_rank_day;
    @ViewInject(R.id.tab_rank_month)
    private SDTabUnderline tab_rank_month;
    @ViewInject(R.id.tab_rank_total)
    private SDTabUnderline tab_rank_total;
    @ViewInject(R.id.tab_rank_week)
    private SDTabUnderline tab_rank_week;
    private SDSelectViewManager<SDTabUnderline> selectViewManager = new SDSelectViewManager<>();
    @Override
    protected int onCreateContentView()
    {
        return R.layout.bm_frag_popularity_tyrant_base;
    }
    @Override
    protected void init()
    {
        super.init();
        initTabs();
    }

    private void initTabs()
    {
        tab_rank_day.setTextTitle("日榜");
        tab_rank_day.getViewConfig(tab_rank_day.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT)
                .setBackgroundColorSelected(SDResourcesUtil.getColor(R.color.main_color));
        tab_rank_day.getViewConfig(tab_rank_day.mTvTitle).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_title_bar)).setTextColorSelected(SDResourcesUtil.getColor(R.color.main_color))
                .setTextSizeNormal(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_13)).setTextSizeSelected(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_13));

        tab_rank_week.setTextTitle("周榜");
        tab_rank_week.getViewConfig(tab_rank_week.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT)
                .setBackgroundColorSelected(SDResourcesUtil.getColor(R.color.main_color));
        tab_rank_week.getViewConfig(tab_rank_week.mTvTitle).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_title_bar)).setTextColorSelected(SDResourcesUtil.getColor(R.color.main_color))
                .setTextSizeNormal(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_13)).setTextSizeSelected(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_13));

        tab_rank_month.setTextTitle("月榜");
        tab_rank_month.getViewConfig(tab_rank_month.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT).setBackgroundColorSelected(SDResourcesUtil.getColor(R.color.main_color));
        tab_rank_month.getViewConfig(tab_rank_month.mTvTitle).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_title_bar)).setTextColorSelected(SDResourcesUtil.getColor(R.color.main_color))
                .setTextSizeNormal(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_13)).setTextSizeSelected(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_13));

        tab_rank_total.setTextTitle("总榜");
        tab_rank_total.getViewConfig(tab_rank_total.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT).setBackgroundColorSelected(SDResourcesUtil.getColor(R.color.main_color));
        tab_rank_total.getViewConfig(tab_rank_total.mTvTitle).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_title_bar)).setTextColorSelected(SDResourcesUtil.getColor(R.color.main_color))
                .setTextSizeNormal(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_13)).setTextSizeSelected(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_13));

        SDTabUnderline[] items = new SDTabUnderline[]{tab_rank_day,tab_rank_week, tab_rank_month, tab_rank_total};
        selectViewManager.addSelectCallback(new SDSelectManager.SelectCallback<SDTabUnderline>()
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
                        clickTabDay();
                        break;
                    case 1:
                        clickTabWeek();
                        break;
                    case 2:
                        clickTabMonth();
                        break;
                    case 3:
                        clickTabTotal();
                        break;
                    default:
                        break;
                }
            }
        });
        selectViewManager.setItems(items);
        selectViewManager.setSelected(0, true);
    }

    abstract void clickTabDay();
    abstract void clickTabWeek();
    abstract void clickTabMonth();

    abstract void clickTabTotal();
}
