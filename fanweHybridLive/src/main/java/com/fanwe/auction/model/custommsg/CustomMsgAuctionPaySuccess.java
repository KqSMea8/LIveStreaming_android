package com.fanwe.auction.model.custommsg;

import com.fanwe.auction.model.PaiBuyerModel;
import com.fanwe.live.LiveConstant;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.custommsg.CustomMsg;

import java.util.List;

/**
 * Created by Administrator on 2016/8/21 0021.支付成功
 */
public class CustomMsgAuctionPaySuccess extends CustomMsg
{
    private int room_id;//房间ID
    private int pai_id;//拍卖的项目ID
    private int post_id;//主播ID
    private String desc;//消息
    private List<PaiBuyerModel> buyer;

    public CustomMsgAuctionPaySuccess()
    {
        super();
        setType(LiveConstant.CustomMsgType.MSG_AUCTION_PAY_SUCCESS);
    }

    public int getRoom_id()
    {
        return room_id;
    }

    public void setRoom_id(int room_id)
    {
        this.room_id = room_id;
    }

    public int getPai_id()
    {
        return pai_id;
    }

    public void setPai_id(int pai_id)
    {
        this.pai_id = pai_id;
    }

    public int getPost_id()
    {
        return post_id;
    }

    public void setPost_id(int post_id)
    {
        this.post_id = post_id;
    }

    public List<PaiBuyerModel> getBuyer()
    {
        return buyer;
    }

    public void setBuyer(List<PaiBuyerModel> buyer)
    {
        this.buyer = buyer;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
