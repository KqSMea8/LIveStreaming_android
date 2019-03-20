package com.fanwe.live.appview.room;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;

import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;

/**
 * Created by shibx on 2017/2/7.
 * PC端回放 竖屏控制视图
 */

public class RoomPCPlaybackPortControlView extends ARoomPCPlaybackBaseControlView {

    public RoomPCPlaybackPortControlView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RoomPCPlaybackPortControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoomPCPlaybackPortControlView(Context context) {
        super(context);
    }

    @Override
    public void setSeekBarListener(SeekBar.OnSeekBarChangeListener listener) {
        seekbar.setSeekBarOnChangeListener(listener);
    }

    @Override
    public void setPlaybackBottonListener(RoomPCPlaybackSeekbarView.PlaybackBottonListener listener) {
        seekbar.setPlaybackBottonListener(listener);
    }

    @Override
    protected int onCreateContentView() {
        return R.layout.view_room_pc_playback_port_control;
    }

    @Override
    protected void initOrientationView() {
        rl_pc_control = find(R.id.rl_pc_control);
        ll_control_view_right = find(R.id.ll_control_view_port_right);
        ll_control_view_top = find(R.id.ll_control_view_port_top);
        seekbar = find(R.id.seekbar);
        iv_pc_close = find(R.id.iv_pc_close);
        rl_pc_control.setOnClickListener(this);
        iv_pc_close.setOnClickListener(this);
    }

    @Override
    protected void initAnimators() {

    }

    @Override
    public void updateProgress(long progress, long total) {
        seekbar.updateProgress(progress,total);
    }

    @Override
    public void setSeekBarMax(int maxValue) {
        seekbar.setSeekBarMax(maxValue);
    }

    @Override
    public void setSeekBarProgress(int progressValue) {
        seekbar.setSeekBarProgress(progressValue);
    }

    @Override
    public void updateBotton(boolean isPlaying) {
        seekbar.updateControlBotton(isPlaying);
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
