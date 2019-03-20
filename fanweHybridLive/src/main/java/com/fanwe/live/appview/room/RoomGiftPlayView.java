package com.fanwe.live.appview.room;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.fanwe.library.looper.ISDLooper;
import com.fanwe.library.looper.impl.SDSimpleLooper;
import com.fanwe.live.LiveConstant.CustomMsgType;
import com.fanwe.live.R;
import com.fanwe.live.activity.room.LiveLayoutActivity;
import com.fanwe.live.appview.ILiveGiftView;
import com.fanwe.live.model.GiftAward;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.custommsg.CustomMsgGift;
import com.fanwe.live.model.custommsg.ILiveGiftMsg;
import com.fanwe.live.model.custommsg.MsgModel;
import com.fanwe.live.view.LiveGiftPlayView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 礼物播放
 *
 * @author Administrator
 * @date 2016-5-16 下午1:16:27
 */
public class RoomGiftPlayView extends RoomView {
    public RoomGiftPlayView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RoomGiftPlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoomGiftPlayView(Context context) {
        super(context);
    }

    private static final long DURATION_LOOPER = 150;

    private LiveGiftPlayView view_gift_play0;
    private LiveGiftPlayView view_gift_play1;
    private LiveGiftPlayView view_gift_play2;
    private LiveGiftPlayView view_gift_play3;
    private List<ILiveGiftView> mListPlayView;
    private List<ILiveGiftMsg> mListMsg;
    private ISDLooper mLooper;

    @Override
    protected int onCreateContentView() {
        return R.layout.view_room_gift_play;
    }

    @Override
    protected void onBaseInit() {
        super.onBaseInit();
        view_gift_play0 = find(R.id.view_gift_play0);
        view_gift_play1 = find(R.id.view_gift_play1);
        view_gift_play2 = find(R.id.view_gift_play2);
        view_gift_play3 = find(R.id.view_gift_play3);
        mListPlayView = new ArrayList<>();
        mListMsg = new ArrayList<>();
        mLooper = new SDSimpleLooper();

        mListPlayView.add(view_gift_play0);
        mListPlayView.add(view_gift_play1);
        mListPlayView.add(view_gift_play2);
        mListPlayView.add(view_gift_play3);
    }

    private void looperWork() {
        boolean foundMsg = false;
        for (ILiveGiftView view : mListPlayView) {
            if (view.canPlay()) {
                // 如果当前view满足播放条件，开始遍历msg列表，寻找可以播放的msg
                for (ILiveGiftMsg msg : mListMsg) {
                    if (msg.isTaked()) {
                        continue;
                    } else {
                        // 如果本条msg还没被播放过，遍历view列表，寻找是否已经有包含本条msg的view
                        ILiveGiftView currentMsgView = null;
                        for (ILiveGiftView otherView : mListPlayView) {
                            if (otherView.containsMsg(msg)) {
                                currentMsgView = otherView;
                                break;
                            }
                        }
                        if (currentMsgView != null) {
                            // 如果找到包含本条msg的view
                            if (view != currentMsgView) {
                                // 这个view不是自己，跳过此条消息
                                continue;
                            } else {
                                // 如果这个view是自己
                                foundMsg = view.playMsg(msg);
                            }
                        } else {
                            // 如果没找到包含本条msg的view
                            foundMsg = view.playMsg(msg);
                        }
                    }
                }
            }
        }

        if (!foundMsg) {
            boolean isAllViewFree = true;
            for (ILiveGiftView view : mListPlayView) {
                if (view.isPlaying()) {
                    isAllViewFree = false;
                    break;
                }
            }
            if (isAllViewFree) {
                mLooper.stop();
            }
        }
    }

    private void addMsg(ILiveGiftMsg newMsg) {
        if (newMsg != null) {
            if (!newMsg.canPlay()) {
                return;
            }

            Iterator<ILiveGiftMsg> it = mListMsg.iterator();
            while (it.hasNext()) {
                ILiveGiftMsg oldMsg = it.next();
                if (oldMsg.isTaked()) {
                    it.remove();
                }
            }
            mListMsg.add(newMsg);
            if (!mLooper.isRunning()) {
                mLooper.start(DURATION_LOOPER, new Runnable() {
                    @Override
                    public void run() {
                        looperWork();
                    }
                });
            }
        }
    }

    @Override
    public void onMsgGift(CustomMsgGift msg) {
        super.onMsgGift(msg);
        addMsg(msg);
    }

    @Override
    public void onMsgAuction(MsgModel msg) {
        super.onMsgAuction(msg);
        if (CustomMsgType.MSG_AUCTION_OFFER == msg.getCustomMsgType()) {
            addMsg(msg.getCustomMsgAuctionOffer());
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mLooper.stop();
    }

}
