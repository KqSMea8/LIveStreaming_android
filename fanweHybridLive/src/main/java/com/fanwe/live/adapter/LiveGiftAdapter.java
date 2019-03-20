package com.fanwe.live.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.model.LiveGiftModel;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.live.view.CustomerTextView;

import java.util.List;

public class LiveGiftAdapter extends SDSimpleRecyclerAdapter<LiveGiftModel> {
    public LiveGiftAdapter(List<LiveGiftModel> listModel, Activity activity) {
        super(listModel, activity);
    }

    int type = 0;//0默认商城

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<LiveGiftModel> holder, final int position, final LiveGiftModel model) {
        ImageView iv_gift = holder.get(R.id.iv_gift);
        ImageView iv_much = holder.get(R.id.iv_much);
        ImageView iv_hot = holder.get(R.id.iv_hot);
        ImageView iv_luck = holder.get(R.id.iv_luck);
        RelativeLayout rl = holder.get(R.id.rl);
        TextView tv_price = holder.get(R.id.tv_price);
        TextView tv_name = holder.get(R.id.tv_name);
        TextView tv_ticket = holder.get(R.id.tv_ticket);
        TextView tv_num = holder.get(R.id.tv_num);
        LinearLayout ll_price = holder.get(R.id.ll_price);
        if ("1".equals(model.getIs_award())) {
            SDViewUtil.setVisible(iv_luck);
        } else {
            SDViewUtil.setInvisible(iv_luck);
        }

        if ("1".equals(model.getIs_heat())) {
            SDViewUtil.setVisible(iv_hot);
            SDViewUtil.setGone(iv_much);
        } else if (model.getIs_much() == 1) {
            SDViewUtil.setVisible(iv_much);
            SDViewUtil.setGone(iv_hot);
        } else {
            SDViewUtil.setGone(iv_much);
            SDViewUtil.setGone(iv_hot);
        }
        if (model.isSelected()) {
            rl.setBackgroundResource(R.drawable.gift_checked_bg);
//            SDViewUtil.setGone(iv_much);
            SDViewUtil.setVisible(tv_ticket);
//            SDViewUtil.setGone(iv_hot);
//            SDViewUtil.setGone(iv_luck);
        } else {
            rl.setBackgroundResource(R.color.transparent);
//            SDViewUtil.setGone(iv_selected);
            SDViewUtil.setInvisible(tv_ticket);

        }
        if (type == 1) {
            ll_price.setVisibility(View.GONE);
            SDViewBinder.setTextView(tv_num, "X" + model.getNum());
        } else {
            SDViewBinder.setTextView(tv_price, String.valueOf(model.getDiamonds()));
            tv_num.setVisibility(View.GONE);
        }
        SDViewBinder.setTextView(tv_ticket, "主播可获秀票 " + model.getTicket());
        SDViewBinder.setTextView(tv_name, model.getName() + "");
        GlideUtil.load(model.getIcon()).into(iv_gift);
        LogUtil.i(String.valueOf(position));
    }

    @Override
    public int getLayoutId(ViewGroup parent, int viewType) {
        return R.layout.item_live_gift;
    }

}
