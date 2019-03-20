package com.fanwe.auction.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fanwe.auction.model.AuctionTabMeItemModel;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import java.util.List;

/**
 * Created by Administrator on 2016/10/21.
 */

public class AuctionTabMeItemNewAdapter extends SDSimpleAdapter<AuctionTabMeItemModel>
{
    public AuctionTabMeItemNewAdapter(List<AuctionTabMeItemModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_auction_tab_me_new_add_item;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup parent, final AuctionTabMeItemModel model)
    {
        RelativeLayout rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
        ImageView iv_center = (ImageView) convertView.findViewById(R.id.iv_center);
        TextView tv_left_name = (TextView) convertView.findViewById(R.id.tv_left_name);
        TextView tv_right_text = (TextView) convertView.findViewById(R.id.tv_right_text);
        ImageView view_line = (ImageView) convertView.findViewById(R.id.view_line);

        if (position == getData().size() - 1)
            SDViewUtil.setGone(view_line);
        else
            SDViewUtil.setVisible(view_line);

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
