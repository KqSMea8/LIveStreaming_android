package com.fanwe.auction.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fanwe.auction.model.PaiCommodityDetailGoodsDetailModel;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/11/25.
 */

public class AuctionGoodsDetailAdapter extends SDSimpleAdapter<PaiCommodityDetailGoodsDetailModel>
{
    public AuctionGoodsDetailAdapter(List<PaiCommodityDetailGoodsDetailModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_auction_goods_detail_pic;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, PaiCommodityDetailGoodsDetailModel model)
    {
        ImageView iv_auction_detail = get(R.id.iv_auction_detail,convertView);

        if (model.getImage_url() != null)
            GlideUtil.load(model.getImage_url()).into(iv_auction_detail);
    }
}
