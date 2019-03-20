package com.fanwe.auction.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.auction.model.AuctionTabMeItemModel;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;

import java.util.List;

/**
 * Created by Administrator on 2016/8/11.
 */
public class AuctionTabMeItemAdapter extends SDSimpleAdapter<AuctionTabMeItemModel>
{
    public AuctionTabMeItemAdapter(List<AuctionTabMeItemModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_auction_tab_me_add_item;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup parent, final AuctionTabMeItemModel model)
    {
        RelativeLayout rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
        ImageView iv_center = (ImageView) convertView.findViewById(R.id.iv_center);
        TextView tv_left_name = (TextView) convertView.findViewById(R.id.tv_left_name);
        TextView tv_right_text = (TextView) convertView.findViewById(R.id.tv_right_text);

        if (position % 2 == 0)
        {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, SDViewUtil.dp2px(1), SDViewUtil.dp2px(2));
            rl_item.setLayoutParams(layoutParams);
        } else
        {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(SDViewUtil.dp2px(1), 0, 0, SDViewUtil.dp2px(2));//4个参数按顺序分别是左上右下
            rl_item.setLayoutParams(layoutParams);
        }

        if (model.isBlankPage())
        {

        } else
        {
            iv_center.setImageResource(model.getImage_Res());
            SDViewBinder.setTextView(tv_left_name, model.getLeft_text());
            SDViewBinder.setTextView(tv_right_text, model.getRight_text());

            convertView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    notifyItemClickCallback(position, model, v);
                }
            });
        }
    }
}
