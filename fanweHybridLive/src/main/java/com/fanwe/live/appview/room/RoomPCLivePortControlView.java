package com.fanwe.live.appview.room;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;

/**
 * Created by shibx on 2017/2/7.
 * PC端直播间 竖屏控制视图
 */

public class RoomPCLivePortControlView extends ARoomPCLiveBaseControlView {

    public RoomPCLivePortControlView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RoomPCLivePortControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoomPCLivePortControlView(Context context) {
        super(context);
    }

    @Override
    protected int onCreateContentView() {
        return R.layout.view_room_pc_port_control;
    }

    @Override
    protected void initOrientationView() {
        rl_pc_control = find(R.id.rl_pc_control);
        ll_control_view_right = find(R.id.ll_control_view_port_right);
        ll_control_view_top = find(R.id.ll_control_view_port_top);
        iv_pc_switch_fullscreen = find(R.id.iv_pc_switch_fullscreen);
        iv_pc_pause = find(R.id.iv_pc_pause);
        iv_pc_close = find(R.id.iv_pc_close);
        rl_pc_control.setOnClickListener(this);
        iv_pc_switch_fullscreen.setOnClickListener(this);
        iv_pc_pause.setOnClickListener(this);
        iv_pc_close.setOnClickListener(this);
    }

    @Override
    protected void initAnimators() {

    }

    @Override
    protected void onClickSwitchScreenOrientation(View view) {
        mListener.onClickSwitchScreenOrientation(view);
    }

    @Override
    protected void onClickPause(View view) {
        mListener.onClickPause(view);
    }

    @Override
    protected void onClickClose(View view) {
        mListener.onClickClose(view);
    }

    @Override
    protected void onClickRefresh(View view) {

    }

    @Override
    protected void onClickLives(View view) {

    }

    @Override
    protected void onClickLock(View view) {

    }

    @Override
    protected void onClickGift(View view) {

    }

    @Override
    protected void onClickDanmakuSwitch(View view) {

    }

    @Override
    public void hideControlView() {
        SDViewUtil.setGone(ll_control_view_right);
        SDViewUtil.setGone(ll_control_view_top);
        isControlViewShown = false;
        SDHandlerManager.getMainHandler().removeCallbacks(mRunnable);
    }

    @Override
    public void showControlView() {
        if(isControlViewShown) {
            return;
        }
        SDViewUtil.setVisible(ll_control_view_right);
        SDViewUtil.setVisible(ll_control_view_top);
        isControlViewShown = true;
        SDHandlerManager.getMainHandler().postDelayed(mRunnable, DEFAULT_HIDE_DELAY);
    }
}
