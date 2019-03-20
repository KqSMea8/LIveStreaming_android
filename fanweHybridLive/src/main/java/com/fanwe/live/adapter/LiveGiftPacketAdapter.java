package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.model.LiveGiftModel;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

public class LiveGiftPacketAdapter extends SDSimpleRecyclerAdapter<LiveGiftModel> {
    public LiveGiftPacketAdapter(List<LiveGiftModel> listModel, Activity activity) {
        super(listModel, activity);
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<LiveGiftModel> holder, final int position, final LiveGiftModel model) {
        ImageView iv_gift = holder.get(R.id.iv_gift);
        ImageView iv_selected = holder.get(R.id.iv_selected);
        ImageView iv_much = holder.get(R.id.iv_much);
        ImageView iv_hot = holder.get(R.id.iv_hot);
        ImageView iv_luck = holder.get(R.id.iv_luck);
        TextView tv_price = holder.get(R.id.tv_price);
        TextView tv_name = holder.get(R.id.tv_name);
        TextView tv_ticket = holder.get(R.id.tv_ticket);
        if (model.isSelected()) {
            SDViewUtil.setVisible(iv_selected);
            SDViewUtil.setGone(iv_much);
            SDViewUtil.setVisible(tv_ticket);
            SDViewUtil.setGone(iv_hot);
            SDViewUtil.setGone(iv_luck);
        } else {
            SDViewUtil.setGone(iv_selected);
            SDViewUtil.setInvisible(tv_ticket);
            if("1".equals(model.getIs_heat())){
                SDViewUtil.setVisible(iv_hot);
                SDViewUtil.setGone(iv_much);
                SDViewUtil.setGone(iv_luck);
            }else if("1".equals(model.getIs_award())){
                SDViewUtil.setVisible(iv_luck);
                SDViewUtil.setGone(iv_much);
                SDViewUtil.setGone(iv_hot);
            }else if(model.getIs_much()==1){
                SDViewUtil.setVisible(iv_much);
                SDViewUtil.setGone(iv_hot);
                SDViewUtil.setGone(iv_luck);
            }else{
                SDViewUtil.setGone(iv_much);
                SDViewUtil.setGone(iv_hot);
                SDViewUtil.setGone(iv_luck);
            }
            SDViewUtil.setGone(iv_selected);
        }
        SDViewBinder.setTextView(tv_price, String.valueOf(model.getDiamonds()));
        SDViewBinder.setTextView(tv_ticket, "主播可获秀票 "+model.getTicket());
        SDViewBinder.setTextView(tv_name, model.getName()+"");
        GlideUtil.load(model.getIcon()).into(iv_gift);
        LogUtil.i(String.valueOf(position));
    }

    @Override
    public int getLayoutId(ViewGroup parent, int viewType) {
        return R.layout.item_live_gift;
    }

}
