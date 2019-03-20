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
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.appview.LiveLinkMicView;
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
public class LivePKView extends BaseAppView {
    public LivePKView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public LivePKView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LivePKView(Context context) {
        super(context);
        init();
    }


    private LiveLinkMicView view_link_mic_0;

    public void stopPlay(){
        view_link_mic_0.stop();
    }
    protected void init() {
        setContentView(R.layout.view_pk_group);
        view_link_mic_0 = find(R.id.view_link_mic_0);
    }

    public void setLinkMicInfo(String url) {
        view_link_mic_0.setVisibility(VISIBLE);
        view_link_mic_0.setPlayer(url, TXLivePlayer.PLAY_TYPE_LIVE_RTMP_ACC);
        view_link_mic_0.getPusher().setConfigLinkMicSub();
        view_link_mic_0.start();
    }

    private void resetView(LiveLinkMicView view) {
        LogUtil.i("resetView:" + view.getUserId());
        view.resetView();
        SDViewUtil.setGone(view);
    }


    /**
     * 根据布局参数，重新设置view的位置和大小
     *
     * @param view
     * @param params
     */
    private void layoutView(LiveLinkMicView view, LinkMicLayoutParams params) {
        if (params == null) {
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

    public void onPause() {
    }

    public void onResume() {
    }

    public void onDestroy() {
        view_link_mic_0.onDestroy();
    }

}
