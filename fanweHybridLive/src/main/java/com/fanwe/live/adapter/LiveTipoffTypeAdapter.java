package com.fanwe.live.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.model.ImageModel;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_tipoff_typeActModel;
import com.fanwe.live.model.App_tipoff_typeModel;
import com.fanwe.live.model.ShopModel;
import com.fanwe.live.utils.GlideUtil;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-5-26 下午7:41:51 类说明
 */
public class LiveTipoffTypeAdapter extends BaseAdapter {
    List<App_tipoff_typeModel> list;
    Context mContext;

    public LiveTipoffTypeAdapter(Context c, List<App_tipoff_typeModel> list) {
        mContext = c;
        this.list = list;
    }

    // 获取图片的个数
    public int getCount() {
        return list.size();
    }

    // 获取图片在库中的位置
    public Object getItem(int position) {
        return position;
    }


    // 获取图片ID
    public long getItemId(int position) {
        return position;
    }

    int index=-1;
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_live_tipoff_type, null);
        TextView tv= (TextView) convertView.findViewById(R.id.tv);
        final App_tipoff_typeModel model=list.get(position);
        if(model.isSelected()){
            tv.setBackgroundResource(R.drawable.layer_tip_checked);
        }else{
            tv.setBackgroundResource(R.drawable.layer_tip_normal);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setSelected(!model.isSelected());
                if(index!=position&&index>-1){
                    list.get(index).setSelected(false);
                }
                if(index==position&&!model.isSelected()){
                    index=-1;
                }else{
                    index=position;
                }
                notifyDataSetChanged();
            }
        });
        tv.setText(list.get(position).getName());
        return convertView;
    }

    public long getSelectedId() {
        if(index==-1){
            return index;
        }
        return list.get(index).getId();
    }
}
