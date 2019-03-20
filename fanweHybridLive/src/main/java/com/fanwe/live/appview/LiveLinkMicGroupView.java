package com.fanwe.live.appview;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.library.holder.CallbackHolder;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.control.IPushSDK;
import com.fanwe.live.control.LivePlayerSDK;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.custommsg.data.DataLinkMicInfoModel;
import com.fanwe.live.model.custommsg.data.LinkMicItem;
import com.fanwe.live.model.custommsg.data.LinkMicLayoutParams;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 多人连麦view
 */
public class LiveLinkMicGroupView extends BaseAppView
{
    public LiveLinkMicGroupView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public LiveLinkMicGroupView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LiveLinkMicGroupView(Context context)
    {
        super(context);
        init();
    }

    public final CallbackHolder<LiveLinkMicGroupViewCallback> mCallBack = new CallbackHolder<>(LiveLinkMicGroupViewCallback.class);

    private LiveLinkMicView view_link_mic_0;
    private LiveLinkMicView view_link_mic_1;
    private LiveLinkMicView view_link_mic_2;
    private List<LiveLinkMicView> listLinkView = new ArrayList<>();

    private DataLinkMicInfoModel model;

    protected void init()
    {
        setContentView(R.layout.view_link_mic_group);
        view_link_mic_0 = find(R.id.view_link_mic_0);
        view_link_mic_1 = find(R.id.view_link_mic_1);
        view_link_mic_2 = find(R.id.view_link_mic_2);

        listLinkView.add(view_link_mic_0);
        listLinkView.add(view_link_mic_1);
        listLinkView.add(view_link_mic_2);

        for (LiveLinkMicView view : listLinkView)
        {
            initLinkMicView(view);
        }
    }

    private void initLinkMicView(final LiveLinkMicView view)
    {
        view.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mCallBack.get().onClickView(view);
            }
        });
        view.setPlayerListener(new LivePlayerSDK.PlayerListener()
        {
            private int errGetAccCount;

            @Override
            public void onPlayEvent(int event, Bundle param)
            {
                if (TXLiveConstants.PLAY_ERR_NET_DISCONNECT == event ||
                        TXLiveConstants.PLAY_EVT_PLAY_END == event)
                {
                    mCallBack.get().onPlayDisconnect(view.getUserId());
                } else if (TXLiveConstants.PLAY_ERR_GET_RTMP_ACC_URL_FAIL == event)
                {
                    errGetAccCount++;
                    LogUtil.i("----------errGetAccCount:" + errGetAccCount);
                    if (errGetAccCount >= 5)
                    {
                        errGetAccCount = 0;
                        mCallBack.get().onPlayDisconnect(view.getUserId());
                    }
                }
            }

            @Override
            public void onPlayBegin(int event, Bundle param)
            {

            }

            @Override
            public void onPlayRecvFirstFrame(int event, Bundle param)
            {
                errGetAccCount = 0;
                mCallBack.get().onPlayRecvFirstFrame(view.getUserId());
            }

            @Override
            public void onPlayProgress(int event, Bundle param, int total, int progress)
            {

            }

            @Override
            public void onPlayEnd(int event, Bundle param)
            {

            }

            @Override
            public void onPlayLoading(int event, Bundle param)
            {

            }

            @Override
            public void onNetStatus(Bundle param)
            {

            }
        });

        //由于推流sdk是单例的，所以会覆盖回调，这里暂时先这样处理
        view.getPusher().setPushCallback(new IPushSDK.PushCallback()
        {
            @Override
            public void onPushStarted()
            {
                mCallBack.get().onPushStart(view);
            }
        });
    }

    public void setLinkMicInfo(DataLinkMicInfoModel model)
    {
        this.model = model;
        if (model != null)
        {
            List<LinkMicItem> listTemp = SDCollectionUtil.getTempList(model.getList_lianmai());
            resetViewIfNeed(listTemp);
            if (listTemp != null)
            {
                for (LinkMicItem item : listTemp)
                {
                    LiveLinkMicView view = findFreeView();
                    if (view != null)
                    {
                        SDViewUtil.setVisible(view);
                        view.setUserId(item.getUser_id());
                        layoutView(view, item.getLayout_params());
                        if (item.isLocalUserLinkMic())
                        {
                            //推流
                            String url = item.getPush_rtmp2();
                            view.setPusher(url);
                            view.getPusher().setConfigLinkMicSub();
                            view.start();
                            LogUtil.i("pushView:" + item.getUser_id() + "," + url);
                        } else
                        {
                            //拉流
                            String url = item.getPlay_rtmp2_acc();
                            view.setPlayer(url, TXLivePlayer.PLAY_TYPE_LIVE_RTMP_ACC);
                            view.start();
                            LogUtil.i("playView:" + item.getUser_id() + "," + url);
                        }
                    }
                }
            }
        }
    }
    /**
     * 根据传进来的连麦列表，把不在连麦列表中的view重置，把正在使用的view重新布局并且把对应的连麦item移除
     *
     * @param listItem
     */
    private void resetViewIfNeed(List<LinkMicItem> listItem)
    {
        if (listItem != null && !listItem.isEmpty())
        {
            for (LiveLinkMicView view : listLinkView)
            {
                boolean isNeedReset = true;

                Iterator<LinkMicItem> it = listItem.iterator();
                while (it.hasNext())
                {
                    LinkMicItem item = it.next();
                    if (item.getUser_id().equals(view.getUserId()))
                    {
                        isNeedReset = false;
                        layoutView(view, item.getLayout_params());
                        it.remove();
                        break;
                    }
                }

                if (isNeedReset)
                {
                    resetView(view);
                }
            }
        } else
        {
            resetAllView();
        }
    }

    /**
     * 查找空闲的view
     *
     * @return
     */
    private LiveLinkMicView findFreeView()
    {
        for (LiveLinkMicView view : listLinkView)
        {
            if (TextUtils.isEmpty(view.getUserId()))
            {
                return view;
            }
        }
        return null;
    }

    /**
     * 重置view
     *
     * @param view
     */
    private void resetView(LiveLinkMicView view)
    {
        LogUtil.i("resetView:" + view.getUserId());
        view.resetView();
        SDViewUtil.setGone(view);
    }

    public void resetAllView()
    {
        LogUtil.i("resetAllView");
        for (LiveLinkMicView view : listLinkView)
        {
            resetView(view);
        }
    }

    /**
     * 根据布局参数，重新设置view的位置和大小
     *
     * @param view
     * @param params
     */
    private void layoutView(LiveLinkMicView view, LinkMicLayoutParams params)
    {
        if (params == null)
        {
            return;
        }

        SDViewUtil.setVisible(view);

        int x = SDViewUtil.getScreenWidthPercent(params.getLocation_x());
        int y = SDViewUtil.getScreenHeightPercent(params.getLocation_y());
        int width = SDViewUtil.getScreenWidthPercent(params.getImage_width());
        int height = SDViewUtil.getScreenHeightPercent(params.getImage_height());

        SDViewUtil.setMarginLeft(view, x);
        SDViewUtil.setMarginTop(view, y);
        SDViewUtil.setSize(view, width, height);
        LogUtil.i("layoutView:" + view.getUserId());
    }

    /**
     * 获得当前连麦用户的view
     *
     * @return
     */
    public LiveLinkMicView getMyView()
    {
        String userId = UserModelDao.getUserId();
        if (TextUtils.isEmpty(userId))
        {
            return null;
        }

        for (LiveLinkMicView view : listLinkView)
        {
            if (userId.equals(view.getUserId()))
            {
                return view;
            }
        }
        return null;
    }

    public void onPause()
    {
        for (LiveLinkMicView item : listLinkView)
        {
            item.pause();
        }
    }

    public void onResume()
    {
        for (LiveLinkMicView item : listLinkView)
        {
            item.resume();
        }
    }

    public void onDestroy()
    {
        for (LiveLinkMicView item : listLinkView)
        {
            item.onDestroy();
        }
    }

    public interface LiveLinkMicGroupViewCallback
    {
        /**
         * 拉不到小主播的流
         *
         * @param userId
         */
        void onPlayDisconnect(String userId);

        /**
         * 小主播的视频第一帧被渲染
         *
         * @param userId
         */
        void onPlayRecvFirstFrame(String userId);

        /**
         * 小主播的连麦view被点击
         *
         * @param view
         */
        void onClickView(LiveLinkMicView view);

        /**
         * 推流开始
         *
         * @param view
         */
        void onPushStart(LiveLinkMicView view);

    }

}
