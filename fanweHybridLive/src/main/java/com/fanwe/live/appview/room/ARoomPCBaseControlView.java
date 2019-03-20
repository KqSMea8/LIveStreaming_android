package com.fanwe.live.appview.room;

import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.live.R;

/**
 * Created by shibx on 2017/2/7.
 * PC端直播间 控制视图 基类
 * {@link RoomPCLiveLandControlView} 直播横屏控制层
 * {@link RoomPCLivePortControlView} 直播竖屏控制层
 */

public abstract class ARoomPCBaseControlView extends RoomView
{

    protected PCControlViewClickListener mListener;

    protected View rl_pc_control;

    protected View ll_control_view_top;

    protected View ll_control_view_bottom;

    protected View ll_control_view_right;

    protected ImageView iv_pc_switch_fullscreen;//竖屏全屏按钮

    protected ImageView iv_pc_close;//竖屏关闭按钮

    protected ImageView iv_pc_return;//横屏返回按钮

    protected ImageView iv_pc_pause;//暂停按钮

    protected ImageView iv_pc_refresh;//刷新按钮

    protected TextView tv_pc_live;//横屏房间列表

    protected ImageView iv_pc_lock;//横屏控制锁

    protected ImageView iv_pc_gift;//横屏礼物

    protected ImageView iv_pc_danmaku_switch;//横屏弹幕显示开关

    protected TextView tv_pc_title;

    protected boolean isControlViewShown = true;

    protected HideViewRunnable mRunnable;

    protected AnimatorSet mAnimatorSetHide;

    protected AnimatorSet mAnimatorSetShow;

    protected final static long DEFAULT_HIDE_DELAY = 5000L;

    protected boolean isKeyboardVisible;

    public ARoomPCBaseControlView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public ARoomPCBaseControlView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public ARoomPCBaseControlView(Context context)
    {
        super(context);
        init();
    }

    protected void init()
    {
        mRunnable = new HideViewRunnable();
        mAnimatorSetHide = new AnimatorSet();
        mAnimatorSetShow = mAnimatorSetHide.clone();
        initOrientationView();
        this.setOnClickListener(this);
        hideControlViewDelay();
    }

    protected void hideControlViewDelay()
    {
        SDHandlerManager.getMainHandler().postDelayed(mRunnable, DEFAULT_HIDE_DELAY);
    }

    protected abstract void initOrientationView();

    protected abstract void initAnimators();

    protected abstract void onClickSwitchScreenOrientation(View view);

    protected abstract void onClickPause(View view);

    protected abstract void onClickClose(View view);

    protected abstract void onClickRefresh(View view);

    protected abstract void onClickLives(View view);

    protected abstract void onClickLock(View view);

    protected abstract void onClickGift(View view);

    protected abstract void onClickDanmakuSwitch(View view);

    protected abstract void hideControlView();

    public abstract void showControlView();

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.rl_pc_control:
                switchControlViewDisplay();
                break;
            case R.id.iv_pc_switch_fullscreen:
                onClickSwitchScreenOrientation(v);
                break;
            case R.id.iv_pc_pause:
                onClickPause(v);
                break;
            case R.id.iv_pc_return:
                onClickSwitchScreenOrientation(v);
                break;
            case R.id.iv_pc_close:
                onClickClose(v);
                break;
            case R.id.iv_pc_refresh:
                onClickRefresh(v);
                break;
            case R.id.tv_pc_live:
                onClickLives(v);
                break;
            case R.id.iv_pc_lock:
                onClickLock(v);
                break;
            case R.id.iv_pc_gift:
                onClickGift(v);
                break;
            case R.id.iv_pc_danmaku_switch:
                onClickDanmakuSwitch(v);
                break;
            default:
                break;
        }
    }

    public void switchControlViewDisplay()
    {
        if (isControlViewShown)
        {
            hideControlView();
        } else
        {
            showControlView();
        }
    }

    private class HideViewRunnable implements Runnable
    {

        @Override
        public void run()
        {
//            hideControlView();
        }
    }


    public void setPCControlViewClickListener(PCControlViewClickListener listener)
    {
        this.mListener = listener;
    }

    public interface PCControlViewClickListener
    {
        void onClickSwitchScreenOrientation(View view);

        void onClickPause(View view);

        void onClickClose(View view);

        void onClickRefresh(View view);

        void onClickLives(View view);

        void onClickLock(View view);

        void onClickGift(View view);

        void onClickDanmakuSwitch(View view);
    }
}
