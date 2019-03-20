package com.fanwe.shop.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.shop.appview.ShopAddGoodsView;
import com.fanwe.shop.model.App_shop_mystoreItemModel;
/**
 * Created by shibx on 2016/9/22.
 */

/**
 * 新增购物商品
 */
public class ShopAddGoodsEmptyActivity extends BaseActivity
{
    private ShopAddGoodsView mAddGoodsView;//新增购物商品

    public static final String EXTRA_MODEL = "extra_model";

    //全屏参数
    private ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_empty_ll_content);
        init();
    }


    private void init()
    {
        addView();
    }

    private void addView()
    {
        App_shop_mystoreItemModel model = (App_shop_mystoreItemModel) getIntent().getSerializableExtra(EXTRA_MODEL);
        mAddGoodsView = new ShopAddGoodsView(this, model);
        mAddGoodsView.setLayoutParams(layoutParams);
        replaceView(R.id.ll_content, mAddGoodsView);
    }
}
