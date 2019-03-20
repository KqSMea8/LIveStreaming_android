package com.fanwe.live.common;

import android.text.TextUtils;

import com.fanwe.baimei.common.BMCommonInterface;
import com.fanwe.games.model.App_banker_applyActModel;
import com.fanwe.games.model.App_banker_listActModel;
import com.fanwe.games.model.App_getGamesActModel;
import com.fanwe.games.model.App_plugin_initActModel;
import com.fanwe.games.model.App_requestGameIncomeActModel;
import com.fanwe.games.model.App_startGameActModel;
import com.fanwe.games.model.GamesWawaModel;
import com.fanwe.games.model.Games_betActModel;
import com.fanwe.games.model.Games_logActModel;
import com.fanwe.hybrid.constant.ApkConstant;
import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.http.AppHttpUtil;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.http.AppRequestCallbackWrapper;
import com.fanwe.hybrid.http.AppRequestParams;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.hybrid.umeng.UmengPushManager;
import com.fanwe.library.adapter.http.handler.SDRequestHandler;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.MD5Util;
import com.fanwe.library.utils.SDJsonUtil;
import com.fanwe.live.IMHelper;
import com.fanwe.live.business.LiveCreaterBusiness;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.event.ERequestFollowSuccess;
import com.fanwe.live.model.App_AuthentActModel;
import com.fanwe.live.model.App_BaseInfoActModel;
import com.fanwe.live.model.App_ContActModel;
import com.fanwe.live.model.App_ExchangeRuleActModel;
import com.fanwe.live.model.App_GainRecordActModel;
import com.fanwe.live.model.App_ProfitBindingActModel;
import com.fanwe.live.model.App_RankConsumptionModel;
import com.fanwe.live.model.App_RankContributionModel;
import com.fanwe.live.model.App_RegionListActModel;
import com.fanwe.live.model.App_UserVipPurchaseActModel;
import com.fanwe.live.model.App_aliyun_stsActModel;
import com.fanwe.live.model.App_check_lianmaiActModel;
import com.fanwe.live.model.App_del_videoActModel;
import com.fanwe.live.model.App_distribution_indexActModel;
import com.fanwe.live.model.App_doExchangeActModel;
import com.fanwe.live.model.App_do_loginActModel;
import com.fanwe.live.model.App_do_updateActModel;
import com.fanwe.live.model.App_end_videoActModel;
import com.fanwe.live.model.App_family_createActModel;
import com.fanwe.live.model.App_family_indexActModel;
import com.fanwe.live.model.App_family_listActModel;
import com.fanwe.live.model.App_family_user_confirmActModel;
import com.fanwe.live.model.App_family_user_logoutActModel;
import com.fanwe.live.model.App_family_user_r_user_listActModel;
import com.fanwe.live.model.App_family_user_user_listActModel;
import com.fanwe.live.model.App_focus_follow_ActModel;
import com.fanwe.live.model.App_followActModel;
import com.fanwe.live.model.App_forbid_send_msgActModel;
import com.fanwe.live.model.App_gameCoinsExchangeActModel;
import com.fanwe.live.model.App_gameExchangeRateActModel;
import com.fanwe.live.model.App_get_p_user_idActModel;
import com.fanwe.live.model.App_get_videoActModel;
import com.fanwe.live.model.App_is_user_verifyActModel;
import com.fanwe.live.model.App_live_live_payActModel;
import com.fanwe.live.model.App_live_live_pay_agreeActModel;
import com.fanwe.live.model.App_monitorActModel;
import com.fanwe.live.model.App_my_follow_ActModel;
import com.fanwe.live.model.App_payActModel;
import com.fanwe.live.model.App_plugin_statusActModel;
import com.fanwe.live.model.App_profitActModel;
import com.fanwe.live.model.App_propActModel;
import com.fanwe.live.model.App_propNewActModel;
import com.fanwe.live.model.App_rechargeActModel;
import com.fanwe.live.model.App_red_envelopeActModel;
import com.fanwe.live.model.App_send_mobile_verifyActModel;
import com.fanwe.live.model.App_set_adminActModel;
import com.fanwe.live.model.App_shareActModel;
import com.fanwe.live.model.App_sociaty_indexActModel;
import com.fanwe.live.model.App_sociaty_joinActModel;
import com.fanwe.live.model.App_sociaty_listActModel;
import com.fanwe.live.model.App_sociaty_user_confirmActModel;
import com.fanwe.live.model.App_sociaty_user_listActModel;
import com.fanwe.live.model.App_sociaty_user_logoutActModel;
import com.fanwe.live.model.App_start_lianmaiActModel;
import com.fanwe.live.model.App_stop_lianmaiActModel;
import com.fanwe.live.model.App_tipoff_typeActModel;
import com.fanwe.live.model.App_uploadImageActModel;
import com.fanwe.live.model.App_userEditActModel;
import com.fanwe.live.model.App_user_adminActModel;
import com.fanwe.live.model.App_user_homeActModel;
import com.fanwe.live.model.App_user_red_envelopeActModel;
import com.fanwe.live.model.App_user_reviewActModel;
import com.fanwe.live.model.App_userinfoActModel;
import com.fanwe.live.model.App_usersigActModel;
import com.fanwe.live.model.App_video_cstatusActModel;
import com.fanwe.live.model.App_viewerActModel;
import com.fanwe.live.model.Deal_send_propActModel;
import com.fanwe.live.model.IndexSearch_areaActModel;
import com.fanwe.live.model.Index_focus_videoActModel;
import com.fanwe.live.model.Index_indexActModel;
import com.fanwe.live.model.Index_new_videoActModel;
import com.fanwe.live.model.Index_topicActModel;
import com.fanwe.live.model.MemoryModel;
import com.fanwe.live.model.Music_downurlActModel;
import com.fanwe.live.model.Music_searchActModel;
import com.fanwe.live.model.Music_user_musicActModel;
import com.fanwe.live.model.MyPackListModel;
import com.fanwe.live.model.PackgeModel;
import com.fanwe.live.model.PayShouhuModel;
import com.fanwe.live.model.ServerData;
import com.fanwe.live.model.SettingsSecurityActModel;
import com.fanwe.live.model.Settings_black_listActModel;
import com.fanwe.live.model.ShopModel;
import com.fanwe.live.model.ShouhuListModel;
import com.fanwe.live.model.SignDailyModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.User_friendsActModel;
import com.fanwe.live.model.User_set_blackActModel;
import com.fanwe.live.model.Video_add_videoActModel;
import com.fanwe.live.model.Video_check_statusActModel;
import com.fanwe.live.model.Video_private_room_friendsActModel;
import com.fanwe.shortvideo.model.MusicDownloadModel;
import com.fanwe.shortvideo.model.MusicListModel;
import com.fanwe.shortvideo.model.MusicSearchModel;
import com.fanwe.shortvideo.model.ShortVideoDetailModel;
import com.fanwe.shortvideo.model.ShortVideoListModel;
import com.fanwe.shortvideo.model.SignModel;
import com.fanwe.shortvideo.model.VideoCommentListModel;
import com.fanwe.shortvideo.model.VideoPraiseModel;
import com.sunday.eventbus.SDEventManager;
import com.weibo.model.XRAddVideoPlayCountResponseModel;
import com.weibo.model.XRCommentFavorite;
import com.weibo.model.XRCommentListModel;
import com.weibo.model.XRCommentModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.fanwe.baimei.common.BMCommonInterface.setAppRequestParams;

/**
 * @author wxy
 */
public class CommonInterface {
    /**
     */
    public static void requestComment(int page, AppRequestCallback<XRCommentModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("discovery");
        params.putAct("index");
        params.put("itype", "xr");//视频id
        params.put("page", page);//视频id
        AppHttpUtil.getInstance().get(params, listener);

    }
    /**
     * 点赞与取消点赞
     */
    public static void requestSetPraise(String sv_id, AppRequestCallback<VideoPraiseModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("videosmall");
        params.putAct("setpraise");
        params.put("sv_id", sv_id);//视频id
        AppHttpUtil.getInstance().get(params, listener);

    }

    /**
     * 删除小视频
     */
    public static void requestDelVideo(String sv_id, AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("videosmall");
        params.putAct("delvideo");
        params.put("sv_id", sv_id);//视频id
        AppHttpUtil.getInstance().get(params, listener);

    }
    /**
     * 添加小视频
     */
    public static void requestAddVideo(int sv_id, AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("videosmall");
        params.putAct("add_video_count");
        params.put("sv_id", sv_id);//视频id
        AppHttpUtil.getInstance().get(params, listener);

    }
    /**
     * 小视频添加评论
     */
    public static void requestAddComment(String sv_id, String com_content, String userid, AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("videosmall");
        params.putAct("addcomment");
        params.put("sv_id", sv_id);//视频id
        params.put("com_content", com_content);
        params.put("to_comment_id", userid);
        AppHttpUtil.getInstance().get(params, listener);

    }

    /**
     * 小视频评论列表
     */
    public static void requestCommentList(int page, String sv_id, AppRequestCallback<VideoCommentListModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("videosmall");
        params.putAct("commentlist");
        params.put("page", page);
        params.put("sv_id", sv_id);//视频id
        AppHttpUtil.getInstance().get(params, listener);

    }

    /**
     * 获取上传签名
     */
    public static void requestUpLoadSign(AppRequestCallback<SignModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("videosmall");
        params.putAct("getsign");
        AppHttpUtil.getInstance().post(params, listener);

    }

    /**
     * 上传视频
     */
    public static void requestUpLoadVideo(String sv_url, String sv_img, String sv_content, AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("videosmall");
        params.putAct("addvideo");
        params.put("sv_url", sv_url);//链接
        params.put("sv_img", sv_img);//封面
        params.put("sv_content", sv_content);//描述
        AppHttpUtil.getInstance().get(params, listener);

    }

    /**
     * 小视频在线音乐列表搜索
     */
    public static void requestSearchMusicList(String songStr, AppRequestCallback<MusicSearchModel> listener) {
        String reqUrl = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.search.catalogSug&query=" + songStr;
        AppRequestParams params = new AppRequestParams();
        params.setUrl(reqUrl);
        AppHttpUtil.getInstance().get(params, listener);

    }

    /**
     * 小视频在线音乐列表获取音乐下载地址
     */
    public static void requestDownLoadMusicPath(long songIds, AppRequestCallback<MusicDownloadModel> listener) {
        String reqUrl = "http://music.baidu.com/data/music/links?songIds=" + songIds;
        AppRequestParams params = new AppRequestParams();
        params.setUrl(reqUrl);
        AppHttpUtil.getInstance().get(params, listener);

    }

    /**
     * 小视频在线音乐列表
     */
    public static void requestMusicList(AppRequestCallback<MusicListModel> listener) {
        String reqUrl = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList&type=2&offset=0&qq-pf-to=pcqq.discussion";
        AppRequestParams params = new AppRequestParams();
        params.setUrl(reqUrl);
        AppHttpUtil.getInstance().get(params, listener);

    }

    /**
     * 小视频送礼物
     */
    public static void requestVideoSendGift(int prop_id, int num, String sv_id,int is_backpack,String coordinate, AppRequestCallback<Deal_send_propActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("deal");
        params.putAct("svideo_prop");
        params.put("prop_id", prop_id);//礼物id
        params.put("num", num);//礼物数量
        params.put("sv_id", sv_id);//小视频ID
        params.put("is_backpack", is_backpack);
        params.put("coordinate", coordinate);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 小视频详情
     *
     * @param videoId
     */
    public static void requestShortVideoDetails(String videoId, AppRequestCallback<ShortVideoDetailModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("videosmall");
        params.putAct("videodetail");
        params.put("sv_id", videoId);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 小视频tab 请求小视频列表
     *
     * @param page
     */
    public static void requestShortVideoList(int page, String user_id, AppRequestCallback<ShortVideoListModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("videosmall");
        params.putAct("svlist");
        params.put("user_id", user_id);
        params.put("page", page);
        AppHttpUtil.getInstance().post(params, listener);
    }
    /**
     * 获取标签
     *
     * @param
     */
    public static void requestTagList(String to_user_id ,AppRequestCallback<MemoryModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user");
        params.putAct("tag_list");
        params.put("to_user_id", to_user_id);
        AppHttpUtil.getInstance().post(params, listener);
    }
    /**
     * 设置标签
     *
     * @param
     */
    public static void setTags(String to_user_id ,String tag_ids,AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user");
        params.putAct("set_tag");
        params.put("to_user_id", to_user_id);
        params.put("tag_ids", tag_ids);
        AppHttpUtil.getInstance().post(params, listener);
    }
    /**
     * s商城购买接口
     *
     * @param
     */
    public static void requestShopPay(String mount_id ,String rule_id,AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("mount");
        params.putAct("pay");
        params.put("mount_id", mount_id);
        params.put("rule_id", rule_id);
        AppHttpUtil.getInstance().post(params, listener);
    }
    /**
     * s商城接口
     *
     * @param
     */
    public static void requestShopList(AppRequestCallback<ShopModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("mount");
        params.putAct("mount_rule_list");
        AppHttpUtil.getInstance().post(params, listener);
    }
    /**
     * 坐骑设置
     *
     * @param
     */
    public static void requestSetMount(String mount_id ,AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("mount");
        params.putAct("use_mount");
        params.put("mount_id", mount_id);
        AppHttpUtil.getInstance().post(params, listener);
    }
    /**
     * s我的背包
     *
     * @param
     */
    public static void requestMyPackList(AppRequestCallback<MyPackListModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("mount");
        params.putAct("my_mounts");
        AppHttpUtil.getInstance().post(params, listener);
    }
    /**
     * s发布图文
     *
     * @param
     * content	string	发送内容
    latitude	string	经度
    longitude	string	纬度
    address	string	地址
    index_id	int	封面ID
    - data	object	图片序列号列表
    url	string	图片地址
    is_model	int	是否高斯处理
     */
    public static void requestCommentImageText(String content,String latitude,String longitude,String address,String imagelist,AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("publish");
        params.putAct("do_publish");
        params.put("itype", "xr");
        params.put("publish_type", "imagetext");
        params.put("content", content);
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        params.put("address", address);
        params.put("data", imagelist);
        AppHttpUtil.getInstance().post(params, listener);
    }
    /**
     * s发布视频
     *
     * @param
     */
    public static void requestCommentVideo(String content,String latitude,String longitude,String url,String video_url,AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("publish");
        params.putAct("do_publish");
        params.put("itype", "xr");
        params.put("publish_type", "video");
        params.put("content", content);
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        params.put("photo_image", url);
        params.put("video_url", video_url);
        AppHttpUtil.getInstance().post(params, listener);
    }
    //点赞 发表评论
    public static void requestDynamicFavorite(int type,int weiboId,int to_comment_id,String content, AppRequestCallback<XRCommentFavorite> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user");
        params.putAct("publish_comment");
        params.put("itype", "xr");
        params.put("type", type);
        params.put("weibo_id", weiboId);
        params.put("to_comment_id", to_comment_id);
        params.put("content", content);
        AppHttpUtil.getInstance().post(params, listener);
    }
//    type	number	1 表示评论 2表示点赞 4表示收藏
//    weibo_id	number	被评论的动态ID
//    to_comment_id	number	被评论的评论ID
//    content	string	评论内容
    /**
     * @param weibo_id 动态ID
     * @param listener
     */
    public static void requestAddVideoPlayCount(int weibo_id, AppRequestCallback<XRAddVideoPlayCountResponseModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("weibo");
        params.putAct("add_video_count");
        params.put("svId", weibo_id);
        AppHttpUtil.getInstance().post(params, listener);
    }
    /**
     * 评论列表
     * @param listener
     */
    public static void requestCommentList(int weibo_id,AppRequestCallback<XRCommentListModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("weibo");
        params.putAct("comment_list");
        params.put("itype", "xr");
        params.put("weibo_id", weibo_id);
        AppHttpUtil.getInstance().post(params, listener);
    }
    /**
     * 测试刷礼物接口
     *
     * @param room_id
     */
    public static void requestTestSendGift(int room_id) {
        UserModel user = UserModelDao.query();
        if (user == null) {
            return;
        }
        AppRequestParams params = new AppRequestParams();
        params.putCtl("deal");
        params.putAct("test_pop_prop");
        params.put("room_id", room_id);
        params.put("cstype", user.getUser_id());
        AppHttpUtil.getInstance().post(params, new AppRequestCallback<BaseActModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.isOk()) {
                    LogUtil.i("requestTestSendGift success");
                }
            }
        });
    }

    /**
     * 服务端退出登录接口
     */
    public static void requestLogout(AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("login");
        params.putAct("loginout");
        AppHttpUtil.getInstance().post(params, new AppRequestCallbackWrapper<BaseActModel>(listener) {
            @Override
            protected void onSuccess(SDResponse resp) {
                LogUtil.i("logout success");
            }
        });
    }

    /**
     * 初始化接口
     *
     * @param callback
     */
    public static void requestInit(AppRequestCallback<InitActModel> callback) {
        //初始化回调
        AppRequestCallbackWrapper<InitActModel> callbackWrapper = new AppRequestCallbackWrapper<InitActModel>(callback) {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.isOk()) {
                    InitActModelDao.insertOrUpdate(actModel);
                    LogUtil.i("requestInit success save InitActModel");
                } else {
                    LogUtil.i("requestInit fail InitActModel is not ok");
                }
            }

            @Override
            protected void onError(SDResponse resp) {
                super.onError(resp);
                LogUtil.i("requestInit error");
            }
        };

        AppRequestParams params = new AppRequestParams();
        if (AppRuntimeWorker.getIsOpenWebviewMain()) {
            params.setUrl(ApkConstant.SERVER_URL_INIT_URL);
            AppHttpUtil.getInstance().get(params, callbackWrapper);
        } else {
            params.putCtl("app");
            params.putAct("init");
            AppHttpUtil.getInstance().post(params, callbackWrapper);
        }
    }

    /**
     * 请求当前用户的usersig
     */
    public static void requestUsersig(AppRequestCallback<App_usersigActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user");
        params.putAct("usersig");
        AppHttpUtil.getInstance().post(params, new AppRequestCallbackWrapper<App_usersigActModel>(listener) {

            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.getStatus() == 1) {
                    String usersig = actModel.getUsersig();
                    AppRuntimeWorker.setUsersig(usersig);
                    AppRuntimeWorker.startContext();
                }
            }
        });
    }

    /**
     * 获得礼物列表
     *
     * @param listener
     */
    public static void requestGift(AppRequestCallback<App_propActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("app");
        params.putAct("prop");
        AppHttpUtil.getInstance().post(params, listener);
    }
    /**
     * 获得新礼物列表
     *
     * @param listener
     */
    public static void requestNewGift(AppRequestCallback<App_propNewActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("app");
        params.putAct("new_prop");
        AppHttpUtil.getInstance().post(params, listener);
    }
    /**
     * 获得背包礼物列表
     *
     * @param listener
     */
    public static void requestPackgeGift(AppRequestCallback<PackgeModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user");
        params.putAct("prop_backpack");
        AppHttpUtil.getInstance().post(params, listener);
    }
    /**
     * 请求当前用户的信息
     *
     * @param listener
     */
    public static void requestMyUserInfo(AppRequestCallback<App_userinfoActModel> listener) {
        UserModel user = UserModelDao.query();
        if (user == null) {
            return;
        }

        requestUserInfo(null, null, new AppRequestCallbackWrapper<App_userinfoActModel>(listener) {
            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.getStatus() == 1) {
                    UserModel user = actModel.getUser();
                    UserModelDao.insertOrUpdate(user);

                }
            }
        });
    }

    /**
     * 获得用户信息
     *
     * @param podcast_id 主播id
     * @param to_user_id 操作对象id
     * @param listener
     */
    public static SDRequestHandler requestUserInfo(String podcast_id, String to_user_id, AppRequestCallback<App_userinfoActModel> listener) {
        return requestUserInfo(podcast_id, to_user_id, 0, listener);
    }

    /**
     * 获得用户信息
     *
     * @param podcast_id 主播id
     * @param to_user_id 操作对象id
     * @param room_id    大于0情况下,代表房间ID(查询用户在房间内对应主播的关系)
     * @param listener
     */
    public static SDRequestHandler requestUserInfo(String podcast_id, String to_user_id, int room_id, AppRequestCallback<App_userinfoActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user");
        params.putAct("userinfo");
        params.put("podcast_id", podcast_id);
        params.put("to_user_id", to_user_id);
        params.put("room_id", room_id);
        return AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 弹幕
     *
     * @param room_id 直播间id
     * @param msg     消息内容
     * @return
     */
    public static AppRequestParams requestPopMsgParams(int room_id, String msg) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("deal");
        params.putAct("pop_msg");
        params.put("room_id", room_id);
        params.put("msg", msg);
        return params;
    }
    /**
     * 喇叭
     *
     * @param room_id 直播间id
     * @param msg     消息内容
     * @return
     */
    public static AppRequestParams requestFullMsgParams(int room_id, String msg) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("deal");
        params.putAct("full_msg");
        params.put("room_id", room_id);
        params.put("msg", msg);
        return params;
    }
    /**
     * 送礼物
     *
     * @param prop_id  礼物id
     * @param num      礼物数量
     * @param is_plus  是否叠加
     * @param is_coins 是否金币礼物
     * @param room_id  直播间id
     * @return
     */
    public static AppRequestParams requestSendGiftParams(int prop_id, int num, int is_plus, int is_coins, int room_id,int is_backpack,String coordinate) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("deal");
        params.putAct("pop_prop");
        params.put("prop_id", prop_id);
        params.put("num", num);
        params.put("is_plus", is_plus);
        params.put("is_coins", is_coins);
        params.put("room_id", room_id);
        params.put("is_backpack", is_backpack);
        params.put("coordinate", coordinate);
        return params;
    }
    /**
     * 获得直播间观众列表
     *
     * @param group_id 聊天室id
     * @param page     当前分页
     * @param listener
     */
    public static SDRequestHandler requestViewerList(String group_id, int page, AppRequestCallback<App_viewerActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("video");
        params.putAct("viewer");
        params.put("group_id", group_id);
        params.put("page", page);
        return AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 禁言
     *
     * @param group_id   聊天室id
     * @param to_user_id 操作对象id
     * @param second     禁言时间，单位为秒; 为0时表示取消禁言
     * @param listener
     */
    public static void requestForbidSendMsg(String group_id, String to_user_id, int second, AppRequestCallback<App_forbid_send_msgActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user");
        params.putAct("forbid_send_msg");
        params.put("group_id", group_id);
        params.put("to_user_id", to_user_id);
        params.put("second", second);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 关注/取消关注
     *
     * @param to_user_id 操作对象id
     * @param room_id    直播间id
     * @param listener
     */
    public static void requestFollow(final String to_user_id, int room_id, AppRequestCallback<App_followActModel> listener) {

        AppRequestParams params = new AppRequestParams();
        params.putCtl("user");
        params.putAct("follow");
        params.put("to_user_id", to_user_id);
        params.put("room_id", room_id);
        AppHttpUtil.getInstance().post(params, new AppRequestCallbackWrapper<App_followActModel>(listener) {
            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.isOk()) {
                    ERequestFollowSuccess event = new ERequestFollowSuccess();
                    event.userId = to_user_id;
                    event.actModel = actModel;
                    SDEventManager.post(event);
                }
            }
        });
    }


    /**
     * 主播心跳
     *
     * @param data
     * @param listener
     * @return
     */
    public static void requestMonitor(LiveCreaterBusiness.CreaterMonitorData data, AppRequestCallback<App_monitorActModel> listener) {
        if (data == null) {
            return;
        }
        AppRequestParams params = new AppRequestParams();
        params.putCtl("video");
        params.putAct("monitor");
        params.put("room_id", data.roomId);
        params.put("watch_number", data.viewerNumber);
        params.put("vote_number", data.ticketNumber);
        params.put("lianmai_num", data.linkMicNumber);
        if (data.liveQualityData != null) {
            params.put("live_quality", SDJsonUtil.object2Json(data.liveQualityData));
        }
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 请求结束直播(主播调用)
     *
     * @param room_id
     * @param listener
     * @return
     */
    public static SDRequestHandler requestEndVideo(int room_id, AppRequestCallback<App_end_videoActModel> listener) {
        return requestEndVideo(0, room_id, listener);
    }

    /**
     * 请求结束直播(主播调用)
     *
     * @param is_out   1离开，0或者不传为关闭房间
     * @param room_id
     * @param listener
     * @return
     */
    public static SDRequestHandler requestEndVideo(int is_out, int room_id, AppRequestCallback<App_end_videoActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("video");
        params.putAct("end_video");
        params.put("room_id", room_id);
        params.put("is_out", is_out);
        BMCommonInterface.setAppRequestParams(params);
        return AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 删除回放视频
     *
     * @param room_id
     * @param listener
     */
    public static void requestDeleteVideo(int room_id, AppRequestCallback<App_del_videoActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("video");
        params.putAct("del_video");
        params.put("room_id", room_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 请求直播间信息
     *
     * @param room_id     直播间id
     * @param is_vod      0:观看直播;1:点播
     * @param private_key 私密直播的时候的口令
     * @param listener
     */
    public static SDRequestHandler requestRoomInfo(int room_id, int is_vod, String private_key, AppRequestCallback<App_get_videoActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("video");
        params.putAct("get_video2");
        params.put("room_id", room_id);
        params.put("is_vod", is_vod);
        params.put("private_key", private_key);
        String tencent_app_id = AppRuntimeWorker.getSdkappid();
        String user_id = AppRuntimeWorker.getLoginUserID();
        String sign = tencent_app_id + user_id + room_id;
        String sign_md5 = MD5Util.MD5(sign);
        params.put("sign", sign_md5);
        return AppHttpUtil.getInstance().post(params, listener);
    }
    /**
     * @param room_id  直播间id
     * @param group_id 聊天组id
     * @param status   1-成功，0-失败，2-主播离开， 3:主播回来
     * @param callback
     */
    public static void requestUpdateLiveState(int room_id, String group_id, int status, AppRequestCallback<App_video_cstatusActModel> callback) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("video");
        params.putAct("video_cstatus");
        params.put("room_id", room_id);
        params.put("group_id", group_id);
        params.put("status", status);
        AppHttpUtil.getInstance().post(params, callback);
    }

    public static void requestShareComplete(String type, String room_id, AppRequestCallback<App_shareActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user");
        params.putAct("share");
        params.put("type", type);
        params.put("room_id", room_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 检查当前用户是否有连麦权限
     *
     * @param room_id
     * @param listener
     * @return
     */
    public static SDRequestHandler requestCheckLianmai(int room_id, AppRequestCallback<App_check_lianmaiActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("video");
        params.putAct("check_lianmai");
        params.put("room_id", room_id);
        return AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 开始连麦
     *
     * @param room_id
     * @param to_user_id 小主播id
     * @param listener
     * @return
     */
    public static SDRequestHandler requestStartLianmai(int room_id, String to_user_id, AppRequestCallback<App_start_lianmaiActModel> listener) {
        LogUtil.i("start_lianmai:" + to_user_id);
        AppRequestParams params = new AppRequestParams();
        params.putCtl("video");
        params.putAct("start_lianmai");
        params.put("room_id", room_id);
        params.put("to_user_id", to_user_id);
        return AppHttpUtil.getInstance().post(params, listener);
    }



    /**
     * 结束连麦
     *
     * @param room_id
     * @param to_user_id 小主播id
     * @param listener
     */
    public static void requestStopLianmai(int room_id, String to_user_id, AppRequestCallback<App_stop_lianmaiActModel> listener) {
        LogUtil.i("stop_lianmai:" + to_user_id);
        AppRequestParams params = new AppRequestParams();
        params.putCtl("video");
        params.putAct("stop_lianmai");
        params.put("room_id", room_id);
        params.put("to_user_id", to_user_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 用户主页
     *
     * @param to_user_id 被查看的用户id
     * @param listener
     */
    public static void requestUser_home(String to_user_id, AppRequestCallback<App_user_homeActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user");
        params.putAct("user_home");
        params.put("to_user_id", to_user_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 设置黑名单
     *
     * @param to_user_id 被拉黑的用户id
     * @param listener
     */
    public static void requestSet_black(String to_user_id, AppRequestCallback<User_set_blackActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user");
        params.putAct("set_black");
        params.put("to_user_id", to_user_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 黑名单列表
     *
     * @param listener
     */
    public static void requestBlackList(int page, AppRequestCallback<Settings_black_listActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("settings");
        params.putAct("black_list");
        params.put("p", page);

        AppHttpUtil.getInstance().post(params, listener);
    }

    public static void requestAccountAndSafe(AppRequestCallback<SettingsSecurityActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("settings");
        params.putAct("security");

        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 用户粉丝
     *
     * @param page       当前页数
     * @param to_user_id 被查看的用户id(该ID不传则查看自己)
     * @param listener
     */
    public static void requestMy_focus(int page, String to_user_id, AppRequestCallback<App_focus_follow_ActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user");
        params.putAct("user_focus");
        params.put("p", page);

        if (!TextUtils.isEmpty(to_user_id)) {
            params.put("to_user_id", to_user_id);
        }
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 直播回看列表
     *
     * @param page       当前页数
     * @param sort       排序类型; 0:最新;1:最热 \
     * @param to_user_id (查看自己则不传) 被查看的用户id
     * @param listener
     */
    public static void requestUser_review(int page, int sort, String to_user_id, AppRequestCallback<App_user_reviewActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user");
        params.putAct("user_review");
        params.put("sort", sort);
        if (!TextUtils.isEmpty(to_user_id)) {
            params.put("to_user_id", to_user_id);
        }
        params.put("p", page);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 管理员列表
     *
     * @param listener
     */
    public static void requestUser_admin(AppRequestCallback<App_user_adminActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user");
        params.putAct("user_admin");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 设置/取消管理员
     *
     * @param to_user_id 被设置的用户id
     * @param listener
     */
    public static void requestSet_admin(String to_user_id, AppRequestCallback<App_set_adminActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user");
        params.putAct("set_admin");
        params.put("to_user_id", to_user_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 用户充值界面
     *
     * @param listener
     */
    public static SDRequestHandler requestRecharge(AppRequestCallback<App_rechargeActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("pay");
        params.putAct("recharge");
        return AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 购买秀豆
     *
     * @param pay_id   支付方式，不能为空
     * @param rule_id  支付项目id
     * @param money    充值金额
     * @param listener 注：money跟rule_id 2个必须有一个值
     */
    public static void requestPay(int pay_id, int rule_id, double money, AppRequestCallback<App_payActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("pay");
        params.putAct("pay");
        params.put("pay_id", pay_id);// 支付id，不能为空

        if (rule_id > 0) {
            params.put("rule_id", rule_id);// 支付项目id
        } else if (money > 0) {
            params.put("money", money);// 充值金额；
        }
        // 注：money跟rule_id 2个必须有一个值
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 抢红包
     *
     * @param user_prop_id 红包id
     * @param listener
     */
    public static void requestRed_envelope(int user_prop_id, AppRequestCallback<App_red_envelopeActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("deal");
        params.putAct("red_envelope");
        params.put("user_prop_id", user_prop_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 查看手气
     *
     * @param user_prop_id 红包id
     * @param listener
     */
    public static void requestUser_red_envelope(int user_prop_id, AppRequestCallback<App_user_red_envelopeActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("deal");
        params.putAct("user_red_envelope");
        params.put("user_prop_id", user_prop_id);
        AppHttpUtil.getInstance().post(params, listener);
    }
    /**
     * 请求每日签到列表
     *
     * @param listener
     */
    public static void requestSignDayList(AppRequestCallback<SignDailyModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("signin");
        params.putAct("signin_list");
        AppHttpUtil.getInstance().post(params, listener);
    }
    /**
     * 请求每日签到
     *
     * @param listener
     */
    public static void requestSignDay(AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("signin");
        params.putAct("do_sign_in");
        AppHttpUtil.getInstance().post(params, listener);
    }
    /**
     * 举报类型列表
     *
     * @param listener
     */
    public static void requestTipoff_type(AppRequestCallback<App_tipoff_typeActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("app");
        params.putAct("tipoff_type");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 举报类型列表
     *
     * @param to_user_id 被举报的用户id
     * @param type       类型
     * @param listener
     */
    public static void requestTipoff(int room_id, String to_user_id, long type,String remark,String data,AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user");
        params.putAct("tipoff");
        params.put("to_user_id", to_user_id);
        params.put("type", type);
        params.put("room_id", room_id);
        params.put("remark", remark);
        params.put("data", data);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 贡献榜
     *
     * @param room_id  如果有值，则取：本场直播贡献榜排行
     * @param user_id  取某个用户的：总贡献榜排行
     * @param p        取第几页数据;从1开始， 不传或传0;则取前50位排行
     * @param listener
     */
    public static void requestCont(int room_id, String user_id, int p, AppRequestCallback<App_ContActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("video");
        params.putAct("cont");
        if (room_id > 0) {
            params.put("room_id", room_id);
        } else if (!TextUtils.isEmpty(user_id)) {
            params.put("user_id", user_id);
        }
        params.put("p", p);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 音乐搜索
     *
     * @param page     第几页
     * @param keyword
     * @param listener
     */
    public static void requestMusic_search(int page, String keyword, AppRequestCallback<Music_searchActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("music");
        params.putAct("search");
        params.put("p", page);
        params.put("keyword", keyword);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 用户音乐列表
     *
     * @param page     第几页
     * @param listener
     */
    public static void requestMusic_user_music(int page, AppRequestCallback<Music_user_musicActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("music");
        params.putAct("user_music");
        params.put("p", page);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 添加音乐
     *
     * @param audio_link  音乐下载地址
     * @param lrc_link    歌词下载地址
     * @param audio_name  歌曲名
     * @param artist_name 演唱者
     * @param listener
     */
    public static void requestMusic_add_music(String audio_id, String audio_name, String audio_link, String lrc_link, String lrc_content, String artist_name, long time_len,
                                              AppRequestCallback<BaseActModel> listener
    ) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("music");
        params.putAct("add_music");
        params.put("audio_id", audio_id);
        params.put("audio_name", audio_name);
        params.put("audio_link", audio_link);
        params.put("lrc_link", lrc_link);
        params.put("lrc_content", lrc_content);
        params.put("artist_name", artist_name);
        params.put("time_len", time_len);
        params.setNeedShowActInfo(false);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 删除音乐
     *
     * @param audio_id 音乐ID
     * @param listener
     */
    public static void requestMusic_del_music(String audio_id, AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("music");
        params.putAct("del_music");
        params.put("audio_id", audio_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 音乐下载地址
     *
     * @param audio_id 音乐ID
     * @param listener
     */
    public static void requestMusic_downurl(String audio_id, AppRequestCallback<Music_downurlActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("music");
        params.putAct("downurl");
        params.put("audio_id", audio_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 友盟推送id提交
     *
     * @param listener
     */
    public static void requestUser_apns(AppRequestCallback<BaseActModel> listener) {
        String regId = UmengPushManager.getPushAgent().getRegistrationId();
        if (!TextUtils.isEmpty(regId)) {
            LogUtil.i("regId:" + regId);
            AppRequestParams params = new AppRequestParams();
            params.putCtl("user");
            params.putAct("apns");
            params.put("apns_code", regId);
            AppHttpUtil.getInstance().post(params, listener);
        }
    }

    /**
     * 用户的关注列表
     *
     * @param page       当前页数
     * @param to_user_id 被查看的用户id(该ID不传则查看自己)
     * @param listener
     */
    public static void requestUser_follow(int page, String to_user_id, AppRequestCallback<App_focus_follow_ActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user");
        params.putAct("user_follow");
        params.put("p", page);

        if (!TextUtils.isEmpty(to_user_id)) {
            params.put("to_user_id", to_user_id);
        }
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * @param live_image      直播间封面
     * @param title           标题
     * @param cate_id         标题id
     * @param city            城市
     * @param province        省份
     * @param share_type      分享类型
     * @param location_switch 是否定位
     * @param is_private      是否开启私密直播
     * @param listener
     */

    public static void requestAddVideo(int is_livepk,String live_image, String title, int cate_id, String city, String province, String share_type, int location_switch, int is_private, AppRequestCallback<Video_add_videoActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("video");
        params.putAct("add_video");
        params.put("live_image", live_image);
        params.put("title", title);
        params.put("cate_id", cate_id);
        params.put("city", city);
        params.put("province", province);
        params.put("share_type", share_type);
        params.put("location_switch", location_switch);
        params.put("is_private", is_private);
        params.put("is_livepk",is_livepk);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 搜索用户
     *
     * @param page     当前页数
     * @param listener
     */
    public static SDRequestHandler requestSearchUser(int page, String keyword, AppRequestCallback<App_focus_follow_ActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user");
        params.putAct("search");
        params.put("p", page);
        params.put("keyword", keyword);
        return AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 删除视频
     *
     * @param room_id  房间id
     * @param listener
     */
    public static void requestDelVideo(int room_id, AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("video");
        params.putAct("del_video_history");
        params.put("room_id", room_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 检查直播间状态
     *
     * @param room_id     直播间id
     * @param private_key 私密直播的口令
     * @param listener
     */
    public static void requestCheckVideoStatus(int room_id, String private_key, AppRequestCallback<Video_check_statusActModel> listener) {
        if (room_id == 0 && TextUtils.isEmpty(private_key)) {
            return;
        }
        AppRequestParams params = new AppRequestParams();
        params.putCtl("video");
        params.putAct("check_status");
        if (room_id != 0) {
            params.put("room_id", room_id);
        }
        if (private_key != null) {
            params.put("private_key", private_key);
        }
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 我关注的所有用户
     *
     * @param listener
     */
    public static void requestMyFollow(AppRequestCallback<App_my_follow_ActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user");
        params.putAct("my_follow");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 请求好友列表
     *
     * @param room_id  直播间id
     * @param p        分页
     * @param listener
     */
    public static void requestFriends(int room_id, int p, AppRequestCallback<User_friendsActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user");
        params.putAct("friends");
        params.put("room_id", room_id);
        params.put("p", p);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 私密房间用户列表
     *
     * @param room_id  直播间id
     * @param p        分页
     * @param listener
     */
    public static void requestPrivateRoomFriends(int room_id, int p, AppRequestCallback<Video_private_room_friendsActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("video");
        params.putAct("private_room_friends");
        params.put("room_id", room_id);
        params.put("p", p);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 私密直播加人
     *
     * @param room_id  直播间id
     * @param user_ids
     * @param listener
     */
    public static void requestPrivatePushUser(int room_id, String user_ids, AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("video");
        params.putAct("private_push_user");
        params.put("room_id", room_id);
        params.put("user_ids", user_ids);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 私密直播踢人
     *
     * @param room_id  直播间id
     * @param user_ids
     * @param listener
     */
    public static void requestPrivateDropUser(int room_id, String user_ids, AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("video");
        params.putAct("private_drop_user");
        params.put("room_id", room_id);
        params.put("user_ids", user_ids);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 超管踢人
     *
     * @param room_id  直播间id
     * @param user_ids
     * @param listener
     */
    public static void requestManagerDropUser(int room_id, String user_ids, AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("admin");
        params.putAct("get_out");
        params.put("room_id", room_id);
        params.put("user_ids", user_ids);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 超管禁言
     *
     */
    public static void requestManagerForbidMessage(String to_user_id, String group_id,String second, AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("admin");
        params.putAct("shut_up");
        params.put("to_user_id", to_user_id);
        params.put("group_id", group_id);
        params.put("second", second);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 超管关闭直播
     *
     * @param room_id  直播间id
     * @param listener
     */
    public static void requestManagerCloseRoom(int room_id, AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("admin");
        params.putAct("close_video");
        params.put("room_id", room_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 超管封用户
     * @param listener
     */
    public static void requestManagerForbidAccount( String to_user_id, AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("admin");
        params.putAct("banned_account");
        params.put("to_user_id", to_user_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 超管封设备
     *
     * @param room_id  直播间id
     * @param listener
     */
    public static void requestManagerForbidDevice(String room_id,AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("admin");
        params.putAct("banned_device");
        params.put("room_id", room_id);
        AppHttpUtil.getInstance().post(params, listener);
    }
    /**
     * 最新直播列表
     *
     * @param listener
     */
    public static void requestNewVideo(int p, AppRequestCallback<Index_new_videoActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("index");
        params.putAct("new_video");
        params.put("p", p);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 关注直播列表
     *
     * @param listener
     */
    public static void requestFocusVideo(AppRequestCallback<Index_focus_videoActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("index");
        params.putAct("focus_video");
        AppHttpUtil.getInstance().post(params, listener);
    }
    /**
     * 主页分类接口请求数据
     *
     * @param cate_id
     * @param listener
     */
    public static void requestCategoryVideo(int cate_id, AppRequestCallback<Index_indexActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("index");
        params.putAct("classify");
        params.put("classified_id", cate_id);
        AppHttpUtil.getInstance().post(params, listener);
    }
    /**
     * 主页推荐接口请求数据
     * @param listener
     */
    public static void requestCommendVideo(AppRequestCallback<Index_indexActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("video");
        params.putAct("video_list");
        params.put("is_recommend", "1");
        AppHttpUtil.getInstance().post(params, listener);
    }
    /**
     * 首页接口
     *
     * @param p        分页
     * @param sex      性别 0:全部, 1-男，2-女
     * @param cate_id  话题id
     * @param city     城市(空为:热门)
     * @param listener
     */
    public static void requestIndex(int p, int sex, int cate_id, String city, AppRequestCallback<Index_indexActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("index");
        params.putAct("index");
        params.put("p", p);
        params.put("need_ww", 1);
        params.put("sex", sex);
        params.put("cate_id", cate_id);
        if (!TextUtils.isEmpty(city)) {
            params.put("city", city);
        }
        setAppRequestParams(params);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 热门根据性别，区域搜索直播列表
     *
     * @param sex
     * @param listener
     */
    public static void requestIndexSearch_area(int sex, AppRequestCallback<IndexSearch_areaActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("index");
        params.putAct("search_area");
        params.put("sex", sex);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 话题列表
     *
     * @param title    关键字
     * @param p        分页
     * @param listener
     */
    public static SDRequestHandler requestToptic(String title, int p, AppRequestCallback<Index_topicActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("index");
        params.putAct("search_video_cate");
        if (title != null) {
            params.put("title", title);
        }
        params.put("p", p);
        return AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 发送验证码
     *
     * @param mobile     手机
     * @param image_code 图片验证码
     * @param type       是否是绑定手机发送的验证码
     * @param listener
     */
    public static void requestSendMobileVerify(int type, String mobile, String image_code, AppRequestCallback<App_send_mobile_verifyActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("login");
        params.putAct("send_mobile_verify");
        params.put("mobile", mobile);
        if (!TextUtils.isEmpty(image_code)) {
            params.put("image_code", image_code);
        }
        if (type == 1) {
            params.put("wx_binding", type);
        }
        AppHttpUtil.getInstance().post(params, listener);
    }

    /***
     * 手机绑定
     *
     * @param mobile
     * @param verify_code
     * @param listener
     */
    public static void requestMobileBind(String mobile, String verify_code, AppRequestCallback<App_ProfitBindingActModel> listener) {
        requestMobileBind(mobile, verify_code, null, null, null, listener);
    }

    /**
     * @param mobile       手机号
     * @param verify_code  验证码
     * @param login_type   登录类型 qq_login wx_login sina_login
     * @param openid       第三方登录唯一ID
     * @param access_token 第三方登录Token
     * @param listener
     */
    public static void requestMobileBind(String mobile, String verify_code, String login_type, String openid, String access_token, AppRequestCallback<App_ProfitBindingActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("settings");
        params.putAct("mobile_binding");
        params.put("mobile", mobile);
        params.put("verify_code", verify_code);
        if (!TextUtils.isEmpty(login_type) && !TextUtils.isEmpty(openid) && !TextUtils.isEmpty(access_token)) {
            params.put("login_type", login_type);
            params.put("openid", openid);
            params.put("access_token", access_token);
        }
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 是否使用验证
     *
     * @param listener
     */
    public static void requestIsUserVerify(AppRequestCallback<App_is_user_verifyActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("login");
        params.putAct("is_user_verify");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 主播同意协议
     *
     * @param listener
     */
    public static void requestAgree(AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user");
        params.putAct("agree");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 登录-手机
     *
     * @param listener
     */
    public static void requestDoLogin(String mobile, String verify_coder, AppRequestCallback<App_do_loginActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("login");
        params.putAct("do_login");
        params.put("mobile", mobile);
        params.put("verify_coder", verify_coder);
        AppHttpUtil.getInstance().post(params, listener);
    }

    public static AppRequestParams getDoUpdateAppRequestParams() {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("login");
        params.putAct("do_update");
        return params;
    }

    /**
     * 登录-补充信息
     *
     * @param listener
     */
    public static void requestDoUpdate(String user_id, String nick_name, String sex, String image_path, AppRequestCallback<App_do_updateActModel> listener) {
        AppRequestParams params = getDoUpdateAppRequestParams();
        params.put("type", 0);//0 补充信息 1 更新头像
        params.put("id", user_id);
        params.put("nick_name", nick_name);
        params.put("sex", sex);
        if (AppRuntimeWorker.getOpen_sts() == 1) {
            params.put("oss_path", image_path);
        } else {
            //head_image上传时候更新登录信息
            params.put("head_image", image_path);
        }
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * Oss更新头像
     *
     * @param user_id
     * @param oss_path
     * @param listener
     */
    public static void requestDoUpdate(String user_id, String oss_path, AppRequestCallback<App_do_updateActModel> listener) {
        AppRequestParams params = getDoUpdateAppRequestParams();
        params.put("id", user_id);
        params.put("oss_path", oss_path);
        params.put("type", 1);//type 0补充信息 1更新头像
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 普通更新头像
     *
     * @param listener
     */
    public static void requestDoUpdateNormal(String user_id, String normal_head_path, AppRequestCallback<App_do_updateActModel> listener) {
        AppRequestParams params = getDoUpdateAppRequestParams();
        params.put("id", user_id);
        params.put("normal_head_path", normal_head_path);
        params.put("type", 1);//type 0补充信息 1更新头像
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 微信登录
     *
     * @param listener
     */
    public static void requestWxLogin(String openid, String access_token, AppRequestCallback<App_do_updateActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("login");
        params.putAct("wx_login");
        params.put("openid", openid);
        params.put("access_token", access_token);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * QQ登录
     *
     * @param listener
     */
    public static void requestQqLogin(String openid, String access_token, AppRequestCallback<App_do_updateActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("login");
        params.putAct("qq_login");
        params.put("openid", openid);
        params.put("access_token", access_token);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 新浪登录
     *
     * @param listener
     */
    public static void requestSinaLogin(String access_token, String uid, AppRequestCallback<App_do_updateActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("login");
        params.putAct("sina_login");
        params.put("access_token", access_token);
        params.put("sina_id", uid);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 编辑-用户信息（生日、情感状态、家乡、职业）
     *
     * @param listener
     */
    public static void requestUserEditInfo(AppRequestCallback<App_userEditActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user_center");
        params.putAct("user_edit");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 更新用户信息
     *
     * @param map
     * @param listener
     */
    public static void requestCommitUserInfo(String nickName,Map<String, String> map, AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user_center");
        params.putAct("user_save");
        for (String key : map.keySet()) {
            if (!TextUtils.isEmpty(key)) {
                params.put(key, map.get(key));
            }
        }
        params.put("nick_name",nickName);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 编辑-获取地区
     */
    public static void requestRegionList(AppRequestCallback<App_RegionListActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user_center");
        params.putAct("region_list");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 设置推送
     *
     * @param is_remind 接收推送消息 0-不接收，1-接收
     * @param listener
     */
    public static void requestSet_push(final int is_remind, AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("settings");
        params.putAct("set_push");
        params.setNeedShowActInfo(false);
        params.put("type", 1);
        params.put("is_remind", is_remind);
        AppHttpUtil.getInstance().post(params, new AppRequestCallbackWrapper<BaseActModel>(listener) {
            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.isOk()) {
                    UserModel user = UserModelDao.query();
                    user.setIs_remind(is_remind);
                    UserModelDao.insertOrUpdate(user);
                }
            }
        });
    }

    /**
     * 会员中心-收益页面
     *
     * @param listener
     */
    public static void requestProfit(AppRequestCallback<App_profitActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user_center");
        params.putAct("profit");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 会员中心-收益-兑换
     *
     * @param listener
     */
    public static void requestExchangeRule(AppRequestCallback<App_ExchangeRuleActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user_center");
        params.putAct("exchange");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 会员中心-收益-领取记录
     *
     * @param listener
     */
    public static void requestGainRecord(AppRequestCallback<App_GainRecordActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user_center");
        params.putAct("extract_record");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 会员中心-收益-兑换
     *
     * @param rule_id  兑换规则id
     * @param ticket   兑现的钱票
     * @param listener
     */
    public static void requestDoExchange(int rule_id, int ticket, AppRequestCallback<App_doExchangeActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user_center");
        params.putAct("do_exchange");
        params.put("rule_id", rule_id);
        params.put("ticket", ticket);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 会员中心-收益-绑定微信
     *
     * @param openid       微信授权回调字段
     * @param access_token 微信授权回调字段
     * @param listener
     */
    public static void requestBindingWz(String openid, String access_token, AppRequestCallback<App_ProfitBindingActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user_center");
        params.putAct("update_wxopenid");
        params.put("openid", openid);
        params.put("access_token", access_token);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 发送礼物给某人
     *
     * @param prop_id    礼物id
     * @param num        数量
     * @param to_user_id 对方id
     * @param listener
     */
    public static SDRequestHandler requestSendGiftPrivate(int prop_id, int num, String to_user_id,int is_backpack,String coordinate, AppRequestCallback<Deal_send_propActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("deal");
        params.putAct("send_prop");
        params.put("prop_id", prop_id);
        params.put("num", num);
        params.put("to_user_id", to_user_id);
        params.put("is_backpack", is_backpack);
        params.put("coordinate", coordinate);
        return AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 提交认证
     *
     * @param authentication_type     认证类型
     * @param authentication_name     认证名称
     * @param mobile                  联系方式
     * @param identify_number         身份证号码
     * @param identify_hold_image     手持身份证正面
     * @param identify_positive_image 身份证正面
     * @param identify_nagative_image 身份证反面
     * @param listener
     */
    public static void requestAttestation(String authentication_type, String authentication_name, String mobile, String identify_number, String identify_hold_image, String identify_positive_image, String identify_nagative_image, AppRequestCallback<BaseActModel> listener) {
        requestAttestation(authentication_type, authentication_name, mobile, identify_number, identify_hold_image, identify_positive_image, identify_nagative_image, 0, null, listener);
    }


    /**
     * 提交认证
     *
     * @param authentication_type     认证类型
     * @param authentication_name     认证名称
     * @param mobile                  联系方式
     * @param identify_number         身份证号码
     * @param identify_hold_image     手持身份证正面
     * @param identify_positive_image 身份证正面
     * @param identify_nagative_image 身份证反面
     * @param invite_type             推荐类型
     * @param invite_input            推荐号码
     * @param listener
     */
    public static void requestAttestation(String authentication_type, String authentication_name,
                                          String mobile, String identify_number, String identify_hold_image,
                                          String identify_positive_image, String identify_nagative_image,
                                          int invite_type, String invite_input,
                                          AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user_center");
        params.putAct("attestation");
        params.put("authentication_type", authentication_type);
        params.put("authentication_name", authentication_name);
        params.put("contact", mobile);
        params.put("identify_number", identify_number);
        params.put("identify_hold_image", identify_hold_image);
        params.put("identify_positive_image", identify_positive_image);
        params.put("identify_nagative_image", identify_nagative_image);
        params.put("invite_type", invite_type);
        params.put("invite_input", invite_input);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 认证初始化
     *
     * @param listener
     */
    public static void requestAuthent(AppRequestCallback<App_AuthentActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user_center");
        params.putAct("authent");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 图片上传(通用返回服务器路径)
     *
     * @param file     图片
     * @param listener
     */
    public static void requestUploadImage(File file, AppRequestCallback<App_uploadImageActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("avatar");
        params.putAct("uploadImage");
        params.putFile("file", file);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 50、获得用户基本信息
     *
     * @param user_ids
     * @param listener
     */
    public static void requestBaseInfo(String user_ids, AppRequestCallback<App_BaseInfoActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user");
        params.putAct("baseinfo");
        params.put("user_ids", user_ids);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 观众进入直播间第一次点亮请求
     *
     * @param room_id
     * @param listener
     */
    public static void requestLike(int room_id, AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("video");
        params.putAct("like");
        params.put("room_id", room_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 更新用户状态为在线
     *
     * @param listener
     */
    public static void requestStateChangeLogin(AppRequestCallback<BaseActModel> listener) {
        if (AppRuntimeWorker.isLogin(null)) {
            requestStateChange("Login", listener);
            IMHelper.joinOnlineGroup();
        }
    }

    /**
     * 更新用户状态为离开
     *
     * @param listener
     */
    public static void requestStateChangeLogout(AppRequestCallback<BaseActModel> listener) {
        if (AppRuntimeWorker.isLogin(null)) {
            requestStateChange("Logout", listener);
            IMHelper.quitOnlineGroup(null);
        }
    }

    /**
     * 请求变更状态接口（用来统计用户在线时长）
     *
     * @param action
     * @param listener
     */
    private static void requestStateChange(String action, AppRequestCallback<BaseActModel> listener) {
        if (AppRuntimeWorker.isLogin(null)) {
            AppRequestParams params = new AppRequestParams();
            params.putCtl("user");
            params.putAct("state_change");
            params.put("action", action);
            AppHttpUtil.getInstance().post(params, listener);
        }
    }

    /**
     * 家族主页
     *
     * @param family_id 家族ID
     * @param listener
     */
    public static void requestFamilyIndex(int family_id, AppRequestCallback<App_family_indexActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("family");
        params.putAct("index");
        params.put("family_id", family_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 创建家族接口
     *
     * @param family_logo      家族LOGO
     * @param family_name      家族名称
     * @param family_manifesto 家族宣言
     * @param family_notice    家族公告
     * @param listener
     */
    public static void requestFamilyCreate(String family_logo, String family_name, String family_manifesto, String family_notice, AppRequestCallback<App_family_createActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("family");
        params.putAct("create");
        params.put("family_logo", family_logo);
        params.put("family_name", family_name);
        params.put("family_manifesto", family_manifesto);
        params.put("family_notice", family_notice);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 修改家族信息接口
     *
     * @param family_id        家族ID
     * @param family_logo      家族LOGO
     * @param family_manifesto 家族宣言
     * @param family_notice    家族公告
     * @param listener
     */
    public static void requestFamilyUpdate(int family_id, String family_logo, String family_manifesto, String family_notice, String family_name, AppRequestCallback<App_family_createActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("family");
        params.putAct("save");
        params.put("family_id", family_id);
        params.put("family_logo", family_logo);
        params.put("family_manifesto", family_manifesto);
        params.put("family_notice", family_notice);
        params.put("family_name", family_name);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 家族成员列表接口
     *
     * @param family_id
     * @param page
     * @param listener
     */
    public static void requestFamilyMembersList(int family_id, int page, AppRequestCallback<App_family_user_user_listActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("family_user");
        params.putAct("user_list");
        params.put("family_id", family_id);
        params.put("page", page);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 家族成员申请列表接口
     *
     * @param family_id
     * @param page
     * @param listener
     */
    public static void requestFamilyMembersApplyList(int family_id, int page, AppRequestCallback<App_family_user_r_user_listActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("family_user");
        params.putAct("r_user_list");
        params.put("family_id", family_id);
        params.put("page", page);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 申请加入家族列表
     *
     * @param family_id
     * @param family_name
     * @param page
     * @param listener
     */
    public static SDRequestHandler requestApplyJoinFamilyList(int family_id, String family_name, int page, AppRequestCallback<App_family_listActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("family");
        params.putAct("family_list");
        params.put("family_id", family_id);
        params.put("family_name", family_name);
        params.put("page", page);
        return AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 申请加入家族接口
     *
     * @param family_id
     * @param listener
     */
    public static void requestApplyJoinFamily(int family_id, AppRequestCallback<App_family_createActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("family_user");
        params.putAct("user_join");
        params.put("family_id", family_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 家族成员移除接口（家族创始人）
     *
     * @param r_user_id
     * @param listener
     */
    public static void requestDelFamilyMember(int r_user_id, AppRequestCallback<App_family_createActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("family_user");
        params.putAct("user_del");
        params.put("r_user_id", r_user_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 成员申请审核(家族长权限)
     *
     * @param r_user_id 要审核的成员ID
     * @param is_agree  是否同意 （1：同意，2：拒绝）
     * @param listener
     */
    public static void requestFamilyMemberConfirm(int r_user_id, int is_agree, AppRequestCallback<App_family_user_confirmActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("family_user");
        params.putAct("confirm");
        params.put("r_user_id", r_user_id);
        params.put("is_agree", is_agree);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 退出家族接口
     *
     * @param listener
     */
    public static void requestFamilyLogout(AppRequestCallback<App_family_user_logoutActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("family_user");
        params.putAct("logout");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * OSS上传图片获取参数
     *
     * @param listener
     */
    public static void requestAliyunSts(AppRequestCallback<App_aliyun_stsActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("app");
        params.putAct("aliyun_sts");
        AppHttpUtil.getInstance().post(params, listener);
    }


    /**
     * 贡献排行榜---日，月，总接口
     *
     * @param listener
     */
    public static void requestRankContribution(int p, String rank_name, AppRequestCallback<App_RankContributionModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("rank");
        params.putAct("contribution");
        params.put("rank_name", rank_name);
        params.put("p", p);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 功德排行榜---日，月，总
     *
     * @param listener
     */
    public static void requestRankConsumption(int p, String rank_name, AppRequestCallback<App_RankConsumptionModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("rank");
        params.putAct("consumption");
        params.put("rank_name", rank_name);
        params.put("p", p);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 切换付费接口
     *
     * @param listener
     */
    public static void requestLiveLivePay(int live_fee, int live_pay_type, int room_id, AppRequestCallback<App_live_live_payActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("live");
        params.putAct("live_pay");
        params.put("live_fee", live_fee);
        params.put("live_pay_type", live_pay_type);
        params.put("room_id", room_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 同意扣费接口
     *
     * @param listener
     */
    public static void requestLiveLivePayAgree(int room_id, AppRequestCallback<App_live_live_pay_agreeActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("live");
        params.putAct("live_pay_agree");
        params.put("room_id", room_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 进入房间请求游戏信息
     *
     * @param roomId   游戏轮数
     * @param listener
     */
    public static SDRequestHandler requestGamesInfo(int roomId, AppRequestCallback<App_getGamesActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("games");
        params.putAct("get_video");
        params.put("video_id", roomId);
        return AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 请求主播的插件列表
     *
     * @param listener
     */
    public static void requestInitPlugin(AppRequestCallback<App_plugin_initActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("app");
        params.putAct("plugin_init");
        params.put("need_ww", 1);
        BMCommonInterface.setAppRequestParams(params);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 调用插件接口
     *
     * @param pluginId 插件id
     * @param listener
     */
    public static SDRequestHandler requestStartPlugin(int pluginId, AppRequestCallback<App_startGameActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("games");
        params.putAct("start");
        params.put("id", pluginId);
        return AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 投注接口
     *
     * @param gameId      游戏id（主键）
     * @param betPosition 投注选项[1-3]
     * @param betCoin     投注金额
     * @param listener
     */
    public static void requestDoBet(int gameId, int betPosition, long betCoin, AppRequestCallback<Games_betActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("games");
        params.putAct("bet");
        params.put("id", gameId);
        params.put("bet", betPosition);
        params.put("money", betCoin);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 娃娃倍率
     *
     * @param listener
     */
    public static void requestWaWaBet(AppRequestCallback<Games_betActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("games");
        params.putAct("ww_bet");
        params.put("id", 100);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 抓娃娃修改金额
     *
     * @param listener
     */
    public static void requestWaWaEditCoin(int coin,int times,int gameid,int type,AppRequestCallback<GamesWawaModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("games");
        params.putAct("edit_coin");
        params.put("coin", coin);
        params.put("times", times);
        params.put("game_log_id", gameid);
        params.put("type", type);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 游戏结余接口
     *
     * @param game_log_id 游戏轮数
     * @param listener
     */
    public static SDRequestHandler requestGameIncome(int game_log_id, AppRequestCallback<App_requestGameIncomeActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("games");
        params.putAct("userDiamonds");
        params.put("id", game_log_id);
        params.setNeedCancelSameRequest(true);
        return AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 结束游戏
     *
     * @param listener
     */
    public static SDRequestHandler requestStopGame(AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("games");
        params.putAct("stop");
        params.setNeedCancelSameRequest(true);
        return AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 游戏记录
     *
     * @param game_id    游戏id
     * @param podcast_id 主播id
     * @param listener
     */
    public static void requestGamesLog(int game_id, String podcast_id, AppRequestCallback<Games_logActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("games");
        params.putAct("log");
        params.put("game_id", game_id);
        params.put("podcast_id", podcast_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 绑定支付宝账号
     *
     * @param alipayAccount 支付宝账号
     * @param alipayName    支付宝账号名称
     * @param listener
     */
    public static void requestBandingAlipay(String alipayAccount, String alipayName, AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user_center");
        params.putAct("binding_alipay");
        params.put("alipay_account", alipayAccount);
        params.put("alipay_name", alipayName);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 支付宝提现
     *
     * @param ticket   秀豆数
     * @param listener
     */
    public static void requestSubmitRefundAlipay(String ticket, AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user_center");
        params.putAct("submit_refund_alipay");
        params.put("ticket", ticket);
        AppHttpUtil.getInstance().post(params, listener);
    }
    /**
     * 绑定银行卡号
     */
    public static void requestBandingBankCard(String bank_name, String branch_name,String open_account_num, String open_account_name, AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user_center");
        params.putAct("binding_bankcard");
        params.put("bank_name", bank_name);
        params.put("branch_name", branch_name);
        params.put("open_account_num", open_account_num);
        params.put("open_account_name", open_account_name);
        AppHttpUtil.getInstance().post(params, listener);
    }
    /**
     *  银行卡提现
     *
     * @param ticket   秀豆数
     * @param listener
     */
    public static void requestSubmitRefundBankCard(String ticket, AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user_center");
        params.putAct("submit_refund_bankcard");
        params.put("ticket", ticket);
        AppHttpUtil.getInstance().post(params, listener);
    }
    /**
     * 我的分销
     *
     * @param p        分页
     * @param listener
     */
    public static void requestDistribution(int p, AppRequestCallback<App_distribution_indexActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("distribution");
        params.putAct("index");
        params.put("p", p);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 插件接口互斥
     * id 插件id
     *
     * @param listener
     */
    public static void requestPlugin_status(int id, AppRequestCallback<App_plugin_statusActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("app");
        params.putAct("plugin_status");
        params.put("plugin_id", id);
        BMCommonInterface.setAppRequestParams(params);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 查询是否有推荐人
     *
     * @param listener
     */
    public static void requestGet_p_user_id(AppRequestCallback<App_get_p_user_idActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user_center");
        params.putAct("get_p_user_id");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 提交推荐人ID
     *
     * @param p_user_id
     * @param listener
     */
    public static void requestUpdata_p_user_id(String p_user_id, AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("user_center");
        params.putAct("update_p_user_id");
        params.put("p_user_id", p_user_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 请求PC端直播列表
     *
     * @param p        页数
     * @param listener
     */
    public static void requestPcLive(int p, AppRequestCallback<Index_new_videoActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("index");
        params.putAct("new_pc_video");
        params.put("p", p);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 购买VIP页面初始化接口
     */
    public static void requestVipPurchase(AppRequestCallback<App_UserVipPurchaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("vip_pay");
        params.putAct("purchase");
        AppHttpUtil.getInstance().post(params, listener);
    }

    public static void requestPayVip(int pay_id, int rule_id, AppRequestCallback<App_payActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("vip_pay");
        params.putAct("pay");
        params.put("pay_id", pay_id);
        params.put("rule_id", rule_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 申请加入公会列表
     *
     * @param society_id
     * @param society_name
     * @param page
     * @param listener
     */
    public static SDRequestHandler requestApplyJoinSociatyList(int society_id, String society_name, int page, AppRequestCallback<App_sociaty_listActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("society");
        params.putAct("society_list");
        params.put("society_id", society_id);
        params.put("society_name", society_name);
        params.put("page", page);
        return AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 公会主页
     *
     * @param society_id 公会ID
     * @param listener
     */
    public static void requestSociatyIndex(int society_id, AppRequestCallback<App_sociaty_indexActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("society");
        params.putAct("index");
        params.put("society_id", society_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 申请加入公会接口
     *
     * @param society_id
     * @param listener
     */
    public static void requestApplyJoinSociaty(int society_id, AppRequestCallback<App_sociaty_joinActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("society_user");
        params.putAct("join");
        params.put("society_id", society_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 创建公会接口
     *
     * @param logo      公会LOGO
     * @param name      公会名称
     * @param manifesto 公会宣言
     * @param notice    公会公告
     * @param listener
     */
    public static void requestSociatyCreate(String logo, String name, String manifesto, String notice, AppRequestCallback<App_sociaty_joinActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("society");
        params.putAct("create");
        params.put("logo", logo);
        params.put("name", name);
        params.put("manifesto", manifesto);
        params.put("notice", notice);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 修改公会信息接口
     *
     * @param id        公会ID
     * @param logo      公会LOGO
     * @param manifesto 公会宣言
     * @param notice    公会公告
     * @param listener
     */
    public static void requestSociatyUpdate(int id, String logo, String manifesto, String notice, String name, AppRequestCallback<App_sociaty_joinActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("society");
        params.putAct("save");
        params.put("id", id);
        params.put("logo", logo);
        params.put("manifesto", manifesto);
        params.put("notice", notice);
        params.put("name", name);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 退出公会接口
     *
     * @param listener
     */
    public static void requestSociatyLogout(AppRequestCallback<App_sociaty_user_logoutActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("society_user");
        params.putAct("logout");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 公会成员列表接口
     *
     * @param society_id
     * @param status     0申请加入待审核、1加入申请通过、2 加入申请被拒绝，3申请退出公会待审核 4退出公会申请通过 5.退出公会申请被拒
     * @param page
     * @param listener
     */
    public static void requestSociatyMembersList(int society_id, int status, int page, AppRequestCallback<App_sociaty_user_listActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("society_user");
        params.putAct("user_list");
        UserModel dao = UserModelDao.query();
        if (dao.getSociety_chieftain() != 1)
            params.put("society_id", society_id);
        params.put("status", status);
        params.put("page", page);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 公会成员移除接口（公会创始人）
     *
     * @param r_user_id
     * @param listener
     */
    public static void requestDelSociatyMember(int r_user_id, AppRequestCallback<App_sociaty_joinActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("society");
        params.putAct("user_del");
        params.put("r_user_id", r_user_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 公会成员申请审核(公会长权限)
     *
     * @param r_user_id 要审核的成员ID
     * @param is_agree  是否同意 （1：同意，2：拒绝）
     * @param listener
     */
    public static void requestSociatyMemberConfirm(int r_user_id, int is_agree, AppRequestCallback<App_sociaty_user_confirmActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("society");
        params.putAct("confirm");
        params.put("r_user_id", r_user_id);
        params.put("is_agree", is_agree);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 公会成员退出申请审核(公会长权限)
     *
     * @param r_user_id 要审核的成员ID
     * @param is_agree  是否同意 （1：同意，2：拒绝）
     * @param listener
     */
    public static void requestSociatyMemberLogout(int r_user_id, int is_agree, AppRequestCallback<App_sociaty_user_confirmActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("society");
        params.putAct("logout_confirm");
        params.put("r_user_id", r_user_id);
        params.put("is_agree", is_agree);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 请求游戏币兑换比例
     *
     * @param listener
     */
    public static SDRequestHandler requestGamesExchangeRate(AppRequestCallback<App_gameExchangeRateActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("games");
        params.putAct("exchangeRate");
        return AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 秀豆兑换游戏币接口
     *
     * @param diamonds
     * @param listener
     */
    public static void requestCoinExchange(long diamonds, AppRequestCallback<App_gameCoinsExchangeActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("games");
        params.putAct("exchangeCoin");
        params.put("diamonds", diamonds);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 赠送好友游戏币接口
     *
     * @param toUserId 接收人id
     * @param coins    游戏币数额
     * @param listener
     */
    public static void requestSendGameCoins(String toUserId, long coins, AppRequestCallback<Deal_send_propActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("games");
        params.putAct("sendCoin");
        params.put("to_user_id", toUserId);
        params.put("coin", coins);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 赠送好友游戏币接口
     *
     * @param toUserId 接收人id
     * @param diamonds 秀豆数额
     * @param listener
     */
    public static void requestSendDiamonds(String toUserId, long diamonds, AppRequestCallback<Deal_send_propActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("games");
        params.putAct("sendDiamonds");
        params.put("to_user_id", toUserId);
        params.put("diamonds", diamonds);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 提交错误信息接口
     *
     * @param desc 错误信息
     */
    public static void reportErrorLog(String desc) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("app");
        params.putAct("log_err");
        params.put("desc", desc);
        AppHttpUtil.getInstance().post(params, null);
    }

    /**
     * 请求上庄
     *
     * @param roomId   游戏直播间id
     * @param coins    上庄金额
     * @param listener
     */
    public static void requestApplyBanker(int roomId, long coins, AppRequestCallback<App_banker_applyActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("games");
        params.putAct("applyBanker");
        params.put("video_id", roomId);
        params.put("coin", coins);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 获取玩家上庄申请列表
     *
     * @param listener
     */
    public static void requestBankerList(AppRequestCallback<App_banker_listActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("games");
        params.putAct("getBankerList");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 开启游戏上庄
     *
     * @param listener
     */
    public static void requestOpenGameBanker(AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("games");
        params.putAct("openBanker");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 请求下庄
     *
     * @param listener
     */
    public static void requestStopBanker(AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("games");
        params.putAct("stopBanker");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 主播选择玩家上庄
     *
     * @param banker_log_id 上庄id
     * @param listener
     */
    public static void requestChooseBanker(String banker_log_id, AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("games");
        params.putAct("chooseBanker");
        params.put("banker_log_id", banker_log_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 请求服务端混合视频
     *
     * @param room_id    直播间id
     * @param to_user_id 要混合的小主播id
     * @param callback
     */
    public static void requestMixStream(int room_id, String to_user_id, AppRequestCallback<BaseActModel> callback) {
        LogUtil.i("mix_stream:" + to_user_id);
        AppRequestParams params = new AppRequestParams();
        params.putCtl("video");
        params.putAct("mix_stream");
        params.put("room_id", room_id);
        params.put("to_user_id", to_user_id);
        AppHttpUtil.getInstance().post(params, callback);
    }
    /**
     * 请求服务端混合视频
     *
     * @param room_id    直播间id
     * @param callback
     */
    public static void requestPKMixStream(int room_id, String to_room_id, AppRequestCallback<App_start_lianmaiActModel> callback) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("video");
        params.putAct("mix_pk");
        params.put("room_id", room_id);
        params.put("to_room_id", to_room_id);
        AppHttpUtil.getInstance().post(params, callback);
    }
    /**
     * 游客登录
     *
     * @param callback
     * @return
     */
    public static SDRequestHandler requestLoginVisitorsLogin(AppRequestCallback<App_do_updateActModel> callback) {
        String um_reg_id = UmengPushManager.getPushAgent().getRegistrationId();
        if (TextUtils.isEmpty(um_reg_id)) {
            if (callback != null) {
                SDResponse response = new SDResponse().setThrowable(new IllegalArgumentException("RegistrationId is empty when LoginVisitors"));
                callback.notifyError(response);
            }
            return null;
        }

        AppRequestParams params = new AppRequestParams();
        params.putCtl("login");
        params.putAct("visitors_login");
        params.put("um_reg_id", um_reg_id);
        return AppHttpUtil.getInstance().post(params, callback);
    }
    /**
     * wait test
     * 请求pk列表
     *keyword  搜索关键词
     * @param p        页数
     */
    /**
     * wait test
     * 请求开通守护
     */
    public static void requestShouhuPay(String anchor_id,AppRequestCallback<PayShouhuModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("guard");
        params.putAct("guard_rule_list");
        params.put("anchor_id", anchor_id);
        AppHttpUtil.getInstance().post(params, listener);
    }
    /**
     * wait test
     * 守护支付
     */
    public static void requestPay(String guard_id,String rule_id,String anchor_id,AppRequestCallback<BaseActModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("guard");
        params.putAct("pay");
        params.put("guard_id",guard_id);
        params.put("rule_id",rule_id);
        params.put("anchor_id",anchor_id);
        AppHttpUtil.getInstance().post(params, listener);
    }
    /**
     * wait test
     * 守护列表
     */
    public static void requestShouhuList(String anchor_id,int room_id,AppRequestCallback<ShouhuListModel> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("guard");
        params.putAct("guard_list");
        params.put("anchor_id",anchor_id);
        params.put("room_id",room_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * wait test
     * 获取服务器网络时间
     */
    public static void requestServerData(AppRequestCallback<ServerData> listener) {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("app");
        params.putAct("servertime");
        AppHttpUtil.getInstance().post(params, listener);
    }
    //    HashMap<String,String> params=new HashMap<>();
//    params.put("platform","2");
//    params.put("page",page+"");
    //get or post获取json字符串
    public interface Json_CallBack {
        public void getJson(String str);
    }
    private static Json_CallBack json_callBack;
    public static void doHttpReqeust(final int request_type, final String POST_GET, final String url,final String body_json,
                                     final HashMap<String, Object> params, final Json_CallBack json_callBack) {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL u = new URL(url);
                        // 打开连接
                        HttpURLConnection connection = (HttpURLConnection) u.openConnection();
                        if(request_type==1){
                            connection.setRequestProperty("Content-Type", "application/json");
                        }
                        // 设置输入可用
                        connection.setDoInput(true);
                        // 设置输出可用
                        connection.setDoOutput(true);
                        // 设置请求方式
                        connection.setRequestMethod(POST_GET);
                        // 设置连接超时
                        connection.setConnectTimeout(5000);
                        // 设置读取超时
                        connection.setReadTimeout(5000);
                        // 设置缓存不可用
                        connection.setUseCaches(false);
                        // 开始连接
                        connection.connect();
                        // 只有当POST请求时才会执行此代码段
                        OutputStream outputStream = connection
                                .getOutputStream();
                        if (params != null) {
                            // 获取输出流,connection.getOutputStream已经包含了connect方法的调用

                            StringBuilder sb = new StringBuilder();
                            Set<Map.Entry<String, Object>> sets = params.entrySet();
                            // 将Hashmap转换为string
                            for (Map.Entry<String, Object> entry : sets) {
                                sb.append(entry.getKey()).append("=")
                                        .append(entry.getValue()).append("&");
                            }
                            String param = sb.substring(0, sb.length() - 1);
                            // 使用输出流将string类型的参数写到服务器
                            outputStream.write(param.getBytes());
                        }else{
                            // 把数据写入请求的Body
                            outputStream.write(body_json.getBytes());
                        }
                        outputStream.flush();
                        outputStream.close();
                        // 当返回码为200时才读取数据
                        if (connection.getResponseCode() == 200) {
                            InputStream is = connection.getInputStream();
                            InputStreamReader isr = new InputStreamReader(is);
                            BufferedReader br = new BufferedReader(isr);
                            String s;
                            String str = "";
                            while ((s = br.readLine()) != null) {
                                str += s;
                            }
                            json_callBack.getJson(str);
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
