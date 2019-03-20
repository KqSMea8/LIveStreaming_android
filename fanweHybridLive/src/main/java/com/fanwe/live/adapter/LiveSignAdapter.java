package com.fanwe.live.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.live.R;
import com.fanwe.live.model.App_tipoff_typeModel;
import com.fanwe.live.model.SignDailyModel;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-5-26 下午7:41:51 类说明
 */
public class LiveSignAdapter extends BaseAdapter {
    List<SignDailyModel.ListBean> list;
    Context mContext;

    public LiveSignAdapter(Context c, List<SignDailyModel.ListBean> list) {
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

    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sign, null);
        TextView tv_day= (TextView) convertView.findViewById(R.id.tv_day);
        TextView tv_desc= (TextView) convertView.findViewById(R.id.tv_desc);
        TextView tv_num= (TextView) convertView.findViewById(R.id.tv_num);
        ImageView iv= (ImageView) convertView.findViewById(R.id.iv_image);
        final SignDailyModel.ListBean model=list.get(position);
        if(1==model.getIs_sign()){
            iv.setImageResource(R.drawable.signed);
        }else{
            GlideUtil.load(model.getIcon()).into(iv);
        }
        tv_day.setText(model.getName());
        tv_desc.setText(model.getProp_name());
        tv_num.setText("X"+model.getNum());
        return convertView;
    }
}
