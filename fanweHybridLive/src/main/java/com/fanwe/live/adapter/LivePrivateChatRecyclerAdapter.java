package com.fanwe.live.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fanwe.library.adapter.SDRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.live.LiveConstant;
import com.fanwe.live.R;
import com.fanwe.live.activity.PhotoViewActivity;
import com.fanwe.live.adapter.viewholder.privatechat.MsgGiftLeftViewHolder;
import com.fanwe.live.adapter.viewholder.privatechat.MsgGiftRightViewHolder;
import com.fanwe.live.adapter.viewholder.privatechat.MsgImageLeftViewHolder;
import com.fanwe.live.adapter.viewholder.privatechat.MsgImageRightViewHolder;
import com.fanwe.live.adapter.viewholder.privatechat.MsgTextLeftViewHolder;
import com.fanwe.live.adapter.viewholder.privatechat.MsgTextRightViewHolder;
import com.fanwe.live.adapter.viewholder.privatechat.MsgVoiceLeftViewHolder;
import com.fanwe.live.adapter.viewholder.privatechat.MsgVoiceRightViewHolder;
import com.fanwe.live.adapter.viewholder.privatechat.PrivateChatViewHolder;
import com.fanwe.live.model.custommsg.MsgModel;

import java.util.ArrayList;


public class LivePrivateChatRecyclerAdapter extends SDRecyclerAdapter<MsgModel>
{

    private PrivateChatViewHolder.ClickListener clickListener;
    private ArrayList<String> imgUrls = new ArrayList<>();

    public LivePrivateChatRecyclerAdapter(Activity activity)
    {
        super(activity);
    }

    public void setClickListener(PrivateChatViewHolder.ClickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    @Override
    public SDRecyclerViewHolder<MsgModel> onCreateVHolder(ViewGroup parent, int viewType)
    {
        PrivateChatViewHolder viewHolder = null;
        switch (viewType)
        {
            case LiveConstant.PrivateMsgType.MSG_TEXT_LEFT:
                viewHolder = new MsgTextLeftViewHolder(inflate(R.layout.item_private_chat_text_left, parent));
                break;
            case LiveConstant.PrivateMsgType.MSG_TEXT_RIGHT:
                viewHolder = new MsgTextRightViewHolder(inflate(R.layout.item_private_chat_text_right, parent));
                break;
            case LiveConstant.PrivateMsgType.MSG_VOICE_LEFT:
                viewHolder = new MsgVoiceLeftViewHolder(inflate(R.layout.item_private_chat_voice_left, parent));
                break;
            case LiveConstant.PrivateMsgType.MSG_VOICE_RIGHT:
                viewHolder = new MsgVoiceRightViewHolder(inflate(R.layout.item_private_chat_voice_right, parent));
                break;
            case LiveConstant.PrivateMsgType.MSG_IMAGE_LEFT:
                viewHolder = new MsgImageLeftViewHolder(inflate(R.layout.item_private_chat_image_left, parent))
                {
                    @Override
                    protected void bindImage(final String uri, ImageView iv_image)
                    {
                        super.bindImage(uri, iv_image);
                        bindImageToAlbum(uri, iv_image);
                    }
                };
                break;
            case LiveConstant.PrivateMsgType.MSG_IMAGE_RIGHT:
                viewHolder = new MsgImageRightViewHolder(inflate(R.layout.item_private_chat_image_right, parent))
                {
                    @Override
                    protected void bindImage(String uri, ImageView iv_image)
                    {
                        super.bindImage(uri, iv_image);
                        bindImageToAlbum(uri, iv_image);
                    }
                };
                break;
            case LiveConstant.PrivateMsgType.MSG_GIFT_LEFT:
                viewHolder = new MsgGiftLeftViewHolder(inflate(R.layout.item_private_chat_gift_left, parent));
                break;
            case LiveConstant.PrivateMsgType.MSG_GIFT_RIGHT:
                viewHolder = new MsgGiftRightViewHolder(inflate(R.layout.item_private_chat_gift_right, parent));
                break;
            default:
                break;
        }
        if (viewHolder != null)
        {
            viewHolder.setClickListener(clickListener);
            viewHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onclick.click();
                }
            });
        }
        return viewHolder;
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<MsgModel> holder, int position, MsgModel model)
    {

    }

    private void bindImageToAlbum(final String uri, ImageView iv_image)
    {
        if (!imgUrls.contains(uri))
        {
            imgUrls.add(uri);
        }

        iv_image.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), PhotoViewActivity.class);
                // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
                intent.putExtra(PhotoViewActivity.EXTRA_IMAGE_URLS, imgUrls);
                intent.putExtra(PhotoViewActivity.EXTRA_POSITION, imgUrls.indexOf(uri));
                getActivity().startActivity(intent);
            }
        });
    }

    public Onclick getOnclick() {
        return onclick;
    }

    public void setOnclick(Onclick onclick) {
        this.onclick = onclick;
    }

    Onclick onclick;
    public interface Onclick{
        void click();
    }
    @Override
    public int getItemViewType(int position)
    {
        return getData(position).getPrivateMsgType();
    }
}
