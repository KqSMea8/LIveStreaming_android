package com.fanwe.shortvideo.appview.mian;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.LiveConstant;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;
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
import com.fanwe.live.appview.room.RoomLooperMainView;
import com.fanwe.live.gif.GifConfigModel;
import com.fanwe.live.model.custommsg.CustomMsgGift;
import com.fanwe.live.view.LiveGiftGifPlayView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * gif礼物
 */
public class VideoGiftGifView extends BaseAppView {
    public VideoGiftGifView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public VideoGiftGifView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VideoGiftGifView(Context context) {
        this(context,null);
    }

    private static final long DURATION_LOOPER = 1000;

    private LiveGiftGifPlayView view_gif0;
    private LiveGiftGifPlayView view_gif1;
    private LiveGiftGifPlayView view_gif2;
    private LiveGiftGifPlayView view_gif3;
    private LiveGiftGifPlayView view_gif4;

    private List<LiveGiftGifPlayView> listView;
    private List<LiveGiftGifPlayView> listViewTarget;

    private FrameLayout fl_animatior_gift_root;
    private GiftAnimatorView animatorView;
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
     * 找到需要播放的view，并初始化
     *
     * @param msg
     */
    private void findTargetView(CustomMsgGift msg) {
        List<GifConfigModel> listConfig = msg.getAnim_cfg();
        if (listConfig != null && !listConfig.isEmpty()) {
            int i = 0;
            for (GifConfigModel config : listConfig) {
                if (i < listView.size()) {
                    LiveGiftGifPlayView view = listView.get(i);
                    view.setConfig(config);
                    view.setMsg(msg);
                    view.playGif();
                }
                i++;
            }
        }
    }

    public void playGiftAnimation(CustomMsgGift msg) {
        if (msg == null) {
            return;
        }

        if (msg.isGif()) {
            //gif
            List<GifConfigModel> listConfig = msg.getAnim_cfg();
            if (listConfig != null && !listConfig.isEmpty()) {
                int i = 0;
                for (GifConfigModel config : listConfig) {
                    if (i < listView.size()) {
                        LiveGiftGifPlayView view = listView.get(i);
                        view.setConfig(config);
                        view.setIsVideo(true);
                        view.setMsg(msg);
//                        view.playGif();
                    }
                    i++;
                }
            }
        } else if (msg.isAnimatior()) {
            //大型礼物动画
            playAnimator(msg);
        }
    }


    public interface SendBigGiftAnimationCallback {
        void addBigAnimation(CustomMsgGift msg);
    }
}
