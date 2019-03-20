package com.fanwe.auction.adapter;

import android.app.Activity;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.auction.model.MessageGetListDataListItemModel;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/8/11.
 */
public class AuctionTradeMsgAdapter extends SDSimpleAdapter<MessageGetListDataListItemModel>
{
    private int screenWidth;

    public AuctionTradeMsgAdapter(List<MessageGetListDataListItemModel> listModel, Activity activity)
    {
        super(listModel, activity);
        this.screenWidth = SDViewUtil.getScreenWidth();
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_auction_trade_msg;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, final MessageGetListDataListItemModel model)
    {
        ImageView civ_head_image = ViewHolder.get(R.id.civ_head_image, convertView);
        TextView tv_nick_name = ViewHolder.get(R.id.tv_nick_name, convertView);
        TextView tv_time = ViewHolder.get(R.id.tv_time, convertView);
        final TextView tv_content = ViewHolder.get(R.id.tv_content, convertView);
        final ImageView iv_arrow_down = ViewHolder.get(R.id.iv_arrow_down, convertView);

        GlideUtil.loadHeadImage(model.getSend_user_avatar()).into(civ_head_image);
        SDViewBinder.setTextView(tv_nick_name, model.getSend_user_name());

        Paint paint = new Paint();
        paint.setTextSize(tv_content.getTextSize());

        SDViewBinder.setTextView(tv_time, model.getCreate_date());
        SDViewBinder.setTextView(tv_content, model.getContent());

        float size = paint.measureText(tv_content.getText().toString());

        if (size > screenWidth - SDViewUtil.dp2px(84))
        {
            iv_arrow_down.setVisibility(View.VISIBLE);
        } else
        {
            iv_arrow_down.setVisibility(View.GONE);
        }

        if (!model.isFlag())
        {
            tv_content.setSingleLine(true);
        } else
        {
            tv_content.setSingleLine(false);
        }

        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!model.isFlag())
                {
                    model.setFlag(true);
                    tv_content.setSingleLine(false);
                    tv_content.setText(model.getContent());
                    iv_arrow_down.setImageResource(R.drawable.ic_arrow_up_gray);
                } else
                {
                    model.setFlag(false);
                    tv_content.setSingleLine(true);
                    iv_arrow_down.setImageResource(R.drawable.ic_arrow_down_gray);
                }
            }
        });
    }
}
