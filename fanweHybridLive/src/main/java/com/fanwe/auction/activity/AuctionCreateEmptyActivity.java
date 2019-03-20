package com.fanwe.auction.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import com.fanwe.auction.FloatViewPermissionHelp;
import com.fanwe.auction.appview.AuctionRealGoodsView;
import com.fanwe.auction.appview.AuctionVirtualGoodsView;
import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.live.R;
import com.fanwe.live.activity.room.LivePushCreaterActivity;

/**
 * Created by shibx on 2016/8/5.
 */
public class AuctionCreateEmptyActivity extends BaseActivity
{


    private AuctionVirtualGoodsView mVirtualGoodsView;//新增虚拟竞拍视图
    private AuctionRealGoodsView mRealGoodsView;//新增实物竞拍视图

    /**
     * 设置商品类型(int) 1为实体商品 0为虚拟商品
     */
    public static final String EXTRA_VIEW_FLAG = "extra_view_flag";
    /**
     * 设置商品ID
     */
    public static final String EXTRA_ID = "extra_id";

    public static final String EXTRA_MODEL = "extra_model";

    private int mFlag;
    //全屏参数
    private ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_empty_ll_content);
        getExtraData();
        init();
    }

    private void getExtraData()
    {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            mFlag = bundle.getInt(EXTRA_VIEW_FLAG);
            id = bundle.getString(EXTRA_ID);
        }
    }

    private void init()
    {
        addView();
        if (!SDActivityManager.getInstance().containActivity(LivePushCreaterActivity.class))
        {
            FloatViewPermissionHelp.checkPermissionAndSwitchSmallScreen(this);
        }
    }

    private void addView()
    {
        if (mFlag == 1)
        {
            mRealGoodsView = new AuctionRealGoodsView(this);
            mRealGoodsView.requestData(id);
            mRealGoodsView.setLayoutParams(layoutParams);
            replaceView(R.id.ll_content, mRealGoodsView);
        } else
        {
            mVirtualGoodsView = new AuctionVirtualGoodsView(this);
            mVirtualGoodsView.setLayoutParams(layoutParams);
            replaceView(R.id.ll_content, mVirtualGoodsView);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        FloatViewPermissionHelp.onActivityResult(this, requestCode, resultCode, data);
    }
}
