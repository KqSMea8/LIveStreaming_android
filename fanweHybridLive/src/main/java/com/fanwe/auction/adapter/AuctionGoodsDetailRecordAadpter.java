package com.fanwe.auction.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.auction.model.PaiUserGoodsDetailDataPaiListItemModel;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/8/8.
 */
public class AuctionGoodsDetailRecordAadpter extends SDSimpleAdapter<PaiUserGoodsDetailDataPaiListItemModel>
{
    public AuctionGoodsDetailRecordAadpter(List<PaiUserGoodsDetailDataPaiListItemModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_auction_records;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, PaiUserGoodsDetailDataPaiListItemModel model)
    {
        TextView tv_user_name = ViewHolder.get(R.id.tv_user_name, convertView);
        ImageView iv_head_image = ViewHolder.get(R.id.iv_head_image, convertView);
        TextView tv_first = ViewHolder.get(R.id.tv_first, convertView);
        TextView tv_pai_date = ViewHolder.get(R.id.tv_pai_date, convertView);
        TextView tv_pai_diamonds = ViewHolder.get(R.id.tv_pai_diamonds, convertView);

        SDViewBinder.setTextView(tv_user_name, model.getUser_name());
        GlideUtil.loadHeadImage(model.getHead_image()).into(iv_head_image);
        if (position == 0)
        {
            tv_user_name.setTextColor(SDResourcesUtil.getColor(R.color.auction_main_color));
            tv_pai_date.setTextColor(SDResourcesUtil.getColor(R.color.auction_main_color));
            tv_pai_diamonds.setTextColor(SDResourcesUtil.getColor(R.color.auction_main_color));
            tv_first.setBackgroundResource(R.drawable.auction_layer_auction_main_color_corner_3dp);
            tv_first.setTextColor(SDResourcesUtil.getColor(R.color.white));
            tv_first.setText("领先");
        } else
        {
            tv_user_name.setTextColor(SDResourcesUtil.getColor(R.color.text_content));
            tv_pai_date.setTextColor(SDResourcesUtil.getColor(R.color.text_content));
            tv_pai_diamonds.setTextColor(SDResourcesUtil.getColor(R.color.text_content_deep));
            tv_first.setBackgroundResource(R.drawable.auction_layer_white_stroke_corner_3dp);
            tv_first.setTextColor(SDResourcesUtil.getColor(R.color.text_content));
            tv_first.setText("出局");
        }
        SDViewBinder.setTextView(tv_pai_date, model.getPai_date());
        SDViewBinder.setTextView(tv_pai_diamonds, model.getPai_diamonds());
    }

}
