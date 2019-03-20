package com.fanwe.live.adapter.viewholder;

import android.view.View;

import com.fanwe.library.adapter.SDRecyclerAdapter;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.live.R;
import com.fanwe.live.model.custommsg.CustomMsg;
import com.fanwe.live.model.custommsg.CustomMsgViewerJoin;
import com.fanwe.live.model.custommsg.MsgModel;

/**
 * 高级用户加入提示
 */
public class MsgProViewerJoinViewHolder extends MsgTextViewHolder
{
    public MsgProViewerJoinViewHolder(View itemView)
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
        String text="金光一闪"+customMsg.getSender().getNick_name()+"进来了";
        if(null!=customMsgViewerJoin.getMount()){
            if(null!=customMsgViewerJoin.getMount().getDesc()){
                text ="金光一闪"+ customMsg.getSender().getNick_name()+customMsgViewerJoin.getMount().getDesc();
            }
        }
        int textColor = SDResourcesUtil.getColor(R.color.live_msg_content);
        appendContent(text, textColor);
        setUserInfoClickListener(tv_content);
    }
}
