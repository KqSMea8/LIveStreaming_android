package com.fanwe.o2o.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.o2o.appview.O2OShoppingMystoreView;

import org.xutils.view.annotation.ViewInject;

public class O2OShoppingMystoreActivity extends BaseTitleActivity
{
    @ViewInject(R.id.fl_content)
    private FrameLayout fl_content;
    private O2OShoppingMystoreView shoppingMystoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_shopping_mystore);

        initTitle();
        addAuctionShopMystoreView();
    }

    private void addAuctionShopMystoreView()
    {
        shoppingMystoreView = new O2OShoppingMystoreView(O2OShoppingMystoreActivity.this);
        replaceView(fl_content, shoppingMystoreView);
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("我的星店");
    }
}
