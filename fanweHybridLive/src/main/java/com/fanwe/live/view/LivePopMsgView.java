package com.fanwe.live.view;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.animator.SDAnim;
import com.fanwe.library.animator.listener.SDAnimatorListener;
import com.fanwe.library.model.SDDelayRunnable;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.dialog.LiveUserInfoDialog;
import com.fanwe.live.event.SwitchRoom;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.custommsg.CustomMsgPopMsg;
import com.fanwe.live.utils.GlideUtil;

import de.greenrobot.event.EventBus;

public class LivePopMsgView extends LinearLayout
{

    private static final long DURATION_TRANSLATION = 8 * 1000;
    /**
     * view在屏幕外的偏移量，为了让平移看上去更顺滑
     */
    private static final int DISTANCE_OFFSET = SDViewUtil.dp2px(30);

    private ImageView iv_head_image;
    private TextView tv_nickname;
    private TextView tv_content;
    private LinearLayout ll_full;
    private SDAnim mAnimator;
    private boolean mIsPlaying;
    private CustomMsgPopMsg mMsg;

    public LivePopMsgView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public LivePopMsgView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LivePopMsgView(Context context)
    {
        super(context);
        init();
    }

    private void init()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.view_live_pop_msg, this, true);

        iv_head_image = (ImageView) findViewById(R.id.iv_head_image);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        tv_content = (TextView) findViewById(R.id.tv_content);
        ll_full= (LinearLayout) findViewById(R.id.ll_full);
        iv_head_image.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                clickHeadImage();
            }
        });
        ll_full.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new SwitchRoom(Integer.parseInt(mMsg.getRoom_id())));
            }
        });
        SDViewUtil.setInvisible(this);

    }

    protected void clickHeadImage()
    {
        UserModel user = mMsg.getSender();
        LiveUserInfoDialog dialog = new LiveUserInfoDialog((Activity) getContext(), user.getUser_id());
        dialog.showCenter();
    }

    public boolean canPlay()
    {
        return !mIsPlaying;
    }

    private void setMsg(CustomMsgPopMsg msg)
    {
        this.mMsg = msg;
    }

    public void playMsg(CustomMsgPopMsg newMsg)
    {
        if (newMsg != null)
        {
            if (canPlay())
            {
                mIsPlaying = true;
                setMsg(newMsg);

                bindData();
                mPlayInRunnable.runDelay(500);
            }
        }
    }

    private void bindData()
    {
        UserModel sender = mMsg.getSender();
        if (sender != null)
        {
            SDViewBinder.setTextView(tv_nickname, sender.getNick_name());
            GlideUtil.loadHeadImage(sender.getHead_image()).into(iv_head_image);
            SDViewBinder.setTextView(tv_content, mMsg.getDesc());
        }
    }

    private SDDelayRunnable mPlayInRunnable = new SDDelayRunnable()
    {
        @Override
        public void run()
        {
            playIn();
        }
    };

    private void playIn()
    {
        int x1 = SDViewUtil.getScreenWidth() + DISTANCE_OFFSET;
        int x2 = -SDViewUtil.getWidth(this) - DISTANCE_OFFSET;

        mAnimator = SDAnim.from(this)
                .moveToX(x1, x2).setDuration(DURATION_TRANSLATION).setLinear()
                .addListener(new SDAnimatorListener()
                {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        super.onAnimationEnd(animation);
                        reset();
                    }
                });
        mAnimator.start();
    }

    private void reset()
    {
        SDViewUtil.setInvisible(this);
        SDViewUtil.resetView(this);
        mIsPlaying = false;
    }

    @Override
    protected void onDetachedFromWindow()
    {
        mPlayInRunnable.removeDelay();
        stopAnimator();
        super.onDetachedFromWindow();
    }

    public void stopAnimator()
    {
        if (mAnimator != null)
        {
            mAnimator.cancel();
        }
    }

}
