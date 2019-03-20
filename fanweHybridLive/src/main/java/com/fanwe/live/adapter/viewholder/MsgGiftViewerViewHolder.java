package com.fanwe.live.adapter.viewholder;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.fanwe.library.adapter.SDRecyclerAdapter;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.live.R;
import com.fanwe.live.model.custommsg.CustomMsg;
import com.fanwe.live.model.custommsg.CustomMsgGift;
import com.fanwe.live.model.custommsg.MsgModel;
import com.fanwe.live.span.LiveMsgGiftSpan;

/**
 * 观众礼物提示
 */
public class MsgGiftViewerViewHolder extends MsgTextViewHolder
{
    public MsgGiftViewerViewHolder(View itemView)
    {
        super(itemView);
    }

    @Override
    protected void bindCustomMsg(int position, CustomMsg customMsg)
    {
        CustomMsgGift msg = (CustomMsgGift) customMsg;

        appendUserInfo(msg.getSender());

        String text = msg.getDesc();
        int textColor = SDResourcesUtil.getColor(R.color.live_msg_send_gift);
        String color = msg.getFonts_color();
        if (!TextUtils.isEmpty(color))
        {
            textColor = Color.parseColor(color);
        }
        appendContent(text, textColor);

        // 礼物
        String url = msg.getIcon();
        LiveMsgGiftSpan giftSpan = new LiveMsgGiftSpan(tv_content);
        giftSpan.setImage(url);
        sb.appendSpan(giftSpan, "gift");

        setUserInfoClickListener(tv_content);
    }
}
