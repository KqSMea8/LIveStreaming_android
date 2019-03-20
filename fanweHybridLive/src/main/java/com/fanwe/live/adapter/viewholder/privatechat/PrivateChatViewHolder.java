package com.fanwe.live.adapter.viewholder.privatechat;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.custommsg.CustomMsg;
import com.fanwe.live.model.custommsg.MsgModel;
import com.fanwe.live.utils.GlideUtil;

/**
 * Created by shibx on 2016/8/30.
 */
public abstract class PrivateChatViewHolder extends SDRecyclerViewHolder<MsgModel>
{

    private ImageView iv_head_img;

    //right
    private ImageView iv_resend;
    private ProgressBar pb_sending;

    private MsgModel msgModel;
    private CustomMsg mCustomMsg;

    private ClickListener clickListener;
    private View.OnClickListener onClickListener;

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setClickListener(ClickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    public PrivateChatViewHolder(View itemView)
    {
        super(itemView);
        iv_head_img = find(R.id.iv_head_img);

        //right
        iv_resend = find(R.id.iv_resend);
        pb_sending = find(R.id.pb_sending);
        this.setOnClickListener(this);
    }

    @Override
    public void onBindData(int position, MsgModel model)
    {
        msgModel = model;
        mCustomMsg = msgModel.getCustomMsg();

        bindHeadImage(iv_head_img, mCustomMsg.getSender());
        bindCustomMsg(position, mCustomMsg);

        //right
        bindSendState(iv_resend, pb_sending);
    }

    protected void bindHeadImage(ImageView iv_head_img, UserModel userModel)
    {
        if (userModel != null)
        {
            GlideUtil.loadHeadImage(userModel.getHead_image()).into(iv_head_img);
            iv_head_img.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (clickListener != null)
                    {
                        clickListener.onClickHeadImage(msgModel);
                    }
                }
            });
        }
    }

    protected void bindSendState(ImageView iv_resend, ProgressBar pb_sending)
    {
        // 重新发送
        if (iv_resend != null && pb_sending != null)
        {
            iv_resend.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (clickListener != null)
                    {
                        clickListener.onClickResend(msgModel);
                    }
                }
            });

            switch (msgModel.getStatus())
            {
                case Sending:
                    SDViewUtil.setVisible(pb_sending);
                    SDViewUtil.setGone(iv_resend);
                    break;
                case SendSuccess:
                    SDViewUtil.setGone(iv_resend);
                    SDViewUtil.setGone(pb_sending);
                    break;
                case SendFail:
                    SDViewUtil.setVisible(iv_resend);
                    SDViewUtil.setGone(pb_sending);
                    break;
                default:
                    SDViewUtil.setGone(iv_resend);
                    SDViewUtil.setGone(pb_sending);
                    break;
            }
        }
    }

    protected abstract void bindCustomMsg(int position, CustomMsg customMsg);


    public interface ClickListener
    {
        void onClickResend(MsgModel model);

        void onClickHeadImage(MsgModel model);
        void onClickBack();
        void onLongClick(MsgModel model, View v);
    }
}
