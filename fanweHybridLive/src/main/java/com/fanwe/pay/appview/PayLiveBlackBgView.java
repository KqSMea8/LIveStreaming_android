package com.fanwe.pay.appview;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.appview.LiveVideoView;
import com.fanwe.live.control.LivePlayerSDK;
import com.tencent.rtmp.TXLivePlayer;

/**
 * Created by Administrator on 2016/12/7.
 */

public class PayLiveBlackBgView extends BaseAppView
{
    public static final String SCENE_TEXT_HINT = "该直播按场收费,您还能预览";
    public static final String TIME_TEXT_HINT = "1分钟内重复进入,不重复扣费,请能正常预览视频后,点击进入,以免扣费后不能正常进入,您还能预览";
    private int proview_play_time = 15000;//倒计时毫秒
    private int is_only_play_voice = 0;//是否只播放声音/默认为0

    private boolean isStarted = false;//防止onPlayBegin重新调用
    private CountDownTimer countDownTimer;
    private LinearLayout ll_view_video;
    private LiveVideoView view_video;
    private TextView tv_time;
    private int pay_type = 0;//付费类型 0 按时 1 按场


    public void setPay_type(int pay_type)
    {
        this.pay_type = pay_type;
    }

    /**
     * \
     * 设置毫秒
     *
     * @param proview_play_time
     */
    public void setProview_play_time(int proview_play_time)
    {
        this.proview_play_time = proview_play_time;
    }

    public void setIs_only_play_voice(int is_only_play_voice)
    {
        this.is_only_play_voice = is_only_play_voice;
    }

    public PayLiveBlackBgView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public PayLiveBlackBgView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public PayLiveBlackBgView(Context context)
    {
        super(context);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.view_pay_live_black_bg);
        register();
    }

    private void register()
    {
        ll_view_video = find(R.id.ll_view_video);
        view_video = find(R.id.view_video);
        tv_time = find(R.id.tv_time);
    }

    public void startPlayer(String preview_play_url)
    {
        SDViewUtil.setGone(ll_view_video);
        if (!TextUtils.isEmpty(preview_play_url))
        {
            SDViewUtil.setVisible(ll_view_video);
            if (is_only_play_voice == 1)
            {
                SDViewUtil.setGone(ll_view_video);
            }

            view_video.getPlayer().setUrl(preview_play_url);
            if (preview_play_url.startsWith("rtmp://"))
            {
                view_video.getPlayer().setPlayType(TXLivePlayer.PLAY_TYPE_LIVE_RTMP);
            } else if ((preview_play_url.startsWith("http://") || preview_play_url.startsWith("https://")) && preview_play_url.endsWith(".flv"))
            {
                view_video.getPlayer().setPlayType(TXLivePlayer.PLAY_TYPE_LIVE_FLV);
            }
            view_video.getPlayer().setPlayerListener(listener);
            view_video.getPlayer().startPlay();
        }
    }


    /**
     * 开始倒计时
     *
     * @param time 毫秒
     */
    private void startCountDown(long time)
    {
        stopCountDown();
        if (time > 0)
        {
            countDownTimer = new CountDownTimer(time, 1000)
            {
                @Override
                public void onTick(long leftTime)
                {
                    String time;
                    if (pay_type == 1)
                    {
                        time = SCENE_TEXT_HINT + "倒计时:" + leftTime / 1000 + "秒";
                    } else
                    {
                        time = TIME_TEXT_HINT + "倒计时:" + leftTime / 1000 + "秒";
                    }

                    tv_time.setText(time);
                }

                @Override
                public void onFinish()
                {
                    String time;
                    if (pay_type == 1)
                    {
                        time = SCENE_TEXT_HINT + "倒计时:0秒";
                    } else
                    {
                        time = TIME_TEXT_HINT + "倒计时:0秒";
                    }

                    tv_time.setText(time);
                    destroyVideo();
                }
            };
            countDownTimer.start();
        }
    }

    /**
     * 停止倒计时
     */
    public void stopCountDown()
    {
        if (countDownTimer != null)
        {
            countDownTimer.cancel();
        }
    }

    private LivePlayerSDK.PlayerListener listener = new LivePlayerSDK.PlayerListener()
    {
        @Override
        public void onPlayEvent(int event, Bundle param)
        {

        }

        @Override
        public void onPlayBegin(int event, Bundle param)
        {
            LogUtil.e("PayLiveBlackBgView======onPlayBegin");
            if (!isStarted)
            {
                isStarted = true;
                startCountDown(proview_play_time);
            }
        }

        @Override
        public void onPlayRecvFirstFrame(int event, Bundle param)
        {

        }

        @Override
        public void onPlayProgress(int event, Bundle param, int total, int progress)
        {

        }

        @Override
        public void onPlayEnd(int event, Bundle param)
        {

        }

        @Override
        public void onPlayLoading(int event, Bundle param)
        {

        }

        @Override
        public void onNetStatus(Bundle param)
        {

        }
    };

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        LogUtil.e("PayLiveBlackBgView=====onDetachedFromWindow");
        destroyVideo();
    }

    public void destroyVideo()
    {
        stopCountDown();
        view_video.getPlayer().onDestroy();
        removeView(ll_view_video);
        SDViewUtil.setGone(tv_time);
    }
}
