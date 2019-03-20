package com.fanwe.live.adapter.viewholder;

import android.view.View;

import com.fanwe.library.adapter.SDRecyclerAdapter;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.live.R;
import com.fanwe.live.model.custommsg.CustomMsg;
import com.fanwe.live.model.custommsg.CustomMsgViewerJoin;
import com.fanwe.live.model.custommsg.MsgModel;

/**
 * 普通用户加入提示
 */
public class MsgViewerJoinViewHolder extends MsgTextViewHolder
{
    public MsgViewerJoinViewHolder(View itemView)
    {
        super(itemView);
    }

    @Override
    protected void bindCustomMsg(int position, CustomMsg customMsg)
    {
        //title
        String title = SDResourcesUtil.getString(R.string.live_msg_title);
        int titleColor = SDResourcesUtil.getColor(R.color.live_msg_title);
        appendContent(title, titleColor);
        CustomMsgViewerJoin customMsgViewerJoin=(CustomMsgViewerJoin)customMsg;
        // 内容
        String text = customMsg.getSender().getNick_name() + " 来了";
        // 内容
        if(null!=customMsgViewerJoin.getMount()){
            if(null!=customMsgViewerJoin.getMount().getDesc()){
                text =customMsg.getSender().getNick_name()+ customMsgViewerJoin.getMount().getDesc();
            }
        }
        int textColor = SDResourcesUtil.getColor(R.color.live_msg_content);
        appendContent(text, textColor);
        setUserInfoClickListener(tv_content);
    }
}
