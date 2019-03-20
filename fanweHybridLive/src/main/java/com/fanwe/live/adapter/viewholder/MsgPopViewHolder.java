package com.fanwe.live.adapter.viewholder;

import android.view.View;

import com.fanwe.library.adapter.SDRecyclerAdapter;
import com.fanwe.live.model.custommsg.CustomMsgPopMsg;
import com.fanwe.live.model.custommsg.MsgModel;

/**
 * 弹幕消息
 */
public class MsgPopViewHolder extends MsgTextViewHolder
{
    public MsgPopViewHolder(View itemView)
    {
        super(itemView);
    }

    @Override
    protected String getText()
    {
        CustomMsgPopMsg msg = (CustomMsgPopMsg) customMsg;
        return msg.getDesc();
    }
}
