package com.fanwe.auction.model.custommsg;

import com.fanwe.auction.model.PaiBuyerModel;
import com.fanwe.live.LiveConstant;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.custommsg.CustomMsg;

import java.util.List;

/**
 * Created by Administrator on 2016/8/16.
 */
public class CustomMsgAuctionFail extends CustomMsg
{

    private int room_id; //房间ID
    private int pai_id; //拍卖的项目ID
    private int last_user_id; //当前要付款用户
    private int no_pay_user_id; //超时未付款用户
    private int post_id; //主播ID
    private int out_type;//竞拍类型 0无人出价 1无人付款
    private String desc;//消息
    private List<PaiBuyerModel> buyer;

    public CustomMsgAuctionFail()
    {
        super();
        setType(LiveConstant.CustomMsgType.MSG_AUCTION_FAIL);
    }

    public int getOut_type()
    {
        return out_type;
    }

    public void setOut_type(int out_type)
    {
        this.out_type = out_type;
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

    public int getLast_user_id()
    {
        return last_user_id;
    }

    public void setLast_user_id(int last_user_id)
    {
        this.last_user_id = last_user_id;
    }

    public int getNo_pay_user_id()
    {
        return no_pay_user_id;
    }

    public void setNo_pay_user_id(int no_pay_user_id)
    {
        this.no_pay_user_id = no_pay_user_id;
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
