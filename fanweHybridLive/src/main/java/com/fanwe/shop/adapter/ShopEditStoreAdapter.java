package com.fanwe.shop.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.shop.model.App_shop_mystoreItemModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/18.
 */
public class ShopEditStoreAdapter extends SDBaseSwipeAdapter<App_shop_mystoreItemModel>
{
    private SDItemClickCallback<App_shop_mystoreItemModel> clickEditCartListener;
    private SDItemClickCallback<App_shop_mystoreItemModel> clickDelGoodListener;
    private List<App_shop_mystoreItemModel> listModel;

    public void setClickEditCartListener(SDItemClickCallback<App_shop_mystoreItemModel> clickEditCartListener)
    {
        this.clickEditCartListener = clickEditCartListener;
    }

    public void setClickDelGoodListener(SDItemClickCallback<App_shop_mystoreItemModel> clickDelGoodListener)
    {
        this.clickDelGoodListener = clickDelGoodListener;
    }

    public ShopEditStoreAdapter(List<App_shop_mystoreItemModel> listModel, Activity activity)
    {
        super(listModel, activity);
        this.listModel = listModel;
    }

    @Override
    public int getSwipeLayoutResourceId(int var1)
    {
        return R.id.swipeLayout;
    }

    @Override
    public View generateView(final int position, ViewGroup parent)
    {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.item_shop_edite_store, null);
        return v;
    }

    @Override
    public void fillValues(final int position, View v)
    {
        final App_shop_mystoreItemModel model = listModel.get(position);
        LinearLayout ll_content = (LinearLayout) v.findViewById(R.id.ll_content);
        ImageView img_pod = (ImageView) v.findViewById(R.id.img_pod);//商品图片
        TextView txv_pod = (TextView) v.findViewById(R.id.txv_pod);//商品名称
        TextView tv_dec = (TextView) v.findViewById(R.id.tv_dec);//商品描述
        TextView txv_price = (TextView) v.findViewById(R.id.txv_price);//商品价格
        TextView txv_edit_cart = (TextView) v.findViewById(R.id.txv_edit_cart);//编辑商品
        TextView txv_del = (TextView) v.findViewById(R.id.txv_del);//商品删除

        if (model.getImgs() != null && model.getImgs().size() > 0)
        {
            GlideUtil.load(model.getImgs().get(0)).into(img_pod);
        }
        SDViewBinder.setTextView(txv_pod, model.getName());
        SDViewBinder.setTextView(tv_dec, model.getDescription());
        SDViewBinder.setTextView(txv_price, "¥ " + model.getPrice());
        txv_edit_cart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (clickEditCartListener != null)
                {
                    clickEditCartListener.onItemClick(position, model, view);
                }
            }
        });

        txv_del.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (clickDelGoodListener != null)
                {
                    clickDelGoodListener.onItemClick(position, model, view);
                }
            }
        });

        ll_content.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                closeAllItems();
            }
        });

    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return 0;
    }

    @Override
    public void bindData(final int position, View convertView, ViewGroup parent, final App_shop_mystoreItemModel model)
    {

    }

}
