package com.fanwe.auction.model.custommsg;

import com.fanwe.auction.model.AuctionCreateSuccessInfoModel;
import com.fanwe.live.LiveConstant;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.custommsg.CustomMsg;

/**
 * Created by Administrator on 2016/8/21 0021.主播发起竞拍
 */
public class CustomMsgAuctionCreateSuccess extends CustomMsg {
    private int room_id;
    private int pai_id;
    private int podcast_id;//直播ID
    private String desc;//消息
    private AuctionCreateSuccessInfoModel info;

    public CustomMsgAuctionCreateSuccess() {
        super();
        setType(LiveConstant.CustomMsgType.MSG_AUCTION_CREATE_SUCCESS);
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getPai_id() {
        return pai_id;
    }

    public void setPai_id(int pai_id) {
        this.pai_id = pai_id;
    }

    public int getPodcast_id() {
        return podcast_id;
    }

    public void setPodcast_id(int podcast_id) {
        this.podcast_id = podcast_id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public AuctionCreateSuccessInfoModel getInfo() {
        return info;
    }

    public void setInfo(AuctionCreateSuccessInfoModel info) {
        this.info = info;
    }
}
