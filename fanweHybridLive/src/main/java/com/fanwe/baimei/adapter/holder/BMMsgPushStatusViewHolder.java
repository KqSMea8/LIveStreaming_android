package com.fanwe.baimei.adapter.holder;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.fanwe.baimei.model.custommsg.BMCustomMsgPushStatus;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.live.R;
import com.fanwe.live.adapter.viewholder.MsgTextViewHolder;
import com.fanwe.live.model.custommsg.CustomMsg;

/**
 * Created by yhz on 2017/6/14.
 */

public class BMMsgPushStatusViewHolder extends MsgTextViewHolder
{
    public BMMsgPushStatusViewHolder(View itemView)
    {
        super(itemView);
    }

    @Override
    protected void bindCustomMsg(int position, CustomMsg customMsg)
    {
        BMCustomMsgPushStatus msg = (BMCustomMsgPushStatus) customMsg;
        // 标题
        String title = SDResourcesUtil.getString(R.string.live_msg_title);
        int titleColor = SDResourcesUtil.getColor(R.color.live_msg_title);
        appendContent(title, titleColor);

        // 内容
        String text = msg.getDesc();
        int textColor = SDResourcesUtil.getColor(R.color.live_msg_content);
        String color = msg.getFonts_color();
        if (!TextUtils.isEmpty(color))
        {
            textColor = Color.parseColor(color);
        }
        appendContent(text, textColor);
    }
}
