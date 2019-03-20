package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.live.R;
import com.fanwe.live.activity.room.LivePlaybackActivity;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.shop.model.App_shop_mystoreItemModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/18.
 */
public class O2OShoppingPodCastAdapter extends SDSimpleAdapter<App_shop_mystoreItemModel>
{
    private boolean isPlayback = false;//是否回放
    private String createrId;//主播Id
    private SDItemClickCallback<App_shop_mystoreItemModel> clickToDetailListener;
    private SDItemClickCallback<App_shop_mystoreItemModel> clickPushListener;
    private UserModel dao = UserModelDao.query();

    public void setClickToDetailListener(SDItemClickCallback<App_shop_mystoreItemModel> clickToDetailListener)
    {
        this.clickToDetailListener = clickToDetailListener;
    }

    public void setClickPushListener(SDItemClickCallback<App_shop_mystoreItemModel> clickPushListener)
    {
        this.clickPushListener = clickPushListener;
    }

    public O2OShoppingPodCastAdapter(List<App_shop_mystoreItemModel> listModel, Activity activity, String id)
    {
        super(listModel, activity);
        createrId = id;
        if (activity instanceof LivePlaybackActivity)
        {
            isPlayback = true;
        }
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.o2o_item_shopping_pod_cast;
    }

    @Override
    public void bindData(final int position, View convertView, ViewGroup parent, final App_shop_mystoreItemModel model)
    {
        LinearLayout ll_all = ViewHolder.get(R.id.ll_all, convertView);
        ImageView img_pod = ViewHolder.get(R.id.img_pod, convertView);//商品图片
        TextView txv_pod = ViewHolder.get(R.id.txv_pod, convertView);//商品名称
        TextView txv_price = ViewHolder.get(R.id.txv_price, convertView);//商品价格
        TextView txv_add_cart = ViewHolder.get(R.id.txv_add_cart, convertView);//推送

        List<String> list = model.getImgs();
        if (list != null && list.size() > 0)
        {
            GlideUtil.load(list.get(0)).into(img_pod);
        }
        SDViewBinder.setTextView(txv_pod, model.getName());
        SDViewBinder.setTextView(txv_price, "¥ " + model.getPrice());

        if (dao.getUser_id().equals(createrId))
        {
            if (isPlayback)
            {
                SDViewUtil.setGone(txv_add_cart);
            } else
            {
                SDViewUtil.setVisible(txv_add_cart);
            }

            /**
             * 推送
             */
            txv_add_cart.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (clickPushListener != null)
                    {
                        clickPushListener.onItemClick(position, model, view);
                    }
                }
            });
        } else
        {
            SDViewUtil.setGone(txv_add_cart);
        }

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
    }
}
