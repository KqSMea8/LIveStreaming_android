package com.fanwe.baimei.common;

import com.fanwe.baimei.model.BMCheckVerifyActModel;
import com.fanwe.baimei.model.BMDailyTaskResponseModel;
import com.fanwe.baimei.model.BMDailyTasksAwardAcceptResponseModel;
import com.fanwe.baimei.model.BMGameCenterResponseModel;
import com.fanwe.baimei.model.BMGameCreateRoomResponseModel;
import com.fanwe.baimei.model.BMGameFriendsRoomResponseModel;
import com.fanwe.baimei.model.BMGameRankActModel;
import com.fanwe.baimei.model.BMGameRoomCodeInputResponseModel;
import com.fanwe.baimei.model.BMGameRoomListResponseModel;
import com.fanwe.baimei.model.BMGamesJoinActModel;
import com.fanwe.hybrid.http.AppHttpUtil;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.http.AppRequestParams;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.handler.SDRequestHandler;

/**
 * Created by yhz on 2017/5/17.
 */

public class BMCommonInterface
{
    public static AppRequestParams setAppRequestParams(AppRequestParams params)
    {
        if (BMAppRuntimeWorker.getOpenBM() == 1)
        {
//            params.put("itype", "bm");
        }
        return params;
    }

    public static AppRequestParams getBMRequestParams()
    {
        AppRequestParams params = new AppRequestParams();
//        params.put("itype", "bm");
        return params;
    }

    /**
     * 请求游戏主页数据
     *
     * @param listener
     */
    public static void requestGameHome(AppRequestCallback<BMGameCenterResponseModel> listener)
    {
        AppRequestParams params = getBMRequestParams();
        params.putCtl("games");
        params.putAct("index");
        params.put("need_ww",1);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 请求游戏房间列表
     *
     * @param listener
     */
    public static void requestGameRoomList(String gameId, AppRequestCallback<BMGameRoomListResponseModel> listener)
    {
        AppRequestParams params = getBMRequestParams();
        params.putCtl("games");
        params.putAct("games_room");
        params.put("id", gameId);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 请求刷新关注房间
     *
     * @param listener
     */
    public static void requestGameFriendsRoomList(AppRequestCallback<BMGameFriendsRoomResponseModel> listener)
    {
        AppRequestParams params = getBMRequestParams();
        params.putCtl("games");
        params.putAct("follow_room");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 请求创建房间
     *
     * @param listener
     */
    public static void requestGameCreateRoom(AppRequestCallback<BMGameCreateRoomResponseModel> listener)
    {
        AppRequestParams params = getBMRequestParams();
        params.putCtl("games");
        params.putAct("bm_add_video");
        params.put("live_image", "");
        params.put("title", "");
        params.put("cate_id", "");
        params.put("city", "");
        params.put("province", "");
        params.put("share_type", "");
        params.put("location_switch", 0);
        params.put("is_private", 1);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 游戏排行榜
     *
     * @param rank_name 类型：'day','all'
     * @param game_id
     * @param p         页码
     * @param listener
     */
    public static void requestRankGame(String rank_name, int game_id, int p, AppRequestCallback<BMGameRankActModel> listener)
    {
        AppRequestParams params = getBMRequestParams();
        params.putCtl("rank");
        params.putAct("game");
        params.put("rank_name", rank_name);
        params.put("game_id", game_id);
        params.put("p", p);
        AppHttpUtil.getInstance().post(params, listener);
    }

    public static SDRequestHandler requestGamesJoin(AppRequestCallback<BMGamesJoinActModel> listener)
    {
        return requestGameJoin(0, listener);
    }

    public static SDRequestHandler requestGameJoin(int game_id, AppRequestCallback<BMGamesJoinActModel> listener)
    {
        AppRequestParams params = getBMRequestParams();
        params.putCtl("games");
        params.putAct("join");
        params.put("game_id", game_id);
        return AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 请求输入房间私密码
     *
     * @param key_number
     * @param listener
     */
    public static void requestGameRoomCodeInput(String key_number, AppRequestCallback<BMGameRoomCodeInputResponseModel> listener)
    {
        AppRequestParams params = getBMRequestParams();
        params.putCtl("games");
        params.putAct("join_by_key");
        params.put("key_number", key_number);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 请求获取每日任务列表
     *
     * @param listener
     */
    public static void requestDailyTasks(AppRequestCallback<BMDailyTaskResponseModel> listener)
    {
        AppRequestParams params = getBMRequestParams();
        params.putCtl("mission");
        params.putAct("getMissionList");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 请求获取每日任务列表完成奖励
     *
     * @param type
     * @param listener
     */
    public static void requestDailyTasksAwardAccept(int type, AppRequestCallback<BMDailyTasksAwardAcceptResponseModel> listener)
    {
        AppRequestParams params = getBMRequestParams();
        params.putCtl("mission");
        params.putAct("commitMission");
        params.put("type",type);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * @param is_out   是否离开，1离开，0或者不传为关闭房间
     * @param listener
     */
    public static void requestGamesBmEndVideo(int is_out, AppRequestCallback<BaseActModel> listener)
    {
        AppRequestParams params = getBMRequestParams();
        params.putCtl("games");
        params.putAct("bm_end_video");
        params.put("is_out", is_out);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     *
     * @param room_id 房间ID
     * @param is_push 是否推流 1 ,0
     * @param listener
     */
    public static void requestGamesIsPush(int room_id,int is_push, AppRequestCallback<BaseActModel> listener)
    {
        AppRequestParams params = getBMRequestParams();
        params.putCtl("games");
        params.putAct("is_push");
        params.put("room_id", room_id);
        params.put("is_push", is_push);
        AppHttpUtil.getInstance().post(params, listener);
    }

    public static void requestVerifyStatus(AppRequestCallback<BMCheckVerifyActModel> listener)
    {
        AppRequestParams params = getBMRequestParams();
        params.putCtl("games");
        params.putAct("checkCode");
        AppHttpUtil.getInstance().post(params, listener);
    }

    public static void requestVerify(String code, AppRequestCallback<BaseActModel> listener)
    {
        AppRequestParams params = getBMRequestParams();
        params.putCtl("games");
        params.putAct("invitationCode");
        params.put("code", code);
        AppHttpUtil.getInstance().post(params, listener);
    }
}
