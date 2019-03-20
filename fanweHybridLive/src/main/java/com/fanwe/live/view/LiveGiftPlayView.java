package com.fanwe.live.view;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.auction.model.custommsg.CustomMsgAuctionOffer;
import com.fanwe.library.animator.SDAnim;
import com.fanwe.library.animator.SDAnimSet;
import com.fanwe.library.animator.listener.SDAnimatorListener;
import com.fanwe.library.model.SDDelayRunnable;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.appview.ILiveGiftView;
import com.fanwe.live.dialog.LiveUserInfoDialog;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.custommsg.CustomMsgGift;
import com.fanwe.live.model.custommsg.ILiveGiftMsg;
import com.fanwe.live.utils.GlideUtil;

public class LiveGiftPlayView extends LinearLayout implements ILiveGiftView<ILiveGiftMsg> {
    private static final long DURATION_IN = 200;
    private static final long DURATION_OUT = 150;
    private static final long DURATION_DELAY = 2000;
    private static final long DURATION_PER_NUMBER = 300;

    private ImageView iv_head_image;
    private TextView tv_nickname;
    private TextView tv_content;
    private ImageView iv_gift;

    private View view_gift_number;
    private TextView tv_gift_number;

    /**
     * 是否正在播放中
     */
    private boolean mIsPlaying = false;
    /**
     * 是否正在播放数字
     */
    private boolean mIsPlayingNumber = false;
    /**
     * 是否正在延迟
     */
    private boolean mIsPlayingDelay = false;

    private SDAnim mAnimatorIn;
    private SDAnim mAnimatorNumber;
    private SDAnim mAnimatorOut;

    private ILiveGiftMsg mMsg;
    /**
     * 礼物显示的数字
     */
    private int mShowNumber;

    public LiveGiftPlayView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public LiveGiftPlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LiveGiftPlayView(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_live_gift_play, this, true);

        iv_head_image = (ImageView) findViewById(R.id.iv_head_image);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        tv_content = (TextView) findViewById(R.id.tv_content);
        iv_gift = (ImageView) findViewById(R.id.iv_gift);

        view_gift_number = findViewById(R.id.ll_gift_number);
        tv_gift_number = (TextView) findViewById(R.id.tv_gift_number);

        iv_head_image.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                clickHeadImage();
            }
        });

        SDViewUtil.setInvisible(this);

    }

    protected void clickHeadImage() {
        LiveUserInfoDialog dialog = new LiveUserInfoDialog((Activity) getContext(), mMsg.getMsgSender().getUser_id());
        dialog.show();
    }

    @Override
    public void bindData(ILiveGiftMsg msg) {
        if (msg != null) {
            if (msg instanceof CustomMsgGift) {
                CustomMsgGift customMsgGift = (CustomMsgGift) msg;
                UserModel sender = customMsgGift.getSender();
                if (sender != null) {
                    SDViewBinder.setTextView(tv_nickname, sender.getNick_name());
                    GlideUtil.loadHeadImage(sender.getHead_image()).into(iv_head_image);
                    SDViewBinder.setTextView(tv_content, customMsgGift.getDesc());
                    GlideUtil.load(customMsgGift.getIcon()).into(iv_gift);
                }
            } else if (msg instanceof CustomMsgAuctionOffer) {
                CustomMsgAuctionOffer customMsgAuctionOffer = (CustomMsgAuctionOffer) msg;
                UserModel sender = customMsgAuctionOffer.getUser();
                if (sender != null) {
                    SDViewBinder.setTextView(tv_nickname, sender.getNick_name());
                    GlideUtil.loadHeadImage(sender.getHead_image()).into(iv_head_image);
                    SDViewBinder.setTextView(tv_content, customMsgAuctionOffer.getGif_dec());
                    iv_gift.setImageResource(R.drawable.ic_auction_hammer_offer);
                }
            }
        }
    }

    @Override
    public ILiveGiftMsg getMsg() {
        return mMsg;
    }

    @Override
    public boolean containsMsg(ILiveGiftMsg msg) {
        return msg.equals(this.mMsg);
    }

    @Override
    public boolean isPlaying() {
        return mIsPlaying;
    }

    /**
     * 是否可以播放新的消息，(空闲，或者处于延迟状态)
     *
     * @return
     */
    @Override
    public boolean canPlay() {
        return (!mIsPlaying || (mIsPlaying && mIsPlayingDelay));
    }

    @Override
    public boolean playMsg(ILiveGiftMsg msg) {
        boolean play = false;
        if (mIsPlaying) {
            if (mIsPlayingDelay) {
                if (this.mMsg.equals(msg)) {
//                    if (msg.getShowNum() > mShowNumber) {
                        setMsg(msg,0);
                        playNumber();
                        play = true;
//                    }
                }
            }
        } else {
            setMsg(msg,1);
            playIn();
            play = true;
        }
        return play;
    }

    @Override
    public void setMsg(ILiveGiftMsg msg,int type) {
        msg.setTaked(true);
        bindData(msg);
        this.mMsg = msg;
        if(type==0){
            mShowNumber += msg.getShowNum();
        }else if(type==1){
            mShowNumber = msg.getShowNum();
        }
    }

    /**
     * 开始动画
     */
    @Override
    public void playIn() {
        if (mIsPlaying) {
            return;
        }

        mIsPlaying = true;
        if (mAnimatorIn == null) {
            mAnimatorIn = SDAnim.from(this)
                    .translationX(-SDViewUtil.getWidth(this), 0).setDuration(DURATION_IN).setAccelerate()
                    .addListener(new SDAnimatorListener() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            playNumber();
                        }
                    });
        }
        mAnimatorIn.start();
    }

    /**
     * 播放数字
     */
    @Override
    public void playNumber() {
        if (mIsPlayingNumber) {
            return;
        }

        mIsPlayingNumber = true;
        stopPlayOutDelayRunnable();
        if (mAnimatorNumber == null) {
            float[] values = new float[]{2.0f, 1.0f, 0.2f, 0.6f, 1.0f, 1.2f, 1.0f};

            mAnimatorNumber = SDAnimSet.from(view_gift_number)
                    .scaleX(values).setDuration(DURATION_PER_NUMBER).setDecelerate()
                    .withClone().scaleY(values)
                    .addListener(new SDAnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            updateNumber();
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                            super.onAnimationRepeat(animation);
                            updateNumber();
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            // 数字播放完毕，开启延迟
                            mIsPlayingNumber = false;
                            startPlayOutDelayRunnable();
                        }
                    });
        }
        mAnimatorNumber.start();
    }

    private void updateNumber() {
        tv_gift_number.setText("X" + mShowNumber);
    }

    private void startPlayOutDelayRunnable() {
        mPlayOutDelayRunnable.runDelay(DURATION_DELAY);
        mIsPlayingDelay = true;
    }

    private void stopPlayOutDelayRunnable() {
        mPlayOutDelayRunnable.removeDelay();
        mIsPlayingDelay = false;
    }

    private SDDelayRunnable mPlayOutDelayRunnable = new SDDelayRunnable() {

        @Override
        public void run() {
            mIsPlayingDelay = false;
            playOut();
        }
    };

    /**
     * 退出动画
     */
    @Override
    public void playOut() {
        if (mAnimatorOut == null) {
            mAnimatorOut = SDAnimSet.from(this)
                    .translationY(0, -SDViewUtil.getHeight(this)).setDuration(DURATION_OUT).setAccelerate()
                    .with().alpha(1.0f, 0).setDuration(DURATION_OUT).setAccelerate()
                    .addListener(new SDAnimatorListener() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            reset();
                        }
                    });
        }
        mAnimatorOut.start();
    }

    private void reset() {
        SDViewUtil.setInvisible(this);
        SDViewUtil.resetView(this);
        mIsPlaying = false;
    }

    @Override
    public void stopAnimator() {
        if (mAnimatorIn != null) {
            mAnimatorIn.cancel();
        }
        if (mAnimatorNumber != null) {
            mAnimatorNumber.cancel();
        }
        if (mAnimatorOut != null) {
            mAnimatorOut.cancel();
        }
        stopPlayOutDelayRunnable();
        reset();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimator();
    }
}
