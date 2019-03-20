package com.fanwe.pay.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDTabUnderline;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.pay.fragment.PaySceneBalanceTabFragment;
import com.fanwe.pay.fragment.PayTimeBalanceTabFramgent;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by yhz on 2016/11/30. 付费榜
 */

public class PayBalanceActivity extends BaseActivity
{
    @ViewInject(R.id.rl_back)
    private View rl_back;
    @ViewInject(R.id.tab_pay_time)
    private SDTabUnderline tab_pay_time;
    @ViewInject(R.id.tab_pay_scene)
    private SDTabUnderline tab_pay_scene;

    private SDSelectViewManager<SDTabUnderline> selectViewManager = new SDSelectViewManager<>();

    @Override
    protected int onCreateContentView()
    {
        return R.layout.act_pay_balance;
    }

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        initTabs();
        rl_back.setOnClickListener(this);
    }

    private void initTabs()
    {
        tab_pay_time.setTextTitle("按时收费");
        tab_pay_time.getViewConfig(tab_pay_time.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT)
                .setBackgroundColorSelected(Color.TRANSPARENT);
        tab_pay_time.getViewConfig(tab_pay_time.mTvTitle).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_title_bar)).setTextColorSelected(SDResourcesUtil.getColor(R.color.text_black))
                .setTextSizeNormal(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_18)).setTextSizeSelected(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_18));

        tab_pay_scene.setTextTitle("按场收费");
        tab_pay_scene.getViewConfig(tab_pay_scene.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT).setBackgroundColorSelected(Color.TRANSPARENT);
        tab_pay_scene.getViewConfig(tab_pay_scene.mTvTitle).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_title_bar)).setTextColorSelected(SDResourcesUtil.getColor(R.color.text_black))
                .setTextSizeNormal(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_18)).setTextSizeSelected(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_18));

        SDTabUnderline[] items = new SDTabUnderline[]{tab_pay_time, tab_pay_scene};

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

        if (AppRuntimeWorker.getLive_pay_time() == 0)
        {
            SDViewUtil.setGone(tab_pay_time);
        }

        if (AppRuntimeWorker.getLive_pay_scene() == 0)
        {
            SDViewUtil.setGone(tab_pay_scene);
        }

        if (AppRuntimeWorker.getLive_pay_time() == 0 && AppRuntimeWorker.getLive_pay_scene() == 1)
        {
            //如果只开按场,选中按场
            selectViewManager.setSelected(1, true);
        } else
        {
            selectViewManager.setSelected(0, true);
        }
    }

    protected void clickTabMerits()
    {
        getSDFragmentManager().toggle(R.id.ll_content, null, PayTimeBalanceTabFramgent.class);
    }

    protected void clickTabContribution()
    {
        getSDFragmentManager().toggle(R.id.ll_content, null, PaySceneBalanceTabFragment.class);
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.rl_back:
                finish();
                break;
        }
    }
}
