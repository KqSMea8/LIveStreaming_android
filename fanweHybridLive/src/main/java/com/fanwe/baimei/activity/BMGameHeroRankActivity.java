package com.fanwe.baimei.activity;

import android.os.Bundle;

import com.fanwe.baimei.fragment.BMGameRankCowFragment;
import com.fanwe.baimei.fragment.BMGameRankDiceFragment;
import com.fanwe.baimei.fragment.BMGameRankGoldenFlowerFragment;
import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDTabText;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.live.R;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by yhz on 2017/5/18. 英雄榜
 */

public class BMGameHeroRankActivity extends BaseTitleActivity
{
    @ViewInject(R.id.tab_left)
    private SDTabText tab_left;
    @ViewInject(R.id.tab_center)
    private SDTabText tab_center;
    @ViewInject(R.id.tab_right)
    private SDTabText tab_right;

    private SDSelectViewManager<SDTabText> selectViewManager = new SDSelectViewManager<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bm_act_game_hero);
        init();
    }

    private void init()
    {
        initTitle();
        initTab();
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("英雄榜");
    }

    private void initTab()
    {
        tab_left.setTextTitle("牛牛");
        tab_left.mTv_title.setPadding(SDViewUtil.dp2px(20), SDViewUtil.dp2px(2), SDViewUtil.dp2px(20), SDViewUtil.dp2px(2));
        tab_left.getViewConfig(tab_left.mTv_title).setBackgroundNormal(SDResourcesUtil.getDrawable(R.drawable.bm_bg_no_color_corner_5dp))
                .setBackgroundSelected(SDResourcesUtil.getDrawable(R.drawable.bm_bg_main_color_corner_5dp));
        tab_left.getViewConfig(tab_left.mTv_title).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_title_bar)).setTextColorSelected(SDResourcesUtil.getColor(R.color.text_title_bar))
                .setTextSizeNormal(SDViewUtil.dp2px(13)).setTextSizeSelected(SDViewUtil.dp2px(13));

        tab_center.mTv_title.setPadding(SDViewUtil.dp2px(20), SDViewUtil.dp2px(2), SDViewUtil.dp2px(20), SDViewUtil.dp2px(2));
        tab_center.setTextTitle("炸金花");
        tab_center.getViewConfig(tab_center.mTv_title).setBackgroundNormal(SDResourcesUtil.getDrawable(R.drawable.bm_bg_no_color_corner_5dp))
                .setBackgroundSelected(SDResourcesUtil.getDrawable(R.drawable.bm_bg_main_color_corner_5dp));
        tab_center.getViewConfig(tab_center.mTv_title).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_title_bar)).setTextColorSelected(SDResourcesUtil.getColor(R.color.text_title_bar))
                .setTextSizeNormal(SDViewUtil.dp2px(13)).setTextSizeSelected(SDViewUtil.dp2px(13));

        tab_right.mTv_title.setPadding(SDViewUtil.dp2px(20), SDViewUtil.dp2px(2), SDViewUtil.dp2px(20), SDViewUtil.dp2px(2));
        tab_right.setTextTitle("猜大小");
        tab_right.getViewConfig(tab_right.mTv_title).setBackgroundNormal(SDResourcesUtil.getDrawable(R.drawable.bm_bg_no_color_corner_5dp))
                .setBackgroundSelected(SDResourcesUtil.getDrawable(R.drawable.bm_bg_main_color_corner_5dp));
        tab_right.getViewConfig(tab_right.mTv_title).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_title_bar)).setTextColorSelected(SDResourcesUtil.getColor(R.color.text_title_bar))
                .setTextSizeNormal(SDViewUtil.dp2px(13)).setTextSizeSelected(SDViewUtil.dp2px(13));

        SDTabText[] items = new SDTabText[]{tab_left, tab_center, tab_right};

        selectViewManager.addSelectCallback(new SDSelectManager.SelectCallback<SDTabText>()
        {

            @Override
            public void onNormal(int index, SDTabText item)
            {
            }

            @Override
            public void onSelected(int index, SDTabText item)
            {
                switch (index)
                {
                    case 0:
                        clickTabLeft();
                        break;
                    case 1:
                        clickTabCenter();
                        break;
                    case 2:
                        clickTabRight();
                        break;
                    default:
                        break;
                }
            }
        });
        selectViewManager.setItems(items);
        selectViewManager.setSelected(0, true);
    }

    private void clickTabLeft()
    {
        getSDFragmentManager().toggle(R.id.fl_content_hero, null, BMGameRankCowFragment.class);
    }

    private void clickTabCenter()
    {
        getSDFragmentManager().toggle(R.id.fl_content_hero, null, BMGameRankGoldenFlowerFragment.class);
    }

    private void clickTabRight()
    {
        getSDFragmentManager().toggle(R.id.fl_content_hero, null, BMGameRankDiceFragment.class);
    }
}
