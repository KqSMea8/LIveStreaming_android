package com.fanwe.live.model.custommsg;

import com.fanwe.auction.model.custommsg.CustomMsgAuctionCreateSuccess;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionFail;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionNotifyPay;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionOffer;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionPaySuccess;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionSuccess;
import com.fanwe.baimei.model.custommsg.BMCustomMsgPushStatus;
import com.fanwe.games.model.custommsg.CustomMsgGameBanker;
import com.fanwe.games.model.custommsg.GameMsgModel;
import com.fanwe.library.utils.SDDateUtil;
import com.fanwe.live.LiveConstant;
import com.fanwe.live.LiveConstant.CustomMsgType;
import com.fanwe.live.LiveConstant.LiveMsgType;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.UserModel;
import com.fanwe.pay.model.custommsg.CustomMsgStartPayMode;
import com.fanwe.pay.model.custommsg.CustomMsgStartScenePayMode;
import com.fanwe.shop.model.custommsg.CustomMsgShopBuySuc;
import com.fanwe.shop.model.custommsg.CustomMsgShopPush;
import com.tencent.TIMMessage;

public abstract class MsgModel
{

    /**
     * 私聊消息类型
     */
    private int privateMsgType = LiveConstant.PrivateMsgType.MSG_TEXT_LEFT;
    /**
     * 直播间消息列表类型
     */
    private int liveMsgType = LiveMsgType.MSG_TEXT;

    //
    private int customMsgType = CustomMsgType.MSG_NONE;
    private CustomMsg customMsg;

    /**
     * true-本地通过EventBus直接发送的消息
     */
    private boolean isLocalPost = false;
    /**
     * 是否自己发送的
     */
    private boolean isSelf = false;
    /**
     * 会话的对方id或者群组Id
     */
    private String conversationtPeer;
    /**
     * 消息在腾讯服务端生成的时间戳
     */
    private long timestamp;
    /**
     * 消息在服务端生成的时间格式化
     */
    private String timestampFormat;
    /**
     * 该条消息对应的会话的未读
     */
    private long unreadNum;

    private MsgStatus status = MsgStatus.Invalid;
    private ConversationType conversationType = ConversationType.Invalid;

    /**
     * 是否是直播间列表显示的消息
     *
     * @return
     */
    public boolean isLiveChatMsg()
    {
        boolean result = false;
        switch (customMsgType)
        {
            case CustomMsgType.MSG_TEXT:
            case CustomMsgType.MSG_GIFT:
            case CustomMsgType.MSG_POP_MSG:
            case CustomMsgType.MSG_FORBID_SEND_MSG:
            case CustomMsgType.MSG_VIEWER_JOIN:
            case CustomMsgType.MSG_LIVE_MSG:
            case CustomMsgType.MSG_RED_ENVELOPE:
            case CustomMsgType.MSG_CREATER_LEAVE:
            case CustomMsgType.MSG_CREATER_COME_BACK:
            case CustomMsgType.MSG_LIGHT:
            case CustomMsgType.MSG_AUCTION_OFFER:
            case CustomMsgType.MSG_AUCTION_SUCCESS:
            case CustomMsgType.MSG_AUCTION_PAY_SUCCESS:
            case CustomMsgType.MSG_AUCTION_FAIL:
            case CustomMsgType.MSG_AUCTION_NOTIFY_PAY:
            case CustomMsgType.MSG_AUCTION_CREATE_SUCCESS:
            case CustomMsgType.MSG_SHOP_GOODS_PUSH:
            case CustomMsgType.MSG_SHOP_GOODS_BUY_SUCCESS:
            case CustomMsgType.MSG_PUSH_STATUS:
                result = true;
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 是否是私聊消息
     *
     * @return
     */
    public boolean isPrivateMsg()
    {
        boolean result = false;
        switch (customMsgType)
        {
            case CustomMsgType.MSG_PRIVATE_TEXT:
            case CustomMsgType.MSG_PRIVATE_VOICE:
            case CustomMsgType.MSG_PRIVATE_IMAGE:
            case CustomMsgType.MSG_PRIVATE_GIFT:
                result = true;
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 是否是竞拍消息
     *
     * @return
     */
    public boolean isAuctionMsg()
    {
        boolean result = false;
        switch (customMsgType)
        {
            case CustomMsgType.MSG_AUCTION_SUCCESS:
            case CustomMsgType.MSG_AUCTION_NOTIFY_PAY:
            case CustomMsgType.MSG_AUCTION_FAIL:
            case CustomMsgType.MSG_AUCTION_OFFER:
            case CustomMsgType.MSG_AUCTION_PAY_SUCCESS:
            case CustomMsgType.MSG_AUCTION_CREATE_SUCCESS:
                result = true;
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 是否购物直播商品推送消息
     *
     * @return
     */
    public boolean isShopPushMsg()
    {
        boolean result = false;
        switch (customMsgType)
        {
            case CustomMsgType.MSG_SHOP_GOODS_PUSH:
            case CustomMsgType.MSG_SHOP_GOODS_BUY_SUCCESS:
                result = true;
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 是否是游戏消息
     */
    public boolean isGameMsg()
    {
        switch (customMsgType)
        {
            case CustomMsgType.MSG_GAME:
                return true;
            case CustomMsgType.MSG_GAMES_STOP:
                return true;
            default:
                return false;
        }
    }

    /**
     * 是否是付费消息
     *
     * @return
     */
    public boolean isPayModeMsg()
    {
        boolean result = false;
        switch (customMsgType)
        {
            case CustomMsgType.MSG_START_PAY_MODE:
                result = true;
                break;
            case CustomMsgType.MSG_START_SCENE_PAY_MODE:
                result = true;
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 重写，用于删除本地缓存的消息
     */
    public abstract void remove();

    //==============================================竞拍自定义消息start

    public GameMsgModel getCustomMsgGame()
    {
        return getCustomMsgReal();
    }

    public CustomMsgGameBanker getCustomMsgGameBanker()
    {
        return getCustomMsgReal();
    }

    public CustomMsgAuctionNotifyPay getCustomMsgAuctionNotifyPay()
    {
        return getCustomMsgReal();
    }

    public CustomMsgAuctionFail getCustomMsgAuctionFail()
    {
        return getCustomMsgReal();
    }

    public CustomMsgAuctionSuccess getCustomMsgAuctionSuccess()
    {
        return getCustomMsgReal();
    }

    public CustomMsgAuctionCreateSuccess getCustomMsgAuctionCreateSuccess()
    {
        return getCustomMsgReal();
    }

    public CustomMsgShopPush getCustomMsgShopPush()
    {
        return getCustomMsgReal();
    }

    public CustomMsgShopBuySuc getCustomMsgShopBuySuc()
    {
        return getCustomMsgReal();
    }

    public CustomMsgAuctionPaySuccess getCustomMsgAuctionPaySuccess()
    {
        return getCustomMsgReal();
    }

    public CustomMsgAuctionOffer getCustomMsgAuctionOffer()
    {
        return getCustomMsgReal();
    }

    //==============================================竞拍自定义消息end
    public CustomMsgData getCustomMsgData()
    {
        return getCustomMsgReal();
    }
    public CustomMsgOpenShouhu getCustomMsgOpenShouhu()
    {
        return getCustomMsgReal();
    }
    public CustomMsgHeatRank getCustomMsgHeatRank()
    {
        return getCustomMsgReal();
    }
    public CustomMsgPrize getCustomMsgPrize()
    {
        return getCustomMsgReal();
    }
    public CustomMsgWarning getCustomMsgWarning()
    {
        return getCustomMsgReal();
    }

    public CustomMsgStartScenePayMode getCustomMsgStartScenePayMode()
    {
        return getCustomMsgReal();
    }

    public CustomMsgStartPayMode getCustomMsgStartPayMode()
    {
        return getCustomMsgReal();
    }

    public CustomMsgPrivateVoice getCustomMsgPrivateVoice()
    {
        return getCustomMsgReal();
    }

    public CustomMsgLiveStopped getCustomMsgLiveStopped()
    {
        return getCustomMsgReal();
    }

    public CustomMsgGift getCustomMsgGift()
    {
        return getCustomMsgReal();
    }

    public CustomMsgViewerJoin getCustomMsgViewerJoin()
    {
        return getCustomMsgReal();
    }

    public CustomMsgViewerQuit getCustomMsgViewerQuit()
    {
        return getCustomMsgReal();
    }

    public int getCustomMsgType()
    {
        return customMsgType;
    }

    public void setCustomMsgType(int customMsgType)
    {
        this.customMsgType = customMsgType;
    }

    public CustomMsgPopMsg getCustomMsgPopMsg()
    {
        return getCustomMsgReal();
    }

    public CustomMsgEndVideo getCustomMsgEndVideo()
    {
        return getCustomMsgReal();
    }

    public boolean isLocalPost()
    {
        return isLocalPost;
    }

    public void setLocalPost(boolean isLocalPost)
    {
        this.isLocalPost = isLocalPost;
    }

    public CustomMsgRedEnvelope getCustomMsgRedEnvelope()
    {
        return getCustomMsgReal();
    }

    public CustomMsgCreaterLeave getCustomMsgCreaterLeave()
    {
        return getCustomMsgReal();
    }

    public CustomMsgCreaterComeback getCustomMsgCreaterComeback()
    {
        return getCustomMsgReal();
    }

    public CustomMsgLight getCustomMsgLight()
    {
        return getCustomMsgReal();
    }

    public CustomMsgRejectLinkMic getCustomMsgRejectLinkMic()
    {
        return getCustomMsgReal();
    }

    public CustomMsgAcceptLinkMic getCustomMsgAcceptLinkMic()
    {
        return getCustomMsgReal();
    }

    public CustomMsgApplyLinkMic getCustomMsgApplyLinkMic()
    {
        return getCustomMsgReal();
    }

    public CustomMsgGreedLinkMic getCustomMsgGreedLinkMic()
    {
        return getCustomMsgReal();
    }
    public CustomMsgStopLinkMic getCustomMsgStopLinkMic()
    {
        return getCustomMsgReal();
    }
    public CustomMsgStopLive getCustomMsgStopLive()
    {
        return getCustomMsgReal();
    }

    public CustomMsgPrivateText getCustomMsgPrivateText()
    {
        return getCustomMsgReal();
    }

    public CustomMsgLargeGift getCustomMsgLargeGift()
    {
        return getCustomMsgReal();
    }

    public BMCustomMsgPushStatus getBMCustomMsgPushStatus()
    {
        return getCustomMsgReal();
    }

    public <T extends ICustomMsg> T getCustomMsgReal()
    {
        T t = null;
        try
        {
            return (T) getCustomMsg();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public CustomMsg getCustomMsg()
    {
        return customMsg;
    }

    public void setCustomMsg(CustomMsg customMsg)
    {
        this.customMsg = customMsg;
        if (customMsg != null)
        {
            int type = customMsg.getType();
            setCustomMsgType(type);
            switch (type)
            {
                case CustomMsgType.MSG_TEXT:
                    // 直播间消息列表类型
                    setLiveMsgType(LiveConstant.LiveMsgType.MSG_TEXT);
                    break;
                case CustomMsgType.MSG_GIFT:
                    CustomMsgGift customMsgGift = getCustomMsgReal();
                    // 直播间消息列表类型
                    UserModel user = UserModelDao.query();
                    if (user != null && customMsgGift.getTo_user_id().equals(user.getUser_id()))
                    {
                        setLiveMsgType(LiveConstant.LiveMsgType.MSG_GIFT_CREATER);
                    } else
                    {
                        setLiveMsgType(LiveConstant.LiveMsgType.MSG_GIFT_VIEWER);
                    }
                    break;
                case CustomMsgType.MSG_POP_MSG:
                    // 直播间消息列表类型
                    setLiveMsgType(LiveConstant.LiveMsgType.MSG_POP_MSG);
                    break;
                case CustomMsgType.MSG_FORBID_SEND_MSG:
                    // 直播间消息列表类型
                    setLiveMsgType(LiveConstant.LiveMsgType.MSG_FORBID_SEND_MSG);
                    break;
                case CustomMsgType.MSG_VIEWER_JOIN:
                    CustomMsgViewerJoin customMsgViewerJoin = getCustomMsgReal();
                    // 直播间消息列表类型
                    if(null!=customMsgViewerJoin.getGuard()){
                        setLiveMsgType(LiveConstant.LiveMsgType.MSG_SHOUHU_VIEWER_JOIN);
                    }
                    else if (customMsgViewerJoin.getSender().isProUser())
                    {
                        setLiveMsgType(LiveConstant.LiveMsgType.MSG_PRO_VIEWER_JOIN);
                    } else
                    {
                        setLiveMsgType(LiveConstant.LiveMsgType.MSG_VIEWER_JOIN);
                    }
                    break;
                case CustomMsgType.MSG_RED_ENVELOPE:
                    // 直播间消息列表类型
                    setLiveMsgType(LiveConstant.LiveMsgType.MSG_RED_ENVELOPE);
                    break;
                case CustomMsgType.MSG_LIVE_MSG:
                    // 直播间消息列表类型
                    setLiveMsgType(LiveConstant.LiveMsgType.MSG_LIVE_MSG);
                    break;
                case CustomMsgType.MSG_CREATER_LEAVE:
                    // 直播间消息列表类型
                    setLiveMsgType(LiveConstant.LiveMsgType.MSG_CREATER_LEAVE);
                    break;
                case CustomMsgType.MSG_CREATER_COME_BACK:
                    // 直播间消息列表类型
                    setLiveMsgType(LiveConstant.LiveMsgType.MSG_CREATER_COME_BACK);
                    break;
                case CustomMsgType.MSG_LIGHT:
                    // 直播间消息列表类型
                    setLiveMsgType(LiveConstant.LiveMsgType.MSG_LIGHT);
                    break;
                case CustomMsgType.MSG_PRIVATE_TEXT:
                    // 私聊消息类型
                    if (isSelf())
                    {
                        setPrivateMsgType(LiveConstant.PrivateMsgType.MSG_TEXT_RIGHT);
                    } else
                    {
                        setPrivateMsgType(LiveConstant.PrivateMsgType.MSG_TEXT_LEFT);
                    }
                    break;
                case CustomMsgType.MSG_PRIVATE_GIFT:
                    // 私聊消息类型
                    if (isSelf())
                    {
                        setPrivateMsgType(LiveConstant.PrivateMsgType.MSG_GIFT_RIGHT);
                    } else
                    {
                        setPrivateMsgType(LiveConstant.PrivateMsgType.MSG_GIFT_LEFT);
                    }
                    break;
                case CustomMsgType.MSG_PRIVATE_VOICE:
                    // 私聊消息类型
                    if (isSelf())
                    {
                        setPrivateMsgType(LiveConstant.PrivateMsgType.MSG_VOICE_RIGHT);
                    } else
                    {
                        setPrivateMsgType(LiveConstant.PrivateMsgType.MSG_VOICE_LEFT);
                    }
                    break;
                case CustomMsgType.MSG_PRIVATE_IMAGE:
                    // 私聊消息类型
                    if (isSelf())
                    {
                        setPrivateMsgType(LiveConstant.PrivateMsgType.MSG_IMAGE_RIGHT);
                    } else
                    {
                        setPrivateMsgType(LiveConstant.PrivateMsgType.MSG_IMAGE_LEFT);
                    }
                    break;
                case CustomMsgType.MSG_AUCTION_SUCCESS:
                    // 直播间消息列表类型
                    setLiveMsgType(LiveConstant.LiveMsgType.MSG_AUCTION_SUCCESS);
                    break;
                case CustomMsgType.MSG_AUCTION_FAIL:
                    // 直播间消息列表类型
                    setLiveMsgType(LiveConstant.LiveMsgType.MSG_AUCTION_FAIL);
                    break;
                case CustomMsgType.MSG_AUCTION_NOTIFY_PAY:
                    // 直播间消息列表类型
                    setLiveMsgType(LiveConstant.LiveMsgType.MSG_AUCTION_NOTIFY_PAY);
                    break;
                case CustomMsgType.MSG_AUCTION_OFFER:
                    // 直播间消息列表类型
                    setLiveMsgType(LiveConstant.LiveMsgType.MSG_AUCTION_OFFER);
                    break;
                case CustomMsgType.MSG_AUCTION_PAY_SUCCESS:
                    // 直播间消息列表类型
                    setLiveMsgType(LiveConstant.LiveMsgType.MSG_AUCTION_PAY_SUCCESS);
                    break;
                case CustomMsgType.MSG_AUCTION_CREATE_SUCCESS:
                    // 直播间消息列表类型
                    setLiveMsgType(LiveConstant.LiveMsgType.MSG_AUCTION_CREATE_SUCCESS);
                    break;
                case CustomMsgType.MSG_SHOP_GOODS_PUSH:
                    // 直播间消息列表类型
                    setLiveMsgType(LiveConstant.LiveMsgType.MSG_SHOP_GOODS_PUSH);
                    break;
                case CustomMsgType.MSG_SHOP_GOODS_BUY_SUCCESS:
                    // 直播间消息列表类型
                    setLiveMsgType(LiveConstant.LiveMsgType.MSG_SHOP_GOODS_BUY_SUCCESS);
                    break;
                case CustomMsgType.MSG_PUSH_STATUS:
                    setLiveMsgType(LiveConstant.LiveMsgType.MSG_PUSH_STATUS);
                    break;
                default:
                    break;
            }
        }
    }

    public boolean isSelf()
    {
        return isSelf;
    }

    public void setSelf(boolean isSelf)
    {
        this.isSelf = isSelf;
    }

    public String getConversationPeer()
    {
        if (conversationtPeer == null)
        {
            conversationtPeer = "";
        }
        return conversationtPeer;
    }

    public void setConversationPeer(String conversationtPeer)
    {
        this.conversationtPeer = conversationtPeer;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;

        String format = SDDateUtil.formatDuringFrom(timestamp * 1000);
        setTimestampFormat(format);
    }

    public String getTimestampFormat()
    {
        return timestampFormat;
    }

    public void setTimestampFormat(String timestampFormat)
    {
        this.timestampFormat = timestampFormat;
    }

    public long getUnreadNum()
    {
        return unreadNum;
    }

    public void setUnreadNum(long unreadNum)
    {
        this.unreadNum = unreadNum;
    }

    public MsgStatus getStatus()
    {
        return status;
    }

    public void setStatus(MsgStatus status)
    {
        this.status = status;
    }

    public void setConversationType(ConversationType conversationType)
    {
        this.conversationType = conversationType;
    }

    public ConversationType getConversationType()
    {
        return conversationType;
    }

    public int getLiveMsgType()
    {
        return liveMsgType;
    }

    public void setLiveMsgType(int liveMsgType)
    {
        this.liveMsgType = liveMsgType;
    }

    public int getPrivateMsgType()
    {
        return privateMsgType;
    }

    public void setPrivateMsgType(int privateMsgType)
    {
        this.privateMsgType = privateMsgType;
    }

    // 腾讯im相关方法

    /**
     * 解析腾讯的消息实体
     *
     * @param timMessage
     */
    public abstract void setTimMessage(TIMMessage timMessage);

}
