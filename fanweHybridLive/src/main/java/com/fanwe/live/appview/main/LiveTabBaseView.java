package com.fanwe.live.appview.main;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

import com.fanwe.library.customview.SDViewPager;
import com.fanwe.library.looper.ISDLooper;
import com.fanwe.library.looper.impl.SDSimpleLooper;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.live.LiveConstant;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.event.EImOnNewMessages;
import com.fanwe.live.model.custommsg.CustomMsgLiveStopped;
import com.fanwe.live.model.custommsg.MsgModel;

/**
 * Created by Administrator on 2016/7/5.
 */
public class LiveTabBaseView extends BaseAppView
{
    public static final long DURATION_LOOP = 20 * 1000;
    public int type_id;

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    private ISDLooper looper;
    private SDViewPager parentViewPager;

    public LiveTabBaseView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public LiveTabBaseView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public LiveTabBaseView(Context context)
    {
        super(context);
    }

    public void setParentViewPager(SDViewPager parentViewPager)
    {
        this.parentViewPager = parentViewPager;
    }

    public SDViewPager getParentViewPager()
    {
        return parentViewPager;
    }

    public ISDLooper getLooper()
    {
        if (looper == null)
        {
            looper = new SDSimpleLooper();
        }
        return looper;
    }

    public void scrollToTop()
    {

    }

    public void onEventMainThread(EImOnNewMessages event)
    {
        try
        {
            if (event.msg.getCustomMsgType() == LiveConstant.CustomMsgType.MSG_LIVE_STOPPED)
            {
                onMsgLiveStopped(event.msg);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    protected void onMsgLiveStopped(MsgModel msgModel)
    {
        CustomMsgLiveStopped customMsg = msgModel.getCustomMsgLiveStopped();
        if (customMsg != null)
        {
            int roomId = customMsg.getRoom_id();
            onRoomClosed(roomId);
        }
    }

    /**
     * 直播间被关闭回调
     *
     * @param roomId
     */
    protected void onRoomClosed(int roomId)
    {

    }

    /**
     * 开始定时执行任务，每隔一段时间执行一下onLoopRun()方法
     */
    protected void startLoopRunnable()
    {
        getLooper().start(DURATION_LOOP, loopRunnable);
    }

    /**
     * 停止定时任务
     */
    private void stopLoopRunnable()
    {
        getLooper().stop();
    }

    private Runnable loopRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            LogUtil.i(getClass().getName() + ":onLoopRun");
            onLoopRun();
        }
    };

    /**
     * 定时执行任务
     */
    protected void onLoopRun()
    {

    }

    @Override
    public void onActivityStopped(Activity activity)
    {
        super.onActivityStopped(activity);
        stopLoopRunnable();
    }

    @Override
    public void onActivityDestroyed(Activity activity)
    {
        super.onActivityDestroyed(activity);
        stopLoopRunnable();
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        stopLoopRunnable();
    }


}
