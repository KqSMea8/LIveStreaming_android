package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import com.fanwe.baimei.adapter.holder.BMMsgPushStatusViewHolder;
import com.fanwe.library.adapter.SDRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.live.LiveConstant;
import com.fanwe.live.R;
import com.fanwe.live.adapter.viewholder.MsgAuctionCreateSucViewHolder;
import com.fanwe.live.adapter.viewholder.MsgAuctionFailViewHolder;
import com.fanwe.live.adapter.viewholder.MsgAuctionNotifyPayViewHolder;
import com.fanwe.live.adapter.viewholder.MsgAuctionOfferViewHolder;
import com.fanwe.live.adapter.viewholder.MsgAuctionPaySucViewHolder;
import com.fanwe.live.adapter.viewholder.MsgAuctionSucViewHolder;
import com.fanwe.live.adapter.viewholder.MsgCreaterComebackViewHolder;
import com.fanwe.live.adapter.viewholder.MsgCreaterLeaveViewHolder;
import com.fanwe.live.adapter.viewholder.MsgForbidSendMsgViewHolder;
import com.fanwe.live.adapter.viewholder.MsgGiftCreaterViewHolder;
import com.fanwe.live.adapter.viewholder.MsgGiftViewerViewHolder;
import com.fanwe.live.adapter.viewholder.MsgLightViewHolder;
import com.fanwe.live.adapter.viewholder.MsgLiveMsgViewHolder;
import com.fanwe.live.adapter.viewholder.MsgPopViewHolder;
import com.fanwe.live.adapter.viewholder.MsgProViewerJoinViewHolder;
import com.fanwe.live.adapter.viewholder.MsgRedEnvelopeViewHolder;
import com.fanwe.live.adapter.viewholder.MsgShouhuViewerJoinViewHolder;
import com.fanwe.live.adapter.viewholder.MsgTextViewHolder;
import com.fanwe.live.adapter.viewholder.MsgViewerJoinViewHolder;
import com.fanwe.live.model.custommsg.MsgModel;
import com.fanwe.shop.adapter.viewholder.MsgAuctionShopBuySucViewHolder;
import com.fanwe.shop.adapter.viewholder.MsgAuctionShopPushSucViewHolder;

/**
 * Created by L on 2016/8/27.
 */
public class LiveMsgRecyclerAdapter extends SDRecyclerAdapter<MsgModel>
{
    public LiveMsgRecyclerAdapter(Activity activity)
    {
        super(activity);
    }

    @Override
    public int getItemViewType(int position)
    {
        return getData(position).getLiveMsgType();
    }

    @Override
    public SDRecyclerViewHolder<MsgModel> onCreateVHolder(ViewGroup parent, int viewType)
    {
        SDRecyclerViewHolder viewHolder = null;
        switch (viewType)
        {
            case LiveConstant.LiveMsgType.MSG_TEXT:
                viewHolder = new MsgTextViewHolder(inflate(R.layout.item_live_msg_text, parent));
                break;
            case LiveConstant.LiveMsgType.MSG_POP_MSG:
                viewHolder = new MsgPopViewHolder(inflate(R.layout.item_live_msg_text, parent));
                break;
            case LiveConstant.LiveMsgType.MSG_VIEWER_JOIN:
                viewHolder = new MsgViewerJoinViewHolder(inflate(R.layout.item_live_msg_text, parent));
                break;
            case LiveConstant.LiveMsgType.MSG_PRO_VIEWER_JOIN:
                viewHolder = new MsgProViewerJoinViewHolder(inflate(R.layout.item_live_msg_text, parent));
                break;
            case LiveConstant.LiveMsgType.MSG_SHOUHU_VIEWER_JOIN:
                viewHolder = new MsgShouhuViewerJoinViewHolder(inflate(R.layout.item_live_msg_text, parent));
                break;
            case LiveConstant.LiveMsgType.MSG_GIFT_VIEWER:
                viewHolder = new MsgGiftViewerViewHolder(inflate(R.layout.item_live_msg_text, parent));
                break;
            case LiveConstant.LiveMsgType.MSG_GIFT_CREATER:
                viewHolder = new MsgGiftCreaterViewHolder(inflate(R.layout.item_live_msg_text, parent));
                break;
            case LiveConstant.LiveMsgType.MSG_FORBID_SEND_MSG:
                viewHolder = new MsgForbidSendMsgViewHolder(inflate(R.layout.item_live_msg_text, parent));
                break;
            case LiveConstant.LiveMsgType.MSG_LIVE_MSG:
                viewHolder = new MsgLiveMsgViewHolder(inflate(R.layout.item_live_msg_live_msg, parent));
                break;
            case LiveConstant.LiveMsgType.MSG_RED_ENVELOPE:
                viewHolder = new MsgRedEnvelopeViewHolder(inflate(R.layout.item_live_msg_text, parent));
                break;
            case LiveConstant.LiveMsgType.MSG_CREATER_LEAVE:
                viewHolder = new MsgCreaterLeaveViewHolder(inflate(R.layout.item_live_msg_text, parent));
                break;
            case LiveConstant.LiveMsgType.MSG_CREATER_COME_BACK:
                viewHolder = new MsgCreaterComebackViewHolder(inflate(R.layout.item_live_msg_text, parent));
                break;
            case LiveConstant.LiveMsgType.MSG_LIGHT:
                viewHolder = new MsgLightViewHolder(inflate(R.layout.item_live_msg_text, parent));
                break;
            case LiveConstant.LiveMsgType.MSG_AUCTION_OFFER:
                viewHolder = new MsgAuctionOfferViewHolder(inflate(R.layout.item_live_msg_text, parent));
                break;
            case LiveConstant.LiveMsgType.MSG_AUCTION_SUCCESS:
                viewHolder = new MsgAuctionSucViewHolder(inflate(R.layout.item_live_msg_text, parent));
                break;
            case LiveConstant.LiveMsgType.MSG_AUCTION_PAY_SUCCESS:
                viewHolder = new MsgAuctionPaySucViewHolder(inflate(R.layout.item_live_msg_text, parent));
                break;
            case LiveConstant.LiveMsgType.MSG_AUCTION_FAIL:
                viewHolder = new MsgAuctionFailViewHolder(inflate(R.layout.item_live_msg_text, parent));
                break;
            case LiveConstant.LiveMsgType.MSG_AUCTION_NOTIFY_PAY:
                viewHolder = new MsgAuctionNotifyPayViewHolder(inflate(R.layout.item_live_msg_text, parent));
                break;
            case LiveConstant.LiveMsgType.MSG_AUCTION_CREATE_SUCCESS:
                viewHolder = new MsgAuctionCreateSucViewHolder(inflate(R.layout.item_live_msg_text, parent));
                break;
            case LiveConstant.LiveMsgType.MSG_SHOP_GOODS_PUSH:
                viewHolder = new MsgAuctionShopPushSucViewHolder(inflate(R.layout.item_live_msg_text, parent));
                break;
            case LiveConstant.LiveMsgType.MSG_SHOP_GOODS_BUY_SUCCESS:
                viewHolder = new MsgAuctionShopBuySucViewHolder(inflate(R.layout.item_live_msg_text, parent));
                break;
            case LiveConstant.LiveMsgType.MSG_PUSH_STATUS:
                viewHolder = new BMMsgPushStatusViewHolder(inflate(R.layout.item_live_msg_text, parent));
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<MsgModel> holder, int position, MsgModel model)
    {

    }
}
