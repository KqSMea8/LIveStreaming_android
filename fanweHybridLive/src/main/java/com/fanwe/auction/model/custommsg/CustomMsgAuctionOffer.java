package com.fanwe.auction.model.custommsg;

import com.fanwe.live.LiveConstant;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.custommsg.CustomMsg;
import com.fanwe.live.model.custommsg.ILiveGiftMsg;

/**
 * Created by Administrator on 2016/8/21 0021.推送出价信息
 */
public class CustomMsgAuctionOffer extends CustomMsg implements ILiveGiftMsg
{
    private int yanshi;//是否延时1 延时
    private long pai_left_time;
    private int room_id;
    private int pai_id;
    private int pai_sort;//出价次数
    private int pai_diamonds;//出价金额
    private String desc;//消息

    //add
    private boolean isTaked = false;
    private String gif_dec;//弹出礼物描述字段

    public String getGif_dec()
    {
        return gif_dec;
    }

    public void setGif_dec(String gif_dec)
    {
        this.gif_dec = gif_dec;
    }

    public int getYanshi()
    {
        return yanshi;
    }

    public void setYanshi(int yanshi)
    {
        this.yanshi = yanshi;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getDesc()
    {
        return desc;
    }

    public CustomMsgAuctionOffer()
    {
        super();
        setType(LiveConstant.CustomMsgType.MSG_AUCTION_OFFER);
        setDesc("参与一次出价");
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null)
        {
            return false;
        }
        if (!(o instanceof CustomMsgAuctionOffer))
        {
            return false;
        }
        CustomMsgAuctionOffer msg = (CustomMsgAuctionOffer) o;
        if (!getUser().equals(msg.getUser()))
        {
            return false;
        }
        return true;
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

    public int getPai_sort()
    {
        return pai_sort;
    }

    public void setPai_sort(int pai_sort)
    {
        this.gif_dec = "参与了" + Integer.toString(pai_sort) + "次出价";
        this.pai_sort = pai_sort;
    }

    public int getPai_diamonds()
    {
        return pai_diamonds;
    }

    public void setPai_diamonds(int pai_diamonds)
    {
        this.pai_diamonds = pai_diamonds;
    }

    public long getPai_left_time()
    {
        return pai_left_time;
    }

    public void setPai_left_time(long pai_left_time)
    {
        this.pai_left_time = pai_left_time;
    }

    //----------ILiveGiftMsg implements start----------

    @Override
    public UserModel getMsgSender()
    {
        return getUser();
    }

    @Override
    public void setShowNum(int showNum)
    {
        this.pai_sort = showNum;
    }

    @Override
    public int getShowNum()
    {
        return pai_sort;
    }

    @Override
    public int getAddNum() {
        return 0;
    }

    @Override
    public boolean isTaked()
    {
        return isTaked;
    }

    @Override
    public void setTaked(boolean taked)
    {
        isTaked = taked;
    }

    @Override
    public boolean canPlay()
    {
        return true;
    }

    //----------ILiveGiftMsg implements end----------

}
