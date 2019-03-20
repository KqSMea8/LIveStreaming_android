package com.fanwe.live.appview.room;

import android.content.Context;
import android.util.AttributeSet;
import com.fanwe.live.R;
import com.fanwe.live.activity.room.LiveLayoutActivity;
import com.fanwe.live.model.UserJoinGuard;
import com.fanwe.live.model.UserJoinMount;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.custommsg.CustomMsgGift;
import com.fanwe.live.model.custommsg.CustomMsgViewerJoin;
import com.fanwe.live.model.custommsg.CustomMsgViewerQuit;
import com.fanwe.live.view.LiveViewerJoinRoomView;

import java.util.Iterator;
import java.util.LinkedList;

public class RoomViewerJoinRoomView extends RoomLooperMainView<CustomMsgViewerJoin> {
    public RoomViewerJoinRoomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RoomViewerJoinRoomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoomViewerJoinRoomView(Context context) {
        super(context);
    }

    private static final long DURATION_LOOPER = 1000;

    private LiveViewerJoinRoomView view_viewer_join;

    @Override
    protected int onCreateContentView() {
        return R.layout.view_room_viewer_join_room;
    }

    @Override
    protected void onBaseInit() {
        super.onBaseInit();

        view_viewer_join = find(R.id.view_viewer_join);
    }

    @Override
    public void onMsgViewerJoin(CustomMsgViewerJoin msg) {
        super.onMsgViewerJoin(msg);
        if (!queue.contains(msg)) {
            if (msg.equals(view_viewer_join.getMsg()) && view_viewer_join.isPlaying()) {
            } else {
                if (null != msg.getGuard()) {
                    offerModel(msg);
                    ((LiveLayoutActivity) getContext()).playShouhu(bindCustomMsgGift(msg.getGuard(), msg.getSender()));
                }else if(null!=msg.getMount()){
                    offerModel(msg);
                    ((LiveLayoutActivity) getContext()).playShouhu(bindCustomMsgGift(msg.getMount(), msg.getSender()));
                }
                else if(msg.getSender().isProUser()){
                    offerModel(msg);
                }
            }
        }
    }
    private CustomMsgGift bindCustomMsgGift(UserJoinMount mount, UserModel userModel) {
        CustomMsgGift customMsgGift = new CustomMsgGift();
        customMsgGift.setShow_type(2);
        customMsgGift.setAnim_cfg(mount.getAnim_cfg());
        customMsgGift.setAnim_type(mount.getAnim_type());
        customMsgGift.setIcon(mount.getIcon());
        customMsgGift.setIs_animated(Integer.parseInt(mount.getIs_animated()));
        customMsgGift.setProp_id(Integer.parseInt(mount.getId()));
        customMsgGift.setTo_user_id(mount.getSender().getUser_id() + "");
        customMsgGift.setSender(userModel);
        return customMsgGift;
    }
    private CustomMsgGift bindCustomMsgGift(UserJoinGuard guard, UserModel userModel) {
        CustomMsgGift customMsgGift = new CustomMsgGift();
        customMsgGift.setShow_type(2);
        customMsgGift.setAnim_cfg(guard.getGuard_animated().getAnim_cfg());
        customMsgGift.setAnim_type(guard.getGuard_animated().getAnim_type());
        customMsgGift.setIcon(guard.getGuard_animated().getIcon());
        customMsgGift.setIs_animated(Integer.parseInt(guard.getGuard_animated().getIs_animated()));
        customMsgGift.setProp_id(Integer.parseInt(guard.getGuard_animated().getId()));
        customMsgGift.setTo_user_id(guard.getSender().getUser_id() + "");
        customMsgGift.setSender(userModel);
        return customMsgGift;
    }

    @Override
    public void onMsgViewerQuit(CustomMsgViewerQuit msg) {
        super.onMsgViewerQuit(msg);

        if (msg.getSender().isProUser()) {
            Iterator<CustomMsgViewerJoin> it = queue.iterator();
            while (it.hasNext()) {
                CustomMsgViewerJoin item = it.next();
                if (msg.getSender().equals(item.getSender())) {
                    it.remove();
                }
            }
        }
    }

    @Override
    protected void startLooper(long period) {
        super.startLooper(DURATION_LOOPER);
    }

    @Override
    protected void looperWork(LinkedList<CustomMsgViewerJoin> queue) {
        if (view_viewer_join.canPlay()) {
            view_viewer_join.playMsg(queue.poll());
        }
    }
}
