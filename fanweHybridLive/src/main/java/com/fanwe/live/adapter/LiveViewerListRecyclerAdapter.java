package com.fanwe.live.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.dialog.LiveUserInfoDialog;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.utils.GlideUtil;

import java.text.DecimalFormat;

/**
 * 直播间观众列表
 */
public class LiveViewerListRecyclerAdapter extends SDSimpleRecyclerAdapter<UserModel> {
    public LiveViewerListRecyclerAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public int getLayoutId(ViewGroup parent, int viewType) {
        return R.layout.item_live_user;
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<UserModel> holder, int position, final UserModel model) {
        ImageView iv_pic = holder.get(R.id.iv_pic);
        ImageView iv_level = holder.get(R.id.iv_level);
        TextView tv_ticket = holder.get(R.id.tv_ticket);
        GlideUtil.loadHeadImage(model.getHead_image()).into(iv_pic);
        if (!TextUtils.isEmpty(model.getV_icon())) {
            SDViewUtil.setVisible(iv_level);
            GlideUtil.load(model.getV_icon()).into(iv_level);
        } else {
            SDViewUtil.setGone(iv_level);
        }
        if (!"0".equals(formatTicket(model.getUser_ticket()))) {
            SDViewUtil.setVisible(tv_ticket);
        }else{
            SDViewUtil.setGone(tv_ticket);
        }
        tv_ticket.setText(formatTicket(model.getUser_ticket()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LiveUserInfoDialog dialog = new LiveUserInfoDialog(getActivity(), model.getUser_id());
                dialog.show();
            }
        });
    }
    private String formatTicket(String ticket){
        String user_ticket="0";
        try {
            int num = Integer.valueOf(ticket).intValue();
            if(num>1000000){
                user_ticket=num/10000+"万";
            }else if(num>100000){
                user_ticket=num/10000+"."+num%10000/1000+"万";
            }else if(num>10000){
                user_ticket=num/10000+"."+num%10000/100+"万";
            }else{
                user_ticket=num+"";
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return user_ticket;
    }
}
