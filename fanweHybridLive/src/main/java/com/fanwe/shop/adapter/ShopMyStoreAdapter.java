package com.fanwe.shop.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.shop.model.App_shop_mystoreItemModel;

import java.util.List;

/**
 * Created by Administrator on 2016/11/18.
 */

public class ShopMyStoreAdapter extends SDSimpleAdapter<App_shop_mystoreItemModel>
{
    private SDItemClickCallback<App_shop_mystoreItemModel> clickPushListener;
    private boolean mIsCreater;

    public void setmIsCreater(boolean mIsCreater)
    {
        this.mIsCreater = mIsCreater;
    }

    public void setClickPushListener(SDItemClickCallback<App_shop_mystoreItemModel> clickPushListener)
    {
        this.clickPushListener = clickPushListener;
    }

    public ShopMyStoreAdapter(List<App_shop_mystoreItemModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_shop_mystore;
    }

    @Override
    public void bindData(final int position, View convertView, ViewGroup parent, final App_shop_mystoreItemModel model)
    {
        ImageView iv_shop = ViewHolder.get(R.id.iv_shop, convertView);//商品图片
        TextView tv_shop = ViewHolder.get(R.id.tv_shop, convertView);//商品名称
        TextView tv_dec = ViewHolder.get(R.id.tv_dec, convertView);//商品名称
        TextView tv_price = ViewHolder.get(R.id.tv_price, convertView);//商品价格
        TextView tv_push = ViewHolder.get(R.id.tv_push, convertView);//推送
        if (!mIsCreater)
        {
            SDViewUtil.setGone(tv_push);
        } else
        {
            SDViewUtil.setVisible(tv_push);
        }

        List<String> list = model.getImgs();
        if (list != null && list.size() > 0)
        {
            GlideUtil.load(list.get(0)).into(iv_shop);
        }
        SDViewBinder.setTextView(tv_shop, model.getName());
        SDViewBinder.setTextView(tv_dec, model.getDescription());
        SDViewBinder.setTextView(tv_price, "¥ " + model.getPrice());

        /**
         * 推送
         */
        tv_push.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (clickPushListener != null)
                {
                    clickPushListener.onItemClick(position, model, v);
                }
            }
        });

        /**
         * 跳转至详情
         */
        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                notifyItemClickCallback(position, model, v);
            }
        });
    }
}
