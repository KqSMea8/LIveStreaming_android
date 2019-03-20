package com.fanwe.live.appview.room;

import android.content.Context;
import android.util.AttributeSet;

import com.fanwe.baimei.model.custommsg.BMCustomMsgPushStatus;
import com.fanwe.games.model.custommsg.CustomMsgGameBanker;
import com.fanwe.live.activity.room.ILiveActivity;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.business.LiveMsgBusiness;
import com.fanwe.live.event.EImOnNewMessages;
import com.fanwe.live.model.App_viewerActModel;
import com.fanwe.live.model.custommsg.CustomMsgAcceptLinkMic;
import com.fanwe.live.model.custommsg.CustomMsgApplyLinkMic;
import com.fanwe.live.model.custommsg.CustomMsgCreaterComeback;
import com.fanwe.live.model.custommsg.CustomMsgCreaterLeave;
import com.fanwe.live.model.custommsg.CustomMsgData;
import com.fanwe.live.model.custommsg.CustomMsgEndVideo;
import com.fanwe.live.model.custommsg.CustomMsgGift;
import com.fanwe.live.model.custommsg.CustomMsgGreedLinkMic;
import com.fanwe.live.model.custommsg.CustomMsgHeatRank;
import com.fanwe.live.model.custommsg.CustomMsgLargeGift;
import com.fanwe.live.model.custommsg.CustomMsgLight;
import com.fanwe.live.model.custommsg.CustomMsgOpenShouhu;
import com.fanwe.live.model.custommsg.CustomMsgPopMsg;
import com.fanwe.live.model.custommsg.CustomMsgPrize;
import com.fanwe.live.model.custommsg.CustomMsgRedEnvelope;
import com.fanwe.live.model.custommsg.CustomMsgRejectLinkMic;
import com.fanwe.live.model.custommsg.CustomMsgStopLinkMic;
import com.fanwe.live.model.custommsg.CustomMsgStopLive;
import com.fanwe.live.model.custommsg.CustomMsgViewerJoin;
import com.fanwe.live.model.custommsg.CustomMsgViewerQuit;
import com.fanwe.live.model.custommsg.CustomMsgWarning;
import com.fanwe.live.model.custommsg.MsgModel;
import com.fanwe.live.model.custommsg.data.DataLinkMicInfoModel;

/**
 * Created by Administrator on 2016/8/4.
 */
public class RoomView extends BaseAppView implements LiveMsgBusiness.LiveMsgBusinessCallback
{
    private LiveMsgBusiness mMsgBusiness;

    public RoomView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public RoomView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RoomView(Context context)
    {
        super(context);
    }

    public ILiveActivity getLiveActivity()
    {
        if (getActivity() instanceof ILiveActivity)
        {
            return (ILiveActivity) getActivity();
        } else
        {
            return null;
        }
    }

    public LiveMsgBusiness getMsgBusiness()
    {
        if (mMsgBusiness == null)
        {
            mMsgBusiness = new LiveMsgBusiness();
            mMsgBusiness.setBusinessCallback(this);
        }
        return mMsgBusiness;
    }

    public final void onEventMainThread(EImOnNewMessages event)
    {
        getMsgBusiness().parseMsg(event.msg, getLiveActivity().getGroupId());
    }

    @Override
    public void onMsgRedEnvelope(CustomMsgRedEnvelope msg)
    {

    }

    @Override
    public void onMsgApplyLinkMic(CustomMsgApplyLinkMic msg)
    {

    }

    @Override
    public void onMsgAcceptLinkMic(CustomMsgAcceptLinkMic msg)
    {

    }

    @Override
    public void onMsgGreedLinkMic(CustomMsgGreedLinkMic msg) {

    }

    @Override
    public void onMsgRejectLinkMic(CustomMsgRejectLinkMic msg)
    {

    }

    @Override
    public void onMsgStopLinkMic(CustomMsgStopLinkMic msg)
    {

    }

    @Override
    public void onMsgEndVideo(CustomMsgEndVideo msg)
    {

    }

    @Override
    public void onMsgStopLive(CustomMsgStopLive msg)
    {

    }

    @Override
    public void onMsgPrivate(MsgModel msg)
    {

    }

    @Override
    public void onMsgAuction(MsgModel msg)
    {

    }

    @Override
    public void onMsgShop(MsgModel msg)
    {

    }

    @Override
    public void onMsgPayMode(MsgModel msg)
    {

    }

    @Override
    public void onMsgGame(MsgModel msg)
    {

    }

    @Override
    public void onMsgGameBanker(CustomMsgGameBanker msg)
    {

    }

    @Override
    public void onMsgGift(CustomMsgGift msg)
    {

    }

    @Override
    public void onMsgPopMsg(CustomMsgPopMsg msg)
    {

    }

    @Override
    public void onMsgViewerJoin(CustomMsgViewerJoin msg)
    {

    }

    @Override
    public void onMsgViewerQuit(CustomMsgViewerQuit msg)
    {

    }

    @Override
    public void onMsgCreaterLeave(CustomMsgCreaterLeave msg)
    {

    }

    @Override
    public void onMsgCreaterComeback(CustomMsgCreaterComeback msg)
    {

    }

    @Override
    public void onMsgLight(CustomMsgLight msg)
    {

    }

    @Override
    public void onMsgLiveChat(MsgModel msg)
    {

    }

    @Override
    public void onMsgWarning(CustomMsgWarning msgWarning)
    {

    }

    @Override
    public void onMsgData(CustomMsgData msg)
    {

    }

    @Override
    public void onMsgHeatRank(CustomMsgHeatRank msg) {

    }



    @Override
    public void onMsgPrize(CustomMsgPrize msg) {

    }

    @Override
    public void onMsgLiveOpenShouhu(CustomMsgOpenShouhu msg) {

    }

    @Override
    public void onMsgDataViewerList(App_viewerActModel model)
    {

    }

    @Override
    public void onMsgDataLinkMicInfo(DataLinkMicInfoModel model)
    {

    }

    @Override
    public void onMsgLargeGift(CustomMsgLargeGift customMsgLargeGift)
    {

    }

    @Override
    public void onMsgPushStatus(BMCustomMsgPushStatus bmCustomMsgPushStatus)
    {

    }
}
