package com.fanwe.pay;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.live.activity.room.ILiveActivity;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.App_get_videoActModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.pay.common.PayCommonInterface;
import com.fanwe.pay.model.App_live_live_pay_deductActModel;
import com.fanwe.pay.model.custommsg.CustomMsgStartScenePayMode;

/**
 * Created by Administrator on 2017/1/9.
 */

public class LiveScenePayViewerBusiness extends LivePayBusiness
{
    private boolean canJoinRoom = true;
    private boolean isAgree; //是否同意观看
    private LiveScenePayViewerBusinessListener businessListener;

    public void setBusinessListener(LiveScenePayViewerBusinessListener businessListener)
    {
        this.businessListener = businessListener;
    }

    public LiveScenePayViewerBusiness(ILiveActivity liveInfo)
    {
        super(liveInfo);
    }

    @Override
    protected void onMsgScenePayWillStart(CustomMsgStartScenePayMode customMsg)
    {
        super.onMsgScenePayWillStart(customMsg);
    }

    public void requestSceneLivePayDeduct()
    {

    }

    /**
     * 按场收费逻辑判断
     */
    public void dealScenePayModeLiveInfo(App_live_live_pay_deductActModel model)
    {

    }

    /**
     * 同意加入按场直播
     */
    public void agreeJoinSceneLive()
    {
    }

    //按场付费请求RequestRoomInfoSuccess逻辑判断

    public void dealPayModelRoomInfoSuccess(App_get_videoActModel actModel)
    {


    }

    private void setCanJoinRoom(boolean canJoinRoom)
    {

    }

    /**
     * 设置isAgree 为false
     */
    public void rejectJoinSceneLive()
    {
        this.isAgree = false;
    }

    @Override
    protected BaseBusinessCallback getBaseBusinessCallback()
    {
        return businessListener;
    }

    public interface LiveScenePayViewerBusinessListener extends BaseBusinessCallback
    {
        /**
         * 是否要显示覆盖层
         */
        void onScenePayViewerShowCovering(boolean show);

        /**
         * 显示充值提示
         */
        void onScenePayViewerShowRecharge(App_live_live_pay_deductActModel model);

        /**
         * @param model 显示按场直播View
         */
        void onScenePayViewerShowPayInfoView(App_live_live_pay_deductActModel model);

        /**
         * 是否加入按场直播
         *
         * @param live_fee
         */
        void onScenePayViewerShowWhetherJoin(int live_fee);

        /**
         * \
         * 是否可以加入房间
         */
        void onScenePayViewerCanJoinRoom(boolean canJoinRoom);

        /**
         * 刚进入是否预览视频
         *
         * @param preview_play_url
         */
        void onScenePayViewerShowCoveringPlayeVideo(String preview_play_url, int countdown, int is_only_play_voice);
    }
}
