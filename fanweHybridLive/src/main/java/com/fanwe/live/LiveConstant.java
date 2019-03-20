package com.fanwe.live;

import com.fanwe.auction.model.custommsg.CustomMsgAuctionCreateSuccess;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionFail;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionNotifyPay;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionOffer;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionPaySuccess;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionSuccess;
import com.fanwe.baimei.model.custommsg.BMCustomMsgPushStatus;
import com.fanwe.games.model.custommsg.CustomMsgGameBanker;
import com.fanwe.games.model.custommsg.GameMsgModel;
import com.fanwe.library.utils.SDBase64;
import com.fanwe.live.model.custommsg.CustomMsgAcceptLinkMic;
import com.fanwe.live.model.custommsg.CustomMsgApplyLinkMic;
import com.fanwe.live.model.custommsg.CustomMsgCreaterComeback;
import com.fanwe.live.model.custommsg.CustomMsgCreaterLeave;
import com.fanwe.live.model.custommsg.CustomMsgCreaterQuit;
import com.fanwe.live.model.custommsg.CustomMsgData;
import com.fanwe.live.model.custommsg.CustomMsgEndVideo;
import com.fanwe.live.model.custommsg.CustomMsgForbidSendMsg;
import com.fanwe.live.model.custommsg.CustomMsgGift;
import com.fanwe.live.model.custommsg.CustomMsgGreedLinkMic;
import com.fanwe.live.model.custommsg.CustomMsgHeatRank;
import com.fanwe.live.model.custommsg.CustomMsgLargeGift;
import com.fanwe.live.model.custommsg.CustomMsgLight;
import com.fanwe.live.model.custommsg.CustomMsgLiveMsg;
import com.fanwe.live.model.custommsg.CustomMsgLiveStopped;
import com.fanwe.live.model.custommsg.CustomMsgOpenShouhu;
import com.fanwe.live.model.custommsg.CustomMsgPopMsg;
import com.fanwe.live.model.custommsg.CustomMsgPrivateGift;
import com.fanwe.live.model.custommsg.CustomMsgPrivateImage;
import com.fanwe.live.model.custommsg.CustomMsgPrivateText;
import com.fanwe.live.model.custommsg.CustomMsgPrivateVoice;
import com.fanwe.live.model.custommsg.CustomMsgPrize;
import com.fanwe.live.model.custommsg.CustomMsgRedEnvelope;
import com.fanwe.live.model.custommsg.CustomMsgRejectLinkMic;
import com.fanwe.live.model.custommsg.CustomMsgStopLinkMic;
import com.fanwe.live.model.custommsg.CustomMsgStopLive;
import com.fanwe.live.model.custommsg.CustomMsgText;
import com.fanwe.live.model.custommsg.CustomMsgViewerJoin;
import com.fanwe.live.model.custommsg.CustomMsgViewerQuit;
import com.fanwe.live.model.custommsg.CustomMsgWarning;
import com.fanwe.live.model.custommsg.ICustomMsg;
import com.fanwe.pay.model.custommsg.CustomMsgStartPayMode;
import com.fanwe.pay.model.custommsg.CustomMsgStartScenePayMode;
import com.fanwe.shop.model.custommsg.CustomMsgShopBuySuc;
import com.fanwe.shop.model.custommsg.CustomMsgShopPush;

import java.util.HashMap;

public class LiveConstant
{
    /**
     * sdk最大连麦数量
     */
    public static final int MAX_LINK_MIC_COUNT = 3;

    public static final String LIVE_PRIVATE_KEY_TAG = SDBase64.decodeToString("8J+UkQ==");

    public static final String LIVE_HOT_CITY = "热门";

    public static final String LEVEL_SPAN_KEY = "level";

    public static final String DEFAULT_CHARSET = "UTF-8";

    public static final class LiveSdkTag
    {
        /**
         * 腾讯sdk日志tag
         */
        public static final String TAG_SDK_TENCENT = "tag_sdk_tencent";
        /**
         * 金山sdk日志tag
         */
        public static final String TAG_SDK_KSY = "tag_sdk_ksy";
    }

    public static final class LiveSdkType
    {
        /**
         * 腾讯sdk
         */
        public static final int TENCENT = 0;
        /**
         * 金山sdk
         */
        public static final int KSY = 1;
    }

    /**
     * 推流视频质量
     */
    public static final class VideoQualityType
    {
        /**
         * 标清
         */
        public static final int VIDEO_QUALITY_STANDARD = 0;
        /**
         * 高清
         */
        public static final int VIDEO_QUALITY_HIGH = 1;
        /**
         * 超清
         */
        public static final int VIDEO_QUALITY_SUPER = 2;
    }


    /**
     * 用于发送和接收自定义消息的类型判断
     */
    public static final class CustomMsgType
    {
        public static final int MSG_NONE = -1;
        /**
         * 正常文字聊天消息
         */
        public static final int MSG_TEXT = 0;
        /**
         * 收到发送礼物消息
         */
        public static final int MSG_GIFT = 1;
        /**
         * 收到弹幕消息
         */
        public static final int MSG_POP_MSG = 2;
        /**
         * 主播结束直播
         */
        public static final int MSG_CREATER_QUIT = 3;
        /**
         * 禁言消息
         */
        public static final int MSG_FORBID_SEND_MSG = 4;
        /**
         * 观众进入直播间消息
         */
        public static final int MSG_VIEWER_JOIN = 5;
        /**
         * 观众退出直播间消息
         */
        public static final int MSG_VIEWER_QUIT = 6;
        /**
         * 主播结束直播消息，直播间内的人可收到
         */
        public static final int MSG_END_VIDEO = 7;
        /**
         * 红包消息
         */
        public static final int MSG_RED_ENVELOPE = 8;
        /**
         * 直播消息
         */
        public static final int MSG_LIVE_MSG = 9;
        /**
         * 主播离开
         */
        public static final int MSG_CREATER_LEAVE = 10;
        /**
         * 主播回来
         */
        public static final int MSG_CREATER_COME_BACK = 11;
        /**
         * 点亮
         */
        public static final int MSG_LIGHT = 12;
        /**
         * 申请连麦
         */
        public static final int MSG_APPLY_LINK_MIC = 13;
        /**
         * 接受连麦
         */
        public static final int MSG_ACCEPT_LINK_MIC = 14;
        /**
         * 拒绝连麦
         */
        public static final int MSG_REJECT_LINK_MIC = 15;
        /**
         * 结束连麦
         */
        public static final int MSG_STOP_LINK_MIC = 16;
        /**
         * 结连麦成功
         */
        public static final int MSG_Greed_LINK_MIC = 96;
        /**
         * 申请pk
         */
        public static final int MSG_STOP_LIVE = 17;
        /**
         * 任何主播的结束，服务端都会推这条消息下来，用于更新列表状态
         */
        public static final int MSG_LIVE_STOPPED = 18;
        /**
         * 私聊文字消息
         */
        public static final int MSG_PRIVATE_TEXT = 20;

        /**
         * 私聊语音消息
         */
        public static final int MSG_PRIVATE_VOICE = 21;
        /**
         * 私聊图片消息
         */
        public static final int MSG_PRIVATE_IMAGE = 22;
        /**
         * 私聊礼物消息
         */
        public static final int MSG_PRIVATE_GIFT = 23;
        /**
         * 竞拍成功
         */
        public static final int MSG_AUCTION_SUCCESS = 25;
        /**
         * 竞拍通知付款，比如第一名超时未付款，通知下一名付款
         */
        public static final int MSG_AUCTION_NOTIFY_PAY = 26;
        /**
         * 流拍
         */
        public static final int MSG_AUCTION_FAIL = 27;

        /**
         * 推送出价信息
         */
        public static final int MSG_AUCTION_OFFER = 28;

        /**
         * 支付成功
         */
        public static final int MSG_AUCTION_PAY_SUCCESS = 29;
        /**
         * 主播发起竞拍成功
         */
        public static final int MSG_AUCTION_CREATE_SUCCESS = 30;

        /**
         * 购物直播商品推送
         */
        public static final int MSG_SHOP_GOODS_PUSH = 31;

        /**
         * 商品购买成功推送
         */
        public static final int MSG_SHOP_GOODS_BUY_SUCCESS = 37;

        /**
         * 开启付费模式推送
         */
        public static final int MSG_START_PAY_MODE = 32;
        /**
         * 游戏关闭推送
         */
        public static final int MSG_GAMES_STOP = 34;

        /**
         * 游戏推送
         */
        public static final int MSG_GAME = 39;

        /**
         * 开启按场直播模式推送
         */
        public static final int MSG_START_SCENE_PAY_MODE = 40;

        /**
         * 管理员警告消息(由主播接收)
         */
        public static final int MSG_WARNING_BY_MANAGER = 41;

        /**
         * 通用数据格式
         */
        public static final int MSG_DATA = 42;

        /**
         * 游戏上庄推送
         */
        public static final int MSG_GAME_BANKER = 43;

        /**
         * 推流状态推送( 推流中 关闭推流)
         */
        public static final int MSG_PUSH_STATUS = 44;

        /**
         * 大型礼物通知消息
         */
        public static final int MSG_LARGE_GIFT = 50;
        public static final int MSG_PRIZE = 60;
        public static final int MSG_HEAT_RANK = 70;

        //开通守护通知
        public static final int MSG_LIVE_OPEN_SHOUHU=150;
    }

    public static final HashMap<Integer, Class<? extends ICustomMsg>> mapCustomMsgClass = new HashMap<>();

    static
    {
        mapCustomMsgClass.put(CustomMsgType.MSG_TEXT, CustomMsgText.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_GIFT, CustomMsgGift.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_POP_MSG, CustomMsgPopMsg.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_CREATER_QUIT, CustomMsgCreaterQuit.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_FORBID_SEND_MSG, CustomMsgForbidSendMsg.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_VIEWER_JOIN, CustomMsgViewerJoin.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_VIEWER_QUIT, CustomMsgViewerQuit.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_END_VIDEO, CustomMsgEndVideo.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_RED_ENVELOPE, CustomMsgRedEnvelope.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_LIVE_MSG, CustomMsgLiveMsg.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_CREATER_LEAVE, CustomMsgCreaterLeave.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_CREATER_COME_BACK, CustomMsgCreaterComeback.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_LIGHT, CustomMsgLight.class);

        mapCustomMsgClass.put(CustomMsgType.MSG_APPLY_LINK_MIC, CustomMsgApplyLinkMic.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_ACCEPT_LINK_MIC, CustomMsgAcceptLinkMic.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_REJECT_LINK_MIC, CustomMsgRejectLinkMic.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_STOP_LINK_MIC, CustomMsgStopLinkMic.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_HEAT_RANK, CustomMsgHeatRank.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_Greed_LINK_MIC, CustomMsgGreedLinkMic.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_STOP_LIVE, CustomMsgStopLive.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_LIVE_STOPPED, CustomMsgLiveStopped.class);

        mapCustomMsgClass.put(CustomMsgType.MSG_PRIVATE_TEXT, CustomMsgPrivateText.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_PRIVATE_VOICE, CustomMsgPrivateVoice.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_PRIVATE_IMAGE, CustomMsgPrivateImage.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_PRIVATE_GIFT, CustomMsgPrivateGift.class);

        mapCustomMsgClass.put(CustomMsgType.MSG_AUCTION_SUCCESS, CustomMsgAuctionSuccess.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_AUCTION_NOTIFY_PAY, CustomMsgAuctionNotifyPay.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_AUCTION_FAIL, CustomMsgAuctionFail.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_AUCTION_OFFER, CustomMsgAuctionOffer.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_AUCTION_PAY_SUCCESS, CustomMsgAuctionPaySuccess.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_AUCTION_CREATE_SUCCESS, CustomMsgAuctionCreateSuccess.class);

        mapCustomMsgClass.put(CustomMsgType.MSG_SHOP_GOODS_PUSH, CustomMsgShopPush.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_SHOP_GOODS_BUY_SUCCESS, CustomMsgShopBuySuc.class);

        mapCustomMsgClass.put(CustomMsgType.MSG_START_PAY_MODE, CustomMsgStartPayMode.class);

        mapCustomMsgClass.put(CustomMsgType.MSG_GAMES_STOP, GameMsgModel.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_GAME, GameMsgModel.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_GAME_BANKER, CustomMsgGameBanker.class);

        mapCustomMsgClass.put(CustomMsgType.MSG_START_SCENE_PAY_MODE, CustomMsgStartScenePayMode.class);

        mapCustomMsgClass.put(CustomMsgType.MSG_WARNING_BY_MANAGER, CustomMsgWarning.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_DATA, CustomMsgData.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_LIVE_OPEN_SHOUHU, CustomMsgOpenShouhu.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_PRIZE, CustomMsgPrize.class);
        mapCustomMsgClass.put(CustomMsgType.MSG_LARGE_GIFT, CustomMsgLargeGift.class);

        mapCustomMsgClass.put(CustomMsgType.MSG_PUSH_STATUS, BMCustomMsgPushStatus.class);
    }

    /**
     * 通用数据格式类型
     */
    public static final class CustomMsgDataType
    {
        /**
         * 直播间观众列表数据
         */
        public static final int DATA_VIEWER_LIST = 0;
        /**
         * 直播间当前连麦的用户信息和连麦的信息
         */
        public static final int DATA_LINK_MIC_INFO = 1;
        /**
         * 直播间当前pk的用户信息和连麦的信息
         */
        public static final int DATA_PK_INFO = 2;
    }

    /**
     * 用于直播间聊天的类型判断
     */
    public static final class LiveMsgType
    {
        /**
         * 正常文字聊天消息
         */
        public static final int MSG_TEXT = 0;
        /**
         * 观看者加入
         */
        public static final int MSG_VIEWER_JOIN = MSG_TEXT + 1;
        /**
         * 观众收到礼物发送成功消息
         */
        public static final int MSG_GIFT_VIEWER = MSG_VIEWER_JOIN + 1;
        /**
         * 主播收到礼物发送成功消息
         */
        public static final int MSG_GIFT_CREATER = MSG_GIFT_VIEWER + 1;
        /**
         * 禁言消息
         */
        public static final int MSG_FORBID_SEND_MSG = MSG_GIFT_CREATER + 1;
        /**
         * 直播间消息
         */
        public static final int MSG_LIVE_MSG = MSG_FORBID_SEND_MSG + 1;
        /**
         * 红包消息
         */
        public static final int MSG_RED_ENVELOPE = MSG_LIVE_MSG + 1;
        /**
         * 主播离开
         */
        public static final int MSG_CREATER_LEAVE = MSG_RED_ENVELOPE + 1;
        /**
         * 主播回来
         */
        public static final int MSG_CREATER_COME_BACK = MSG_CREATER_LEAVE + 1;
        /**
         * 点亮
         */
        public static final int MSG_LIGHT = MSG_CREATER_COME_BACK + 1;
        /**
         * 弹幕
         */
        public static final int MSG_POP_MSG = MSG_LIGHT + 1;
        /**
         * 高级用户加入
         */
        public static final int MSG_PRO_VIEWER_JOIN = MSG_POP_MSG + 1;
        /**
         * 推送出价信息
         */
        public static final int MSG_AUCTION_OFFER = MSG_PRO_VIEWER_JOIN + 1;
        /**
         * 竞拍成功
         */
        public static final int MSG_AUCTION_SUCCESS = MSG_AUCTION_OFFER + 1;
        /**
         * 支付成功
         */
        public static final int MSG_AUCTION_PAY_SUCCESS = MSG_AUCTION_SUCCESS + 1;
        /**
         * 流拍
         */
        public static final int MSG_AUCTION_FAIL = MSG_AUCTION_PAY_SUCCESS + 1;
        /**
         * 竞拍通知付款，比如第一名超时未付款，通知下一名付款
         */
        public static final int MSG_AUCTION_NOTIFY_PAY = MSG_AUCTION_FAIL + 1;
        /**
         * 主播发起竞拍成功
         */
        public static final int MSG_AUCTION_CREATE_SUCCESS = MSG_AUCTION_NOTIFY_PAY + 1;

        /**
         * 购物直播商品推送
         */
        public static final int MSG_SHOP_GOODS_PUSH = MSG_AUCTION_CREATE_SUCCESS + 1;
        /**
         * 商品购买成功推送
         */
        public static final int MSG_SHOP_GOODS_BUY_SUCCESS = MSG_SHOP_GOODS_PUSH + 1;

        /**
         * 是否推流状态
         */
        public static final int MSG_PUSH_STATUS = MSG_SHOP_GOODS_BUY_SUCCESS+ 1;

        public static final int MSG_PK_BEGIN = MSG_SHOP_GOODS_BUY_SUCCESS+ 1;
        public static final int MSG_PK_MVP = MSG_PK_BEGIN+ 1;
        public static final int MSG_PK_STOP = MSG_PK_MVP+ 1;
        /**
         * 守护用户加入
         */
        public static final int MSG_SHOUHU_VIEWER_JOIN = MSG_PK_STOP + 1;
    }

    /**
     * 私聊消息类型
     */
    public static final class PrivateMsgType
    {
        /**
         * 文字消息
         */
        public static final int MSG_TEXT_LEFT = 0;
        public static final int MSG_TEXT_RIGHT = 1;

        //语音
        public static final int MSG_VOICE_LEFT = 2;
        public static final int MSG_VOICE_RIGHT = 3;

        //图片
        public static final int MSG_IMAGE_LEFT = 4;
        public static final int MSG_IMAGE_RIGHT = 5;

        //礼物
        public static final int MSG_GIFT_LEFT = 6;
        public static final int MSG_GIFT_RIGHT = 7;

    }

    public static final class GiftType
    {
        /**
         * 普通礼物
         */
        public static final int NORMAL = 0;

        /**
         * gif礼物
         */
        public static final int GIF = 1;

        /**
         * 大型礼物
         */
        public static final int ANIMATOR = 2;
        /**
         * svga
         */
        public static final int SVGA = 3;
        //手绘4
        public static final int SHOUHUI= 4;
    }

    public static final class GiftAnimatorType
    {
        public static final String PLANE1 = "plane1";

        public static final String PLANE2 = "plane2";
        /**
         * 火箭
         */
        public static final String ROCKET1 = "rocket1";
        /**
         * 蓝色法拉利
         */
        public static final String FERRARI = "ferrari";
        /**
         * 红色兰博基尼
         */
        public static final String LAMBORGHINI = "lamborghini";
        public static final String CASTLE = "castle";//城堡
        public static final String THREELIFETIMES = "threeLifeTimes";//三生三世
        public static final String WEDDING = "wedding";//求婚
        public static final String CAKE = "cake";//蛋糕
        public static final String STAGE = "stage";//跑车 迈凯伦
    }

    public static final class PluginClassName
    {
        public static final String P_LIVE_PAY = "live_pay";

        public static final String P_LIVE_PAY_SCENE = "live_pay_scene";

        public static final String P_PAI = "pai";

        public static final String P_SHOP = "shop";

        public static final String P_GAME = "game";

        public static final String P_LIVE_PK = "live_pk";

        /**
         * 我的小店
         */
        public static final String P_PODCAST_GOODS = "podcast_goods";

        public static final String P_LIVE = "live";//直播
    }
}
