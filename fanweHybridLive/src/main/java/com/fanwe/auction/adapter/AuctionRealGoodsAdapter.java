package com.fanwe.auction.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.auction.model.App_shop_paigoodsItemModel;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * 设置为实物拍品
 * Created by Administrator on 2016/10/11.
 */

public class AuctionRealGoodsAdapter extends SDSimpleAdapter<App_shop_paigoodsItemModel>
{
    private SDItemClickCallback<App_shop_paigoodsItemModel> clickToDetailListener;
    private SDItemClickCallback<App_shop_paigoodsItemModel> clickAuctionListener;

    public void setClickToDetailListener(SDItemClickCallback<App_shop_paigoodsItemModel> clickToDetailListener)
    {
        this.clickToDetailListener = clickToDetailListener;
    }

    public void setClickAuctionListener(SDItemClickCallback<App_shop_paigoodsItemModel> clickAuctionListener)
    {
        this.clickAuctionListener = clickAuctionListener;
    }

    public AuctionRealGoodsAdapter(List<App_shop_paigoodsItemModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_auction_real_good;
    }

    @Override
    public void bindData(final int position, View convertView, ViewGroup parent, final App_shop_paigoodsItemModel model)
    {
        LinearLayout ll_all = ViewHolder.get(R.id.ll_all, convertView);
        ImageView img_good = ViewHolder.get(R.id.img_good, convertView);//商品图片
        TextView tv_good = ViewHolder.get(R.id.tv_good, convertView);//商品名称
        TextView tv_price = ViewHolder.get(R.id.tv_price, convertView);//商品价格
        TextView tv_set_auction = ViewHolder.get(R.id.tv_set_auction, convertView);//设为拍品

        List<String> list = model.getImgs();
        if (list != null & list.size() > 0)
        {
            GlideUtil.load(list.get(0)).into(img_good);
        }
        SDViewBinder.setTextView(tv_good, model.getName());
        SDViewBinder.setTextView(tv_price, model.getPrice());

        /**
         * 跳转至详情页
         */
        ll_all.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (clickToDetailListener != null)
                {
                    clickToDetailListener.onItemClick(position, model, view);
                }
            }
        });

        /**
         * 设置为拍品
         */
        tv_set_auction.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (clickAuctionListener != null)
                {
                    clickAuctionListener.onItemClick(position, model, view);
                }
            }
        });
    }
}
