package com.fanwe.live.appview.animator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
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
 * 求婚
 */
public class GiftAnimatorMarry extends GiftAnimatorView {
    private ImageView imgBalloon;
    private ImageView imgBalloonFly;
    private ImageView imgLight;
    private ImageView imgLovers;
    private ImageView imgStage;
    private ImageView imgStar;

    public GiftAnimatorMarry(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public GiftAnimatorMarry(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GiftAnimatorMarry(Context context) {
        super(context);
    }

    @Override
    protected int onCreateContentView() {
        return R.layout.marry;
    }

    @Override
    protected void onBaseInit() {
        super.onBaseInit();
        imgBalloon = find(R.id.img_marry_balloon);
        imgBalloonFly = find(R.id.img_marry_balloon_fly);
        imgLight = find(R.id.img_marry_light);
        imgLovers = find(R.id.img_marry_lovers);
        imgStage = find(R.id.img_marry_stage);
        imgStar = find(R.id.img_marry_star);
    }

    @Override
    protected void createAnimator() {
        int loversX1 = getXRightOut(imgLovers);
        int loversX2 = getXCenterCenter(imgLovers);

        int starY1 = getYTopOut(imgStar);
        int balloonY1 = getYBottomOut(imgBalloon);

        SDAnimSet animSet = SDAnimSet.from(imgStar)
                .moveToY(starY1, imgStar.getY()).setDuration(2000)
                .addListener(new SDAnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        notifyAnimationStart(animation);
                    }
                })
                .next(imgStar).alpha(1f, 0f, 1f).setDuration(1500).setRepeatCount(ValueAnimator.INFINITE)
                .withClone(imgBalloon).moveToY(balloonY1, imgBalloon.getY()).setRepeatCount(0)
                .next(imgBalloonFly).moveToY(imgBalloonFly.getY(), starY1).setRepeatCount(ValueAnimator.INFINITE).setDuration(2500)
                .withClone(imgLight).scaleX(1.0f, 1.4f).setDuration(2000)
                .withClone(imgLight).scaleY(1.0f, 1.4f)
                .withClone(imgLight).alpha(1.0f, 0.4f, 1.0f).setRepeatCount(1).delay(1000)
                .withClone(imgStage).moveToY(balloonY1, imgStage.getY()).setRepeatCount(0)
                .withClone(imgLovers).moveToX(loversX1, loversX2)
                .addListener(new SDAnimatorListener() {
                    @Override
                    public void onAnimationEnd(final Animator animation) {
                        super.onAnimationEnd(animation);
                        final Handler handler = new Handler() {
                            public void handleMessage(Message msg) {
                                if (msg.what == 0x521) {
                                    notifyAnimationEnd(animation);
                                }
                            }
                        };
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            public void run() {
                                Message msg = new Message();
                                msg.what = 0x521;
                                handler.sendMessage(msg);
                            }
                        }, 1500);

                    }
                });

        setAnimatorSet(animSet.getSet());
    }

    @Override
    protected void resetView() {

    }
}
