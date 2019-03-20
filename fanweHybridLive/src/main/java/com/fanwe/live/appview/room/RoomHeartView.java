package com.fanwe.live.appview.room;

import android.content.Context;
import android.util.AttributeSet;

import com.fanwe.hybrid.constant.ApkConstant;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.blocker.SDDurationBlocker;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDLoopQueue;
import com.fanwe.live.IMHelper;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.custommsg.CustomMsgLight;
import com.fanwe.live.view.HeartLayout;

import java.util.LinkedList;

/**
 * 心心
 */
public class RoomHeartView extends RoomView
{

    public RoomHeartView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public RoomHeartView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public RoomHeartView(Context context)
    {
        super(context);
        init();
    }

    /**
     * 点击拦截间隔
     */
    private static final long DURATION_BLOCK_CLICK = 200;
    /**
     * 主播队列显示间隔
     */
    private static final long DURATION_LOOPER_CREATER = 300;
    /**
     * 观众队列显示间隔
     */
    private static final long DURATION_LOOPER_VIEWER = 300;
    /**
     * 队列最大消息数量
     */
    private static final int MAX_MSG = 10;

    private HeartLayout view_heart;
    private SDDurationBlocker blocker;

    private boolean isFirst = true;
    private SDLoopQueue<CustomMsgLight> loopQueue;

    @Override
    protected int onCreateContentView()
    {
        return R.layout.view_room_heart;
    }

    protected void init()
    {
        view_heart = find(R.id.view_heart);
        blocker = new SDDurationBlocker(DURATION_BLOCK_CLICK);
        initLoopQueue();
    }

    private void initLoopQueue()
    {
        loopQueue = new SDLoopQueue<>();
        loopQueue.setListener(new SDLoopQueue.SDLoopQueueListener<CustomMsgLight>()
        {
            @Override
            public void onLoop(LinkedList<CustomMsgLight> queue)
            {
                if (ApkConstant.DEBUG)
                {
                    LogUtil.i("onLoop:" + queue.size());
                }
                CustomMsgLight msg = queue.poll();
                if (msg != null)
                {
                    addHeartInside(msg.getImageName());
                }
            }
        });
        if (getLiveActivity().isCreater())
        {
            loopQueue.startLoop(DURATION_LOOPER_CREATER);
        } else
        {
            loopQueue.startLoop(DURATION_LOOPER_VIEWER);
        }
    }

    public void addHeart()
    {
        if (blocker.block())
        {
            return;
        }

        sendLightMsg();
    }

    private void sendLightMsg()
    {
        boolean sendImMsg = true;

        final CustomMsgLight msg = new CustomMsgLight();
        final String name = view_heart.randomImageName();
        msg.setImageName(name);

        if (isFirst)
        {
            isFirst = false;
            if (getLiveActivity().getRoomInfo() != null)
            {
                if (getLiveActivity().getRoomInfo().getJoin_room_prompt() == 0)
                {
                    UserModel user = UserModelDao.query();
                    if (user != null && !user.isProUser())
                    {
                        sendImMsg = false;
                    }
                }
            }

            CommonInterface.requestLike(getLiveActivity().getRoomId(), new AppRequestCallback<BaseActModel>()
            {
                @Override
                protected void onSuccess(SDResponse sdResponse)
                {
                    if (actModel.isOk())
                    {
                        LogUtil.i("request like success");
                    }
                }
            });
            msg.setShowMsg(1);
        } else
        {
            msg.setShowMsg(0);
        }

        if (view_heart.getHeartCount() >= 7)
        {
            // 界面中已经有足够的星星，不发送im消息
            sendImMsg = false;
        }

        if (sendImMsg)
        {
            LogUtil.i("add heart im");
            String groupId = getLiveActivity().getGroupId();
            IMHelper.sendMsgGroup(groupId, msg, null);
            if (msg.getShowMsg() == 1)
            {
                IMHelper.postMsgLocal(msg, groupId);
            } else
            {
                addHeartInside(name);
            }
        } else
        {
            LogUtil.i("add heart local");
            addHeartInside(name);
        }
    }

    private void addHeartInside(String imageName)
    {
        view_heart.addHeart(imageName);
    }

    @Override
    public void onMsgLight(CustomMsgLight msg)
    {
        super.onMsgLight(msg);
        if (loopQueue.size() >= MAX_MSG)
        {
            return;
        }
        loopQueue.offer(msg);
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        loopQueue.stopLoop();
    }
}
