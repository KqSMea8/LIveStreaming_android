package com.fanwe.pay.fragment;

import android.graphics.Color;

import com.fanwe.hybrid.fragment.BaseFragment;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.view.SDTabUnderline;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.live.R;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2017/1/9.
 */

public class PayBalanceTabBaseFragment extends BaseFragment
{
    @ViewInject(R.id.tab_left)
    private SDTabUnderline tab_left;
    @ViewInject(R.id.tab_right)
    private SDTabUnderline tab_right;

    private SDSelectViewManager<SDTabUnderline> mSelectManager = new SDSelectViewManager<SDTabUnderline>();

    private int mSelectTabIndex = 0;

    @Override
    protected int onCreateContentView()
    {
        return R.layout.frag_pay_balance_tab;
    }

    @Override
    protected void init()
    {
        super.init();
        register();
    }

    private void register()
    {
        addTab();
    }

    private void addTab()
    {
        tab_left.setTextTitle("付费记录");
        tab_left.getViewConfig(tab_left.mTvTitle).setTextColorNormalResId(R.color.text_gray).setTextColorSelectedResId(R.color.text_black);
        tab_left.getViewConfig(tab_left.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT).setBackgroundColorSelectedResId(R.color.main_color);

        tab_right.setTextTitle("收费记录");
        tab_right.getViewConfig(tab_right.mTvTitle).setTextColorNormalResId(R.color.text_gray).setTextColorSelectedResId(R.color.text_black);
        tab_right.getViewConfig(tab_right.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT)
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

        mSelectManager.setItems(new SDTabUnderline[]{tab_left, tab_right});
        mSelectManager.performClick(mSelectTabIndex);
    }

    /**
     * 付费记录
     */
    protected void click0()
    {
        getSDFragmentManager().toggle(R.id.ll_content, null, PaySceneBalanceExpendFragment.class);
    }

    /**
     * 收费记录
     */
    protected void click1()
    {
        getSDFragmentManager().toggle(R.id.ll_content, null, PaySceneBalanceIncomeFragment.class);
    }
}
