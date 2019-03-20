package com.fanwe.shop.appview.room;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.dialog.SDDialogProgress;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveFloatViewWebViewActivity;
import com.fanwe.live.activity.LiveWebViewActivity;
import com.fanwe.live.appview.room.RoomView;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.shop.common.ShopCommonInterface;
import com.fanwe.shop.model.App_pai_user_open_goods_urlActModel;
import com.fanwe.shop.model.ShopGoodsDetailModel;
import com.fanwe.shop.model.custommsg.CustomMsgShopPush;

import org.xutils.view.annotation.ViewInject;

/**
 * 购物直播商品推送
 * Created by Administrator on 2016/9/21.
 */

public class RoomShopGoodsPushView extends RoomView
{
    @ViewInject(R.id.ll_goods)
    private LinearLayout ll_goods;
    @ViewInject(R.id.iv_good)
    private ImageView iv_good;
    @ViewInject(R.id.tv_goods_name)
    private TextView tv_goods_name;
    @ViewInject(R.id.tv_price)
    private TextView tv_price;
    @ViewInject(R.id.ll_give)
    private LinearLayout ll_give;

    private CustomMsgShopPush customMsgShopPush;
    private String goods_id;
    private String createrId;

    public RoomShopGoodsPushView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public RoomShopGoodsPushView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public RoomShopGoodsPushView(Context context, String createrId, String goods_id)
    {
        super(context);
        this.createrId = createrId;
        this.goods_id = goods_id;
        init();
    }

    protected void init()
    {
        setContentView(R.layout.view_shop_good_push);
        register();
    }

    public void bindData(CustomMsgShopPush customMsgShopPush)
    {
        this.customMsgShopPush = customMsgShopPush;
        if (customMsgShopPush != null && customMsgShopPush.getGoods() != null)
        {
            ShopGoodsDetailModel goods = customMsgShopPush.getGoods();
            if (goods.getType() == 1)
            {
                SDViewUtil.setGone(ll_give);
            } else
            {
                if (!getLiveActivity().isCreater())
                {
                    SDViewUtil.setVisible(ll_give);
                } else
                {
                    SDViewUtil.setGone(ll_give);
                }
            }

            ShopGoodsDetailModel shopGoodsDetailModel = customMsgShopPush.getGoods();
            GlideUtil.load(shopGoodsDetailModel.getImgs()).into(iv_good);
            SDViewBinder.setTextView(tv_goods_name, shopGoodsDetailModel.getName());
            SDViewBinder.setTextView(tv_price, shopGoodsDetailModel.getPrice());
        }
    }

    private void register()
    {
        ll_goods.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (customMsgShopPush == null)
                {
                    return;
                }
                ShopGoodsDetailModel goods = customMsgShopPush.getGoods();
                if (goods == null)
                {
                    return;
                }
                int type = goods.getType();
                if (type == 1)
                {
                    Intent intent = new Intent(getActivity(), LiveWebViewActivity.class);
                    intent.putExtra(LiveWebViewActivity.EXTRA_URL, goods.getUrl());
                    getActivity().startActivity(intent);
                } else
                {
                    requestGoodsUrl();
                }
            }
        });
    }

    private void requestGoodsUrl()
    {
        ShopCommonInterface.requestShopGoodsUrl(goods_id, getLiveActivity().getCreaterId(), new AppRequestCallback<App_pai_user_open_goods_urlActModel>()
        {
            private SDDialogProgress dialog = new SDDialogProgress(getActivity());

            @Override
            protected void onStart()
            {
                super.onStart();
                dialog.setTextMsg("");
                dialog.show();
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                dialog.dismiss();
            }

            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.getStatus() == 1)
                {
                    Intent intent = new Intent(getActivity(), LiveFloatViewWebViewActivity.class);
                    intent.putExtra(LiveWebViewActivity.EXTRA_URL, actModel.getUrl());
                    getActivity().startActivity(intent);
                }
            }
        });
    }
}
