package com.fanwe.libgame.wawa.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.games.R;
import com.fanwe.libgame.wawa.model.WawaItemModel;

import java.util.List;


/**
 * Created by caoliang.song on 2018/3/1.
 */

public class AutoPollAdapter extends RecyclerView.Adapter<AutoPollAdapter.BaseViewHolder> {
    private final Context mContext;
    private int[] advance_drawable;
    private boolean isTop;
    private BaseViewHolder baseViewHolder;
    private int isInVisiblePos;
    private List<WawaItemModel> list;

    public AutoPollAdapter(Context context, List<WawaItemModel> list, boolean isTop) {
        this.mContext = context;
        this.isTop=isTop;
        this.list = list;
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        if(isTop){
            view= LayoutInflater.from(mContext).inflate(R.layout.item_auto_poll_top, parent, false);
        } else {
            view= LayoutInflater.from(mContext).inflate(R.layout.item_auto_poll, parent, false);
        }
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        baseViewHolder=holder;
        position=position%list.size();
        holder.sort_icon.setImageResource(list.get(position).wawaDrawable);
        holder.sort_name.setText("x"+list.get(position).rate);
//        if(isInVisiblePos==position){
//            holder.sort_icon.setVisibility(View.INVISIBLE);
//        }else {
//            holder.sort_icon.setVisibility(View.VISIBLE);
//        }
    }
    public void updateIcon(int position){
        this.isInVisiblePos=position;
        notifyDataSetChanged();
//        baseViewHolder.sort_icon.setVisibility();
    }
    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder {
       public TextView sort_name;
       public ImageView sort_icon;
       public ImageView sort_icon_bottom;

        public BaseViewHolder(View convertView) {
            super(convertView);
            sort_name = (TextView) convertView.findViewById(R.id.sort_name);
            sort_icon = (ImageView) convertView.findViewById(R.id.sort_icon);
            sort_icon_bottom = (ImageView) convertView.findViewById(R.id.sort_icon);

        }
    }
}
