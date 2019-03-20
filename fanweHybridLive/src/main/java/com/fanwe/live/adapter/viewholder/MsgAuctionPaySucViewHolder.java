package com.fanwe.live.adapter.viewholder;

import android.view.View;

import com.fanwe.auction.model.custommsg.CustomMsgAuctionPaySuccess;
import com.fanwe.library.adapter.SDRecyclerAdapter;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.live.R;
import com.fanwe.live.model.custommsg.CustomMsg;
import com.fanwe.live.model.custommsg.MsgModel;

/**
 * 支付成功
 * Created by Administrator on 2016/9/6.
 */
public class MsgAuctionPaySucViewHolder extends MsgViewHolder
{

    public MsgAuctionPaySucViewHolder(View itemView)
    {
        super(itemView);
    }

    @Override
    protected void bindCustomMsg(int position, CustomMsg customMsg)
    {
        CustomMsgAuctionPaySuccess msg = (CustomMsgAuctionPaySuccess) customMsg;
        appendUserInfo(msg.getUser());
        String text = msg.getDesc();
        int textColor = SDResourcesUtil.getColor(R.color.main_color);
        appendContent(text, textColor);
        setUserInfoClickListener(tv_content, msg.getUser());
    }

}
