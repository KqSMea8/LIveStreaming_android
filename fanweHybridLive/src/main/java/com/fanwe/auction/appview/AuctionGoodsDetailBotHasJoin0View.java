package com.fanwe.auction.appview;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fanwe.auction.activity.AuctionGinsengShootMarginActivity;
import com.fanwe.auction.activity.AuctionRealGoodsGinsengShootMarginActivity;
import com.fanwe.auction.model.App_pai_user_goods_detailActModel;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;

/**
 * Created by Administrator on 2016/8/9.
 */
public class AuctionGoodsDetailBotHasJoin0View extends BaseAppView
{
    private App_pai_user_goods_detailActModel app_pai_user_goods_detailActModel;
    private boolean is_web_start;

    public App_pai_user_goods_detailActModel getApp_pai_user_goods_detailActModel()
    {
        return app_pai_user_goods_detailActModel;
    }

    public void setApp_pai_user_goods_detailActModel(App_pai_user_goods_detailActModel app_pai_user_goods_detailActModel)
    {
        this.app_pai_user_goods_detailActModel = app_pai_user_goods_detailActModel;
    }

    public boolean is_web_start()
    {
        return is_web_start;
    }

    public void setIs_web_start(boolean is_web_start)
    {
        this.is_web_start = is_web_start;
    }

    private TextView tv_bz_diamonds_bot;
    private Button btn_take_part_in;

    private int bz_diamonds;//保证金
    private int id;//商品ID
    private int is_true;//0虚拟 1普通商品

    public AuctionGoodsDetailBotHasJoin0View(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public AuctionGoodsDetailBotHasJoin0View(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public AuctionGoodsDetailBotHasJoin0View(Context context)
    {
        super(context);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.include_auction_goods_bottom_status_0);
        register();
    }

    private void register()
    {
        tv_bz_diamonds_bot = find(R.id.tv_bz_diamonds_bot);
        btn_take_part_in = find(R.id.btn_take_part_in);
        btn_take_part_in.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (id <= 0)
                {
                    SDToast.showToast("info里面id==0");
                    return;
                }
                if (bz_diamonds <= 0)
                {
                    SDToast.showToast("bz_diamonds小于等于0");
                    return;
                }
                if (is_true == 1)
                {
                    Intent intent = new Intent(getActivity(), AuctionRealGoodsGinsengShootMarginActivity.class);
                    intent.putExtra(AuctionRealGoodsGinsengShootMarginActivity.EXTRA_PAI_ID, Integer.valueOf(id));
                    intent.putExtra(AuctionRealGoodsGinsengShootMarginActivity.EXTRA_PAI_BOND, bz_diamonds);
                    getActivity().startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(getActivity(), AuctionGinsengShootMarginActivity.class);
                    intent.putExtra(AuctionGinsengShootMarginActivity.EXTRA_PAI_ID, Integer.valueOf(id));
                    intent.putExtra(AuctionGinsengShootMarginActivity.EXTRA_PAI_BOND, bz_diamonds);
                    getActivity().startActivity(intent);
                }
            }
        });
    }

    public void bindData(App_pai_user_goods_detailActModel actModel)
    {
        if (actModel != null && actModel.getData() != null && actModel.getData().getInfo() != null)
        {
            id = actModel.getData().getInfo().getId();
            bz_diamonds = actModel.getData().getInfo().getBz_diamonds();
            is_true = actModel.getData().getInfo().getIs_true();
            SDViewBinder.setTextView(tv_bz_diamonds_bot, Integer.toString(bz_diamonds));
        }
    }
}
