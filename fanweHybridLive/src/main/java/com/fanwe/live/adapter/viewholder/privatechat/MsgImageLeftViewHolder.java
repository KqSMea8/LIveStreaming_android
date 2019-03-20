package com.fanwe.live.adapter.viewholder.privatechat;

import android.view.View;
import android.widget.ImageView;

import com.fanwe.library.adapter.SDRecyclerAdapter;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.model.custommsg.CustomMsg;
import com.fanwe.live.model.custommsg.CustomMsgPrivateImage;
import com.fanwe.live.model.custommsg.MsgModel;
import com.fanwe.live.utils.GlideUtil;

/**
 * Created by Administrator on 2016/8/30.
 */
public class MsgImageLeftViewHolder extends PrivateChatViewHolder
{
    private ImageView iv_image;

    public MsgImageLeftViewHolder(View itemView)
    {
        super(itemView);
        iv_image = find(R.id.iv_image);
    }

    @Override
    protected void bindCustomMsg(final int position, CustomMsg customMsg)
    {
        CustomMsgPrivateImage msg = (CustomMsgPrivateImage) customMsg;

        SDViewUtil.setSize(iv_image, msg.getViewWidth(), msg.getViewHeight());

        final String uri = msg.getAvailableUri();
        bindImage(uri, iv_image);

    }


    protected void bindImage(String uri, ImageView iv_image)
    {
        GlideUtil.load(uri).into(iv_image);
    }
}
