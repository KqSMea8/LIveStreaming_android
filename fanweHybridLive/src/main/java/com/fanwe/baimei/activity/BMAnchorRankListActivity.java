package com.fanwe.baimei.activity;

import android.os.Bundle;

import com.fanwe.baimei.fragment.BMPopularityFragment;
import com.fanwe.baimei.fragment.BMTyrantFragment;
import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDTabText;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.live.R;
import com.fanwe.live.event.EFinishAdImg;
import com.sunday.eventbus.SDEventManager;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by yhz on 2017/5/23.主播榜
 */

public class BMAnchorRankListActivity extends BaseTitleActivity
{
    @ViewInject(R.id.tab_popularity)
    private SDTabText tab_popularity;
    @ViewInject(R.id.tab_tyrant)
    private SDTabText tab_tyrant;

    private SDSelectViewManager<SDTabText> selectViewManager = new SDSelectViewManager<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bm_act_anchor_ranklist);
        init();
    }

    private void init()
    {
        initTitle();
        initTabs();
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("主播榜");
    }

    private void initTabs()
    {
        tab_popularity.setTextTitle("人气榜");
        tab_popularity.mTv_title.setPadding(SDViewUtil.dp2px(20),SDViewUtil.dp2px(2),SDViewUtil.dp2px(20),SDViewUtil.dp2px(2));
        tab_popularity.getViewConfig(tab_popularity.mTv_title).setBackgroundNormal(SDResourcesUtil.getDrawable(R.drawable.bm_bg_no_color_corner_5dp))
                .setBackgroundSelected(SDResourcesUtil.getDrawable(R.drawable.bm_bg_main_color_corner_5dp));
        tab_popularity.getViewConfig(tab_popularity.mTv_title).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_title_bar)).setTextColorSelected(SDResourcesUtil.getColor(R.color.white))
                .setTextSizeNormal(SDViewUtil.dp2px(13)).setTextSizeSelected(SDViewUtil.dp2px(13));

        tab_tyrant.mTv_title.setPadding(SDViewUtil.dp2px(20),SDViewUtil.dp2px(2),SDViewUtil.dp2px(20),SDViewUtil.dp2px(2));
        tab_tyrant.setTextTitle("土豪榜");
        tab_tyrant.getViewConfig(tab_tyrant.mTv_title).setBackgroundNormal(SDResourcesUtil.getDrawable(R.drawable.bm_bg_no_color_corner_5dp))
                .setBackgroundSelected(SDResourcesUtil.getDrawable(R.drawable.bm_bg_main_color_corner_5dp));
        tab_tyrant.getViewConfig(tab_tyrant.mTv_title).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_title_bar)).setTextColorSelected(SDResourcesUtil.getColor(R.color.white))
                .setTextSizeNormal(SDViewUtil.dp2px(13)).setTextSizeSelected(SDViewUtil.dp2px(13));

        SDTabText[] items = new SDTabText[]{tab_popularity, tab_tyrant};

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
                        clickTabMerits();
                        break;
                    case 1:
                        clickTabContribution();
                        break;
                    default:
                        break;
                }
            }
        });
        selectViewManager.setItems(items);
        selectViewManager.setSelected(0, true);
    }

    protected void clickTabMerits()
    {
        getSDFragmentManager().toggle(R.id.fl_content_anchor, null, BMPopularityFragment.class);
    }

    protected void clickTabContribution()
    {
        getSDFragmentManager().toggle(R.id.fl_content_anchor, null, BMTyrantFragment.class);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        onSendEvent();
    }

    /**
     * 发送关闭事件
     */
    private void onSendEvent()
    {
        EFinishAdImg event = new EFinishAdImg();
        SDEventManager.post(event);
    }
}
