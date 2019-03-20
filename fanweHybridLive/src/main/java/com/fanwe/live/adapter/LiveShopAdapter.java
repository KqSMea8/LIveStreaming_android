package com.fanwe.live.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.ShopModel;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.shortvideo.activity.ShortVideoDetailActivity;
import com.fanwe.shortvideo.appview.mian.ItemShortVideoView;
import com.fanwe.shortvideo.model.ShortVideoModel;

import java.util.ArrayList;

/**
 * @author wxy
 */
public class LiveShopAdapter extends SDSimpleRecyclerAdapter<ShopModel.MountRuleListBean> {

    private Activity activity;
    private int tag = 0;
    public LiveShopAdapter(ArrayList<ShopModel.MountRuleListBean> listModel, Activity activity) {
        super(listModel, activity);
        this.activity = activity;
    }
    public void setTag(int tag) {
        this.tag = tag;
        notifyDataSetChanged();
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<ShopModel.MountRuleListBean> holder, final int position, final ShopModel.MountRuleListBean model) {
        LinearLayout ll=holder.get(R.id.ll_parent);
        ImageView iv = holder.get(R.id.iv_image);
        TextView tv_name = holder.get(R.id.tv_name);
        TextView tv_plugin_price = holder.get(R.id.tv_plugin_price);
        GlideUtil.load(model.getIcon()).into(iv);
        SDViewBinder.setTextView(tv_name,model.getName());
        SDViewBinder.setTextView(tv_plugin_price,model.getRules().get(0).getDiamonds()+"/"+model.getRules().get(0).getDay_length()+"天");
        ll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onShopItemClickListener.onClickItemView(model);
            }
        });
    }


    @Override
    public int getLayoutId(ViewGroup parent, int viewType) {
        return R.layout.item_shop;
    }

    protected void requestData(String sv_id) {
        CommonInterface.requestDelVideo(sv_id, new AppRequestCallback<BaseActModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.isOk()) {

                }
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
            }
        });
    }
    private OnShopItemClickListener onShopItemClickListener;

    public void setOnShopItemClickListener(OnShopItemClickListener onShopItemClickListener) {
        this.onShopItemClickListener = onShopItemClickListener;
    }

    public interface OnShopItemClickListener
    {
        void onClickItemView(ShopModel.MountRuleListBean model);
    }
}
