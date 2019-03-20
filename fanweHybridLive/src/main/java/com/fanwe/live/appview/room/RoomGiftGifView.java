package com.fanwe.live.appview.room;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.LiveConstant;
import com.fanwe.live.R;
import com.fanwe.live.appview.animator.GifAnimatorCar1;
import com.fanwe.live.appview.animator.GiftAnimatorCake;
import com.fanwe.live.appview.animator.GiftAnimatorCar2;
import com.fanwe.live.appview.animator.GiftAnimatorCastle;
import com.fanwe.live.appview.animator.GiftAnimatorForever;
import com.fanwe.live.appview.animator.GiftAnimatorMarry;
import com.fanwe.live.appview.animator.GiftAnimatorPlane1;
import com.fanwe.live.appview.animator.GiftAnimatorPlane2;
import com.fanwe.live.appview.animator.GiftAnimatorRocket1;
import com.fanwe.live.appview.animator.GiftAnimatorStage;
import com.fanwe.live.appview.animator.GiftAnimatorView;
import com.fanwe.live.gif.GifConfigModel;
import com.fanwe.live.model.GiftAward;
import com.fanwe.live.model.custommsg.CustomMsgGift;
import com.fanwe.live.model.custommsg.CustomMsgOpenShouhu;
import com.fanwe.live.view.LiveGiftGifPlayView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * gif礼物
 */
public class RoomGiftGifView extends RoomLooperMainView<CustomMsgGift> {
    public RoomGiftGifView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RoomGiftGifView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoomGiftGifView(Context context) {
        super(context);
    }

    private static final long DURATION_LOOPER = 500;
    private int type = 0;
    private LiveGiftGifPlayView view_gif0;
    private LiveGiftGifPlayView view_gif1;
    private LiveGiftGifPlayView view_gif2;
    private LiveGiftGifPlayView view_gif3;
    private LiveGiftGifPlayView view_gif4;
    private GiftAnimatorView animatorView;

    public void setType(int type) {
        this.type = type;
    }

    private List<LiveGiftGifPlayView> listView;
    private List<LiveGiftGifPlayView> listViewTarget;
    private FrameLayout fl_animatior_gift_root;

    private SendBigGiftAnimationCallback mCallback;


    @Override
    protected int onCreateContentView() {
        return R.layout.view_room_gift_gif;
    }

    @Override
    protected void onBaseInit() {
        super.onBaseInit();
        view_gif0 = find(R.id.view_gif0);
        view_gif1 = find(R.id.view_gif1);
        view_gif2 = find(R.id.view_gif2);
        view_gif3 = find(R.id.view_gif3);
        view_gif4 = find(R.id.view_gif4);
        fl_animatior_gift_root = find(R.id.fl_animatior_gift_root);
        listView = new ArrayList<>();
        listViewTarget = new ArrayList<>();
        listView.add(view_gif0);
        listView.add(view_gif1);
        listView.add(view_gif2);
        listView.add(view_gif3);
        listView.add(view_gif4);
    }

    public void setCallback(SendBigGiftAnimationCallback callback) {
        this.mCallback = callback;
    }

    private void playAnimator(CustomMsgGift msg) {
        if (msg != null) {
            String type = msg.getAnim_type();
            if (type != null) {
                GiftAnimatorView view = null;
                if (type.equalsIgnoreCase(LiveConstant.GiftAnimatorType.PLANE1)) {
                    view = new GiftAnimatorPlane1(getActivity());
                } else if (type.equalsIgnoreCase(LiveConstant.GiftAnimatorType.PLANE2)) {
                    view = new GiftAnimatorPlane2(getActivity());
                } else if (type.equalsIgnoreCase(LiveConstant.GiftAnimatorType.ROCKET1)) {
                    view = new GiftAnimatorRocket1(getActivity());
                } else if (type.equalsIgnoreCase(LiveConstant.GiftAnimatorType.FERRARI)) {
                    view = new GifAnimatorCar1(getActivity());
                } else if (type.equalsIgnoreCase(LiveConstant.GiftAnimatorType.LAMBORGHINI)) {
                    view = new GiftAnimatorCar2(getActivity());
                } else if (type.equalsIgnoreCase(LiveConstant.GiftAnimatorType.CAKE)) {
                    view = new GiftAnimatorCake(getActivity());
                } else if (type.equalsIgnoreCase(LiveConstant.GiftAnimatorType.CASTLE)) {
                    view = new GiftAnimatorCastle(getActivity());
                } else if (type.equalsIgnoreCase(LiveConstant.GiftAnimatorType.STAGE)) {
                    view = new GiftAnimatorStage(getActivity());
                } else if (type.equalsIgnoreCase(LiveConstant.GiftAnimatorType.WEDDING)) {
                    view = new GiftAnimatorMarry(getActivity());
                } else if (type.equalsIgnoreCase(LiveConstant.GiftAnimatorType.THREELIFETIMES)) {
                    view = new GiftAnimatorForever(getActivity());
                } else {
                    mCallback.addBigAnimation(msg);
                }

                if (view != null) {
                    view.setMsg(msg);
                }
                playAnimatorView(view);
            }
        }
    }

    private boolean hasAnimatorPlaying() {
        if (animatorView != null) {
            return animatorView.isPlaying();
        }
        return false;
    }

    private void playAnimatorView(GiftAnimatorView view) {
        if (view != null) {
            removeAnimatorView();
            this.animatorView = view;
            animatorView.setAnimatorListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    removeAnimatorView();
                }
            });
            SDViewUtil.addView(fl_animatior_gift_root, animatorView);
            animatorView.play();
        }
    }

    private void removeAnimatorView() {
        if (animatorView != null) {
            animatorView.removeSelf();
            animatorView = null;
        }
    }

    /**
     * 是否有view正在播放
     *
     * @return
     */
    private boolean hasGifPlaying() {
        for (LiveGiftGifPlayView view : listView) {
            if (view.isPlaying()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否所有View都处于空闲
     *
     * @return
     */
    private boolean isAllGifViewFree() {
        for (LiveGiftGifPlayView view : listView) {
            if (view.isBusy()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否所有的目标view都准备完毕
     *
     * @return
     */
    private boolean isAllTargetViewReady() {
        if (listViewTarget.isEmpty()) {
            return false;
        }
        for (LiveGiftGifPlayView view : listViewTarget) {
            if (view.isLoadingGif()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 找到需要播放的view，并初始化
     *
     * @param msg
     */
    private boolean findTargetView(CustomMsgGift msg) {
        List<GifConfigModel> listConfig = msg.getAnim_cfg();
        if (listConfig != null && !listConfig.isEmpty()) {
            listViewTarget.clear();
            int i = 0;
            for (GifConfigModel config : listConfig) {
                if (i < listView.size()) {
                    LiveGiftGifPlayView view = listView.get(i);
                    view.setConfig(config);
                    view.setMsg(msg);
                    listViewTarget.add(view);
                }
                i++;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void looperWork(LinkedList<CustomMsgGift> queue) {
        final CustomMsgGift msg = queue.peek();
        if (msg == null) {
            return;
        }
        if (hasGifPlaying() || hasAnimatorPlaying() ) {
            // 当前有gif或者动画在播放
            LogUtil.i("playing");
        } else {
            if (msg.isGif()) {
                //gif
                if (isAllGifViewFree()) {
                    // 所有view处于空闲
                    LogUtil.i("isAllGifViewFree");
                    boolean found = findTargetView(msg);
                    if (!found) {
                        LogUtil.i("gif config not found");
                        queue.poll();
                    }
                } else {
                    if (isAllTargetViewReady()) {
                        queue.poll();
                        for (LiveGiftGifPlayView view : listViewTarget) {
                            view.playGif();
                        }
                    } else {
                    }
                }
            } else if (msg.isAnimatior()) {
                //大型礼物动画
                queue.poll();
                playAnimator(msg);
            }
        }
    }

    @Override
    protected void startLooper(long period) {
        super.startLooper(DURATION_LOOPER);
    }

    @Override
    protected void offerModel(final CustomMsgGift model) {
        if (model != null) {
            if (model.isGif() || model.isAnimatior()) {
                super.offerModel(model);
            }
        }
    }

    @Override
    public void onMsgGift(final CustomMsgGift msg) {
        super.onMsgGift(msg);
        if (type == msg.getShow_type()) {
            offerModel(msg);
        }
        if (type == 3) {
            if (null != msg.getAward()) {
                if (1 == msg.getAward().getStatus()) {
                    for (GiftAward.AwardListBean bean : msg.getAward().getAward_list()) {
                        CustomMsgGift customMsgGift = new CustomMsgGift();
                        customMsgGift.setAnim_cfg(bean.getAnim_cfg());
                        customMsgGift.setIs_animated(bean.getIs_animated());
                        offerModel(customMsgGift);
                    }
                }
            }
        }
    }

    @Override
    public void onMsgLiveOpenShouhu(CustomMsgOpenShouhu msg) {
        super.onMsgLiveOpenShouhu(msg);
        if (type == 2) {
            CustomMsgGift customMsgGift = new CustomMsgGift();
            customMsgGift.setAnim_cfg(msg.getAnim_cfg());
            customMsgGift.setIs_animated(msg.getIs_animated());
            offerModel(customMsgGift);
        }
    }

    public void addShouhuAnimator(CustomMsgGift msg) {
        if (msg.getShow_type() == 2) {
            offerModel(msg);
        }
    }

    public interface SendBigGiftAnimationCallback {
        void addBigAnimation(CustomMsgGift msg);
    }
}
