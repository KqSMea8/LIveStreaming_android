package com.fanwe.live.appview.animator;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.fanwe.library.animator.SDAnimSet;
import com.fanwe.library.animator.listener.SDAnimatorListener;
import com.fanwe.live.R;

/**
 * 跑车
 */
public class GiftAnimatorStage extends GiftAnimatorView {
    private ImageView imgCar;
    private ImageView imgCarStage;
    private ImageView imgCarLight;
    private ImageView imgCarColorBar;

    public GiftAnimatorStage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public GiftAnimatorStage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GiftAnimatorStage(Context context) {
        super(context);
    }

    @Override
    protected int onCreateContentView() {
        return R.layout.car;
    }

    @Override
    protected void onBaseInit() {
        super.onBaseInit();
        imgCar = find(R.id.img_car);
        imgCarStage = find(R.id.img_car_stage);
        imgCarLight = find(R.id.img_car_light);
        imgCarColorBar = find(R.id.img_car_color_bar);

    }

    @Override
    protected void createAnimator() {
        int carX1 = getXRightOut(imgCar);
        int carX2 = getXCenterCenter(imgCar);
        int carY1 = getYTopOut(imgCarLight);
        int carY3 = getYBottomOut(imgCarStage);
        final AnimationDrawable animationDrawable = (AnimationDrawable) imgCar.getBackground();
        animationDrawable.start();
        SDAnimSet animSet = SDAnimSet.from(imgCarStage)
                .moveToY(carY3, imgCarStage.getY()).setDuration(2000)
                .addListener(new SDAnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        notifyAnimationStart(animation);
                    }
                })
                .next(imgCar).moveToX(carX1, carX2).setDuration(2000).setDecelerate()
                .withClone().moveToY(0, imgCar.getY())
                .addListener(new SDAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        animationDrawable.stop();
                    }
                })
                .next(imgCarLight).moveToY(carY1, imgCarLight.getY()).setDuration(2000)
                .next(imgCarColorBar).moveToY(carY1, imgCarColorBar.getY()).setDuration(2000)
                .withClone().alpha(1.0f, 0f, 1.0f).setRepeatCount(1)
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
    protected void resetView() {

    }
}
