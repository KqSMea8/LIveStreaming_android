package com.fanwe.shop.adapter.viewholder;

import android.view.View;

import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.live.R;
import com.fanwe.live.adapter.viewholder.MsgViewHolder;
import com.fanwe.live.model.custommsg.CustomMsg;
import com.fanwe.shop.model.custommsg.CustomMsgShopBuySuc;

/**
 * Created by Administrator on 2016/12/5.
 */

public class MsgAuctionShopBuySucViewHolder extends MsgViewHolder
{
    public MsgAuctionShopBuySucViewHolder(View itemView)
    {
        super(itemView);
    }

    @Override
    protected void bindCustomMsg(int position, CustomMsg customMsg)
    {
        CustomMsgShopBuySuc msg = (CustomMsgShopBuySuc) customMsg;
        appendUserInfo(msg.getUser());
        String text = msg.getDesc();
        int textColor = SDResourcesUtil.getColor(R.color.main_color);
        appendContent(text, textColor);
        setUserInfoClickListener(tv_content, msg.getUser());
    }
}
