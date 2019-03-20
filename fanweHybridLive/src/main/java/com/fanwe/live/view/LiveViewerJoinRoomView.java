package com.fanwe.live.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.dialog.LiveUserInfoDialog;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.custommsg.CustomMsgViewerJoin;
import com.fanwe.live.utils.GlideUtil;

public class LiveViewerJoinRoomView extends LinearLayout
{
    private static final long DURATION_IN = 1200;
    private static final long DURATION_OUT = 200;
    private static final long DURATION_DELAY = 2 * 1000;

    private LinearLayout ll_user,ll_join_bg;
    private ImageView iv_level,iv_shouhu;
    private TextView tv_nickname,tv_join,tv_shouhu;

    private AnimatorSet animatorSetIn;
    private AnimatorSet animatorSetOut;
    private boolean isPlaying;
    private CustomMsgViewerJoin msg;

    public LiveViewerJoinRoomView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public LiveViewerJoinRoomView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LiveViewerJoinRoomView(Context context)
    {
        super(context);
        init();
    }

    private void init()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.view_live_viewer_join_room, this, true);
        tv_shouhu=(TextView) findViewById(R.id.tv_shouhu);
        ll_user = (LinearLayout) findViewById(R.id.ll_user);
        iv_level = (ImageView) findViewById(R.id.iv_level);
        iv_shouhu = (ImageView) findViewById(R.id.iv_shouhu);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        tv_join = (TextView) findViewById(R.id.tv_join);
        ll_join_bg= (LinearLayout) findViewById(R.id.view_join_bg);
        ll_user.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (msg != null)
                {
                    LiveUserInfoDialog dialog = new LiveUserInfoDialog((Activity) getContext(), msg.getSender().getUser_id());
                    dialog.show();
                }
            }
        });

        SDViewUtil.setInvisible(this);
    }

    public CustomMsgViewerJoin getMsg()
    {
        return msg;
    }

    public boolean isPlaying()
    {
        return isPlaying;
    }

    public boolean canPlay()
    {
        return !isPlaying;
    }

    private void setMsg(CustomMsgViewerJoin msg)
    {
        this.msg = msg;
    }

    public void playMsg(CustomMsgViewerJoin newMsg)
    {
        if (newMsg != null)
        {
            if (canPlay())
            {
                isPlaying = true;
                setMsg(newMsg);
                SDHandlerManager.getMainHandler().post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        bindData();
                        playIn();
                    }
                });
            }
        }
    }

    private void bindData()
    {
        UserModel sender = msg.getSender();
        if (sender != null)
        {
            int resId = sender.getLevelImageResId();
            if (resId != 0)
            {
                iv_level.setImageResource(resId);
            }
            if(null!=msg.getGuard()){
                ll_join_bg.setBackgroundResource(R.drawable.bg_live_viewer_shouhu_join);
                SDViewBinder.setTextView(tv_nickname, sender.getNick_name());
                SDViewUtil.setGone(iv_level);
                GlideUtil.load(msg.getGuard().getGuard_animated().getIcon()).into(iv_shouhu);
                if(null!=msg.getGuard().getGuard_animated().getContent()){
                    SDViewBinder.setTextView(tv_join, msg.getGuard().getGuard_animated().getContent());
                }
                if(null!=msg.getGuard().getGuard_animated().getName()){
                    SDViewBinder.setTextView(tv_shouhu, msg.getGuard().getGuard_animated().getName());
                }
                SDViewUtil.setVisible(iv_shouhu);
                tv_nickname.setTextColor(getResources().getColor(R.color.live_username_shouhu));
            }else if(msg.getSender().getUser_level()>InitActModelDao.query().getJr_user_level()+10){
                ll_join_bg.setBackgroundResource(R.drawable.bg_live_viewer_super_join);
                SDViewBinder.setTextView(tv_nickname,sender.getNick_name());
                SDViewUtil.setGone(iv_shouhu);
                SDViewUtil.setVisible(iv_level);
                if(null!=msg.getMount()){
                    if(null!=msg.getMount().getDesc()){
                        SDViewBinder.setTextView(tv_join, msg.getMount().getDesc());
                    }
                    else{
                        SDViewBinder.setTextView(tv_join, "进来了");
                    }
                }else{
                    SDViewBinder.setTextView(tv_join, "进来了");
                }
                tv_shouhu.setText("金光一闪");
                tv_nickname.setTextColor(getResources().getColor(R.color.live_username_viewer));
            }else if(msg.getSender().isProUser()){
                ll_join_bg.setBackgroundResource(R.drawable.bg_live_viewer_high_join);
                SDViewBinder.setTextView(tv_nickname,sender.getNick_name());
                SDViewUtil.setGone(iv_shouhu);
                SDViewUtil.setVisible(iv_level);
                if(null!=msg.getMount()){
                    if(null!=msg.getMount().getDesc()){
                        SDViewBinder.setTextView(tv_join, msg.getMount().getDesc());
                    }
                    else{
                        SDViewBinder.setTextView(tv_join, "进来了");
                    }
                }else{
                    SDViewBinder.setTextView(tv_join, "进来了");
                }
                tv_shouhu.setText("金光一闪");
                tv_nickname.setTextColor(getResources().getColor(R.color.live_username_viewer));
            }
        }
    }

    private void playIn()
    {
        if (animatorSetIn == null)
        {
            ObjectAnimator inTranslationX = ObjectAnimator.ofFloat(this, "translationX", -SDViewUtil.getWidth(this), 0f);
            inTranslationX.setInterpolator(new DecelerateInterpolator());
            inTranslationX.setDuration(DURATION_IN);

            animatorSetIn = new AnimatorSet();
            animatorSetIn.playTogether(inTranslationX);
            animatorSetIn.addListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationStart(Animator animation)
                {
                    SDViewUtil.setVisible(LiveViewerJoinRoomView.this);
                }

                @Override
                public void onAnimationEnd(Animator animation)
                {
                    SDHandlerManager.getMainHandler().postDelayed(new Runnable()
                    {

                        @Override
                        public void run()
                        {
                            playOut();
                        }
                    }, DURATION_DELAY);
                }
            });
        }
        animatorSetIn.start();
    }

    private void playOut()
    {
        if (animatorSetOut == null)
        {
            ObjectAnimator outTranslationY = ObjectAnimator.ofFloat(this, "translationY", 0f, -SDViewUtil.getHeight(this));
            outTranslationY.setInterpolator(new DecelerateInterpolator());

            ObjectAnimator outAlpha = ObjectAnimator.ofFloat(this, "alpha", 1.0f, 0.0f);
            outAlpha.setInterpolator(new DecelerateInterpolator());

            animatorSetOut = new AnimatorSet();
            animatorSetOut.playTogether(outTranslationY, outAlpha);
            animatorSetOut.setDuration(DURATION_OUT);
            animatorSetOut.addListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    SDViewUtil.setInvisible(LiveViewerJoinRoomView.this);
                    SDViewUtil.resetView(LiveViewerJoinRoomView.this);
                    isPlaying = false;
                }
            });
        }
        animatorSetOut.start();
    }

    @Override
    protected void onDetachedFromWindow()
    {
        stopAnimator(animatorSetIn);
        super.onDetachedFromWindow();
    }

    private void stopAnimator(Animator animator)
    {
        if (animator != null)
        {
            animator.cancel();
            animator.removeAllListeners();
        }
    }
}
