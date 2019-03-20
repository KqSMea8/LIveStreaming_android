package com.fanwe.live.adapter.viewholder.privatechat;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDRecyclerAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.model.custommsg.CustomMsg;
import com.fanwe.live.model.custommsg.CustomMsgPrivateGift;
import com.fanwe.live.model.custommsg.MsgModel;
import com.fanwe.live.utils.GlideUtil;

/**
 * Created by Administrator on 2016/8/30.
 */
public class MsgGiftLeftViewHolder extends PrivateChatViewHolder
{
    public ImageView iv_gift;
    public TextView tv_msg;

    public MsgGiftLeftViewHolder(View itemView)
    {
        super(itemView);
        iv_gift = find(R.id.iv_gift);
        tv_msg = find(R.id.tv_msg);
    }

    @Override
    protected void bindCustomMsg(int position, CustomMsg customMsg)
    {
        CustomMsgPrivateGift msg = (CustomMsgPrivateGift) customMsg;

        // 图片
        GlideUtil.load(msg.getProp_icon()).into(iv_gift);
        SDViewBinder.setTextView(tv_msg, msg.getTo_msg());
    }
}
