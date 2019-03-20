package com.fanwe.live.appview.room;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.library.event.EKeyboardVisibilityChange;
import com.fanwe.library.utils.SDAnimationUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;

/**
 * Created by shibx on 2017/2/7.
 * PC端直播间 横屏控制视图
 */

public class RoomPCLiveLandControlView extends ARoomPCLiveBaseControlView {

    public RoomPCLiveLandControlView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RoomPCLiveLandControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoomPCLiveLandControlView(Context context) {
        super(context);
    }

    @Override
    protected int onCreateContentView() {
        return R.layout.view_room_pc_land_control;
    }

    @Override
    protected void initOrientationView() {
        rl_pc_control = find(R.id.rl_pc_control);
        ll_control_view_top = find(R.id.ll_control_view_land_top);
        ll_control_view_bottom = find(R.id.ll_control_view_land_bottom);
        ll_control_view_right = find(R.id.ll_control_view_land_right);
        tv_pc_title = find(R.id.tv_pc_title);
        iv_pc_pause = find(R.id.iv_pc_pause);
        iv_pc_return = find(R.id.iv_pc_return);
        iv_pc_refresh = find(R.id.iv_pc_refresh);
        tv_pc_live = find(R.id.tv_pc_live);
        iv_pc_lock = find(R.id.iv_pc_lock);
        iv_pc_gift = find(R.id.iv_pc_gift);
        iv_pc_danmaku_switch = find(R.id.iv_pc_danmaku_switch);
        rl_pc_control.setOnClickListener(this);
        iv_pc_pause.setOnClickListener(this);
        iv_pc_return.setOnClickListener(this);
        iv_pc_refresh.setOnClickListener(this);
        tv_pc_live.setOnClickListener(this);
        iv_pc_lock.setOnClickListener(this);
        iv_pc_gift.setOnClickListener(this);
        iv_pc_danmaku_switch.setOnClickListener(this);
        initTitle();
    }

    @Override
    protected void initAnimators() {
        float top_height = SDViewUtil.getHeight(ll_control_view_top);
        float bottom_height = SDViewUtil.getHeight(ll_control_view_bottom);
        float right_width = SDViewUtil.getWidth(ll_control_view_right);

        mAnimatorSetHide.play(SDAnimationUtil.translationY(ll_control_view_top, 0, - top_height))
                .with(SDAnimationUtil.translationY(ll_control_view_bottom, 0, bottom_height))
                .with(SDAnimationUtil.translationX(ll_control_view_right, 0, right_width));

        mAnimatorSetShow.play(SDAnimationUtil.translationY(ll_control_view_top, - top_height, 0))
                .with(SDAnimationUtil.translationY(ll_control_view_bottom, bottom_height, 0))
                .with(SDAnimationUtil.translationX(ll_control_view_right, right_width, 0));
    }

    private void initTitle() {
        tv_pc_title.setText(getLiveActivity().getRoomInfo().getTitle());
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
        mListener.onClickRefresh(view);
    }

    @Override
    protected void onClickLives(View view) {
        mListener.onClickLives(view);
    }

    @Override
    protected void onClickLock(View view) {
        mListener.onClickLock(view);
    }

    @Override
    protected void onClickGift(View view) {
        mListener.onClickGift(view);
    }

    @Override
    protected void onClickDanmakuSwitch(View view) {
        mListener.onClickDanmakuSwitch(view);
    }

    @Override
    protected void hideControlView() {
        if(mAnimatorSetHide.getChildAnimations().size() == 0) {
            initAnimators();
        }
        mAnimatorSetHide.start();
        isControlViewShown = false;
        SDHandlerManager.getMainHandler().removeCallbacks(mRunnable);
    }

    @Override
    public void showControlView() {
        if(isControlViewShown) {
            return;
        }
        if(mAnimatorSetShow.getChildAnimations().size() == 0) {
            initAnimators();
        }
        mAnimatorSetShow.start();
        isControlViewShown = true;
        SDHandlerManager.getMainHandler().postDelayed(mRunnable, DEFAULT_HIDE_DELAY);
    }

    public void onEventMainThread(EKeyboardVisibilityChange event) {
        isKeyboardVisible = event.visible;
        if(isKeyboardVisible) {
//            SDHandlerManager.getMainHandler().removeCallbacks(mRunnable);
        } else {
            SDHandlerManager.getMainHandler().postDelayed(mRunnable, DEFAULT_HIDE_DELAY);
        }
    }
}
