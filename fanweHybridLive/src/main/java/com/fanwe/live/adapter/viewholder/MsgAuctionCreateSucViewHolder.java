package com.fanwe.live.adapter.viewholder;

import android.view.View;

import com.fanwe.auction.model.custommsg.CustomMsgAuctionCreateSuccess;
import com.fanwe.library.adapter.SDRecyclerAdapter;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.live.R;
import com.fanwe.live.model.custommsg.CustomMsg;
import com.fanwe.live.model.custommsg.MsgModel;

/**
 * 主播创建竞拍
 * Created by Administrator on 2016/9/7.
 */
public class MsgAuctionCreateSucViewHolder extends MsgViewHolder{

    public MsgAuctionCreateSucViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void bindCustomMsg(int position, CustomMsg customMsg) {
        CustomMsgAuctionCreateSuccess msg = (CustomMsgAuctionCreateSuccess) customMsg;
//        appendUserInfo(msg.getUser());

        //title
        String title = SDResourcesUtil.getString(R.string.live_msg_auction_title);
        int titleColor = SDResourcesUtil.getColor(R.color.live_msg_title);
        appendContent(title, titleColor);

        // 内容
        String text = msg.getDesc();
        int textColor = SDResourcesUtil.getColor(R.color.main_color);
        appendContent(text, textColor);
        setUserInfoClickListener(tv_content, msg.getUser());
    }
}
