package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.shop.model.App_shop_mystoreItemModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/18.
 */
public class O2OShoppingMystoreAdapter extends SDSimpleAdapter<App_shop_mystoreItemModel>
{
    public O2OShoppingMystoreAdapter(List<App_shop_mystoreItemModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.o2o_item_shopping_mystore;
    }

    @Override
    public void bindData(final int position, View convertView, ViewGroup parent, final App_shop_mystoreItemModel model)
    {
        ImageView img_pod = ViewHolder.get(R.id.img_pod, convertView);//商品图片
        TextView txv_pod = ViewHolder.get(R.id.txv_pod, convertView);//商品名称
        TextView txv_price = ViewHolder.get(R.id.txv_price, convertView);//商品价格

        if (model.getImgs() != null && model.getImgs().size() > 0)
        {
            GlideUtil.load(model.getImgs().get(0)).into(img_pod);
        }
        SDViewBinder.setTextView(txv_pod, model.getName());
        SDViewBinder.setTextView(txv_price, "¥ " + model.getPrice());
    }
}
