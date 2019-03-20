package com.fanwe.live.appview.animator;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.fanwe.library.animator.SDAnimSet;
import com.fanwe.library.animator.listener.SDAnimatorListener;
import com.fanwe.live.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 三生三世
 */
public class GiftAnimatorForever extends GiftAnimatorView
{
   private ImageView imgForeverPetal1;
   private ImageView imgForeverPetal2;
   private ImageView imgForeverPetal3;
   private ImageView imgForever;
   private ImageView imgForeverLove;
   private ImageView imgForeverMountain;
   private ImageView imgForeverPeach;

    public GiftAnimatorForever(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public GiftAnimatorForever(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public GiftAnimatorForever(Context context)
    {
        super(context);
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.forever;
    }

    @Override
    protected void onBaseInit()
    {
        super.onBaseInit();
        imgForeverPetal1 = find(R.id.img_forever_petal1);
        imgForeverPetal2 = find(R.id.img_forever_petal2);
        imgForeverPetal3 = find(R.id.img_forever_petal3);
        imgForever = find(R.id.img_forever);
        imgForeverLove = find(R.id.img_forever_love);
        imgForeverMountain = find(R.id.img_forever_mountain);
        imgForeverPeach = find(R.id.img_forever_peach);

    }

    @Override
    protected void createAnimator(){
    int petalX1 = getXRightOut(imgForeverPetal1);
    int petalX2 = getXLeftOut(imgForeverPetal1);

    int mountainY3 = getYBottomOut(imgForeverMountain);

        SDAnimSet animSet = SDAnimSet.from(imgForeverMountain)
                .moveToY(mountainY3,imgForeverMountain.getY()+60).setDuration(2000)
                .addListener(new SDAnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        notifyAnimationStart(animation);
                    }
                })
                .next(imgForeverPeach).moveToY(mountainY3,imgForeverPeach.getY()+60).setDuration(2000)
                .next(imgForever).addListener(new SDAnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        ClipDrawable foreverClip = (ClipDrawable) imgForever.getDrawable();
                        setClipAnim(foreverClip);
                    }
                })
                .next(imgForeverLove).addListener(new SDAnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        ClipDrawable foreverLoveClip = (ClipDrawable) imgForeverLove.getDrawable();
                        setClipLoveAnim(foreverLoveClip);
                    }
                }).delay(3000)
                .next(imgForeverPetal1).moveToX(petalX1,petalX2).setDuration(2000)
                .next(imgForeverPetal2).moveToX(petalX1,petalX2).setDuration(2000)
                .next(imgForeverPetal3).moveToX(petalX1,petalX2).setDuration(2000)
                .addListener(new SDAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        notifyAnimationEnd(animation);
                    }
                });

        setAnimatorSet(animSet.getSet());
    }

    @Override
    protected void resetView()
    {

    }
    private void setClipAnim(final ClipDrawable drawable) {
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 0x1233) {
                    //修改ClipDrawable的level值
                    drawable.setLevel(drawable.getLevel() + 200);
                }
            }
        };
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Message msg = new Message();
                msg.what = 0x1233;
                //发送消息,通知应用修改ClipDrawable对象的level值
                handler.sendMessage(msg);
                //取消定时器
                if (drawable.getLevel() >= 10000) {
                    timer.cancel();
                }
            }
        }, 0, 50);
    }
    private void setClipLoveAnim(final ClipDrawable drawable) {
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 0x1234) {
                    //修改ClipDrawable的level值
                    drawable.setLevel(drawable.getLevel() + 200);
                }
            }
        };
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Message msg = new Message();
                msg.what = 0x1234;
                //发送消息,通知应用修改ClipDrawable对象的level值
                handler.sendMessage(msg);
                //取消定时器
                if (drawable.getLevel() >= 10000) {
                    timer.cancel();
                }
            }
        }, 3000, 50);
    }
}
